package gui;

import javafx.scene.shape.Rectangle;

/**
 * Represents a normal, rectangle shaped, hitbox.
 * @author Eric
 * @version 1.0
 */

public class Hitbox {
	
	private Rectangle hitbox;
	private boolean isHit;
	
	public Hitbox() {
		hitbox = new Rectangle();
	}
	
	/**
	 * Sets the Hitbox as a Rectangle
	 * @param xStart Start on the x-Axis of the Canvas
	 * @param xEnd End on the x-Axis of the Canvas
	 * @param yStart Start on the y-Axis of the Canvas
	 * @param yEnd End on the y-Axis of the Canvas
	 */
	public void setHitbox(double xStart, double xEnd, double yStart, double yEnd) {		
		double width = xEnd - xStart;
		double height = yEnd - yStart;
		this.hitbox = new Rectangle(xStart, yStart, width, height);
	}
	
	/**
	 * Return true if Hitbox ist hit, false otherwise.
	 * @return isHit of this Hitbox
	 */
	public boolean isHit() {
		return this.isHit;
	}
	
	/**
	 * Sets the Hitstatus for this Hitbox.
	 * @param hit Value for Hitstatus, true if hit, false otherwise.
	 */
	public void setHit(boolean hit) {
		this.isHit = hit;
	}
	
	/**
	 * Gets the startposition of the Hitbox on the Canvas
	 * @return the Position on the x-Axis
	 */
	public double getX() {
		return this.hitbox.getX();
	}
	
	/**
	 * Gets the startposition of the Hitbox on the Canvas
	 * @return the Position on the y-Axis
	 */
	public double getY() {
		return this.hitbox.getY();
	}
	
	public double getMinX() {
		return this.hitbox.getLayoutBounds().getMinX();
	}
	
	public double getMaxX() {
		return this.hitbox.getLayoutBounds().getMaxX();
	}
	
	public double getMinY() {
		return this.hitbox.getLayoutBounds().getMinY();
	}
	
	public double getMaxY() {
		return this.hitbox.getLayoutBounds().getMaxY();
	}
	
	/**
	 * Return whether or not the Point(x,y) 
	 * lays inbetween the Hitbox
	 * @param x Point on x-Axis
	 * @param y Point on y-Axis
	 * @return true, when Hitbox contains Point(x,y), false otherwise
	 */
	public boolean contains(double x, double y) {
		return this.hitbox.contains(x, y);
	}
	
}
