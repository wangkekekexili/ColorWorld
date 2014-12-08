package colorworld;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;
import java.util.TreeMap;

import colorworld.image.ColorCoherentVector;
import colorworld.image.ColorCoherentVectorLibrary;
import colorworld.image.ColorHistogramDistance;
import colorworld.image.ColorHistogramLibrary;
import colorworld.image.HSVHistogram;
import colorworld.image.RGBHistogram;
import colorworld.utilities.Description;
import colorworld.utilities.DistancePair;
import colorworld.utilities.btree.BTree;
import colorworld.utilities.ktree.KeywordTree;


/**
 * 
 * @author Ke Wang
 *
 */
public class ColorWorld {

	private final int TOP = 4; // top 4 results are returned if there are multiple results
	
	private BTree btree;
	private KeywordTree ktree;
	private ColorHistogramLibrary chl;
	private ColorCoherentVectorLibrary ccvl;
	
	public ColorWorld() throws IOException{
		System.out.println("Please wait while ColorWorld is initializing...");
		
		initialization();
		
		System.out.println("Welcome to ColorWorld - a color-based image indexing and retrieval system.");
		System.out.println("Made by Ke and Qichao.");
		System.out.println("Enter 'help' for more information.");
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print("> ");
			if (sc.hasNextLine()) {
				int result = this.parseCommand(sc.nextLine());
			}
		}
	}
	
	private void initialization(){
		// B+ tree
		this.btree = new BTree();
		try {
			btree.initializeWithTestDataset();
		} catch (FileNotFoundException e) {
			System.exit(1);
		}
		// keyword tree
		this.ktree = new KeywordTree();
		try {
			ktree.initializeWithTestDataset();
		} catch (FileNotFoundException e) {
			System.exit(1);
		}
		// color histogram library
		this.chl = new ColorHistogramLibrary();
		try {
			chl.initializeWithTestDataset();
		} catch (FileNotFoundException e) {
			System.exit(1);
		}
		// ccv library
		this.ccvl = new ColorCoherentVectorLibrary();
		try {
			ccvl.initializeWithTestDataset();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void help() {
		System.out.println("You can input the following commands:");
		System.out.println();
		System.out.println("help\tlist the commands");
		System.out.println();
		System.out.println("exit\texit the program");
		System.out.println();
		System.out.println();
		System.out.println("search by (color, keyword, image) STR");
		//System.out.println("\tsearch by color RED");
		System.out.println("\tsearch by keyword Shanghai");
		System.out.println("\tsearch by image input/hello.jpg");
		System.out.println();
		System.out.println("vote (up, down) image_id keyword");
		System.out.println("\tvote up 37 tree");
		System.out.println();
		System.out.println("add image_file_path description_file_path");
		System.out.println();
	}
	
	private int parseCommand(String command) throws IOException {
		command = command.toLowerCase();
		if (command.startsWith("exit")) {
			System.out.println("Good Bye!");
			System.exit(0);
		} else if (command.startsWith("help")) {
			this.help();
		} else if (command.startsWith("search")) {
			this.parseSearch(command);
		} else if (command.startsWith("vote")) {
			this.parseVote(command);
		} else if (command.startsWith("add")) {
			this.parseAdd(command);
		} else {
			this.help();
		}
		return 0;
	}
	
	private int parseSearch(String command) throws IOException {
		
		String method; // color, keyword, or image
		
		int beginIndex = "search by ".length();
		int endIndex;
		while (beginIndex < command.length() && command.charAt(beginIndex)==' '){
			beginIndex++;
		}
		endIndex = beginIndex;
		while (endIndex < command.length() && command.charAt(endIndex)!=' '){
			endIndex++;
		}
		method = command.substring(beginIndex, endIndex);
				
		if (method.equals("keyword")) {
			String keyword;
			beginIndex = endIndex;
			while (beginIndex < command.length() && command.charAt(beginIndex)==' '){
				beginIndex++;
			}
			endIndex = beginIndex;
			while (endIndex < command.length() && command.charAt(endIndex)!=' '){
				endIndex++;
			}
			keyword = command.substring(beginIndex, endIndex);
			Map<Integer, Integer> result = this.ktree.search(keyword);
			
			if (result == null || result.size() == 0) {
				System.out.println("No images found");
				return 1;
			}
			
			TreeMap<Integer, Integer> sortedResult = KeywordTree.sortResult(result);
			
			try {
				this.generateHTMLResult(command, keyword, sortedResult);
			} catch (IOException e) {
				System.out.println("HTML result generation error");
				System.out.println("Plain result follows:");
				System.out.println(sortedResult);
			}
			
		} else if (method.equals("image")) {
			// for example: search by image input/hello.jpg
			String searchImageFilePath;
			beginIndex = endIndex;
			while (beginIndex < command.length() && command.charAt(beginIndex)==' '){
				beginIndex++;
			}
			endIndex = beginIndex;
			while (endIndex < command.length() && command.charAt(endIndex)!=' '){
				endIndex++;
			}
			searchImageFilePath = command.substring(beginIndex, endIndex);
			
			File searchImageFile = new File(searchImageFilePath);
			if (searchImageFile.exists() == false) {
				System.out.println("File not found");
				return 1;
			} 
			
			RGBHistogram rgbHistogram = new RGBHistogram();
			rgbHistogram.loadImage(searchImageFilePath);
			HSVHistogram hsvHistogram = new HSVHistogram();
			hsvHistogram.loadImage(searchImageFilePath);
			ColorCoherentVector ccv = new ColorCoherentVector();
			ccv.loadImage(searchImageFilePath);
			
			ArrayList<DistancePair> resultRGBEuclidean = this.chl.compare(rgbHistogram, ColorHistogramDistance.EUCLIDEAN);
			ArrayList<DistancePair> resultRGBIntersection = this.chl.compare(rgbHistogram, ColorHistogramDistance.INTERSECTION);
			ArrayList<DistancePair> resultRGBQuadratic = this.chl.compare(rgbHistogram, ColorHistogramDistance.QUADRATIC);
			ArrayList<DistancePair> resultHSVEuclidean = this.chl.compare(hsvHistogram, ColorHistogramDistance.EUCLIDEAN);
			ArrayList<DistancePair> resultHSVIntersection = this.chl.compare(hsvHistogram, ColorHistogramDistance.INTERSECTION);
			ArrayList<DistancePair> resultCCV = this.ccvl.compare(ccv);
			
			this.generateHTMLResult(searchImageFilePath, command, 
					resultRGBEuclidean, resultRGBIntersection, resultRGBQuadratic, 
					resultHSVEuclidean, resultHSVIntersection, 
					resultCCV);
			
		}
		
		return 0;
	}
	
	private void generateHTMLResult(String searchImageFilePath, String command,
			ArrayList<DistancePair> resultRGBEuclidean,
			ArrayList<DistancePair> resultRGBIntersection,
			ArrayList<DistancePair> resultRGBQuadratic,
			ArrayList<DistancePair> resultHSVEuclidean,
			ArrayList<DistancePair> resultHSVIntersection,
			ArrayList<DistancePair> resultCCV) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		sb.append(System.currentTimeMillis());
		sb.append(".html");
		
		File htmlResult = new File(sb.toString());
		if (htmlResult.exists() == false) {
			htmlResult.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(htmlResult));
		
		bw.write("<!DOCTYPE html><html><head><title>");
		bw.write(command);
		bw.write("</title></head><body>");
		bw.write("<h1>");
		bw.write(command);
		bw.write("</h1>");
		bw.write("<img src=\""+searchImageFilePath+"\" stype=\"width:240px;height:180px\">");
		bw.write("<br>");
		
		int count = 0;
		
		// RGB euclidean
		bw.write("<h2>");
		bw.write("RGB Color Histogram, Euclidean Distance");
		bw.write("</h2>");
		count = 0;
		for(DistancePair p : resultRGBEuclidean) {
			count++;
			  
			int id = p.getID();
			double distance = p.getDistance();

			bw.write("<p>");
			bw.write("image id: "+id+"\t"+"distance: "+distance);
			bw.write("</p>");
			
			//<img src="pic_mountain.jpg" alt="Mountain View" style="width:240px;height:180px>">
			bw.write("<img src=\"data/");
			bw.write(""+id+".jpg\" style=\"width:240px;height:180px\">");
			bw.write("<br>");
			
			if (count==TOP) {
				break;
			}
		}
		
		// RGB intersection
		bw.write("<h2>");
		bw.write("RGB Color Histogram, Intersection Distance");
		bw.write("</h2>");
		count = 0;
		for(DistancePair p : resultRGBIntersection) {
			count++;
			  
			int id = p.getID();
			double distance = p.getDistance();

			bw.write("<p>");
			bw.write("image id: "+id+"\t"+"distance: "+distance);
			bw.write("</p>");
			
			//<img src="pic_mountain.jpg" alt="Mountain View" style="width:240px;height:180px>">
			bw.write("<img src=\"data/");
			bw.write(""+id+".jpg\" style=\"width:240px;height:180px\">");
			bw.write("<br>");
			
			if (count==TOP) {
				break;
			}
		}		
		
		
		// RGB quadratic
		bw.write("<h2>");
		bw.write("RGB Color Histogram, Quadratic Distance");
		bw.write("</h2>");
		count = 0;
		for(DistancePair p : resultRGBQuadratic) {
			count++;
			  
			int id = p.getID();
			double distance = p.getDistance();

			bw.write("<p>");
			bw.write("image id: "+id+"\t"+"distance: "+distance);
			bw.write("</p>");
			
			//<img src="pic_mountain.jpg" alt="Mountain View" style="width:240px;height:180px>">
			bw.write("<img src=\"data/");
			bw.write(""+id+".jpg\" style=\"width:240px;height:180px\">");
			bw.write("<br>");
			
			if (count==TOP) {
				break;
			}
		}	
		
		// HSV Euclidean
		bw.write("<h2>");
		bw.write("HSV Color Histogram, Euclidean Distance");
		bw.write("</h2>");
		count = 0;
		for(DistancePair p : resultHSVEuclidean) {
			count++;
			  
			int id = p.getID();
			double distance = p.getDistance();

			bw.write("<p>");
			bw.write("image id: "+id+"\t"+"distance: "+distance);
			bw.write("</p>");
			
			//<img src="pic_mountain.jpg" alt="Mountain View" style="width:240px;height:180px>">
			bw.write("<img src=\"data/");
			bw.write(""+id+".jpg\" style=\"width:240px;height:180px\">");
			bw.write("<br>");
			
			if (count==TOP) {
				break;
			}
		}		
		
		// HSV Intersection
		bw.write("<h2>");
		bw.write("HSV Color Histogram, Intersection Distance");
		bw.write("</h2>");
		count = 0;
		for(DistancePair p : resultHSVIntersection) {
			count++;
			  
			int id = p.getID();
			double distance = p.getDistance();

			bw.write("<p>");
			bw.write("image id: "+id+"\t"+"distance: "+distance);
			bw.write("</p>");
			
			//<img src="pic_mountain.jpg" alt="Mountain View" style="width:240px;height:180px>">
			bw.write("<img src=\"data/");
			bw.write(""+id+".jpg\" style=\"width:240px;height:180px\">");
			bw.write("<br>");
			
			if (count==TOP) {
				break;
			}
		}
		
		// CCV
		bw.write("<h2>");
		bw.write("Color Coherence Vector");
		bw.write("</h2>");
		count = 0;
		for(DistancePair p : resultCCV) {
			count++;
			  
			int id = p.getID();
			double distance = p.getDistance();

			bw.write("<p>");
			bw.write("image id: "+id+"\t"+"distance: "+distance);
			bw.write("</p>");
			
			//<img src="pic_mountain.jpg" alt="Mountain View" style="width:240px;height:180px>">
			bw.write("<img src=\"data/");
			bw.write(""+id+".jpg\" style=\"width:240px;height:180px\">");
			bw.write("<br>");
			
			if (count==TOP) {
				break;
			}
		}
		
		bw.write("</body></html>");
		
		bw.close();
		
		System.out.println("HTML report generated successfully");				
	}

	private int parseVote(String command) {
		String decision;
		int imageId;
		String keyword;
		
		// decision
		int beginIndex = 4;
		while (beginIndex < command.length() && command.charAt(beginIndex)==' ') {
			beginIndex++;
		}
		int endIndex = beginIndex;
		while (endIndex < command.length() && command.charAt(endIndex)!=' ') {
			endIndex++;
		}
		decision = command.substring(beginIndex, endIndex);
		
		// imageId
		beginIndex = endIndex;
		while (beginIndex < command.length() && command.charAt(beginIndex)==' ') {
			beginIndex++;
		}
		endIndex = beginIndex;
		while (endIndex < command.length() && command.charAt(endIndex)!=' ') {
			endIndex++;
		}
		imageId = Integer.parseInt(command.substring(beginIndex, endIndex));
		
		// keyword
		beginIndex = endIndex;
		while (beginIndex < command.length() && command.charAt(beginIndex)==' ') {
			beginIndex++;
		}
		endIndex = beginIndex;
		while (endIndex < command.length() && command.charAt(endIndex)!=' ') {
			endIndex++;
		}
		keyword = command.substring(beginIndex, endIndex);
		
		boolean ifSuccessful = false;
		if (decision.equals("up")) {
			ifSuccessful = this.ktree.increaseConfidence(imageId, keyword, 1);
		} else if (decision.equals("down")) {
			ifSuccessful = this.ktree.decreaseConfidence(imageId, keyword, 1);
		}
		
		if (ifSuccessful == true) {
			System.out.println("Vote successfully!");
		} else {
			System.out.println("Cannot find image or keyword");
		}
		
		return 0;
	}
	
	private int parseAdd(String command) {
		System.out.println("Open soon");
		return 0;
	}
	
	private void generateHTMLResult(String command, String keyword, TreeMap<Integer, Integer> sortedResult) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		sb.append(System.currentTimeMillis());
		sb.append(' ');
		sb.append(command);
		sb.append(".html");
		
		File htmlResult = new File(sb.toString());
		if (htmlResult.exists() == false) {
			htmlResult.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(htmlResult));
		
		bw.write("<!DOCTYPE html><html><head><title>");
		bw.write(command);
		bw.write("</title></head><body>");
		bw.write("<h1>");
		bw.write(command);
		bw.write("</h1>");
		
		
		int count = 0;
		for(Map.Entry<Integer,Integer> entry : sortedResult.entrySet()) {
			count++;
			  
			Integer key = entry.getKey();
			Integer value = entry.getValue();

			bw.write("<p>");
			bw.write("image id: "+key+"\t"+"keyword: "+keyword+"\t"+"confidence:"+value);
			bw.write("</p>");
			String description = this.btree.search(key);
			ArrayList<String> keywords = Description.extractKeywords(description);
			bw.write("<p>");
			bw.write("keywords: ");
			for (String k : keywords) {
				bw.write(k);
				bw.write(" ");
			}
			bw.write("</p>");
			
			//<img src="pic_mountain.jpg" alt="Mountain View" style="width:240px;height:180px>">
			bw.write("<img src=\"data/");
			bw.write(""+key+".jpg\" style=\"width:240px;height:180px\">");
			bw.write("<br><br>");
			
			if (count==TOP) {
				break;
			}
		}
		
		bw.write("</body></html>");
		
		bw.close();
		
		System.out.println("HTML report generated successfully");
	}
	
}
