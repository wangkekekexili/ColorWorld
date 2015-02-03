package colorworld.color;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class ColorConverterTest {

	public static void main(String[] args) {
		double[] test1 = {0,0,0};
		double[] test2 = {100,100,100};
		double[] test3 = {255,255,255};
		double[] xyzResult = ColorConverter.rgbToXyz(test3);
		for (double d : xyzResult) {
			System.out.println(d);
		}
	}

}
