package colorworld.color;

/**
 * ColorConverter converts colors in one color space
 * to another
 * 
 * @author Ke Wang
 *
 */
public class ColorConverter {

	/**
	 * Convert rgb color space to hsv color space
	 * 
	 * @param rgb
	 * @return hsv, h is in degree
	 */
	public static double[] rgbToHsv(int rgb[]) {
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
	 * Convert an RGB array to an int value that represents
	 * RGB which can be used in BufferedImage setRGB() method
	 * 
	 * @param array an RGB array
	 * @return the corresponding RGB int value
	 */
	public static int rgbArrayToRgbInt(int[] array) {
		if (array == null) {
			return 0;
		}
		if (array.length != 3) {
			return 0;
		}
		double[] doubleArray = {array[0], array[1], array[2]};
		return ColorConverter.rgbArrayToRgbInt(doubleArray);
	}
	
	/**
	 * Convert an RGB array to an int value that represents
	 * RGB which can be used in BufferedImage setRGB() method
	 * 
	 * @param array an RGB array
	 * @return the corresponding RGB int value
	 */
	public static int rgbArrayToRgbInt(double[] array) {
		if (array == null) {
			return 0 ;
		}
		if (array.length != 3) {
			return 0;
		}
		return (255 & 0xFF) << 24 +
				((int)array[0] & 0xFF) << 16 +
				((int)array[1] & 0xFF) << 8 +
				((int)array[2] & 0xFF);
	}
	
}
