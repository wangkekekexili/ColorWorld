package colorworld.utilities.btree.ktree;

import java.io.FileNotFoundException;

import colorworld.utilities.ktree.KeywordTree;

public class KeywordTreeTest {
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(KeywordTree.trival.contains("the"));
		System.out.println(KeywordTree.trival.contains("hello"));
		
		KeywordTree ktree = new KeywordTree();
		ktree.initializeWithTestDataset();
		System.out.println(ktree.search("the"));
		System.out.println(ktree.search(""));
		System.out.println(ktree.search("Husky"));
	}
}
