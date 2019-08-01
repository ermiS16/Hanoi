package gui;

import javafx.scene.shape.Rectangle;
import javafx.geometry.Bounds;

public class Hitbox {
	
	private Rectangle hitbox;
//	private double xStart;
//	private double xEnd;
//	private double yStart;
//	private double yEnd;
	private boolean isHit;
	
	public Hitbox() {
//		xStart = 0;
//		xEnd = 0;
//		yStart = 0;
//		yEnd = 0;
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
//		this.xStart = xStart;
//		this.xEnd = xEnd;
//		this.yStart = yStart;
//		this.yEnd = yEnd;
		
		double width = xEnd - xStart;
		double height = yStart - yEnd;
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
