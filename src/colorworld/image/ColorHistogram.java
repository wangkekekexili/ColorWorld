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
