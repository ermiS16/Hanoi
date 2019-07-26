package gui;

import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;


public class TowerImage {

	private double width;
	private int height;
	private Rectangle towerImg;
	
	private final int TOWER_PART_HEIGHT = 45; 	// in px
	private final int TOWER_PART_WIDTH = 25;
	
	public TowerImage(int height) {
		towerImg = new Rectangle(TOWER_PART_WIDTH, TOWER_PART_HEIGHT*height);
		towerImg.setFill(Color.BLACK);
		width = TOWER_PART_WIDTH;
		this.height = height;
	}
	
	public Rectangle getTowerImage() {
		return towerImg;
	}
	
	public double getImageHeight() {
		return this.height;
	}
	
	public double getImageWidth() {
		return this.width;
	}
}
