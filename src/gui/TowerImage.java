package gui;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class TowerImage {
	List<Image> towerImg;
	private Image towerParts;
	private double width;
	
	public TowerImage(int height) {
		towerParts = new Image("tower_part.png");
		towerImg = new ArrayList<>();
		
		for(int i=0; i<height; i++) {
			towerImg.add(towerParts);
		}
		if(!towerImg.isEmpty()) {
			width = towerImg.size();
		}else {
			width = 0;
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
