package colorworld.image;

public class ColorCoherentVectorTest {
	public static void main(String[] args) {
		ColorCoherentVector ccv = new ColorCoherentVector();
		ccv.loadImage("data/70.jpg");
		for (int i = 70;i != 81;i++) {
			ColorCoherentVector ccv1 = new ColorCoherentVector();
			ccv1.loadImage("data/"+i+".jpg");
			System.out.println(i+" "+ColorCoherentVector.compareCCV(ccv, ccv1));

		}
	}
}
