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
	private static final String EMPTY_REPRESENTATIVE = "0";
	
	//determines the sorting oder on this tower
	private static final Comparator<Plate> PLATE_SORTING_ORDER = new Comparator<Plate>() {
		@Override
		public int compare(Plate o1, Plate o2) {
			//o1 - o2 <=> sort from thin to thick
			//o2 - o1 <=> sort from thick to thin
			return o2.getWidth() - o1.getWidth();
		}
	};
	
	private int system;
	private List<Plate> platesOnThisTower;

	private int[] valueCount;
	private int value;
	private String representation;
	
	public Tower(int height, int dimension, boolean initWithPlates) {
		this.system = dimension + 1;
		this.platesOnThisTower = new ArrayList<Plate>();
		
		this.valueCount = new int[height];
		
		//if initWidthPlates is true, create plates for this tower
		if (initWithPlates) {
			//create for each height...
			for (int y = 0; y < height; y++) {
				//...dimensions times a plate
				for (int d = 0; d < dimension; d++) {
					//create a new plate from biggest to smallest
					this.platesOnThisTower.add(new Plate(y));
				}
				
				this.valueCount[y] = dimension;
			}
		}
		
		//sort plates into correct order
		sortPlates();
		
		//calculate representation and value of this tower
		recalculate();
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
	 * Gets the caluclated value of this Hanoi tower as an integer in decimal format.
	 * 
	 * @return the value of this tower.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Gets the representation of this Hanoi tower in the given format.
	 * 
	 * @return the representation of this tower.
	 */
	public String getRepresentation() {
		return representation;
	}
	
	
	/**
	 * Sorts the plates on the tower. Should be called after every time plates are added or removed.
	 */
	private void sortPlates() {
		platesOnThisTower.sort(PLATE_SORTING_ORDER);
	}
	
	/**
	 * Recalculates value and representation of this tower. Should be called after every time plates are added or
	 * removed.
	 */
	private void recalculate() {
		this.value = 0;
		
		//if the tower is empty, the value stays zero and the representation is set accordingly
		if (platesOnThisTower.isEmpty()) {
			this.representation = EMPTY_REPRESENTATIVE;
			return;
		}
		
		//otherwise, start calculating value and representation
		StringBuilder representationBuilder = new StringBuilder(this.valueCount.length);
		StringBuilder pendingRepresenationBuilder = new StringBuilder(this.valueCount.length);
		
		for (int i = 0; i < valueCount.length; i++) {
			this.value += this.valueCount[i] * Math.pow(this.system, i);
			pendingRepresenationBuilder.append(this.valueCount[i]);
			
			if (this.valueCount[i] != 0) {
				representationBuilder.append(pendingRepresenationBuilder);
				pendingRepresenationBuilder.delete(0, i);
			}
		}
		
		this.representation = representationBuilder.toString();
	}
}
