package logic.hanoi;

import gui.Hitbox;

/**
 * Represents a single plate that can be on a tower.
 * 
 * @author Jonathan, Eric
 * @version 1.0
 */

public class Plate implements Comparable<Object>{
	
	private static final double MAX_WIDTH = 150;
	private static final double MIN_WIDTH = 30;
	private static final double MIN_HEIGHT = 3;
	private static final double MAX_HEIGHT = 15;
	
	private int value;
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
		this.hitbox = new Hitbox();
		this.physicalWidth = 0;
		this.physicalHeight = 0;
	}
		
	public static double getMinWidth() {
		return MIN_WIDTH;
	}
	public static double getMaxWidth() {
		return MAX_WIDTH;
	}
	public static double getMaxHeight() {
		return MAX_HEIGHT;
	}
	public static double getMinHeight() {
		return MIN_HEIGHT;
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
	
	@Override
	public boolean equals (Object obj) {
		boolean result = false;
		if (this == obj) return true;
		if(obj instanceof Plate){
			Plate plate = (Plate) obj;
			if (this.getValue() == plate.getValue()) result = true; 
		}
		return result;
	}
	
	@Override
	public String toString() {
		return "Value: "+this.getValue();
	}
}
