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
	 * Concatenate several images horizontally using the images given from left to right
	 * 
	 * @param im the images to be concatenated
	 * @return the result image
	 * @throws ImagesHeightNotAlignedException an exception will be thrown if images do not have the same height
	 */
	public static BufferedImage concatenateHorizontal(BufferedImage...im) throws ImagesHeightNotAlignedException {
		if (im==null || im.length==0) {
			return null;
		}
		if (im.length==1) {
			return im[0];
		}
		
		int height = im[0].getHeight();
		int totalWidth = 0;
		for (BufferedImage i : im) {
			int currentHeight = i.getHeight();
			if (currentHeight != height) {
				throw new ImagesHeightNotAlignedException();
			}
			totalWidth += i.getWidth();
		}
		
		BufferedImage resultImage = new BufferedImage(totalWidth, height, BufferedImage.TYPE_INT_BGR);
		
		int cumulativeWidth = 0;
		for (int i = 0;i < im.length;i++) {
			for (int j = 0;j < im[i].getWidth();j++) {
				for (int k = 0;k < height;k++) {
					resultImage.setRGB(j+cumulativeWidth, k, im[i].getRGB(j, k));
				}
			}
			cumulativeWidth += im[i].getWidth();
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
	
	/**
	 * Flip vertically
	 * 
	 * @param image the image to flip
	 * @return the result image
	 */
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
	
	/**
	 * Rotate the image 90 degrees clockwise
	 * 
	 * @param image the image to be rotated
	 * @return the result image
	 */
	public static BufferedImage rotateRight(BufferedImage image) {
		
		if (image == null) {
			return null;
		}
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		BufferedImage resultImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_BGR);
		
		for (int i = 0;i < height;i++) {
			for (int j = 0;j < width;j++) {
				resultImage.setRGB(i, j, image.getRGB(j, height-i-1));
			}
		}
		
		return resultImage;
		
	}
	
	/**
	 * Rotate the image 90 degrees count-clockwise
	 * 
	 * @param image the image to be rotated
	 * @return the result image
	 */
	public static BufferedImage rotateLeft(BufferedImage image) {
		
		if (image==null) {
			return null;
		}
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		BufferedImage resultImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_BGR);
		
		for (int i = 0;i < width;i++) {
			for (int j = 0;j < height;j++) {
				resultImage.setRGB(j, width-i-1, image.getRGB(i, j));
			}
		}
		
		return resultImage;
	}
	
}
