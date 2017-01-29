package org.ire.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import org.ire.uima.tika.TikaExtraction;

public class ScoreCalculator {
	private TreeMap<String, HashMap<String, Integer>> wordSet;
	private static final String OUTPUT_PATH = "wordScore/word.txt";
	private static final String OUTPUT_PATH1 = "wordScore/word1.txt";
	private static final String INPUTFOLDER = "token-output";
	private PrintWriter pr,pr1;
	private List<String> fileNames;

	public ScoreCalculator() {
		wordSet = new TreeMap<String, HashMap<String, Integer>>();
		fileNames = new ArrayList<String>();
		try {
			pr = new PrintWriter(new File(OUTPUT_PATH));
			pr1 = new PrintWriter(new File(OUTPUT_PATH1));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void process() {
		File files = new File(INPUTFOLDER);
		String inpFiles[] = files.list();

		for (int i = 0; i < inpFiles.length; i++) {
			System.out.println("calculating frequency of " + inpFiles[i]);
			extractWordScoreFromFile(inpFiles[i]);
		}
	}

	public void extractWordScoreFromFile(String file) {
		try {
			Scanner sc = new Scanner(new File(INPUTFOLDER +"/"+ file));

			String actualFileName = null, data = null;
			if (sc.hasNext()) {
				actualFileName = sc.nextLine();
				fileNames.add(actualFileName);
			}
			while (sc.hasNext()){
				data = sc.nextLine();
			if (data != null) {
				String dataWord[] = data.split(",");
				HashMap<String, Integer> temp;
				for (int i = 1; i < dataWord.length; i++) {
					// System.out.println(dataWord[i]);
					if (wordSet.containsKey(dataWord[i])) {
						if (wordSet.get(dataWord[i])
								.containsKey(actualFileName)) {
							wordSet.get(dataWord[i]).put(
									actualFileName,
									wordSet.get(dataWord[i])
											.get(actualFileName) + 1);

						} else {
							wordSet.get(dataWord[i]).put(actualFileName, 1);
						}
					} else {
						HashMap<String, Integer> value = new HashMap<String, Integer>();
						value.put(actualFileName, 1);
						wordSet.put(dataWord[i], value);
					}
				}
			  }
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void printResult() {
		System.out.println("-------result----------");
		System.out.print("FileNames,");

		for (String file : fileNames) {
			System.out.print(file + ",");
		}
		System.out.println();
		for (Map.Entry<String, HashMap<String, Integer>> entry : wordSet
				.entrySet()) {
			System.out.print(entry.getKey());
			System.out.print(",");
			for (String file : fileNames) {
				if (entry.getValue().containsKey(file)) {
					System.out.print(entry.getValue().get(file));
				} else
					System.out.print("0");
				System.out.print(",");
			}
			System.out.println();
		}
	}

	public void printOutPut() {
		System.out.println("-------result----------");
		System.out.print("FileNames,");

		for (String file : fileNames) {
			System.out.print(file + ",");
		}
		System.out.println();
		for (Map.Entry<String, HashMap<String, Integer>> entry : wordSet
				.entrySet()) {
			System.out.print(entry.getKey());
			System.out.print(",");
			for (String file : fileNames) {
				if (entry.getValue().containsKey(file)) {
					System.out.print(entry.getValue().get(file));
				} else
					System.out.print("0");
				System.out.print(",");
			}
			System.out.println();
		}
	}
	
	public void printMatrix() {
		int rowsize=fileNames.size()+1;
		int colsize=wordSet.size()+1;
		String[][] mat= new String[rowsize][colsize];
		mat[0][0]="FileNames";
		int row=1;
		for (String file : fileNames) {
			mat[row][0]=TikaExtraction.getConvertedFileMap().get(file);
			row++;
		}
		int col=1;
		for (Map.Entry<String, HashMap<String, Integer>> entry : wordSet
				.entrySet()) {
			mat[0][col]=entry.getKey();
			col++;
		}
		
	  
		col=1;
		for (Map.Entry<String, HashMap<String, Integer>> entry : wordSet
				.entrySet()) {
			
			for (String file : fileNames) {
				
				int rownum=getFileNameRowFromMatrix(mat,TikaExtraction.getConvertedFileMap().get(file),rowsize);
				if (entry.getValue().containsKey(file)) {
					mat[rownum][col]=entry.getValue().get(file)+"";
				} else
					mat[rownum][col]="0";
				
			}
			col++;
		}
		
		for(int i=0;i<rowsize;i++)
		{
			for(int j=0;j<colsize;j++){
				
				//System.out.print(mat[i][j]+ ",");
				
				if(j==colsize-1){
					pr.print(mat[i][j]);
					break;
				}else{
					pr.print(mat[i][j]+",");
				}
				
			}
			pr.print("\n");
			//System.out.println("");
		}
	}

	private int getFileNameRowFromMatrix(String[][] mat, String file,int row) {
		for(int i=1;i<row;i++){
			if(mat[i][0].equalsIgnoreCase(file)){
				return i;
			}
		}
		return 0;
	}

	public void close() {
		pr.close();
	}

/*	public static void main(String[] args) {
		ScoreCalculator sc = new ScoreCalculator();
		sc.process();
		sc.printMatrix();
		sc.close();
	}*/
}
