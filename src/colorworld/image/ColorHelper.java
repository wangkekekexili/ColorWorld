package colorworld.image;

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
			hsv[0] = (60*(temp[1]-temp[2])/delta);
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
	
}
