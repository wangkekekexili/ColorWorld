package colorworld.utilities;

import java.util.ArrayList;

public class DescriptionTest {
	public static void main(String[] args) {
		String s = "30;30.jpg;Image30.jpg;keyword:  clear sky, trees, bushes, grass, people, dogs;";
		ArrayList<String> results = Description.extractKeywords(s);
		for (String a : results) {
			System.out.println(a);
		}
	}
}
