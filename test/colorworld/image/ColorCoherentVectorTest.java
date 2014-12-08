package colorworld.image;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import colorworld.utilities.DistancePair;

public class ColorCoherentVectorTest {
	public static void main(String[] args) {
		ColorCoherentVector ccv1 = new ColorCoherentVector();
		ColorCoherentVector ccv2 = new ColorCoherentVector();
		ccv1.loadImage("data/179.jpg");
		ccv2.loadImage("data/649.jpg");
		System.out.println(ColorCoherentVector.compareCCV(ccv1, ccv2));
	}
}
