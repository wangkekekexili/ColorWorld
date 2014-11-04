package colorworld.utilities;

/**
 * represent the result of distance calculation
 * a pair of ID and distance
 * 
 * @author Ke Wang
 *
 */

public class DistancePair implements Comparable<Double>{

	private int id;
	private double distance;
	
	public DistancePair(int id, double distance) {
		this.id = id;
		this.distance = distance;
	}
	
	public int getID() {
		return id;
	}
	
	public double getDistance() {
		return distance;
	}

	@Override
	public int compareTo(Double o) {
		return (int)(distance-o.doubleValue());
	}
	
}
