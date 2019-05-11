package logic.hanoi;

/**
 * Represents a single plate that can be on a tower.
 * 
 * @author Jonathan
 * @version 1.0
 */
public class Plate {
	private int width;
	private boolean ghost;
	
	
	/**
	 * Creates a new plate.
	 * 
	 * @param width - the width of this plate.
	 */
	public Plate(int width) {
		this.width = width;
		this.ghost = false;
	}
	
	/**
	 * Creates a new plate.
	 * 
	 * @param width - the width of the new plate.
	 * @param ghost - if true, this is marked as a ghost plate.
	 */
	private Plate(int width, boolean ghost) {
		this.width = width;
		this.ghost = ghost;
	}
	
	
	/**
	 * Gets the width of this plate.
	 * 
	 * @return the width of this plate.
	 */
	public int getWidth() {
		return width;
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
		return new Plate(width, true);
	}
}
