package logic.hanoi;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents one tower that can hold mulltiple plates.
 * 
 * @author Jonathan
 * @version 1.0
 */
public class Tower {
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
}
