package org.ire.visualization;


import java.util.*;
import java.io.*;

import org.apache.commons.io.FilenameUtils;

//import org.ire.uima.bargraph.ValueComparator;

public class CreateBarGraph{
	public static String clusterInput;
	public static String featureName;
	public static TreeMap<String, Integer> SortByValue 
	(HashMap<String, Integer> map) {
		System.out.println("MAP HERE");
		ValueComparator vc =  new ValueComparator(map);
		TreeMap<String,Integer> sortedMap = new TreeMap<String,Integer>(vc);
		for(String key: map.keySet())
		{
			sortedMap.put(key,map.get(key));
		}
		return sortedMap;
	}
	
	public static void init(String[] clusterNumbers,String[] featureArr) throws IOException {
		
		
		Map<String, Integer> clusterFiles = new HashMap<String, Integer>();
		for (String clusterName: clusterNumbers) {
			File clusterDir = new File("output/"+clusterName);
			File[] allFiles = clusterDir.listFiles();
			System.out.println(allFiles.length);
			for (File aFile : allFiles) {
				System.out.println(aFile.toString());
				System.out.println(FilenameUtils.getBaseName(aFile.getName())+".txt");
				if (!clusterFiles.containsKey(FilenameUtils.getBaseName(aFile.getName())+".txt")) {
					clusterFiles.put(FilenameUtils.getBaseName(aFile.getName())+".txt", 1);
				}
			}
		}
		
	
		/*for (int i = 0;i < featureArr.length;i++) {
			if (!featureList.containsKey(featureArr[i])) {
				featureList.put(featureArr[i], 1);
			}
			else 
				featureList.put(featureArr[i], featureList.get(featureArr[i])+1);
		}*/
//		HashMap<String,Integer> mymap = new HashMap<String,Integer>();
//		CreateBarGraph cbg = new CreateBarGraph();
//		Comparator<HashMap<String,Integer>> bvc =  new Comparator<HashMap<String,Integer> >();
		HashMap<String, Integer> wordFrequency = new HashMap<String, Integer>();
		for (int k = 0;k < featureArr.length;k++) {
			ArrayList<String> allFeatures = new ArrayList<String>();
			File directory = new File("token-output/");
			File[] dirList = directory.listFiles();
			for (File children : dirList) {
				System.out.println(FilenameUtils.getBaseName(children.getName())+".txt");
				for (String key : clusterFiles.keySet()) {
				    System.out.print(key+" ");
				}
				if (clusterFiles.containsKey(FilenameUtils.getBaseName(children.getName())+".txt")) {
					Scanner input = new Scanner(children);
					String title = input.next();
					System.out.println(title);
					while (input.hasNext()) {
						String nextFeature = input.nextLine().toLowerCase();
						String[] wordList = nextFeature.split(",");
						System.out.println(wordList[0]);
						System.out.println(featureArr[k]);
						if (wordList[0].equals(featureArr[k])) {
							System.out.println("yes");
							for (int j = 1;j < wordList.length;j++) {
								if (!wordFrequency.containsKey(wordList[j]))
									wordFrequency.put(wordList[j],1);
								else
									wordFrequency.put(wordList[j], wordFrequency.get(wordList[j])+1);
								allFeatures.add(wordList[j]);
								//System.out.println(wordList[j]);
							}
							System.out.println(allFeatures.size());
						}
					}
				}
				else
					System.out.println("File not found");
			}
			System.out.println("Total words = " + wordFrequency.size());
			TreeMap<String, Integer> sortedMap = SortByValue(wordFrequency);
			System.out.println(sortedMap.size()+"heycheckthisout");
			File file = new File("InputForBar.txt");
			int disp = 10;
			if (!file.exists())
				file.createNewFile();
			FileWriter writer = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(writer);
			for (String word : sortedMap.keySet()) {
				if (disp == 0)
					break;
				//System.out.println(word);
				//System.out.println(sortedMap.get(word));
				int count = wordFrequency.get(word);
				writer.write(word+","+count+"\n");
				System.out.println(count + "\t" + word);
				disp--;
			}
			writer.close();
//			File dir = new File("outputRaw/");
//			File[] clusterArray = dir.listFiles();
//			for (int i = 0;i < clusterArray.length;i++) {
//				File[] fileList = clusterArray[i].listFiles();
//				for (File child : fileList) {
//
//				}
//			}
		}
	}
}

class ValueComparator implements Comparator<String> {
	 
    Map<String, Integer> map;
 
    public ValueComparator(Map<String, Integer> base) {
        this.map = base;
    }
 
    public int compare(String a, String b) {
        if (map.get(a) >= map.get(b)) {
            return -1;
        } else {
            return 1;
        } 
    }
}

