package org.ire.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.attribute.AclEntry.Builder;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.ire.uima.tokenizer.Punctuation;

public class FileExtractor {
	
	private static final String SPACE = " ";
	private static final String STOPWORD = "resources/stopwords.txt";
	private String file;
	private Scanner sc;
	private String document;
	private Set<String> stopWordSet;
	private File input;
	
	public FileExtractor() {
		
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
	
	public boolean isNotNum(String inp){
		try  
		  {  
		    double d = Double.parseDouble(inp);  
		  }  
		  catch(NumberFormatException nfe)  
		  {  
		    return true;  
		  }  
		  return false;  
	}
	
	public String getDocument(String inputTextFile,ClassType classType){
		StringBuilder builder = null;
		try {
			File input = new File("data/"+inputTextFile);
			sc = new Scanner(input);
			String line,words[];
			builder = new StringBuilder();
			while(sc.hasNext()){
				line=sc.nextLine();
				line=line.trim();
				words=line.split(SPACE);
				for(int i=0;i<words.length;i++){
					//words[i]=words[i].toLowerCase();
					if(!stopWordSet.contains(words[i].toLowerCase()) && isNotNum(words[i])){
						if(classType != ClassType.PUNCTUATION && classType != ClassType.URL)
							words[i]=words[i].replaceAll("\\W", "");
						else{
							words[i]=words[i].replaceAll("\\s+", "");
							words[i]=words[i].replaceAll(",", "@");
						}
						System.out.print(words[i]+SPACE);;
						builder.append(words[i]);
						builder.append(SPACE);
					}
				}
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return builder.toString();
	}
}
