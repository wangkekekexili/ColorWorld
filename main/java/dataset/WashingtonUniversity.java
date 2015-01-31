package dataset;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import com.sun.org.apache.xalan.internal.xsltc.trax.OutputSettings;

import colorworld.utilities.FileHelper;
import colorworld.utilities.ImageFileFilter;

/**
 * This program is designed specifically for dataset
 * from Washington University.
 * Data have been extracted to several directories using shell.
 * This program will change the names of all images,
 * and combine information of images into one file to 'data' directory.
 * The altered images will be moved to 'data' directory
 * by another shell program.
 * 
 * @author Ke Wang
 * @email wangkekekexili@gmail.com
 *
 */

public class WashingtonUniversity {
	public static void main(String[] args) throws IOException, FileNotFoundException {
		int numberOfImages = 0;
		
		// description file to save all descriptions
		File descriptionFile = new File("data/description");
		if (descriptionFile.exists() == false) {
			descriptionFile.createNewFile();
		}
		FileOutputStream descriptionFileStream = new FileOutputStream(descriptionFile, false);
		Writer writer = new BufferedWriter(new OutputStreamWriter(descriptionFileStream, "utf-8"));
		
		// mainDirectory is the directory that contains 
		File mainDirectory = new File("downloads");
		
		// all files and directories in mainDirectory
		File[] files = mainDirectory.listFiles();
		
		for (File d : files) {
			if (d.isDirectory()) {
				File local = new File(d.getPath()+"/descriptions");
				if (local.exists() == false) {
					File[] imageFiles = d.listFiles(new ImageFileFilter());
					String schemeName = d.getName();
					for (File oldImageFile : imageFiles) {
						numberOfImages++;
						String oldImageFileExtension = FileHelper.getExtension(oldImageFile.getName());
						String newImageFileName = ""+numberOfImages+"."+oldImageFileExtension;
						
						// write description to description file
						String currentDescription = ""+numberOfImages+";"+newImageFileName+":"
								+oldImageFile.getName()+";"+"keyword:"+schemeName+";\n";
						writer.write(currentDescription);
						
						// rename the image file to newImageFileName
						File newFile = new File(FileHelper.rename(oldImageFile.getPath(), newImageFileName));
						oldImageFile.renameTo(newFile);
					}
				} else {
					// the description exists, so we need to obtain information from it
					BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(local)));
					
					// the description has the following two formats
					// 12.  bushes, flowers
					// Image13.jpg : bush, rocks
					// if the first format is used, then nuberFormat is set to true
					// else set to false
					boolean numberFormat;
					String line = null;
					line = reader.readLine();
					if (line.startsWith("Image")) {
						numberFormat = false;
					} else {
						numberFormat = true;
					}
					//System.out.println(d.getPath()+" "+ numberFormat);
					do {
						
						// ignore meaningless rows
						if (line.replaceAll(" ","").length()<=2) {
							continue;
						}
						numberOfImages++;
						if (numberFormat == true) {
							// get the number and get the image file according to the number
							int getNumber = 0;
							while (line.charAt(getNumber) != '.') {
								getNumber++;
							}
							int numberOfImage = Integer.parseInt(line.substring(0,getNumber));
							StringBuilder oldImageFileName = new StringBuilder("Image");
							if (numberOfImage<10){
								oldImageFileName.append('0');
							}
							oldImageFileName.append(numberOfImage);
							oldImageFileName.append(".jpg");
							File imageFile = new File(d.getPath()+"/"+oldImageFileName.toString());
							
							// modify the line and output to description file
							StringBuilder newDescriptionLine = new StringBuilder();
							newDescriptionLine.append(numberOfImages);
							newDescriptionLine.append(";");
							newDescriptionLine.append(""+numberOfImages+".jpg;");
							newDescriptionLine.append(oldImageFileName);
							newDescriptionLine.append(";keyword:");
							newDescriptionLine.append(line.substring(getNumber+1));
							newDescriptionLine.append(";\n");
							writer.write(newDescriptionLine.toString());
							
							// rename the image file
							File newImageFile = new File(d.getPath()+"/"+numberOfImages+".jpg");
							imageFile.renameTo(newImageFile);
						} else {
							// get the number and get the image file according to the number
							int getNumber = 5;
							while (line.charAt(getNumber) != '.') {
								getNumber++;
							}
							int numberOfImage = Integer.parseInt(line.substring(5, getNumber)); // the number to locate the image
							StringBuilder oldImageFileName = new StringBuilder("Image");
							if (numberOfImage<10) {
								oldImageFileName.append(0);
							}
							oldImageFileName.append(numberOfImage);
							oldImageFileName.append(".jpg");
							File imageFile = new File(d.getPath()+"/"+oldImageFileName.toString());
							
							// modify the line and output to description file
							// "Image13.jpg : bush, rocks"
							StringBuilder ndl = new StringBuilder(); // new description line
							ndl.append(numberOfImages);
							ndl.append(";");
							ndl.append(""+numberOfImages+".jpg;");
							ndl.append(oldImageFileName);
							ndl.append(";keyword");
							while (line.charAt(getNumber)!=':') {
								getNumber++;
							}
							ndl.append(line.substring(getNumber));
							ndl.append(";\n");
							writer.write(ndl.toString());
							
							// rename the image file
							File newImageFile = new File(d.getPath()+"/"+numberOfImages+".jpg");
							imageFile.renameTo(newImageFile);
						}
						
					} while ((line=reader.readLine())!=null);
					reader.close();
				}
			}
		}
		
		writer.close();
		descriptionFileStream.close();
		
	}
}
