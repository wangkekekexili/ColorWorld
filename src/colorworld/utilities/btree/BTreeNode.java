package colorworld.utilities.btree;


/**
 * B-tree node
 * 
 * @author Ke Wang
 *
 */

public class BTreeNode {

	
	private int nodeId;
	private BTreeNodeType type; // this is a leaf node or inner node?
	private int[] keys;
	private int numberOfKeys;
	private BTreeNode[] children;
	private int numberOfChildren;
	private String[] descriptions;
	private int numberOfDescriptions;
	private boolean isRoot;

	
	private boolean isValid; // used when deleting an entry in leaf node
	
	private BTreeNode father;
	private BTreeNode sibling;
	
	public BTreeNode(int id, BTreeNodeType type, int fanout, boolean isRoot) {
		this(id, type, fanout);
		this.isRoot = isRoot;
	}
	
	public BTreeNode(int id, BTreeNodeType type, int fanout) {
		this.nodeId = id;
		this.father = null;
		this.sibling = null;
		this.isRoot = false;
		this.numberOfChildren = 0;
		this.numberOfDescriptions = 0;
		this.numberOfKeys = 0;
		this.isValid = true;
		if (type == BTreeNodeType.InnerNode) {
			this.type = BTreeNodeType.InnerNode;
			keys = new int[fanout];
			children = new BTreeNode[fanout+1];
			descriptions = null;
		} else if (type == BTreeNodeType.LeafNode) {
			this.type = BTreeNodeType.LeafNode;
			keys = new int[fanout+1];
			children = null;
			descriptions = new String[fanout+1];
		}
	}
	
	public int getId() {
		return this.nodeId;
	}
	
	public BTreeNodeType getType() {
		return this.type;
	}
	
	public boolean isRoot() {
		return this.isRoot;
	}
	
	public int getNumberOfKeys() {
		return this.numberOfKeys;
	}
	
	public int getNumberOfChildren() {
		return this.numberOfChildren;
	}
	
	public int getNumberOfDescriptions() {
		return this.numberOfDescriptions;
	}
	
	public boolean isValid() {
		return this.isValid;
	}
	
	
	public void setIsValid(boolean value) {
		this.isValid = value;
	}
	
	public void setIsRoot(boolean value) {
		this.isRoot = value;
	}
	
	public void setFather(BTreeNode father2) {
		this.father = father2;
		
	}

	public int getKey(int i) {
		return this.keys[i];
	}

	public void setNumberOfKeys(int i) {
		this.numberOfKeys = i;
	}

	public void setNumberOfDescriptions(int i) {
		this.numberOfDescriptions = i;
	}

	public void setKey(int j, int key) {
		this.keys[j] = key;
	}

	public String getDescription(int i) {
		return this.descriptions[i];
	}

	public void setDescription(int j, String description) {
		this.descriptions[j] = description;
	}

	public BTreeNode getChild(int i) {
		return this.children[i];
	}

	public void increaseNumberOfKeys() {
		this.numberOfKeys += 1;
	}
	
	public void increaseNumberOfKeys(int i) {
		this.numberOfKeys += i;
	}

	public void increaseNumberOfDescriptions() {
		this.numberOfDescriptions += 1;
	}
	
	public void increaseNumberofDescriptions(int i) {
		this.numberOfDescriptions += i;
	}

	public void setSibling(BTreeNode splitNode) {
		this.sibling = splitNode;
	}

	public BTreeNode getFather() {
		return this.father;
	}

	public BTreeNode getSibling() {
		return this.sibling;
	}

	public void setChild(int i, BTreeNode child) {
		this.children[i] = child;
	}

	public void increaseNumberOfChildren() {
		this.numberOfChildren++;
	}
	
	public void increaseNumberOfChildren(int i) {
		this.numberOfChildren += i;
	}

	public void setNumberOfChildren(int i) {
		this.numberOfChildren = i;		
	}
	
	
}
