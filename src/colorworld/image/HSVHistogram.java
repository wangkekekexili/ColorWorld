package colorworld.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HSVHistogram extends ColorHistogram {

	public HSVHistogram() {
		bins = new int[]{16,3,3};
		histogram = new int[16][3][3];
		colorSpace = ColorHelper.HSV;
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
				double[] hsv = ColorHelper.RGB2HSV(colors[i][j]);
				//System.out.print(""+colors[i][j][0]+" "+colors[i][j][1]+" "+colors[i][j][2]);
				colors[i][j][0] = (int)hsv[0];
				colors[i][j][1] = (int)(hsv[1]*100);
				colors[i][j][2] = (int)(hsv[2]*100);
				//System.out.println(" "+colors[i][j][0]+" "+colors[i][j][1]+" "+colors[i][j][2]);
			}
		}
		
		return loadColors(colors);
	}

	public boolean loadColors(int[][][] colors) {
		
		if (colors==null) {
			return false;
		}
		
		if (colors[0][0].length != 3) { 
			return false;
		}
		
		int rows = colors.length;
		int columns = colors[0].length;
		numberOfPixels = rows*columns;
		
		int h,s,v;
		
		for (int i = 0;i != rows;i++) {
			for (int j = 0;j != columns;j++) {
				h = (int)(colors[i][j][0] / (360.0/bins[0]));
				s = (int)(colors[i][j][1] / (101.0/bins[1]));
				v = (int)(colors[i][j][2] / (101.0/bins[2]));
				//System.out.println(""+colors[i][j][0]+" "+colors[i][j][1]+" "+colors[i][j][2]+" "+h+" "+s+" "+v);
				histogram[h][s][v]++;
			}
		}
		
		return true;
	}

}
