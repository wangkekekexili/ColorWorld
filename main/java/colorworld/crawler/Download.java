package colorworld.crawler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * Simple Download Helper
 * 
 * @author Ke Wang
 *
 */

public class Download {
	
	/**
	 * save a file from url to destination
	 * 
	 * @param url
	 * @param destination
	 * @return if the file is saved, return true
	 * @throws IOException 
	 */
	public static boolean saveFile(URL url, String destination) throws IOException {

		File destinationFile = new File(destination);
		if (destinationFile.isFile()) {
			System.out.println("Destination file already exists. Overwrite original file? (y/n)");
			Scanner sc = new Scanner(System.in);
			if (!sc.next().equals("y")) {
				System.out.println("The file will not be downloaded.");
				sc.close();
				return false;
			}
			else {
				System.out.println("The file will be overwritten.");
			}
			sc.close();
			destinationFile.delete();
		}
		
		// check if the folder/directory exists
		// if not, create a new one
		int lastFolderIndex = destination.length()-1;
		while (destination.charAt(lastFolderIndex) != '/') {
			lastFolderIndex--;
		}
		File destinationFolder = new File(destination.substring(0,lastFolderIndex));
		if (!destinationFolder.isDirectory()) {
			destinationFolder.mkdir();
		}
		
		// create a new file
		destinationFile.createNewFile();
		
				
		InputStream is = new BufferedInputStream(url.openStream());
		OutputStream os = new FileOutputStream(destinationFile);
		
		final byte data[] = new byte[1024];
		int count;
		while ((count=is.read(data, 0, 1024)) != -1) {
			os.write(data, 0, count);
		}
		is.close();
		os.close();
		
		return true;
		
	}

	/**
	 * save a file from url to destination
	 * 
	 * @param url
	 * @param folderPath
	 * @return true if the file is saved successfully
	 * @throws IOException 
	 */
	public static boolean saveFileToFolder(URL url, String folderPath) throws IOException {
		
		// get filename from url
		String urlFilename = url.getFile();
		int firstFilenameIndex = urlFilename.length()-1;
		while (urlFilename.charAt(firstFilenameIndex) != '/') {
			firstFilenameIndex--;
		}
		String filename = urlFilename.substring(firstFilenameIndex+1);
		if (folderPath.endsWith("/")) {
			return saveFile(url, folderPath+filename);
		}
		else {
			return saveFile(url, folderPath+"/"+filename);
		}
		
	}

	/**
	 * save a file from url to "downloads" folder of the project
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static boolean saveFile(URL url) throws IOException {
		return saveFileToFolder(url, "downloads/");
	}
	
}
