package colorworld.utilities;

import java.util.ArrayList;


/**
 * This class is used to parse description file
 * 
 * @author Ke Wang
 *
 */

public class Description {

	public static final String trival = "the and a in that have I it for not on with he as you do at this but his by from they we say her she or an will my one all would there their what so up out if about who get which go me when make can like time no just him know take people into year your good some could them see other than then now look only come its over think also back after use two how our work first well way even new want because any these give day most";
	
	public static String extract(String input, String key) {
		if (input==null || key==null) {
			return null;
		}
		String searchString = key+":";
		int beginIndex = input.indexOf(searchString);
		return input.substring(beginIndex+searchString.length());
	}
	
	public static ArrayList<String> extractKeywords(String input) {
		if (input==null) {
			return null;
		}
		ArrayList<String> results=  new ArrayList<String>();
		int beginIndex = input.indexOf("keyword:");
		beginIndex += "keyword:".length();
		int endIndex = beginIndex;
		while (endIndex <= input.length()) {
			while (" ".contains(input.substring(endIndex, endIndex+1))) {
				endIndex++;
			}
			beginIndex = endIndex;
			while (!" ,;".contains(input.substring(endIndex, endIndex+1))) {
				endIndex++;
			}
			String tempKeyword = input.substring(beginIndex, endIndex).toLowerCase();
			if (!" \t\n".contains(tempKeyword) && !trival.contains(tempKeyword)) {
				results.add(tempKeyword);
			}
			if (input.charAt(endIndex) == ';') {
				break;
			}
			endIndex++;
		}
		return results;
	}
	
	
}
