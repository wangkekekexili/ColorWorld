package colorworld.color;

/**
 * ColorConverter converts colors from RGB color space to
 * another or from the other to RGB.
 * 
 * @author Ke Wang
 *
 */
public class ColorConverter {

	/**
	 * Convert from CMY color space to RGB color space
	 * 
	 * @param cmy CMY color space value
	 * @return RGB color space value
	 */
	public static double[] cmyToRgb(double[] cmy) {
		assert(cmy != null && cmy.length == 3);
		double[] rgb = new double[3];
		rgb[0] = (1-cmy[0]) * 255;
		rgb[1] = (1-cmy[1]) * 255;
		rgb[2] = (1-cmy[2]) * 255;
		return rgb;
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
	
	/**
	 * Convert an RGB int value to RGB array
	 * 
	 * @param rgb RGB int value
	 * @return RGB array
	 */
	public static double[] rgbIntToRgbArray(int rgb) {
		double[] rgbArray = new double[3];
		rgbArray[0] = (rgb >> 16) & 0xFF;
		rgbArray[1] = (rgb >> 8) & 0xFF;
		rgbArray[2] = (rgb) & 0xFF;
		return rgbArray;
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
	 * Convert from RGB color space to CMY color space
	 * 
	 * @param rgb a double array of size 3 containing RGB data
	 * @return a double array of size 3 containing XYZ
	 */
	public static double[] rgbToCmy(double[] rgb) {
		assert(rgb != null && rgb.length == 3);
		double[] cmy = new double[3];
		cmy[0] = 1 - (rgb[0] / 255);
		cmy[1] = 1 - (rgb[1] / 255);
		cmy[2] = 1 - (rgb[2] / 255);
		return cmy;
	}	
	
	/**
	 * Convert rgb color space to hsv color space
	 * h: degree
	 * s,v: percentage
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
	 * Convert from RGB color space to XYZ color space
	 * 
	 * @param rgb a double array of size 3 containing RGB data
	 * @return a double array of size 3 containing XYZ
	 */
	public static double[] rgbToXyz(double[] rgb) {
		double r = rgb[0]/255;
		double g = rgb[0]/255;
		double b = rgb[0]/255;
		if (r > 0.04045) {
			r = Math.pow((r+0.055)/1.055, 2.4);
		} else {
			r = r/ 12.92;
		}
		if (g > 0.04045) {
			g = Math.pow((g+0.055)/1.055, 2.4);
		} else {
			g = g/ 12.92;
		}
		if (b > 0.04045) {
			b = Math.pow((b+0.055)/1.055, 2.4);
		} else {
			b = b/ 12.92;
		}
		r *= 100;
		g *= 100;
		b *= 100;
		double[] xyz = new double[3];
		xyz[0] = r * 0.4124 + g * 0.3576 + b * 0.1805;
		xyz[1] = r * 0.2126 + g * 0.7152 + b * 0.0722;
		xyz[2] = r * 0.0193 + g * 0.1192 + b * 0.9505;
		return xyz;
	}
}
