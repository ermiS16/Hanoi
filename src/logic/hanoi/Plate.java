package logic.hanoi;

import gui.Hitbox;

/**
 * Represents a single plate that can be on a tower.
 * 
 * @author Jonathan
 * @version 1.0
 */
public class Plate {
	private int value;
	private boolean ghost;
	private Hitbox hitbox;
	
	/**
	 * Creates a new plate.
	 * 
	 * @param value - the value of this plate.
	 */
	public Plate(int value) {
		this.value = value;
		this.ghost = false;
		this.hitbox = new Hitbox();
	}
	
	/**
	 * Creates a new plate.
	 * 
	 * @param value - the value of the new plate.
	 * @param ghost - if true, this is marked as a ghost plate.
	 */
	public Plate(int value, boolean ghost) {
		this.value = value;
		this.ghost = ghost;
		this.hitbox = new Hitbox();
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
		return value;
	}
	
	/**
	 * Returns whether or not this plate is a ghost plate.
	 * 
	 * @return true if this plate is a ghost plate.
	 */
	public boolean isGhost() {
		return ghost;
	}
	
	
	/**
	 * Creates a copy of this plate which will return true on isGhost.
	 * 
	 * @return a new ghost plate with same width.
	 */
	public Plate ghostClone() {
		return new Plate(value, true);
	}
}
