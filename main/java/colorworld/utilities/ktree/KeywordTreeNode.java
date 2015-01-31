package colorworld.utilities.ktree;

import java.util.HashMap;
import java.util.Map;

/**
 * A node in keyword tree
 * 
 * @author Ke Wang
 *
 */

public class KeywordTreeNode {
	
	private static int numberOfNodes = 0;
	
	private int nodeId; // for serialization and deserialzation
	
	private boolean isRoot;
	private Map<Character, KeywordTreeNode> index;
	private Map<Integer, Integer> imageId;
	
	public KeywordTreeNode() {
		numberOfNodes++;
		this.nodeId = numberOfNodes;
		this.isRoot = false;
		this.index = new HashMap<Character, KeywordTreeNode>();
		this.imageId = new HashMap<Integer, Integer>();
	}
	
	public KeywordTreeNode(boolean isRoot) {
		this();
		this.isRoot = isRoot;
	}
	
	public boolean isRoot() {
		return this.isRoot;
	}

	public boolean hasIndex(char ch) {
		return this.index.containsKey(new Character(ch));
	}

	public KeywordTreeNode getNextLevel(char ch) {
		return this.index.get(new Character(ch));
	}

	public void addNextLevel(char ch) {
		if (this.index.containsKey(new Character(ch)) == false) {
			KeywordTreeNode nextLevelNode = new KeywordTreeNode();
			this.index.put(new Character(ch), nextLevelNode);
		}
	}

	public void addImageId(int imageId2, int confidence) {
		this.imageId.put(new Integer(imageId2), new Integer(confidence));
	}

	/**
	 * get all image ids and their confidences in this level
	 * 
	 * @return
	 */
	public Map<Integer, Integer> getAll() {
		return this.imageId;
	}

	public boolean increaseConfidence(int imageId2, int increase) {
		if (this.imageId.containsKey(new Integer(imageId2)) == false) {
			return false;
		} else {
			this.imageId.replace(new Integer(imageId2), new Integer(imageId.get(new Integer(imageId2).intValue())+increase));
			return true;
		}
	}

	public boolean decreaseConfidence(int imageId2, int decrease) {
		if (this.imageId.containsKey(new Integer(imageId2)) == false) {
			return false;
		} else {
			this.imageId.replace(new Integer(imageId2), new Integer(imageId.get(new Integer(imageId2).intValue())-decrease));
			return true;
		}
	}
	
}
