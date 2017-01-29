package org.ire.visualization;
// This program prompts the user for the name of a file and then counts the
// occurrences of words in the file (ignoring case).  It then reports the
// frequencies using a cutoff supplied by the user that limits the output
// to just those words with a certain minimum frequency.

import java.util.*;
import java.io.*;
//import java.

//import org.tagcloud.TagCloud;

public class WordCount {
	private static final String STOPWORD = "resources/stopwords.txt";
	private static Set<String> stopWordSet;
	private static Scanner sc;
	public static int clusternum =0;
	public static void WordCountinit(){
		stopWordSet = new HashSet<String>();
		System.out.println("Loading Stopwords..");
		try {
			sc = new Scanner(new File(STOPWORD));
			String temp;
			while(sc.hasNext()){
				temp=sc.nextLine();
				if(!stopWordSet.contains(temp))
					stopWordSet.add(temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		sc.close();
	}
	public static void init(String clusterName) throws IOException{
		// open the file
		Scanner console = new Scanner(System.in);
		Map<String, Integer> wordCounts = new TreeMap<String, Integer>();
		
		String fileName = clusterName;
		String[] listFiles = fileName.split(" ");
		for (String folders : listFiles) {
			File dir = new File("output/"+folders);
			File[] directoryListing = dir.listFiles();
			System.out.println(folders);                
			if (directoryListing != null) {
				for (File child : directoryListing) {
					Scanner input = new Scanner(child);
					// count occurrences
					while (input.hasNext()) {
						String next = input.next().toLowerCase();
						while(next.charAt(next.length()-1)=='.' || next.charAt(next.length()-1)==')' || next.charAt(next.length()-1)==',' || next.charAt(next.length()-1)=='"') {
							next = next.replace(next.substring(next.length()-1), "");
							if (next.length() == 0)
								break;
						}
						if (next.length() != 0) {
							while (next.charAt(0) == '.' || next.charAt(0)=='(' || next.charAt(0)==',' || next.charAt(0)=='"') {
								next = next.replace(next.substring(0), "");
								if (next.length() == 0)
									break;
							}
						}
						if(stopWordSet.contains(next)){
							continue;
						}
						if(!next.matches("\\b\\w+.?\\w*\\b")){
							continue;
						}
						if (next.length() == 0)
							continue;
					
						//String[] words = next.split(",");
						//for (int i = 0;i < words.length;i++) {
						if (!wordCounts.containsKey(next)) {
							wordCounts.put(next, 1);
						}
						else {
							wordCounts.put(next, wordCounts.get(next) + 1);
						}
					}
				}
			}
			else {
				//Not a directory
				System.out.println("Not a Directory!");
			}
		}
		// get cutoff and report frequencies
		System.out.println("Total words = " + wordCounts.size());
		//System.out.print("Minimum number of occurrences for printing? ");
		int min = 1;//console.nextInt();TODO:Need to make dynamic
		File file = new File("wordScore/WordFrequency.txt");
		if (!file.exists())
			file.createNewFile();
		FileWriter writer = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(writer);
		//writer = new PrintWriter("WordFrequency.txt", "UTF-8");
		for (String word : wordCounts.keySet()) {
			int count = wordCounts.get(word);
			if (count >= min)
				writer.write(word+","+count+"\n");
			//System.out.println(count + "\t" + word);
		}
		writer.close();
		console.close();
	} 
  public static void invoke(String clusterName,String[] args) {
	try {
		WordCountinit();
		init(clusterName);
		//TagCloud wordCloud = new TagCloud();
		//wordCloud.main(args);
	//	TagCloud.main(args);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
  public static void main(String[] args) {
	    //clusternum=0;
	 	invoke("cluster0", null);
	 	System.out.println("cluster haha"+clusternum);
	}
  
  public static boolean generate() {
	    //clusternum=0;
	 	invoke("cluster0", null);
	 	System.out.println("cluster haha"+clusternum);
	 	return true;
	}
}
