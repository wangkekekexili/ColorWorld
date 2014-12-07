package colorworld.utilities.ktree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * A KeyWordTree data structure to speed up search for keywords
 * Each item has a 'confidence' 
 * 
 * @author Ke Wang
 *
 */
public class KeywordTree {
	private static final int DEFAULT_CONFIDENCE = 10;
	
	// trival keywords that will not be included in the tree
	public static final String trival = "the and a in that have I it for not on with he as you do at this but his by from they we say her she or an will my one all would there their what so up out if about who get which go me when make can like time no just him know take people into year your good some could them see other than then now look only come its over think also back after use two how our work first well way even new want because any these give day most";
	
	private KeywordTreeNode root;
	
	public KeywordTree() {
		this.root = null;
	}
	
	public void insert(int imageId, String keywords) {
		this.insert(imageId, keywords, DEFAULT_CONFIDENCE);
	}
	
	public void insert(int imageId, Set<String> keywords) {
		for (String keyword : keywords) {
			this.insert(imageId, keyword, DEFAULT_CONFIDENCE);
		}
	}
	
	public void insert(int imageId, ArrayList<String> keywords) {
		for (String keyword : keywords) {
			this.insert(imageId, keyword, DEFAULT_CONFIDENCE);
		}
	}
	
	public void insert(int imageId, String keywords, int confidence) {
		if (this.root == null) {
			this.root = new KeywordTreeNode(true);
		}
		this.insert(this.root, imageId, keywords, confidence);
	}

	public void insert(KeywordTreeNode node, int imageId, String keywords, int confidence) {
		assert(node != null);
		for (char ch : keywords.toCharArray()) {
			if (node.hasIndex(ch)) {
				node = node.getNextLevel(ch);
				continue;
			} else {
				node.addNextLevel(ch);
				node = node.getNextLevel(ch);
			}
		}
		// now 'node' is the leaf node
		node.addImageId(imageId, confidence);
	}
	
	public Map<Integer, Integer> search(String keyword) {
		keyword = keyword.toLowerCase();
		if (this.root == null || trival.contains(keyword)) {
			return null;
		}
		return this.search(this.root, keyword);
	}
	
	public Map<Integer, Integer> search(KeywordTreeNode node, String keyword) {
		assert(node != null);
		for (char ch : keyword.toCharArray()) {
			if (node.hasIndex(ch)) {
				node = node.getNextLevel(ch);
				continue;
			} else {
				return null;
			}
		}
		
		// now 'node' is the leaf level representing 'keyword'
		return node.getAll();
	}
	
	public boolean increaseConfidence(String keywords, int increase) {
		return true;
	}
	
	public boolean decreaseConfidence(String keywords, int decrease) {
		return true;
	}
	
	/**
	 * Initialize the keyword-tree using dataset from WU
	 * The data has already been handled
	 * What we need is to parse the 'description' file
	 * 
	 * @return
	 * @throws FileNotFoundException 
	 */
	public boolean initializeWithTestDataset() throws FileNotFoundException {
		File descriptionFile = new File("data/description");
		if (descriptionFile.exists() == false) {
			System.out.println("Description file not found");
			return false;
		}
		Scanner sc = new Scanner(descriptionFile);
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			int iterator = 0;
			while (line.charAt(iterator) != ';') {
				iterator++;
			}
			int key = Integer.parseInt(line.substring(0,iterator));
			String description = line.substring(iterator+1);
			// parse keywords from 'description' to 'arraylist'
			ArrayList<String> keywords = new ArrayList<String>();
			//132.jpg;Image01.jpg;keyword:  partly cloudy sky, building, tree, tree trunk, bush, people,grass, HUB area;
			int beginIndex = description.indexOf("keyword:");
			beginIndex += "keyword:".length();
			int endIndex = beginIndex;
			while (endIndex <= description.length()) {
				while (" ".contains(description.substring(endIndex, endIndex+1))) {
					endIndex++;
				}
				beginIndex = endIndex;
				while (!" ,;".contains(description.substring(endIndex, endIndex+1))) {
					endIndex++;
				}
				String tempKeyword = description.substring(beginIndex, endIndex).toLowerCase();
				if (!" \t\n".contains(tempKeyword) && !trival.contains(tempKeyword)) {
					keywords.add(tempKeyword);
				}
				if (description.charAt(endIndex) == ';') {
					break;
				}
				endIndex++;
			}
			//System.out.println(keywords);
			this.insert(key, keywords);
		}
		sc.close();
		return true;
	}
	
}
