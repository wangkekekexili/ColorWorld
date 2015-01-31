package colorworld.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageConverterTest {

	public static void main(String[] args) throws IOException, ImagesHeightNotAlignedException {
		BufferedImage input = ImageIO.read(new File("test/data/lake.jpg"));
		BufferedImage output = ImageConverter.concatenateHorizontal(input, input, input);
		ImageIO.write(output, "jpg", new File("test/data/result.jpg"));
	}

}
