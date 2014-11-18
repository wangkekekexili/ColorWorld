package colorworld.utilities;

/**
 * File Helper
 * 
 * @author Ke Wang
 *
 */

public class FileHelper {
	
	/**
	 * get the extension name of a String
	 * 
	 * @param s
	 * @return
	 */
	public static String getExtension(String s) {
		if (s==null) {
			return null;
		}
		if (s.equals("")) {
			return "";
		}
		int lastIndex = s.length()-1;
		boolean found = false;
		while (lastIndex>=1) {
			if (s.charAt(lastIndex)=='.') {
				found=true;
				break;
			}
			lastIndex--;
		}
		if (!found || lastIndex==s.length()-1) {
			return "";
		} else {
			return s.substring(lastIndex+1);
		}
	}
	
	/**
	 * rename the file name
	 * 
	 * @param filePath
	 * @param newFileName
	 * @return
	 */
	public static String rename(String filePath, String newFileName) {
		if (filePath.contains("/")) {
			int lastIndex = filePath.lastIndexOf('/');
			return filePath.substring(0,lastIndex+1)+newFileName;
		} else {
			return newFileName;
		}
	}
}
