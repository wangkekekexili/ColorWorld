package colorworld.utilities.btree;

import java.io.FileNotFoundException;

public class BTreeTest {
	public static void main(String[] args) throws FileNotFoundException {
		BTree btree = new BTree(3);
		btree.initializeWithTestDataset();
		for (int i = 1;i != 786;i++) {
			System.out.println(btree.search(i));
		}
	}
}
