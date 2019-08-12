package logic.hanoi;

import gui.Hitbox;

/**
 * Represents a single plate that can be on a tower.
 * 
 * @author Jonathan
 * @version 1.0
 */
public class Plate implements Comparable<Object>{
	
	private static final double MAXWIDTH = 150;
	private static final double MINWIDTH = 30;
	private static final double HEIGHT = 15;
	
	private int value;
	private boolean ghost;
	private Hitbox hitbox;
	private double physicalWidth;
	private double physicalHeight;
	
	/**
	 * Creates a new plate.
	 * 
	 * @param value - the value of this plate.
	 */
	public Plate(int value) {
		this.value = value;
		this.ghost = false;
		this.hitbox = new Hitbox();
		this.physicalWidth = 0;
		this.physicalHeight = 0;
	}
	
	/**
	 * Creates a new plate.
	 * 
	 * @param value - the value of the new plate.
	 * @param ghost - if true, this is marked as a ghost plate.
	 */
//	public Plate(int value, boolean ghost) {
//		this.value = value;
//		this.ghost = ghost;
//		this.hitbox = new Hitbox();
//		this.physicalWidth = 0;
//		this.physicalHeight = 0;
//	}
	
	public static double getMinWidth() {
		return MINWIDTH;
	}
	public static double getMaxWidth() {
		return MAXWIDTH;
	}
	public static double getHeight() {
		return HEIGHT;
	}
	
	public void setPhysicalParameters(double width, double height) {
		this.physicalWidth = width;
		this.physicalHeight = height;
	}
	
	public double getPhysicalWidth() {
		return this.physicalWidth;
	}
	public double getPhysicalHeight() {
		return this.physicalHeight;
	}
	/**
	 * Gets the Hitbox of this Tower
	 * @return the Hitbox
	 */
	public Hitbox getHitbox() {
		return this.hitbox;
	}
	
	/**
	 * Gets the value of this plate.
	 * 
	 * @return the value of this plate.
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Returns whether or not this plate is a ghost plate.
	 * 
	 * @return true if this plate is a ghost plate.
	 */
	public boolean isGhost() {
		return this.ghost;
	}
	
	
	/**
	 * Creates a copy of this plate which will return true on isGhost.
	 * 
	 * @return a new ghost plate with same width.
	 */
//	public Plate ghostClone() {
//		Plate newPlate = new Plate(value, true);
//		newPlate.setPhysicalParameters(this.physicalWidth, this.physicalHeight);
//		return newPlate;
//	}

	@Override
	public int compareTo(Object o) {
		int compare = 0;
		try { 
			if(o instanceof Plate) {
				Plate obj = (Plate) o;
				if (obj.getValue() == this.getValue()) compare = 0;
				else if(obj.getValue() < this.getValue()) compare = -1;
				else if(obj.getValue() > this.getValue()) compare = 1;
			}
		} catch(ClassCastException e) {
			e.printStackTrace();
		}
		return compare;
	}
}
