package colorworld.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class RGBHistogram extends ColorHistogram {

	/**
	 * default constructor for RGB space histogram
	 * 
	 */
	public RGBHistogram() {
		bins = new int[]{8,8,8}; // default is 8 bins for each of RGB
		histogram = new int[8][8][8];
		colorSpace = ColorHelper.RGB;
		numberOfPixels = 0;
	}
	
	/**
	 * n bins for each of rgb
	 * 
	 * @param n
	 */
	public RGBHistogram(int n) {
		bins = new int[]{n,n,n};
		histogram = new int[n][n][n];
		colorSpace = ColorHelper.RGB;
		numberOfPixels = 0;
	}
	

	
	@Override
	public boolean loadImage(String imageFilePath) {
		
		// check if the image exists
		File imageFile = new File(imageFilePath);
		if (imageFile.isFile() == false) {
			return false;
		}
		
		// if the image exists, open it and extract colors
		BufferedImage image = null;
		try{
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			return false;
		} finally {
		}
		int width = image.getWidth();
		int height = image.getHeight();
		numberOfPixels = width*height;
		int colors[][][] = new int[height][width][3];
		for (int i = 0;i != height;i++) {
			for (int j = 0;j != width;j++) {
				int colorInt =  image.getRGB(j,i);
				colors[i][j][0] = (colorInt >> 16) & 0xFF;
				colors[i][j][1] = (colorInt >> 8) & 0xFF;
				colors[i][j][2] = (colorInt) & 0xFF;
			}
		}
		
		return loadColors(colors);
		
	}
	
	@Override
	public boolean loadColors(int colors[][][]) {
		
		if (colors==null) {
			return false;
		}
		
		// if one color is not represented by RGB
		if (colors[0][0].length != 3) { 
			return false;
		}
		
		int rows = colors.length;
		int columns = colors[0].length;
		numberOfPixels = rows*columns;
		
		int r,g,b;
		
		for (int i = 0;i != rows;i++) {
			for (int j = 0;j != columns;j++) {
				r = colors[i][j][0] / (256/bins[0]);
				g = colors[i][j][1] / (256/bins[1]);
				b = colors[i][j][2] / (256/bins[2]);
				histogram[r][g][b]++;
			}
		}
		
		return true;
	}

}
