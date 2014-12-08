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
			//RGBHistogram rgb = new RGBHistogram();
			//rgb.loadImage(filePath);
			this.imageIds.add(new Integer(i));
			//this.rgbHistograms.add(rgb);
			this.hsvHistograms.add(hsv);
		}
		
		return true;
	}


	public ArrayList<DistancePair> compare(RGBHistogram rgbHistogram, String method) {
		ArrayList<HasDoubleValue> tempResult = new ArrayList<HasDoubleValue>();
		for (int i = 0;i != imageIds.size();i++) {
			tempResult.add(new DistancePair(this.imageIds.get(i), ColorHistogramDistance.compare(rgbHistogram, this.rgbHistograms.get(i), method)));
		}
		Algorithm.quicksort(tempResult);
		ArrayList<DistancePair> result = new ArrayList<DistancePair>();
		for (HasDoubleValue item : tempResult) {
			result.add((DistancePair)(item));
		}
		return result;
	}
	
	public ArrayList<DistancePair> compare(HSVHistogram hsvHistogram, String method) {
		if (method==ColorHistogramDistance.QUADRATIC) {
			return null;
		}
		ArrayList<HasDoubleValue> tempResult = new ArrayList<HasDoubleValue>();
		for (int i = 0;i != imageIds.size();i++) {
			tempResult.add(new DistancePair(this.imageIds.get(i), ColorHistogramDistance.compare(hsvHistogram, this.hsvHistograms.get(i), method)));
		}
		Algorithm.quicksort(tempResult);
		ArrayList<DistancePair> result = new ArrayList<DistancePair>();
		for (HasDoubleValue item : tempResult) {
			result.add((DistancePair)(item));
		}
		return result;
	}
	
}


