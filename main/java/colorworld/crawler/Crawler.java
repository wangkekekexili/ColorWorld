package colorworld.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Simple web crawler
 * 
 * 
 * @author Ke Wang
 *
 */

public class Crawler {

	public Crawler() {
		
	}
	
	/**
	 * Given a string of webpage url,
	 * return the html content as string
	 * 
	 * @param url string
	 * @return html content, string format
	 * @throws IOException
	 */
	public static String getWebPage(String u) throws IOException{
		URL url = new URL(u);
		if (!url.getProtocol().equals("http")) {
			return null;
		}
		BufferedReader in = null;
		String temp = "";
		StringBuilder sb = new StringBuilder();
		try {
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			while ((temp=in.readLine()) != null) {
				sb.append(temp);
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return sb.toString();
	}
	
	
	public static boolean downloadAllTarFiles(String url) throws IOException{
		
		String html = getWebPage(url);
		int currentIndex = 0;
		while ((currentIndex = html.indexOf("href=\"")) != -1) {
			int firstQuatationMark = 0;
			int secondQuatationMark = 0;
			while (html.charAt(currentIndex) != '\"') {
				currentIndex++;
			}
			firstQuatationMark = currentIndex;
			currentIndex++;
			while (html.charAt(currentIndex) != '\"') {
				currentIndex++;
			}
			secondQuatationMark = currentIndex;
			currentIndex++;
					
			String downloadPart = html.substring(firstQuatationMark+1, secondQuatationMark);
			if (!downloadPart.endsWith(".tar")) {
				html = html.substring(currentIndex);
				continue;
			}
					
			// get the absolute URL for downloading
			URL downloadURL = null;
			try {
				downloadURL = new URL(downloadPart);
			} catch (MalformedURLException me) {
				downloadPart = url+downloadPart;
				downloadURL = new URL(downloadPart);
			}
			
			Download.saveFile(downloadURL);
			
			html = html.substring(currentIndex); // get the remaining of the html text

		}
		return true;
	}
	
}
