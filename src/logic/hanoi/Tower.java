package logic.hanoi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
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
			return o2.getValue() - o1.getValue();
		}
	};
	
	private int numberSystem;
	private List<Plate> platesOnThisTower;
	
	private int value;
	private String representation;
	
	/**
	 * Creates a new Hanoi tower.
	 * 
	 * @param height - the amount of plate sizes.
	 * @param numberSystem - the number system this tower calculates in (for value and representation calculation).
	 * @param initWithPlates - pass true if this tower should initialize with plates. If so, there will be
	 * numberSystem - 1 plates per width and height different widths, for a total of (numberSystem - 1) * height
	 * plates.
	 */
	public Tower(int height, int numberSystem, boolean initWithPlates) {
		this.numberSystem = numberSystem;
		this.platesOnThisTower = new ArrayList<Plate>();
		
		//if initWithPlates is true, create plates for this tower
		if (initWithPlates) {
			//create for each height...
			for (int y = 0; y < height; y++) {
				//...(numberSystem - 1) times a plate
				for (int d = 0; d < numberSystem - 1; d++) {
					//create a new plate from biggest to smallest
					this.platesOnThisTower.add(new Plate(y));
				}
			}
		}
		
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
	 * Gets the value of the plate with the lowest value that isn't a ghost.
	 * 
	 * @return the value of the least worth plate or, if this tower has no plates, the number system, which is higher
	 * than any plate value.
	 */
	public int getLSBAValue() {
		if (platesOnThisTower.isEmpty()) {
			return numberSystem;
		}
		
		int lowest = numberSystem;
		
		for (Plate plate : platesOnThisTower) {
			if (plate.getValue() < lowest) {
				lowest = plate.getValue();
			}
		}
		
		return lowest;
	}
	
	
	/**
	 * Moves the top plates of this tower to another tower.
	 * 
	 * @param to - the tower to move plates to.
	 * @param amount - the amount of plates to move.
	 * 
	 * @return true if the move was successful, false if there was a problem (there are not enough plates of the
	 * smallest type on this tower, the to-tower has a smaller plate at the top or this receiver is this tower). In
	 * such a case, no tower will be updated.
	 */
	public boolean movePlates(Tower to, int amount) {
		if (to == this) {
			return false;
		}
		
		int lsba = getLSBAValue();
		
		//if the smallest plate on the receiving tower is smaller than this lsba, this can't be done
		if (lsba > to.getLSBAValue()) {
			return false;
		}
		
		//check if enough plates can be removed
		if (hasOfValue(lsba, amount + 1)) {
			//if there are more lsba's than removed, no ghost is needed
			removeOfValue(amount, lsba, false);
		} else {
			if (hasOfValue(amount, lsba)) {
				//if the exact amount of lsba's is removed, a ghost is needed
				removeOfValue(amount, lsba, true);
			} else {
				//otherwise, the action can not be performed
				return false;
			}
		}
		
		//add plates to receiving tower
		to.addPlates(lsba, amount);
		
		//
		recalculate();
		
		return true;
	}
	
	/**
	 * Adds plates to this tower.
	 * 
	 * @param value - the value of these plates.
	 * @param amount - the amount of plates to add.
	 */
	public void addPlates(int value, int amount) {
		if (amount < 1) {
			return;
		}
		
		//if a ghost of given value exists, remove
		Iterator<Plate> plateIterator = platesOnThisTower.iterator();
		
		while (plateIterator.hasNext()) {
			Plate current = plateIterator.next();
			
			if (current.getValue() == value && current.isGhost()) {
				platesOnThisTower.remove(current);
				break;
			}
		}
		
		//add given amount of plates
		for (int i = 0; i < amount; i++) {
			platesOnThisTower.add(new Plate(value));
		}
		
		recalculate();
	}
	
	/**
	 * Method needed for plate moving.
	 */
	private boolean hasOfValue(int amount, int value) {
		for (Plate plate : platesOnThisTower) {
			if (plate.getValue() == value && !plate.isGhost()) {
				amount--;
				
				if (amount == 0) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Method needed for plate moving.
	 */
	private void removeOfValue(int amount, int value, boolean retainGhost) {
		Iterator<Plate> plateIterator = platesOnThisTower.iterator();
		
		while (plateIterator.hasNext()) {
			Plate current = plateIterator.next();
			
			if (current.getValue() == value && !current.isGhost()) {
				if (retainGhost) {
					platesOnThisTower.add(current.ghostClone());
					retainGhost = false;
				}
				
				platesOnThisTower.remove(current);
				amount--;
				
				if (amount == 0) {
					break;
				}
			}
		}
	}
	
	
	/**
	 * Sorts the plates on the tower. Should be called after every time plates are added or removed.
	 */
	private void sortPlates() {
		platesOnThisTower.sort(PLATE_SORTING_ORDER);
	}
	
	/**
	 * Recalculates value and representation of this tower. Should be called after every time plates are added or
	 * removed. This method calls sortPlates().
	 */
	private void recalculate() {
		sortPlates();
		
		this.value = 0;
		
		//if the tower is empty, the value stays zero and the representation is set accordingly
		if (platesOnThisTower.isEmpty()) {
			this.representation = EMPTY_REPRESENTATIVE;
			return;
		}
		
		//otherwise, start calculating value and representation
		int msbValue = platesOnThisTower.get(0).getValue();
		int[] valueCount = new int[msbValue + 1];
		
		for (Plate p : platesOnThisTower) {
			valueCount[p.getValue()] += 1;
		}
		
		StringBuilder representationBuilder = new StringBuilder(msbValue);
		
		for (int i = 0; i < valueCount.length; i++) {
			//add value of one digit
			this.value += valueCount[i] * Math.pow(this.numberSystem, i);
			
			//add to representation
			representationBuilder.append(valueCount[i]);
		}
		
		//save representation (and remove leading zeros)
		this.representation = representationBuilder.reverse().toString().replaceFirst("^0+", "");
	}
}
