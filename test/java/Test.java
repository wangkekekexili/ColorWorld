import java.io.IOException;
import java.net.MalformedURLException;

import colorworld.image.ColorHistogramDistance;
import colorworld.image.RGBHistogram;

public class Test {
	
	public static void main(String[] args) throws MalformedURLException, IOException {
		/*RGBHistogram source = new RGBHistogram(16);
		source.loadImage("data/90.jpg");
		ArrayList<HasDoubleValue> result = new ArrayList<HasDoubleValue>();
		for (int i = 1;i != 786;i++) {
			String filePath = "data/"+ i + ".jpg";
			File f = new File(filePath);
			if (f.exists()==false) {
				System.out.print(""+i+" ");
				continue;
			}
			RGBHistogram target = new RGBHistogram(16);
			target.loadImage(filePath);
			result.add(new DistancePair(i, ColorHistogramDistance.compare(source, target, ColorHistogramDistance.INTERSECTION)));
		}
		Algorithm.quicksort(result);
		int count = 0;
		for (HasDoubleValue i : result) {
			count++;
			System.out.println(((DistancePair)i).getID()+" "+((DistancePair)i).getDistance());
			if (count==20) {
				break;
			}
		}*/
		RGBHistogram hsv1 = new RGBHistogram();
		RGBHistogram hsv2 = new RGBHistogram();
		hsv1.loadImage("temp/4.jpg");
		hsv2.loadImage("temp/10.jpg");
		System.out.println(ColorHistogramDistance.compare(hsv1, hsv2, ColorHistogramDistance.quadratic));
	}

}
