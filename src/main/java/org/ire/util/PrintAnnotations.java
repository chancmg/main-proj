package org.ire.util;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.FloatArrayFS;
import org.apache.uima.cas.IntArrayFS;
import org.apache.uima.cas.StringArrayFS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.ire.uima.tokenizer.Bigram;
import org.ire.uima.tokenizer.Capital;
import org.ire.uima.tokenizer.DocCount;
import org.ire.uima.tokenizer.POS;
import org.ire.uima.tokenizer.Punctuation;
import org.ire.uima.tokenizer.Trigram;
import org.ire.uima.tokenizer.URL;
import org.ire.uima.tokenizer.WordAnnot;

/**
 * A simple example of how to extract information from the CAS. This example
 * retrieves all annotations of a specified type from a CAS and prints them
 * (along with all of their features) to a PrintStream.
 * 
 * 
 */
public class PrintAnnotations {
	public static boolean isFirst = true;
	public static Set<String> positiveSet, negativeSet;

	/**
	 * Prints all Annotations to a PrintStream.
	 * 
	 * @param aCAS
	 *            the CAS containing the FeatureStructures to print
	 * @param aOut
	 *            the PrintStream to which output will be written
	 */
	public static void printAnnotations(CAS aCAS, PrintStream aOut) {
		// get iterator over annotations
		FSIterator iter = aCAS.getAnnotationIndex().iterator();

		// iterate
		while (iter.isValid()) {
			FeatureStructure fs = iter.get();
			System.out.println(fs.getClass());
			// printFS(fs, aCAS, 0, aOut);
			iter.moveToNext();
		}
	}

	public static void loadPositiveAndNegativeTextToMap() {

		positiveSet = new HashSet<String>();
		negativeSet = new HashSet<String>();

		try {
			File file = new File("posWords/positive.txt");
			String word;
			Scanner sc = new Scanner(file);
			while (sc.hasNext()) {
				word = sc.nextLine();
				if (!positiveSet.contains(word)) {
					positiveSet.add(word);
				}
			}
			sc.close();

			file = new File("posWords/negative.txt");
			sc = new Scanner(file);
			while (sc.hasNext()) {
				word = sc.nextLine();
				if (!negativeSet.contains(word)) {
					negativeSet.add(word);
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

	}

	/**
	 * Function to handle postive and negative word count
	 */
	public static void processPosNegCount(String input, String fileName) {
		FileWriter fw = null;
		// int i=0;
		try {
			System.out.println(fileName);
			// System.out.println("write");
			File f = new File("token-output/" + fileName);
			fw = new FileWriter(f, true);
			fw.write("\n POSITIVE_NEGATIVE" + ",");
			StringBuilder content = new StringBuilder();
			StringTokenizer t = new StringTokenizer(input, " \n\t");
			String word;
			while (t.hasMoreTokens()) {
				word = t.nextToken();
				if (positiveSet.contains(word.toLowerCase())) {
					// System.out.println("positive:"+word);
					content.append("P" + ",");

				}
				if (negativeSet.contains(word.toLowerCase())) {
					// System.out.println("negative:"+word);
					content.append("N" + ",");
				}
			}
			if (content.toString().length() > 0)
				fw.write(content.toString().substring(0,
						content.toString().length() - 1));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * Function to handle postive and negative word count
	 */
	public static void processDOCLENGTHCount(String input, String fileName) {
		FileWriter fw = null;
		// int i=0;
		try {
			System.out.println(fileName);
			// System.out.println("write");
			File f = new File("token-output/" + fileName);
			fw = new FileWriter(f, true);
			fw.write("\n LENGTH_OF_DOC" + ",");
			StringBuilder content = new StringBuilder();
			StringTokenizer t = new StringTokenizer(input, " \n\t");
			String word;
			while (t.hasMoreTokens()) {
				word = t.nextToken();
				
					content.append("doclen" + ",");
				
			}
			if (content.toString().length() > 0)
				fw.write(content.toString().substring(0,
						content.toString().length() - 1));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Prints all Annotations of a specified Type to a PrintStream.
	 * 
	 * @param aCAS
	 *            the CAS containing the FeatureStructures to print
	 * @param aAnnotType
	 *            the Type of Annotation to be printed
	 * @param aOut
	 *            the PrintStream to which output will be written
	 */
	public static void printAnnotations(CAS aCAS, Type aAnnotType,
			PrintStream aOut, String fileName, ClassType classType) {
		// get iterator over annotations
		FSIterator iter = aCAS.getAnnotationIndex(aAnnotType).iterator();

		// iterate
		FileWriter fw = null;
		// int i=0;
		try {
			System.out.println(fileName);
			// System.out.println("write");
			File f = new File("token-output/" + fileName);
			fw = new FileWriter(f, true);
			fw.write(fileName);
			fw.write("\n"+classType + ",");
			StringBuilder content = new StringBuilder();
			while (iter.isValid()) {
				FeatureStructure fs = iter.get();

				String text = printFeatures(fs, aCAS, 0, aOut, classType);
				if (text != null) {
					content.append(text);
					content.append(",");
					// fw.write(text);
					// fw.write(",");

				}

				iter.moveToNext();
			}
			if (content.toString().length() > 0)
				fw.write(content.toString().substring(0,
						content.toString().length() - 1));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Prints a FeatureStructure to a PrintStream.
	 * 
	 * @param aFS
	 *            the FeatureStructure to print
	 * @param aCAS
	 *            the CAS containing the FeatureStructure
	 * @param aNestingLevel
	 *            number of tabs to print before each line
	 * @param aOut
	 *            the PrintStream to which output will be written
	 */
	public static void printFS(FeatureStructure aFS, CAS aCAS,
			int aNestingLevel, PrintStream aOut) {
		Type stringType = aCAS.getTypeSystem().getType(CAS.TYPE_NAME_STRING);

		printTabs(aNestingLevel, aOut);
		aOut.println(aFS.getType().getName());

		// if it's an annotation, print the first 64 chars of its covered text
		if (aFS instanceof AnnotationFS) {
			AnnotationFS annot = (AnnotationFS) aFS;
			String coveredText = annot.getCoveredText();
			printTabs(aNestingLevel + 1, aOut);
			aOut.print("\"");
			if (coveredText.length() <= 64) {
				aOut.print(coveredText);
			} else {
				aOut.println(coveredText.substring(0, 64) + "...");
			}
			aOut.println("\"");
		}

		// print all features
		List aFeatures = aFS.getType().getFeatures();
		Iterator iter = aFeatures.iterator();
		while (iter.hasNext()) {
			Feature feat = (Feature) iter.next();
			printTabs(aNestingLevel + 1, aOut);
			// print feature name
			aOut.print(feat.getShortName());
			aOut.print(" = ");
			// prnt feature value (how we get this depends on feature's range
			// type)
			String rangeTypeName = feat.getRange().getName();
			if (aCAS.getTypeSystem().subsumes(stringType, feat.getRange())) // must
																			// check
																			// for
																			// subtypes
																			// of
																			// string
			{
				String str = aFS.getStringValue(feat);
				if (str == null) {
					aOut.println("null");
				} else {
					aOut.print("\"");
					if (str.length() > 64) {
						str = str.substring(0, 64) + "...";
					}
					aOut.print(str);
					aOut.println("\"");
				}
			} else if (CAS.TYPE_NAME_INTEGER.equals(rangeTypeName)) {
				aOut.println(aFS.getIntValue(feat));
			} else if (CAS.TYPE_NAME_FLOAT.equals(rangeTypeName)) {
				aOut.println(aFS.getFloatValue(feat));
			} else if (CAS.TYPE_NAME_STRING_ARRAY.equals(rangeTypeName)) {
				StringArrayFS arrayFS = (StringArrayFS) aFS
						.getFeatureValue(feat);
				if (arrayFS == null) {
					aOut.println("null");
				} else {
					String[] vals = arrayFS.toArray();
					aOut.print("[");
					for (int i = 0; i < vals.length - 1; i++) {
						aOut.print(vals[i]);
						aOut.print(',');
					}
					if (vals.length > 0) {
						aOut.print(vals[vals.length - 1]);
					}
					aOut.println("]\"");
				}
			} else if (CAS.TYPE_NAME_INTEGER_ARRAY.equals(rangeTypeName)) {
				IntArrayFS arrayFS = (IntArrayFS) aFS.getFeatureValue(feat);
				if (arrayFS == null) {
					aOut.println("null");
				} else {
					int[] vals = arrayFS.toArray();
					aOut.print("[");
					for (int i = 0; i < vals.length - 1; i++) {
						aOut.print(vals[i]);
						aOut.print(',');
					}
					if (vals.length > 0) {
						aOut.print(vals[vals.length - 1]);
					}
					aOut.println("]\"");
				}
			} else if (CAS.TYPE_NAME_FLOAT_ARRAY.equals(rangeTypeName)) {
				FloatArrayFS arrayFS = (FloatArrayFS) aFS.getFeatureValue(feat);
				if (arrayFS == null) {
					aOut.println("null");
				} else {
					float[] vals = arrayFS.toArray();
					aOut.print("[");
					for (int i = 0; i < vals.length - 1; i++) {
						aOut.print(vals[i]);
						aOut.print(',');
					}
					if (vals.length > 0) {
						aOut.print(vals[vals.length - 1]);
					}
					aOut.println("]\"");
				}
			} else // non-primitive type
			{
				FeatureStructure val = aFS.getFeatureValue(feat);
				if (val == null) {
					aOut.println("null");
				} else {
					printFS(val, aCAS, aNestingLevel + 1, aOut);
				}
			}
		}
	}

	public static String printFeatures(FeatureStructure aFS, CAS aCAS,
			int aNestingLevel, PrintStream aOut, ClassType classType) {

		// if it's an annotation, print the first 64 chars of its covered text
		String coveredText = null;
		if (aFS instanceof AnnotationFS) {
			AnnotationFS annot = (AnnotationFS) aFS;
			if (ClassType.UNIGRAM == classType && annot instanceof WordAnnot) {
				coveredText = annot.getCoveredText();
			} else if (ClassType.CAPITALIZE == classType
					&& annot instanceof Capital) {
				// System.out.println("capital");
				coveredText = annot.getCoveredText();
			} else if (ClassType.BIGRAM == classType && annot instanceof Bigram) {
				// System.out.println("capital");
				coveredText = annot.getCoveredText();
			} else if (ClassType.PUNCTUATION == classType
					&& annot instanceof Punctuation) {
				// System.out.println("capital");
				coveredText = annot.getCoveredText();
			} else if (ClassType.TRIGRAM == classType
					&& annot instanceof Trigram) {
				// System.out.println("capital");
				coveredText = annot.getCoveredText();
			} else if (ClassType.URL == classType && annot instanceof URL) {
				// System.out.println("capital");
				coveredText = annot.getCoveredText();
			}else if (ClassType.SENTENCECOUNT == classType && annot instanceof DocCount) {
				// System.out.println("capital");
				coveredText = annot.getCoveredText();
			}else if (ClassType.POSITIVE_NEGATIVE == classType && annot instanceof POS) {
				// System.out.println("capital");
				coveredText = annot.getCoveredText();
			}

		}
		return coveredText;

	}

	/**
	 * Prints tabs to a PrintStream.
	 * 
	 * @param aNumTabs
	 *            number of tabs to print
	 * @param aOut
	 *            the PrintStream to which output will be written
	 */
	private static void printTabs(int aNumTabs, PrintStream aOut) {
		for (int i = 0; i < aNumTabs; i++) {
			aOut.print("\t");
		}
	}

}
