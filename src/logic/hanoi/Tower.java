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
		
		if (initWithPlates) {
			for (int y = 0; y < height; y++) {
				for (int d = 0; d < dimension; d++) {
					this.platesOnThisTower.add(new Plate());
				}
			}
		}
	}
	
	
	public List<Plate> getPlates() {
		return platesOnThisTower;
	}
}
