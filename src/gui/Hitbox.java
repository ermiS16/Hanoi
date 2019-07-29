package gui;

import javafx.scene.shape.Rectangle;

public class Hitbox {
	
	private double xStart;
	private double xEnd;
	private double yStart;
	private double yEnd;
	
	public Hitbox() {
		xStart = 0;
		xEnd = 0;
		yStart = 0;
		yEnd = 0;
	}
	
//	public Hitbox(double xStart, double xEnd, double yStart, double yEnd) {
//		this.xStart = xStart;
//		this.xEnd = xEnd;
//		this.yStart = yStart;
//		this.yEnd = yEnd;
//	}
	
	public void setHitbox(double xStart, double xEnd, double yStart, double yEnd) {
		this.xStart = xStart;
		this.xEnd = xEnd;
		this.yStart = yStart;
		this.yEnd = yEnd;
	}
}
