package colorworld.image;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import colorworld.algorithm.Algorithm;
import colorworld.utilities.DistancePair;
import colorworld.utilities.HasDoubleValue;

/**
 * A library containing CCVs
 * 
 * @author Ke Wang
 *
 */

public class ColorCoherentVectorLibrary {
	private ArrayList<Integer> imageIds;
	private ArrayList<ColorCoherentVector> ccvs;
	
	public ColorCoherentVectorLibrary() {
		this.imageIds = new ArrayList<Integer>();
		this.ccvs = new ArrayList<ColorCoherentVector>();
	}
	
	/**
	 * Initialize the CCV library using dataset from WU

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
			ColorCoherentVector ccv = new ColorCoherentVector();
			ccv.loadImage(filePath);
			this.imageIds.add(new Integer(i));
			this.ccvs.add(ccv);
		}
		
		return true;
	}

	public ArrayList<DistancePair> compare(ColorCoherentVector ccv) {
		ArrayList<HasDoubleValue> tempResult = new ArrayList<HasDoubleValue>();
		for (int i = 0;i != imageIds.size();i++) {
			tempResult.add(new DistancePair(this.imageIds.get(i), ColorCoherentVectorLibrary.compare(ccv, this.ccvs.get(i))));
		}
		Algorithm.quicksort(tempResult);
		ArrayList<DistancePair> result = new ArrayList<DistancePair>();
		for (HasDoubleValue item : tempResult) {
			result.add((DistancePair)(item));
		}
		return result;
	}

	public static double compare(ColorCoherentVector ccv1, ColorCoherentVector ccv2) {
		return (double)(ColorCoherentVector.compareCCV(ccv1, ccv2));
	}
	
}
