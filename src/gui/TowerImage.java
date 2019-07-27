package gui;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.scene.shape.Rectangle;


public class TowerImage {


//	List<Image> towerImg;
	
	private Image towerParts;
	private double width;
	private int height;
//	private Rectangle towerImg;
	private ArrayList<Image> towerImg;
	
	private final int TOWER_PART_HEIGHT = 45; 	// in px
	private final int TOWER_PART_WIDTH = 25;
	
//	public TowerImage(int height) {
//		towerImg = new Rectangle(TOWER_PART_WIDTH, TOWER_PART_HEIGHT*height);
//		towerImg.setFill(Color.BLACK);
//		width = TOWER_PART_WIDTH;
//		this.height = height;
//	}
	
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
	
	public List<Image> getImage() {
		return towerImg;
	}
	
//	public Rectangle getTowerImage() {
//		return towerImg;
//	}
	
	public double getImageHeight() {
		return this.height;
	}
	
	public double getImageWidth() {
		return this.width;
	}
}
