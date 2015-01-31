package colorworld.image;


/**
 * Represent distance between color histograms
 * 
 * @author Ke Wang
 *
 */

public class ColorHistogramDistance {
	
	final public static String EUCLIDEAN = "euclidean";
	final public static String euclidean = "euclidean";
	final public static String INTERSECTION = "intersection";
	final public static String intersection = "intersection";
	final public static String QUADRATIC = "quadratic";
	final public static String quadratic = "quadratic";
	
	/**
	 * compare h1 and h2 using method
	 * 
	 * @param h1
	 * @param h2
	 * @param method it can be euclidean, intersection and quadratic
	 * @return if h1 and h2 cannot be compared, return -1
	 */
	public static double compare(ColorHistogram h1, ColorHistogram h2, String method) {
		
		// if two histograms are of different color spaces, they cannot be compared
		if (h1.getColorSpace().equals(h2.getColorSpace()) == false) {
			return -1;
		}
		
		int bin1[] = h1.getBins();
		int bin2[] = h2.getBins();
		if (bin1[0]!=bin2[0] || bin1[1]!=bin2[1] || bin1[2]!=bin2[2]) {
			return -1;
		}
		
		switch (method) {
		case "euclidean":
			return euclideanCompare(h1, h2);
		case "intersection":
			return intersectionCompare(h1, h2);
		case "quadratic":
			if (h1.getColorSpace() == ColorHelper.HSV) {
				System.out.println("HSV color space doesn't support this algorithm");
			}
			return quadraticCompare(h1, h2);
		default:
			System.out.println("The comparing method is not supported");
			break;
		}
		
		return -1;
	}
	
	/**
	 * compare histogram h1 and h2 using euclidean distance
	 * 
	 * @param h1
	 * @param h2
	 * @return
	 */
	private static double euclideanCompare(ColorHistogram h1, ColorHistogram h2) {
		double distance = 0;
		for (int i = 0;i != h1.getBins()[0];i++) {
			for (int j = 0;j != h1.getBins()[1];j++) {
				for (int k = 0;k != h1.getBins()[2];k++) {
					distance += Math.pow(h1.getValue(i, j, k)-h2.getValue(i, j, k),2);
				}
			}
		}
		return Math.sqrt(distance);
	}
	
	/**
	 * compare histogram h1 and h2 using intersection distance
	 * 
	 * @param h1
	 * @param h2
	 * @return
	 */
	private static double intersectionCompare(ColorHistogram h1, ColorHistogram h2) {
		int h1Pixels = h1.numberOfPixels();
		int h2Pixels = h2.numberOfPixels();
		int minPixels = Math.min(h1Pixels, h2Pixels);
		double distance = 0;
		
		for (int i = 0;i != h1.getBins()[0];i++) {
			for (int j = 0;j != h1.getBins()[1];j++) {
				for (int k = 0;k != h1.getBins()[2];k++) {
					distance += Math.min(h1.getValue(i,j,k), h2.getValue(i,j,k));
				}
			}
		}
		
		return 1-distance/minPixels;
		
	}
	
	/**
	 * compare histogram h1 and h2 using quadratic distance
	 * 
	 * @param h1
	 * @param h2
	 * @return
	 */
	private static double quadraticCompare(ColorHistogram h1, ColorHistogram h2) {
		if (h1.getColorSpace() == ColorHelper.RGB) {
			return RGBQuadraticCompare(h1, h2);
		}
		assert(false);
		return 0;
	}
	
	/**
	 * compare histogram h1 and h2 using quadratic distance under RGB color space
	 * 
	 * @param h1
	 * @param h2
	 * @return
	 */
	private static double RGBQuadraticCompare(ColorHistogram h1, ColorHistogram h2) {
		int bins[] = h1.getBins();
		int totalBins = bins[0]*bins[1]*bins[2];
		int diff[] = new int[totalBins];
		for (int i = 0;i != bins[0];i++) {
			for (int j = 0;j != bins[1];j++) {
				for (int k = 0;k != bins[2];k++) {
					diff[i*bins[1]*bins[2]+j*bins[2]+k] = h1.getValue(i,j,k) - h2.getValue(i,j,k);
				}
			}
		}
		// build similarity matrix A
		double A[][] = new double[totalBins][totalBins];
		double max = 0;
		for (int i1 = 0;i1 != bins[0];i1++)
			for (int j1 = 0;j1 != bins[1];j1++)
				for (int k1 = 0;k1 != bins[2];k1++)
					for (int i2 = 0;i2 != bins[0];i2++) 
						for (int j2 = 0;j2 != bins[1];j2++)
							for (int k2 = 0;k2 != bins[2];k2++) {
								double temp = Math.sqrt((i1-i2)*(i1-i2)+(j1-j2)*(j1-j2)+(k1-k2)*(k1-k2));
								A[i1*bins[1]*bins[2]+j1*bins[2]+k1][i2*bins[1]*bins[2]+j2*bins[2]+k2] = temp;
								if (temp > max) {
									max = temp;
								}
							}
		for (int i = 0;i != totalBins;i++) {
			for (int j = 0;j != totalBins;j++) {
				A[i][j] = 1 - A[i][j]/max;
			}
		}
		double intermediate[] = new double[totalBins];
		for (int i = 0;i != totalBins;i++) {
			for (int j = 0;j != totalBins;j++) {
				intermediate[i] += diff[j] * A[i][j]; 
			}
		}
		double distance = 0;
		for (int i = 0;i != totalBins;i++) {
			distance += intermediate[i] * diff[i];
		}
		
		return Math.sqrt(distance);
	}

}
