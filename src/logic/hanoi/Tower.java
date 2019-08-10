package logic.hanoi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import gui.Hitbox;
import gui.Position;
import javafx.geometry.Point2D;

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
	
	private int logicalHeight;
	private int numberSystem;
	private List<Plate> platesOnThisTower;
	
	private Hitbox hitbox;
	private Position pos;
	private double physicalHeight;
	private double physicalWidth;
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
		this.logicalHeight = height;
		this.numberSystem = numberSystem;
		this.platesOnThisTower = new ArrayList<Plate>();
		this.hitbox = new Hitbox();
		this.pos = new Position();
		this.physicalHeight = 0;
		this.physicalWidth = 0;
		
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
	 * Gets the Hitbox of this Tower
	 * @return the Hitbox
	 */
	public Hitbox getHitbox() {
		return this.hitbox;
	}
	
	/**
	 * Gets the current Position of the Tower on the Canvas
	 * @return the current Position.
	 */
	public Position getPosition() {
		return this.pos;
	}
	
	/**
	 * Gets the physical height (in px) as it's shown on the canvas.
	 * @return the physical height of this tower.
	 */
	public double getPhysicalHeight() {
		return this.physicalHeight;
	}
	
	/**
	 * Gets the physical width (in px) as it's shown on the canvas.
	 * @return the physical width of this tower.
	 */
	public double getPhysicalWidth() {
		return this.physicalWidth;
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
	 * Gets the logical height of the Tower, as it needed for the number of total plates that the tower can carry.
	 * @return the logical height of the Tower.
	 */
	public int getLogicalHeight() {
		return logicalHeight;
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
	 * @return the value of the least worth plate or, if this tower has no plates, the height, which is higher than any
	 * plate value.
	 */
	public int getLSBAValue() {
		if (platesOnThisTower.isEmpty()) {
			return logicalHeight;
		}
		
		int lowest = logicalHeight;
		
		for (Plate plate : platesOnThisTower) {
			if (plate.getValue() < lowest && !plate.isGhost()) {
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
	/*
	public boolean movePlates(Tower to, int amount) {
		if (to == this) {
			System.out.println("same");
			return false;
		}
		
		int lsba = getLSBAValue();
		
		//if the smallest plate on the receiving tower is smaller than this lsba, this can't be done
		if (lsba > to.getLSBAValue()) {
			System.out.println("bigger on other");
			return false;
		}
		
		//check if enough plates can be removed
		int amountOfValue = getAmount(lsba);
		
		if (amountOfValue < amount) {
			return false;
		}
		
		removeOfValue(amount, lsba, amountOfValue == amount);
		
		//if there are only ghosts on this tower, remove them
		clearGhostTower();
		
		//add plates to receiving tower
		to.addPlates(lsba, amount);
		
		//
		recalculate();
		
		return true;
	}
	*/
	/**
	 * Sets the physical Parameters for the Tower, as it's drawn on the Canvas
	 * @param height of the Tower (in px).
	 * @param width of the Tower (in px).
	 */
	public void setPhysicalParameters(double height, double width) {
		this.physicalHeight = height;
		this.physicalWidth = width;
	}
	
	/**
	 * Adds plates to this tower.
	 * 
	 * @param value - the value of these plates.
	 * @param amount - the amount of plates to add.
	 */
	public void addPlates(int value, int amount, List<Plate> platesToMove) {
		if (amount < 1) {
			return;
		}
		if (platesOnThisTower.isEmpty()) { //if there are no plates on this tower, ghosts might need to be added
			for (int i = value - 1; i >= 0; i--) {
//				double width = platesToMove.get(i).getPhysicalWidth();
//				double height = platesToMove.get(i).getPhysicalHeight();
				platesOnThisTower.add(new Plate(i, true));
			}
		} else { //if there plates on this tower, there might be a ghost that needs to be removed
			//if a ghost of given value exists, remove
			Iterator<Plate> plateIterator = platesOnThisTower.iterator();
			
			while (plateIterator.hasNext()) {
				Plate current = plateIterator.next();
				
				if (current.getValue() == value && current.isGhost()) {
					platesOnThisTower.remove(current);
					break;
				}
			}
		}
		platesToMove.sort(PLATE_SORTING_ORDER);
		for(Plate p : platesToMove) {
			platesOnThisTower.add(p);
		}
		//add given amount of plates
//		for (int i = 0; i < amount; i++) {
//			platesOnThisTower.add(new Plate(value));
//		}
		
		recalculate();
	}
	
	/**
	 * Method needed for plate moving.
	 */
	public int getAmount(int value) {
		int amount = 0;
		
		for (Plate plate : platesOnThisTower) {
			if (plate.getValue() == value && !plate.isGhost()) {
				amount++;
			}
		}
		
		return amount;
	}
	
	/**
	 * Method needed for plate moving.
	 */
	public void removeOfValue(int amount, int value, boolean retainGhost) {
		System.out.println("ghost? " + retainGhost);
		List<Plate> platesToRemove = new ArrayList<Plate>();
		
		Iterator<Plate> plateIterator = platesOnThisTower.iterator();
		
		while (plateIterator.hasNext()) {
			Plate current = plateIterator.next();
			
			if (current.getValue() == value && !current.isGhost()) {
				if (retainGhost) {
					platesOnThisTower.add(current.ghostClone());
					retainGhost = false;
				}
				platesToRemove.add(current);
				amount--;
				if (amount == 0) {
					break;
				}
			}
		}
		for(Plate p : platesToRemove) {
			platesOnThisTower.remove(p);
		}
	}
	
	/**
	 * Method needed for plate moving.
	 */
	public void clearGhostTower() {
		for (Plate p : platesOnThisTower) {
			if (!p.isGhost()) {
				//if one plate isn't a ghost, this isn't a ghost tower
				return;
			}
		}
		
		//remove all plates (which are all ghosts) from this tower
		platesOnThisTower = new ArrayList<Plate>();
	}
	
	/**
	 * Sorts the plates on the tower. Should be called after every time plates are added or removed.
	 */
	public void sortPlates() {
		platesOnThisTower.sort(PLATE_SORTING_ORDER);
	}
	
	/**
	 * Recalculates value and representation of this tower. Should be called after every time plates are added or
	 * removed. This method calls sortPlates().
	 */
	public void recalculate() {
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
			if (p.isGhost()) {
				continue;
			}
			
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
