package colorworld.utilities.btree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import colorworld.utilities.SerializeToFile;

/**
 * B-tree data structure
 * 
 * @author Ke Wang
 *
 */

public class BTree implements SerializeToFile{
	
	private static final int DEFAULT_FANOUT = 20;
	
	private int fanout;
	private int numberOfNodes; // for serialization and deserialization
	private BTreeNode root;
	
	public BTree() {
		this.fanout = DEFAULT_FANOUT;
		this.numberOfNodes = 0;
		this.root = null;
	}
	
	public BTree(int fanout) {
		this.fanout = fanout;
		this.numberOfNodes = 0;
		this.root = null;
	}
	
	public boolean delete(BTreeNode node, int id) {
		if (node.getType() == BTreeNodeType.LeafNode) {
			for (int i = 0;i != node.getNumberOfKeys();i++) {
				if (node.getKey(i) == id) {
					node.setNumberOfKeys(node.getNumberOfKeys()-1);
					node.setNumberOfDescriptions(node.getNumberOfDescriptions()-1);
					for (int j = i; j != node.getNumberOfKeys();j++) {
						node.setKey(j, node.getKey(j+1));
						node.setDescription(j, node.getDescription(j+1));
					}
					return true;
				}
			}
			return false;
		} else if (node.getType() == BTreeNodeType.InnerNode) {
			if (id < node.getKey(0)) {
				return this.delete(node.getChild(0), id);
			} else {
				for (int i = node.getNumberOfKeys()-1;i >= 0;i--) {
					if (id >= node.getKey(i)) {
						return this.delete(node.getChild(i+1), id);
					}
				}
			}
 		}
		return false;
	}
	
	/**
	 * delete an entry from the tree 
	 * 
	 * @param id the image having 'id' will be deleted
	 * @return
	 */
	public boolean delete(int id) {
		if (this.root == null) {
			return false;
		}
		return this.delete(this.root, id);
	}
	
	/**
	 * Initialize the b-tree using dataset from WU
	 * The data has already been handled
	 * What we need is to parse the 'description' file
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
			this.insert(key, description);
			//System.out.println(key);
			//System.out.println(description);
		}
		sc.close();
		return true;
	}
	
	public void insert(BTreeNode node, int key) {
		if (node.getType() == BTreeNodeType.LeafNode) {
			return;
		} else if (node.getType() == BTreeNodeType.InnerNode) {
			
			// find the position to insert the key
			int insertPosition;
			if (key < node.getKey(0)) {
				insertPosition = 0;
			} else {
				for (insertPosition = node.getNumberOfKeys()-1;insertPosition >= 0;insertPosition++) {
					if (key > node.getKey(insertPosition)) {
						insertPosition++;
						break;
					}
				}
			}
			
			for (int i = node.getNumberOfKeys();i >= insertPosition+1;i--) {
				node.setKey(i, node.getKey(i-1));
				node.setChild(i+1, node.getChild(i));
			}
			node.setKey(insertPosition, key);
			node.setChild(insertPosition+1, node.getChild(insertPosition).getSibling());
			node.increaseNumberOfKeys();
			node.increaseNumberOfChildren();
			
			// if the node is full, then split
			if (node.getNumberOfKeys() == this.fanout) {
				int newKey; // used to give the father a key
				
				this.numberOfNodes++;
				BTreeNode splitNode = new BTreeNode(this.numberOfNodes, BTreeNodeType.InnerNode, this.fanout);
				splitNode.setFather(node.getFather());
				splitNode.setSibling(node.getSibling());
				node.setSibling(splitNode);
				node.setNumberOfKeys(node.getNumberOfKeys()/2);
				node.setNumberOfChildren(node.getNumberOfKeys()+1);
				
				newKey = node.getKey(node.getNumberOfKeys());
				
				splitNode.setNumberOfKeys(this.fanout-1-node.getNumberOfKeys());
				splitNode.setNumberOfChildren(splitNode.getNumberOfKeys()+1);
				for (int i = this.fanout-1;i >= node.getNumberOfKeys()+1;i--) {
					splitNode.setKey(i-node.getNumberOfKeys()-1, node.getKey(i));
					splitNode.setChild(i-node.getNumberOfKeys()-1, node.getChild(i));
				}
				splitNode.setChild(splitNode.getNumberOfChildren()-1, node.getChild(this.fanout));
				for (int i = 0;i != splitNode.getNumberOfChildren();i++) {
					splitNode.getChild(i).setFather(splitNode);
				}
				
				if (node.getFather() == null) {
					node.setIsRoot(false);
 					this.numberOfNodes++;
 	 				BTreeNode newRoot = new BTreeNode(this.numberOfNodes, BTreeNodeType.InnerNode, this.fanout, true);
 	 				this.root = newRoot;
 	 				node.setFather(newRoot);
 	 				splitNode.setFather(newRoot);
 	 				this.insert(node.getFather(), newKey, node);
				} else {
					this.insert(node.getFather(),newKey);
				}
				
			}
			
		}
	}
	
	/**
	 * add a key to a new inner node
	 * 
	 * @param node
	 * @param key
	 * @param leftChildNode
	 */
	public void insert(BTreeNode node, int key, BTreeNode leftChildNode) {
		assert(node.getType() == BTreeNodeType.InnerNode);
		node.setKey(0, key);
		node.setChild(0, leftChildNode);
		node.setChild(1, leftChildNode.getSibling());
		node.increaseNumberOfKeys();
		node.increaseNumberOfChildren(2);
	}
	
	public void insert(BTreeNode node, int key, String description) {
		if (node.getType() == BTreeNodeType.InnerNode) {
			if (key < node.getKey(0)) {
				this.insert(node.getChild(0), key, description);
			} else {
				for (int i = node.getNumberOfKeys()-1;i >= 0;i--) {
					if (key >= node.getKey(i)) {
						this.insert(node.getChild(i+1), key, description);
						break;
					}
				}
			}
 		} else if (node.getType() == BTreeNodeType.LeafNode) {
			// insert into the node if the node if not full
 			if (node.getNumberOfKeys() < this.fanout) {
 				int insertPosition;
 				for (insertPosition = 0;insertPosition != node.getNumberOfKeys();insertPosition++) {
 					if (key < node.getKey(insertPosition)) {
 						insertPosition++;
 						break;
 					}
 				}
 				for (int j = node.getNumberOfKeys();j >= insertPosition+1;j--) {
 					node.setKey(j, node.getKey(j-1));
 					node.setDescription(j, node.getDescription(j-1));
 				}
 				node.setKey(insertPosition, key);
 				node.setDescription(insertPosition, description);
 				node.increaseNumberOfKeys();
 				node.increaseNumberOfDescriptions();
 			} else { // else split the leaf node
 				int insertPosition;
 				for (insertPosition = 0;insertPosition != node.getNumberOfKeys();insertPosition++) {
 					if (key < node.getKey(insertPosition)) {
 						insertPosition++;
 						break;
 					}
 				}
 				for (int j = node.getNumberOfKeys();j >= insertPosition+1;j--) {
 					node.setKey(j, node.getKey(j-1));
 					node.setDescription(j, node.getDescription(j-1));
 				}
 				node.setKey(insertPosition, key);
 				node.setDescription(insertPosition, description);
 				node.increaseNumberOfKeys();
 				node.increaseNumberOfDescriptions();
 				this.numberOfNodes++;
 				BTreeNode splitNode = new BTreeNode(this.numberOfNodes,BTreeNodeType.LeafNode,this.fanout);
 				splitNode.setSibling(node.getSibling());
 				node.setSibling(splitNode);
 				splitNode.setFather(node.getFather());
 				// leave half of the entries in current node
 				node.setNumberOfKeys(node.getNumberOfKeys()/2);
 				node.setNumberOfDescriptions(node.getNumberOfDescriptions()/2);
 				// add another half to the split node
 				for (int i = node.getNumberOfKeys();i != this.fanout+1;i++) {
 					this.insert(splitNode, node.getKey(i), node.getDescription(i));
 				}
 				// if parent is null, create a new root
 				if (node.getFather() == null) {
 					node.setIsRoot(false);
 					this.numberOfNodes++;
 	 				BTreeNode newRoot = new BTreeNode(this.numberOfNodes, BTreeNodeType.InnerNode, this.fanout, true);
 	 				this.root = newRoot;
 	 				node.setFather(newRoot);
 	 				splitNode.setFather(newRoot);
 	 				this.insert(node.getFather(), splitNode.getKey(0), node);
 				} else { // recursively add a key to the parent node
 					this.insert(node.getFather(), splitNode.getKey(0));
 				}
 			}

		}
	}
	
	/**
	 * insert a <image id, image description> pair to the b-tree
	 * 
	 * @param id
	 * @param description
	 */
	public void insert(int id, String description) {
		BTreeNode node = this.root;
		if (node == null) {
			this.numberOfNodes++;
			BTreeNode newNode = new BTreeNode(this.numberOfNodes, BTreeNodeType.LeafNode, this.fanout, true);
			this.root = newNode;
		}
		this.insert(this.root, id, description);
	}
	
	public String search(BTreeNode node, int key) {
		if (node.getType() == BTreeNodeType.LeafNode) {
			for (int i = 0;i != node.getNumberOfKeys();i++) {
				if (node.getKey(i) == key) {
					return node.getDescription(i);
				}
			}
			return null;
		} else if (node.getType() == BTreeNodeType.InnerNode) {
			if (key < node.getKey(0)) {
				return this.search(node.getChild(0), key);
			} else {
				for (int i = node.getNumberOfKeys()-1;i >= 0;i--) {
					if (key >= node.getKey(i)) {
						return this.search(node.getChild(i+1), key);
					}
				}
			}
		}
		return null;
	}
	
	public String search(int id) {
		if (this.root == null) {
			return null;
		}
		return this.search(this.root, id);
	}
	
	public void serializeToFile(){
		
	}
}
