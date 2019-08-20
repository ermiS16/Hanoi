package logic.hanoi;

import gui.Hitbox;
import gui.Position;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;



/**
 * Represents one tower that can hold mulltiple plates.
 * 
 * @author Jonathan, Eric
 * @version 1.0
 */

public class Tower {
	private static final String EMPTY_REPRESENTATIVE = "0";
	private static final int BINARAY_SYSTEM = 2;
	private static final double  MAX_HEIGHT = 250;
	private static final double MIN_HEIGHT = 20;
	private static final double WIDTH = 20;
	
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
	private List<Plate> platesOnThisTower;
	
	private int id;
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
	public Tower(int height, boolean initWithPlates, int id) {
		this.logicalHeight = height;
		this.platesOnThisTower = new ArrayList<Plate>();
		this.hitbox = new Hitbox();
		this.pos = new Position();
		this.physicalHeight = MIN_HEIGHT;
		this.physicalWidth = WIDTH;
		this.id = id;
		
		//if initWithPlates is true, create plates for this tower
		if (initWithPlates) {
			//create for each height...
			for (int y = 0; y < height; y++) {
				this.platesOnThisTower.add(new Plate(y));
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
	
	public static double getMinHeight() {
		return MIN_HEIGHT;
	}
	public static double getMinWidth() {
		return WIDTH;
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
			if (plate.getValue() < lowest) {
				lowest = plate.getValue();
			}
		}
		
		return lowest;
	}
	
	
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
		platesToMove.sort(PLATE_SORTING_ORDER);
		for(Plate p : platesToMove) {
			platesOnThisTower.add(p);
		}		
		recalculate();
	}
	
	/**
	 * Method needed for plate moving.
	 */
	public int getAmount(int value) {
		int amount = 0;
		
		for (Plate plate : platesOnThisTower) {
			if (plate.getValue() == value) {
				amount++;
			}
		}
		
		return amount;
	}
	
	/**
	 * Removes a Value from the Tower
	 * 
	 * @param amount of Plates, that has to be removed
	 * @param value the LSBA Value of the Tower, the Plates are removed from
	 * @param retainGhost -> Need to be removed
	 */
	public void removeOfValue(int amount, int value, boolean retainGhost) {
		List<Plate> platesToRemove = new ArrayList<Plate>();
		
		Iterator<Plate> plateIterator = platesOnThisTower.iterator();
		
		while (plateIterator.hasNext()) {
			Plate current = plateIterator.next();
			
			if (current.getValue() == value) {
				if (retainGhost) {
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
			valueCount[p.getValue()] += 1;
		}
		
		StringBuilder representationBuilder = new StringBuilder(msbValue);
		
		for (int i = 0; i < valueCount.length; i++) {
			//add value of one digit
			this.value += valueCount[i] * Math.pow(BINARAY_SYSTEM, i);
			
			//add to representation
			representationBuilder.append(valueCount[i]);
		}
		
		//save representation (and remove leading zeros)
		this.representation = representationBuilder.reverse().toString().replaceFirst("^0+", "");
	}
	
	@Override public String toString() {
		String tower = "Tower: "+this.id;
		String plates = ", Plates: "  + this.getPlates().size();
		String height = ", Height: " + this.getLogicalHeight();

		return tower+plates+height;
	}
}
