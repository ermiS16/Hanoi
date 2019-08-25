package gui;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

/**
 * Represents a normal, rectangle shaped, hitbox.
 * @author Eric
 * @version 1.0
 */

public class Hitbox {
	
	private Rectangle hitbox;
	private boolean isHit;
	private double width;
	private double height;
	private List<Point2D> pointMatrix;

	
	public Hitbox() {
		hitbox = new Rectangle();
		this.isHit = false;
		this.height = 0;
		this.width = 0;
		this.pointMatrix = new ArrayList<>();
	}
	
	/**
	 * Sets the Hitbox as a Rectangle
	 * @param xStart Start on the x-Axis of the Canvas
	 * @param xEnd End on the x-Axis of the Canvas
	 * @param yStart Start on the y-Axis of the Canvas
	 * @param yEnd End on the y-Axis of the Canvas
	 */
	public void setHitbox(double xStart, double xEnd, double yStart, double yEnd) {
		
		double tmp = 0;
		if(xStart > xEnd) {
			tmp = xStart;
			xStart = xEnd;
			xEnd = tmp;
		}else if(yStart > yEnd) {
			tmp = yStart;
			yStart = yEnd;
			yEnd = tmp;
		}
		
		this.width = xEnd - xStart;
		this.height = yEnd - yStart;
		this.hitbox = new Rectangle(xStart, yStart, this.width, this.height);
//		setPointMatrix();
	}
	
	public void setPointMatrix() {
		double x = Math.round(this.getMinX());
		double y = Math.round(this.getMinY());
		
		for(int i=0; i<this.getWidth(); i++) {
			for(int k=0; k<this.getHeight(); k++) {		
				this.pointMatrix.add(new Point2D(x,y));
				y++;
			}
			x++;
		}
	}
	
	public boolean calculateConsistency(Hitbox box) {
		boolean consistent = false;
		
		double hitboxHalfWidth = this.getWidth()/2;
		double hitboxHalfHeight = this.getHeight()/2;
		if(box.getMinX()+box.getWidth() > this.getMinX()+hitboxHalfWidth &&
				box.getMinY()+box.getHeight() > this.getMinY() + hitboxHalfHeight) {
			consistent = true;
		}
	
		
		return consistent;
	}
	
	
	
	
	public List<Point2D> getPointMatrix(){
		return this.pointMatrix;
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
	
	public double getWidth() {
		return this.width;
	}
	
	public double getHeight() {
		return this.height;
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
