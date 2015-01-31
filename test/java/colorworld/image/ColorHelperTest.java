package colorworld.image;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class ColorHelperTest {
	public static void main(String[] args) throws IOException {
		BufferedImage image = ImageIO.read(new File("data/64.jpg"));
		ColorHelper.blur(image);
		
		File outputfile = new File("test.jpg");
		ImageIO.write(image, "jpg", outputfile);
	}
}
