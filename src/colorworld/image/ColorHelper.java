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

	final public static String RGB = "RGB";
	final public static String HSV = "HSV";
	
	/**
	 * Change rgb color space to hsv color space
	 * 
	 * @param rgb
	 * @return hsv, h is in degree
	 */
	public static double[] RGB2HSV(int rgb[]) {
		double temp[] = new double[3];
		double hsv[] = new double[3];
		
		temp[0] = (double)rgb[0]/255;
		temp[1] = (double)rgb[1]/255;
		temp[2] = (double)rgb[2]/255;
		
		double max = Math.max(temp[0], Math.max(temp[1], temp[2]));
		double min = Math.min(temp[0], Math.min(temp[1], temp[2]));
		
		double delta = max-min;
		
		if (delta == 0) {
			hsv[0] = 0;
		} else if (max == temp[0]) {
			hsv[0] = (60*(temp[1]-temp[2])/delta)+360;
			while (hsv[0] >= 360) {
				hsv[0] -= 360;
			}
		} else if (max == temp[1]) {
			hsv[0] = 60 * (temp[2]-temp[0]) / delta + 120;
		} else if (max == temp[2]) {
			hsv[0] = 60 * (temp[0]-temp[1]) / delta + 240;
		}
		
		
		if (max == 0) {
			hsv[1] = 0;
		}
		else {
			hsv[1] = delta/max;
		}
		
		hsv[2] = max;
		
		return hsv;
		
	}
	
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
		for (int i = 0;i != width;i++) {
			for (int j = 0;j != height;j++) {
				System.out.println(image.getRGB(i,j));
				System.out.println(buffer[i][j]);
			}
		}
	}
	
}
