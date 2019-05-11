package logic.hanoi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents one tower that can hold mulltiple plates.
 * 
 * @author Jonathan
 * @version 1.0
 */
public class Tower {
	//determines the sorting oder on this tower
	private static final Comparator<Plate> PLATE_SORTING_ORDER = new Comparator<Plate>() {
		@Override
		public int compare(Plate o1, Plate o2) {
			//o1 - o2 <=> sort from thin to thick
			//o2 - o1 <=> sort from thick to thin
			return o2.getWidth() - o1.getWidth();
		}
	};
	
	private int dimension;
	private List<Plate> platesOnThisTower;
	
	public Tower(int height, int dimension, boolean initWithPlates) {
		this.dimension = dimension;
		this.platesOnThisTower = new ArrayList<Plate>();
		
		//if initWidthPlates is true, create plates for this tower
		if (initWithPlates) {
			//create for each height...
			for (int y = 0; y < height; y++) {
				//...dimensions times a plate
				for (int d = 0; d < dimension; d++) {
					//create a new plate from biggest to smallest
					this.platesOnThisTower.add(new Plate(height - y));
				}
			}
		}
	}
	
	/**
	 * Gets the plates on this tower. The plates are sorted from broadest to thinnest.
	 * 
	 * @return the plates on this tower.
	 */
	public List<Plate> getPlates() {
		return platesOnThisTower;
	}
	
	/**
	 * Sorts the plates on the tower. Should be called after every time plates are added or removed.
	 */
	private void sortPlates() {
		platesOnThisTower.sort(PLATE_SORTING_ORDER);
	}
}
