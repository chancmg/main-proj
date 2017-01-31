package org.ire.weka;

//package greenblocks.statistics;

import java.io.*;
import java.util.*;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

public class ClusterKmean {

	private static final String FILE_TXT = "wordScore/file.txt";

	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;

		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}

		return inputReader;
	}

	public static void main(String[] args) throws Exception {
		SimpleKMeans kmeans = new SimpleKMeans();
		BufferedReader obj = new BufferedReader(
				new InputStreamReader(System.in));
		int k = Integer.parseInt(obj.readLine());
		int seed = Integer.parseInt(obj.readLine());
		ClusterKmean.clusterOriginalDoc(k, seed);
		/*kmeans.setSeed(seed);
		// important parameter to set: preserver order, number of cluster.
		kmeans.setPreserveInstancesOrder(true);
		kmeans.setNumClusters(k);
		// System.out.println(args[0]);
		BufferedReader datafile = readDataFile("wordScore/wordOut2.arff");
		Instances data = new Instances(datafile);

		kmeans.buildClusterer(data);

		// This array returns the cluster number (starting with 0) for each
		// instance
		// The array has as many elements as the number of instances
		int[] assignments = kmeans.getAssignments();
		System.out.println(kmeans.getCapabilities());
		int i = 0;
		String[] fileNames = new String[100000];
		int l = 0;
		try {
			File folder = new File("data");

			for (File file : folder.listFiles()) {
				fileNames[l++] = file.getName();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int j = 0; j < l; j++) {
			System.out.println(fileNames[j]);
		}
		File folder = new File("output");
		if (!folder.exists()) {

			folder.mkdirs();
		}
		for (int clusterNum : assignments) {
			File theDir = new File("output/cluster" + clusterNum);
			// if the directory does not exist, create it
			if (!theDir.exists()) {
				// System.out.println("creating directory: " + directoryName);
				boolean result = false;

				try {
					theDir.mkdir();
					result = true;
				} catch (SecurityException se) {
					// handle it
				}
				if (result) {
					System.out.println("DIR created");
				}
			}
			InputStream inStream = null;
			OutputStream outStream = null;

			try {

				File afile = new File("data/" + fileNames[i]);
				File bfile = new File("output/cluster" + clusterNum + "/"
						+ fileNames[i]);

				inStream = new FileInputStream(afile);
				outStream = new FileOutputStream(bfile);

				byte[] buffer = new byte[1024];

				int length;
				// copy the file content in bytes
				while ((length = inStream.read(buffer)) > 0) {

					outStream.write(buffer, 0, length);

				}
				inStream.close();
				outStream.close();
				// delete the original file
				// afiile.delete();
				System.out.println("File is copied successful!");

			} catch (IOException e) {
				// e.printStackTrace();
			}
			System.out.printf("Instance %d -> Cluster %d \n", i, clusterNum);
			i++;
		}*/
	}
	
	public static void clusterDoc(int k, int seed) throws Exception {
		SimpleKMeans kmeans = new SimpleKMeans();
		
		// important parameter to set: preserver order, number of cluster.
		kmeans.setPreserveInstancesOrder(true);
		kmeans.setNumClusters(k);
		// System.out.println(args[0]);
		BufferedReader datafile = readDataFile("wordScore/wordOut2.arff");
		Instances data = new Instances(datafile);

		kmeans.buildClusterer(data);

		// This array returns the cluster number (starting with 0) for each
		// instance
		// The array has as many elements as the number of instances
		int[] assignments = kmeans.getAssignments();
		System.out.println(kmeans.getCapabilities());
		int i = 0;
		String[] fileNames = new String[100000];
		int l = 0;
		try {
			File folder = new File("data");

			for (File file : folder.listFiles()) {
				fileNames[l++] = file.getName();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int j = 0; j < l; j++) {
			System.out.println(fileNames[j]);
		}
		File folder = new File("output");
		if (!folder.exists()) {

			folder.mkdirs();
		}
		for (int clusterNum : assignments) {
			File theDir = new File("output/cluster" + clusterNum);
			// if the directory does not exist, create it
			if (!theDir.exists()) {
				// System.out.println("creating directory: " + directoryName);
				boolean result = false;

				try {
					theDir.mkdir();
					result = true;
				} catch (SecurityException se) {
					// handle it
				}
				if (result) {
					System.out.println("DIR created");
				}
			}
			InputStream inStream = null;
			OutputStream outStream = null;

			try {

				File afile = new File("data/" + fileNames[i]);
				File bfile = new File("output/cluster" + clusterNum + "/"
						+ fileNames[i]);

				inStream = new FileInputStream(afile);
				outStream = new FileOutputStream(bfile);

				byte[] buffer = new byte[1024];

				int length;
				// copy the file content in bytes
				while ((length = inStream.read(buffer)) > 0) {

					outStream.write(buffer, 0, length);

				}
				inStream.close();
				outStream.close();
				// delete the original file
				// afiile.delete();
				System.out.println("File is copied successful!");

			} catch (IOException e) {
				// e.printStackTrace();
			}
			System.out.printf("Instance %d -> Cluster %d \n", i, clusterNum);
			i++;
		}
	}
	
	public static void clusterOriginalDoc(int k,int seed) throws Exception {
		SimpleKMeans kmeans = new SimpleKMeans();
		
		kmeans.setSeed(seed);
		// important parameter to set: preserver order, number of cluster.
		kmeans.setPreserveInstancesOrder(true);
		kmeans.setNumClusters(k);
		// System.out.println(args[0]);
		BufferedReader datafile = readDataFile("wordScore/wordOut2.arff");
		Instances data = new Instances(datafile);

		kmeans.buildClusterer(data);

		// This array returns the cluster number (starting with 0) for each
		// instance
		// The array has as many elements as the number of instances
		int[] assignments = kmeans.getAssignments();
		System.out.println(kmeans.getCapabilities());
		int i = 0;
		String[] fileNames = new String[100000];
		int l = 0;
		try {
			File folder = new File("rawwsdl");

			for (File file : folder.listFiles()) {
				fileNames[l++] = file.getName();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int j = 0; j < l; j++) {
			System.out.println(fileNames[j]);
		}
		File folder = new File("outputRaw");
		if (!folder.exists()) {

			folder.mkdirs();
		}
		for (int clusterNum : assignments) {
			File theDir = new File("outputRaw/cluster" + clusterNum);
			// if the directory does not exist, create it
			if (!theDir.exists()) {
				// System.out.println("creating directory: " + directoryName);
				boolean result = false;

				try {
					theDir.mkdir();
					result = true;
				} catch (SecurityException se) {
					// handle it
				}
				if (result) {
					System.out.println("DIR created");
				}
			}
			InputStream inStream = null;
			OutputStream outStream = null;

			try {

				File afile = new File("rawwsdl/" + fileNames[i]);
				File bfile = new File("outputRaw/cluster" + clusterNum + "/"
						+ fileNames[i]);

				inStream = new FileInputStream(afile);
				outStream = new FileOutputStream(bfile);

				byte[] buffer = new byte[1024];

				int length;
				// copy the file content in bytes
				while ((length = inStream.read(buffer)) > 0) {

					outStream.write(buffer, 0, length);

				}
				inStream.close();
				outStream.close();
				// delete the original file
				// afiile.delete();
				System.out.println("File is copied successful!");

			} catch (IOException e) {
				// e.printStackTrace();
			}
			System.out.printf("Instance %d -> Cluster %d \n", i, clusterNum);
			i++;
		}
	}
}
