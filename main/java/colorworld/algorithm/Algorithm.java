package colorworld.algorithm;

import java.util.ArrayList;

import colorworld.utilities.HasDoubleValue;

/**
 * a set of algorithms
 * 
 * @author Ke Wang
 *
 */

public class Algorithm {

	/**
	 * sort an array according to the double value
	 * 
	 * @param array
	 */
	public static void quicksort(ArrayList<HasDoubleValue> array) {
		if (array==null || array.size()==0 || array.size()==1) {
			return;
		}
		quicksort(array, 0, array.size()-1);
	}
	
	private static void quicksort(ArrayList<HasDoubleValue> array, int first, int last) {
		if (first < last) {
			int p =  partition(array, first, last);
			quicksort(array,first,p-1);
			quicksort(array,p+1,last);
		}
	}
	
	private static int partition(ArrayList<HasDoubleValue> array, int first, int last) {
		double pivotValue = array.get(last).getValue();
		int storeIndex = first;
		for (int i = first;i != last;i++) {
			if (array.get(i).getValue() < pivotValue) {
				HasDoubleValue  temp = array.get(i);
				array.set(i, array.get(storeIndex));
				array.set(storeIndex, temp);
				storeIndex++;
			}
		}
		HasDoubleValue temp = array.get(last);
		array.set(last, array.get(storeIndex));
		array.set(storeIndex, temp);
		return storeIndex;
	}
	
	/**
	 * swap two objects in the array
	 * 
	 * @param array
	 * @param first
	 * @param second
	 */
	public static void swap(ArrayList<Object> array, int first, int second) {
		if (array==null) {
			return;
		}
		if (first == second) {
			return;
		}
		Object temp = array.get(first);
		array.set(first, array.get(second));
		array.set(second, temp);
	}
	
}
