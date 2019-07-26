package gui;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class PlateImage {
	
private final double width;
private Rectangle plateImg;
private final int DEFAULT_WIDTH = 3;
private final double PLATE_PART_HEIGHT = 35;
private final double PLATE_PART_WIDTH = 40;
	

	public PlateImage() {
		this.width = DEFAULT_WIDTH;
		plateImg = new Rectangle(width*PLATE_PART_WIDTH, PLATE_PART_HEIGHT);
		plateImg.setFill(Color.GRAY);
	}
	
	public PlateImage(int width, boolean isGhost) {
		this.width = width;
		plateImg = new Rectangle(width*PLATE_PART_WIDTH, PLATE_PART_HEIGHT);
		if(!isGhost) plateImg.setFill(Color.GRAY);
		else plateImg.setFill(Color.WHITESMOKE);
	}
	
	public double getImageWidth() {
		return this.width;
	}
	public double getImageHeight() {
		return PLATE_PART_HEIGHT;
	}
	public Rectangle getPlateImage(){
		return plateImg;
	}
}
