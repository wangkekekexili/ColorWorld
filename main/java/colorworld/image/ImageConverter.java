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
	 * Concatenate two images horizontally
	 * 
	 * @param left the image to be put on the left
	 * @param right the image to be put on the right
	 * @return the result image
	 */
	public static BufferedImage concatenateHorizontal(BufferedImage left, BufferedImage right) {
		
		if (left==null || right==null) {
			return null;
		}
		
		int heightLeft = left.getHeight();
		int heightRight = right.getHeight();
		if (heightLeft != heightRight) {
			// TODO exception
			return null;
		}
		int height = heightLeft;
		
		int widthLeft = left.getWidth();
		int widthRight = right.getWidth();
		
		BufferedImage resultImage = new BufferedImage(widthLeft+widthRight, height, BufferedImage.TYPE_INT_BGR);
		
		for (int i = 0;i < height;i++) {
			for (int j = 0;j < widthLeft;j++) {
				resultImage.setRGB(j, i, left.getRGB(j, i));
			}
			for (int j = 0;j < widthRight;j++) {
				resultImage.setRGB(j+widthLeft, i, right.getRGB(j, i));
			}
		}
		
		return resultImage;
		
	}
	
	/**
	 * Flip horizontally
	 * 
	 * @param image the image to flip
	 * @return the result image
	 */
	public static BufferedImage flipHorizontal(BufferedImage image) {
		
		if (image == null) {
			return null;
		}
		
		int height = image.getHeight();
		int width = image.getWidth();
		
		BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		
		for (int i = 0;i < height;i++) {
			for (int j = 0;j < width;j++) {
				resultImage.setRGB(j, i, image.getRGB(width-j-1, i));
			}
		}
		
		return resultImage;
		
	}
	
	public static BufferedImage flipVertical(BufferedImage image) {
		
		if (image == null) {
			return null;
		}
		
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		
		for (int i = 0;i < width;i++) {
			for (int j = 0;j < height;j++) {
				resultImage.setRGB(i, j, image.getRGB(i, height-j-1));
			}
		}
		
		return resultImage;
		
	}
	
	/**
	 * Invert the color of an image
	 * 
	 * @param image the image to be inverted
	 * @return the result image
	 */
	public static BufferedImage invertColor(BufferedImage image) {
		
		if (image == null) {
			return null;
		}
		
		int height = image.getHeight();
		int width = image.getWidth();
		
		BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		
		for (int i = 0;i < height;i++) {
			for (int j = 0;j < width;j++) {
				int rgb = image.getRGB(j, i);
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb) & 0xFF;
				Color c = new Color(255-r,255-g,255-b);
				resultImage.setRGB(j, i, c.getRGB());
			}
		}
		
		return resultImage;
		
	}
	
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
	
}
