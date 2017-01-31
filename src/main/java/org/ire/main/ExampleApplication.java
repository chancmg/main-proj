package org.ire.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.util.XMLInputSource;
import org.ire.uima.tika.TikaExtraction;
import org.ire.util.ClassType;
import org.ire.util.FileExtractor;
import org.ire.util.PrintAnnotations;
import org.ire.util.ScoreCalculator;
import org.ire.weka.ClusterKmean;
import org.ire.weka.WekaMatrix;


public class ExampleApplication {

	private static final String CAPITALIZE_ANNOTATOR = "analysis_engine/CapitalAnnotator.xml";
	private static final String UNIGRAM_ANNOTATOR = "analysis_engine/SimpleTokenAndSentenceAnnotator.xml";
	private static final String BIGRAM_ANNOTATOR ="analysis_engine/BigramAnnotator.xml";
	private static final String PUNCTUATION_ANNOTATOR ="analysis_engine/PunctuationAnnotator.xml";
	private static final String TRIGRAM_ANNOTATOR ="analysis_engine/TrigramAnnotator.xml";
	private static final String URL_ANNOTATOR ="analysis_engine/UrlAnnotator.xml";
	private static final String SENTENCE_ANNOTATOR ="analysis_engine/DocCountAnnotator.xml";
	private static final String POS_ANNOTATOR ="analysis_engine/POSAnnotator.xml";
	private static final String INPUTDIRNAME="rawdata";
	private static final String EXTRACTEDDIRNAME="data";
	private static FileExtractor extractor;
	
	public static String getAnnotator(String value) {
		ClassType input=ClassType.values()[Integer.parseInt(value)];
		switch(input){
		case UNIGRAM: return UNIGRAM_ANNOTATOR;
		
		case BIGRAM: return BIGRAM_ANNOTATOR;
		
		case TRIGRAM: return TRIGRAM_ANNOTATOR;
		
		case CAPITALIZE: return CAPITALIZE_ANNOTATOR;
		
		case SENTENCECOUNT: return SENTENCE_ANNOTATOR;
		
		case PUNCTUATION: return PUNCTUATION_ANNOTATOR;
		
		case URL: return URL_ANNOTATOR;
		
		case POSITIVE_NEGATIVE: return UNIGRAM_ANNOTATOR;
		
		case LENGTH_OF_DOC: return UNIGRAM_ANNOTATOR;
		default: return UNIGRAM_ANNOTATOR; 	
			
		}
	}

	public static ClassType getClassType(String value) {
		return ClassType.values()[Integer.parseInt(value)];
	}

	public void run() {
		try {
			File taeDescriptor = null;

			//File inputDir = null;
			File dir = new File("token-output");
			for(File file: dir.listFiles()){
				file.delete();
				file.createNewFile();
			}
			TikaExtraction.extractTextFromRawData(INPUTDIRNAME,EXTRACTEDDIRNAME);

			File extractedDir = null;
            File inputDir = null;
            inputDir = new File(INPUTDIRNAME);
            extractedDir = new File(EXTRACTEDDIRNAME);
            PrintAnnotations.loadPositiveAndNegativeTextToMap();
        

			/*System.out
					.println("Enter the features 1. unigram \n 2. bigram \n 3. trigram \n"
							+ " 4.Capitalize \n 5.Senetence \n 6.Punctuation \n 7. url \n 8. positive and negative words");
			Scanner scanner = new Scanner(System.in);*/
			String inp ="1";
			String featureList[] = inp.split(",");
			extractor = new FileExtractor();
			for (String feature1 : featureList) {

				String annotator = getAnnotator(feature1);
				ClassType featureClass = getClassType(feature1);
				System.out.println(annotator);
				taeDescriptor = new File(annotator);
								
				// get Resource Specifier from XML file
				XMLInputSource in = new XMLInputSource(taeDescriptor);
				ResourceSpecifier specifier = UIMAFramework.getXMLParser()
						.parseResourceSpecifier(in);
				
				// create Analysis Engine
				AnalysisEngine ae = UIMAFramework
						.produceAnalysisEngine(specifier);
				// create a CAS
				CAS cas = ae.newCAS();
			
				
				File[] files = extractedDir.listFiles();
				if (files == null) {
					System.out.println("No files to process");
				} else {
					 //converting documents

					for (int i = 0; i < files.length; i++) {
						if (!files[i].isDirectory()) {
						   
							processFile(files[i], ae, cas,featureClass);
						}
					}
				}
				ae.destroy();
			}

			System.out.println("----------Calculating score---------");

			ScoreCalculator sc = new ScoreCalculator();
			sc.process();
			sc.printMatrix();
			sc.close();
			System.out.println("----------Score calculation completed---------");
			WekaMatrix.calculateMatrix();
			System.out.println("----------Cluster Analysis completed---------");
			System.out.println("enter number of cluster and seed");
			BufferedReader obj = new BufferedReader(
					new InputStreamReader(System.in));
			String inp1 = obj.readLine();
			String inp2[]=inp1.split(" ");
			int k = Integer.parseInt(inp2[0]);
			int seed = Integer.parseInt(inp2[1]);
			ClusterKmean.clusterDoc(k, seed);
			ClusterKmean.clusterOriginalDoc(k, seed);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

	/**
	 * Processes a single XML file and prints annotations to System.out
	 * 
	 * @param aFile
	 *            file to process
	 * @param aAE
	 *            Analysis Engine that will process the file
	 * @param aCAS
	 *            CAS that will be used to hold analysis results
	 */
	private static void processFile(File aFile, AnalysisEngine aAE, CAS aCAS,ClassType classType)
			throws IOException, AnalysisEngineProcessException {

		String fileName = aFile.getName();
		System.out.println("Processing file " + aFile.getName());

		// String document = FileUtils.file2String(aFile);

		String document = extractor.getDocument(aFile.getName(),classType);
		
		
		if(classType == ClassType.LENGTH_OF_DOC){
			classType = ClassType.UNIGRAM;
		}

		// document = document.trim();

		// put document text in CAS
		aCAS.setDocumentText(document);

		// process
		aAE.process(aCAS);
		
		// print annotations to System.out
		PrintAnnotations.printAnnotations(aCAS, aCAS.getAnnotationType(),
				System.out, fileName,classType);

		// reset the CAS to prepare it for processing the next document
		aCAS.reset();
	}

}
