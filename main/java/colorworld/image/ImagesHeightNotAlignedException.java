package colorworld.image;
/**
 * Thrown to indicate that certain conversions can not be done
 * because the heights of images are not aligned
 * 
 * @author Ke Wang
 *
 */
@SuppressWarnings("serial")
public class ImagesHeightNotAlignedException extends Exception {
	public ImagesHeightNotAlignedException() {
		super("The height of images are not aligned");
	}
}
