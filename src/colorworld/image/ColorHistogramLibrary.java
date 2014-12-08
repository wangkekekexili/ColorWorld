package colorworld.image;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import colorworld.algorithm.Algorithm;
import colorworld.utilities.DistancePair;
import colorworld.utilities.HasDoubleValue;

/**
 * A library containing color histograms
 * 
 * @author Ke Wang
 *
 */

public class ColorHistogramLibrary { 
	
	private ArrayList<Integer> imageIds;
	private ArrayList<RGBHistogram> rgbHistograms;
	private ArrayList<HSVHistogram> hsvHistograms;
	
	public ColorHistogramLibrary() {
		imageIds = new ArrayList<Integer>();
		rgbHistograms = new ArrayList<RGBHistogram>();
		hsvHistograms = new ArrayList<HSVHistogram>();
	}
	

	/**
	 * Initialize the histogram library using dataset from WU
	 * The data has already been handled
	 * What we need is to parse the 'description' file
	 * 
	 * @return
	 * @throws FileNotFoundException 
	 */
	public boolean initializeWithTestDataset() throws FileNotFoundException {
		File configurationFile = new File("data/configuration");
		Scanner sc = new Scanner(configurationFile);
		
		int maxId = 0;
		if (sc.next().equals("next_image_id:")) {
			maxId = sc.nextInt();
		} else {
			sc.close();
			return false;
		}
		
		sc.close();
		
		for (int i = 1;i != maxId;i++) {
			String filePath = "data/"+ i + ".jpg";
			File f = new File(filePath);
			if (f.exists()==false) {
				continue;
			}
			HSVHistogram hsv = new HSVHistogram();
			hsv.loadImage(filePath);
			RGBHistogram rgb = new RGBHistogram();
			rgb.loadImage(filePath);
			this.imageIds.add(new Integer(i));
			this.rgbHistograms.add(rgb);
			this.hsvHistograms.add(hsv);
		}
		
		return true;
	}
	
}


