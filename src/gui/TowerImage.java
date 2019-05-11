package gui;

import javafx.scene.image.Image;

public class TowerImage {
	private final Image towerImg;
	private double height;
	private double width;
	
	public TowerImage() {
		towerImg = new Image("assets/tower_part");
		height = towerImg.getHeight();
		width = towerImg.getWidth();
	}
	
	public Image getTowerImage() {
		return towerImg;
	}
	
	public double getImageHeight() {
		return height;
	}
	
	public double getImageWidth() {
		return width;
	}
	
	
}
