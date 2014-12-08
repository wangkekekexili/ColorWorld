package colorworld.image;

import java.io.FileNotFoundException;

public class ColorHistogramLIbraryTest {
	public static void main(String[] args) throws FileNotFoundException {
		ColorHistogramLibrary library = new ColorHistogramLibrary();
		library.initializeWithTestDataset();
	}
}
