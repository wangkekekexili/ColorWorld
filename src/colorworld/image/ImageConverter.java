package colorworld.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * ImageConverter contains several static methods
 * which can convert images
 * 
 * @author Ke Wang
 *
 */
public class ImageConverter {
	
	/**
	 * Convert a colorful image to grey scale image
	 * by convert RGB to YCbCr, and only pick up
	 * Y component.
	 * 
	 * @param colorImage a colorful image
	 * @return the image in grey scale
	 */
	public static BufferedImage toGreyScale(BufferedImage colorImage) {
		
		if (colorImage == null) {
			return null;
		}
				
		int height = colorImage.getHeight();
		int width = colorImage.getWidth();
		
		BufferedImage greyImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		
		for (int i = 0;i < height;i++) {
			for (int j = 0;j < width;j++) {
				int rgb = colorImage.getRGB(j, i);
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb) & 0xFF;
				
				double y = (16 + (0.257*r + 0.504*g + 0.098*b));
				int grey = (int)((y - 16) * 1.164);
				Color c = new Color(grey, grey, grey);
				greyImage.setRGB(j, i, c.getRGB());
			}
		}
		
		return greyImage;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedImage b = ImageIO.read(new File("temp/1.jpg"));
		BufferedImage im = ImageConverter.toGreyScale(b);
		ImageIO.write(im, "jpg", new File("hello.jpg"));
	}
	
}
