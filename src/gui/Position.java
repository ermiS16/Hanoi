package gui;

import javafx.geometry.Point2D;

/**
 * Represents a Position as a Point on a 2D coordination system
 * @author Eric
 * @version 1.0
 */

public class Position {
	private Point2D pos;
	
	public Position() {
		this.pos = new Point2D(0,0);
	}
		
	public void setPosition(double x, double y) {
		this.pos = new Point2D(x, y);
	}

	public double getX() {
		return this.pos.getX();
	}
	
	public double getY() {
		return this.pos.getY();
	}
}
