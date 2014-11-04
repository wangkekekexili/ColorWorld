package colorworld.image;

import java.awt.Image;

/**
 * Color Histogram
 * 
 * @author Ke Wang
 *
 */

public abstract class ColorHistogram {
	
	protected int histogram[][][];
	protected int bins[];
	protected String colorSpace;
	protected int numberOfPixels;
	
	/**
	 * get the bins used for the histogram
	 * 
	 * @return
	 */
	public int[] getBins() {
		return bins;
	}
	
	/**
	 * get the color space used for the histogram
	 * 
	 * @return
	 */
	public String getColorSpace() {
		return colorSpace;
	}
	
	public int numberOfPixels() {
		return numberOfPixels;
	}
	
	/**
	 * get the value at specific bin number
	 * 
	 * @param i
	 * @param j
	 * @param k
	 * @return
	 */
	public int getValue(int i, int j,int k) {
		return histogram[i][j][k];
	}
	
	/**
	 * load an image from path 'imageFilePath' and calculate the histogram 
	 * 
	 * @param imageFilePath
	 * @return
	 */
	abstract public boolean loadImage(String imageFilePath);
	
	/**
	 * build the histogram from input colors
	 * 
	 * @param colors
	 * @return
	 */
	abstract public boolean loadColors(int colors[][][]);
}
