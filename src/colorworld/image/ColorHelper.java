package colorworld.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Some helper methods for color 
 * 
 * @author Ke Wang
 *
 */

public class ColorHelper {

	final public static String COLORS_SUPPORTED = "red orange yellow green aqua blue purple white black grey";
	final public static String HSV = "HSV";
	final public static String RGB = "RGB";
	
	/**
	 * blur an image in RGB color space using its nearest 8 pixels
	 * 
	 * @param image
	 */
	public static void blur(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int[][] buffer = new int[width][height]; 
		for (int i = 0;i != width;i++) {
			for (int j = 0;j != height;j++) {
				int rgb = image.getRGB(i, j);
				int r = (rgb>>16) & 0xFF;
				int g = (rgb>>8) & 0xFF;
				int b = (rgb) & 0xFF;
				//System.out.print(r+" "+g+" "+b+" ");
				int n = 1;
				int temp;
				// i-1 j-1
				if (i-1>=0 && j-1>=0) {
					temp = image.getRGB(i-1,j-1);
					r += (temp>>16) & 0xFF;
					g += (temp>>8) & 0xFF;
					b += (temp) & 0xFF;
					n++;

				}
				// i-1 j
				if (i-1>=0) {
					temp = image.getRGB(i-1, j);
					r += (temp>>16) & 0xFF;
					g += (temp>>8) & 0xFF;
					b += (temp) & 0xFF;
					n++;
				}
				// i-1 j+1
				if (i-1>=0 && j+1<=height-1) {
					temp = image.getRGB(i-1,j+1);
					r += (temp>>16) & 0xFF;
					g += (temp>>8) & 0xFF;
					b += (temp) & 0xFF;
					n++;
				}
				// i j-1
				if (j-1>=0) {
					temp = image.getRGB(i, j-1);
					r += (temp>>16) & 0xFF;
					g += (temp>>8) & 0xFF;
					b += (temp) & 0xFF;
					n++;
				}
				// i j+1
				if (j+1<=height-1) {
					temp = image.getRGB(i, j+1);
					r += (temp>>16) & 0xFF;
					g += (temp>>8) & 0xFF;
					b += (temp) & 0xFF;
					n++;
				}
				// i+1 j-1
				if (i+1<=width-1 && j-1>=0) {
					temp = image.getRGB(i+1, j-1);
					r += (temp>>16) & 0xFF;
					g += (temp>>8) & 0xFF;
					b += (temp) & 0xFF;
					n++;
				}
				// i+1 j
				if (i+1<=width-1) {
					temp = image.getRGB(i+1,j);
					r += (temp>>16) & 0xFF;
					g += (temp>>8) & 0xFF;
					b += (temp) & 0xFF;
					n++;
				}
				// i+1 j+1
				if (i+1<=width-1 && j+1<=height-1) {
					temp = image.getRGB(i+1, j+1);
					r += (temp>>16) & 0xFF;
					g += (temp>>8) & 0xFF;
					b += (temp) & 0xFF;
					n++;
				}

				Color c = new Color(r/n,g/n,b/n);
				buffer[i][j] = c.getRGB();
			}
		}
	
	}
	
	
	public static int colorToNumber(String color) {
		if (color==null) {
			return -1;
		}
		color = color.toLowerCase();
		if (color.equals("red")) { return 0; }
		else if (color.equals("orange")) { return 1; }
		else if (color.equals("yellow")) { return 2; }
		else if (color.equals("green")) { return 3; }
		else if (color.equals("aqua")) { return 4; }
		else if (color.equals("blue")) { return 5; }
		else if (color.equals("purple")) { return 6; }
		else if (color.equals("white")) { return 7; }
		else if (color.equals("black")) { return 8; }
		else if (color.equals("grey")) { return 9; }
		else return -1;
	}
	
}
