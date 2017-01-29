package org.ire.uima.tika;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

public class TikaExtraction {

	private static File out;
	private static PrintWriter writer;
	private static Map<String,String>convertedFileMap=new HashMap<String,String>();
	

	private static void extractAndSaveInFile(String extractedDir,File file) {
		Tika tika = new Tika();
		try {
			String filecontent = tika.parseToString(file);
			out = new File(extractedDir+File.separator+FilenameUtils.getBaseName(file.getName())+ ".txt");
			convertedFileMap.put(FilenameUtils.getBaseName(file.getName())+ ".txt",file.getName());
			//System.out.println(convertedFileMap.get(FilenameUtils.getBaseName(file.getName())+ ".txt"));
			writer = new PrintWriter(out);
			writer.println(out.getName());
			writer.println(filecontent);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TikaException e) {
			e.printStackTrace();
		}

	}

	public static void extractTextFromRawData(String rawDataDir,String extractedDir) {
		File folder = new File(rawDataDir);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			System.out.println("extracting data from " + listOfFiles[i] + " using tika");
			extractAndSaveInFile(extractedDir,listOfFiles[i]);
			System.out.println("------------------------------------------");
		}
	}
	
	public static Map<String,String>getConvertedFileMap(){
		return convertedFileMap;
	}
	
	


}
