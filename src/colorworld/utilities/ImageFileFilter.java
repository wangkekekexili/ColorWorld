package colorworld.utilities;

import java.io.File;
import java.io.FileFilter;

/**
 * Image File Filter
 * 
 * @author Ke Wang
 *
 */

public class ImageFileFilter implements FileFilter {

	final static String[] extensions =
			new String[] {"jpg", "jpeg", "bmp", "gif", "png"};
	
	@Override
	public boolean accept(File pathname) {
		for (String extension : extensions) {
			if (pathname.getName().toLowerCase().endsWith(extension)) {
				return true;
			}
		}
		return false;
	}
}
