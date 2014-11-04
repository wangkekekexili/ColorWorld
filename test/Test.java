import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javafx.util.Pair;

import javax.imageio.ImageIO;

import sun.nio.cs.HistoricallyNamedCharset;
import colorworld.algorithm.Algorithm;
import colorworld.crawler.Crawler;
import colorworld.crawler.Download;
import colorworld.image.ColorHelper;
import colorworld.image.ColorHistogramDistance;
import colorworld.image.RGBHistogram;
import colorworld.utilities.DistancePair;
import colorworld.utilities.HasDoubleValue;

public class Test {
	
	public static void main(String[] args) throws MalformedURLException, IOException {
		RGBHistogram source = new RGBHistogram();
		source.loadImage("data/14.jpg");
		ArrayList<HasDoubleValue> result = new ArrayList<HasDoubleValue>();
		for (int i = 1;i != 25;i++) {
			String filePath = "data/"+ i + ".jpg";
			RGBHistogram target = new RGBHistogram();
			target.loadImage(filePath);
			result.add(new DistancePair(i, ColorHistogramDistance.compare(source, target, ColorHistogramDistance.QUADRATIC)));
		}
		Algorithm.quicksort(result);
		for (HasDoubleValue i : result) {
			System.out.println(((DistancePair)i).getID()+" "+((DistancePair)i).getDistance());
		}
	}

}
