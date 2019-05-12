package gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.image.Image;

public class TowerImage {
	List<Image> towerImg;
	private final Image towerParts;
	private double width;
	
	public TowerImage(int height) {
		towerParts = new Image("file:/assets/tower_part.png");
		width = towerParts.getWidth();
		towerImg = new ArrayList<>();

		for(int i=0; i<height; i++) {
			towerImg.add(towerParts);
		}
	}
	
	public List<Image> getTowerImage() {
		return towerImg;
	}
	
	public double getImageHeight() {
		return towerImg.size();
	}
	
	public double getImageWidth() {
		return this.width;
	}
}
