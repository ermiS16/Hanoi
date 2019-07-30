package gui;

import javafx.scene.shape.Rectangle;
import javafx.geometry.Bounds;

public class Hitbox {
	
	private Rectangle hitbox;
	private double xStart;
	private double xEnd;
	private double yStart;
	private double yEnd;
	private boolean isHit;
	
	public Hitbox() {
		xStart = 0;
		xEnd = 0;
		yStart = 0;
		yEnd = 0;
		hitbox = new Rectangle();
	}
	
//	public Hitbox(double xStart, double xEnd, double yStart, double yEnd) {
//		this.xStart = xStart;
//		this.xEnd = xEnd;
//		this.yStart = yStart;
//		this.yEnd = yEnd;
//		
//		double width = xEnd - xStart;
//		double height = yStart - yEnd;
//		this.hitbox = new Rectangle(xStart, yStart, width, height);
//	}
	
	public void setHitbox(double xStart, double xEnd, double yStart, double yEnd) {
		this.xStart = xStart;
		this.xEnd = xEnd;
		this.yStart = yStart;
		this.yEnd = yEnd;
		
		double width = xEnd - xStart;
		double height = yStart - yEnd;
		this.hitbox = new Rectangle(xStart, yStart, width, height);
	}
	
	public boolean isHit() {
		return this.isHit;
	}
	
	public void setHit(boolean hit) {
		this.isHit = hit;
	}
	public double getX() {
		return this.hitbox.getX();
	}
	
	public double getY() {
		return this.hitbox.getY();
	}
	
	public Bounds getLayoutBounds() {
		return this.hitbox.getLayoutBounds();
	}
	
	public boolean contains(double x, double y) {
		return this.hitbox.contains(x, y);
	}
}
