import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.util.Pair;

import javax.imageio.ImageIO;

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
	
	public static void main(String[] args) 
	throws IOException{
		int count[][][] = new int[4][4][4]; 
		int count1[][][] = new int[4][4][4];
		File tempImageFile = new File("data/14.jpg");
		File tempImageFile2 = new File("data/25.jpg");
		
		BufferedImage image = ImageIO.read(tempImageFile);
		BufferedImage image2 = ImageIO.read(tempImageFile2);
		int width = image.getWidth();
		int height = image.getHeight();
		int count11 = 0;
		for (int i = 0;i != height;i++) {
			for (int j = 0;j != width;j++) {
				int c = image.getRGB(j, i);
				int c1 = image2.getRGB(j,i);
			
				if (c!=c1) {
					count11++;
				}
			}
		}
		System.out.println(count11);
	}

}
