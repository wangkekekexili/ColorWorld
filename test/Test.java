import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javafx.util.Pair;

import javax.imageio.ImageIO;

import colorworld.crawler.Crawler;
import colorworld.crawler.Download;
import colorworld.image.ColorHelper;

public class Test {

	public static double euclideanDistance(int a[][][], int b[][][]) {
		double distance = 0;
		int hello = 0;
		int hello1 = 0;
		for (int i = 0;i != 4;i++) {
			for (int j = 0;j != 4;j++) {
				for (int k = 0;k != 4;k++) {
					hello += a[i][j][k];
					hello1 += b[i][j][k];
					distance += Math.pow(a[i][j][k]-b[i][j][k], 2);
				}
			}
		}
		System.out.println(hello + " " + hello1);
		return Math.sqrt(distance);
	}
	
	public static void main(String[] args) throws MalformedURLException, IOException {

		//Download.saveFile(new URL("http://www.cs.washington.edu/research/imagedatabase/groundtruth/_tars.for.download/arborgreens.tar"), "downloads/a.tar");
		//URL u = new URL("http://www.cs.washington.edu/research/imagedatabase/groundtruth/_tars.for.download/arborgreens.tar");
		//System.out.println(u.getFile());
		//Crawler.downloadAllTarFiles("http://www.cs.washington.edu/research/imagedatabase/groundtruth/_tars.for.download/");
		int rgb[] = {1,2,5};
		double hsv[] = ColorHelper.RGB2HSV(rgb);
		for (int i = 0;i !=3;i++)
			System.out.println(hsv[i]);
	}

}
