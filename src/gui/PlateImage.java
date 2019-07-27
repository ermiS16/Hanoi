package gui;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class PlateImage {
	
//	List<Image> plateImage;
//	private final Image start;
//	private final Image mid;
//	private final Image end;
//	private final double height;
//	private final double width;
	
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
	
//	public PlateImage(int width) {
//		start = new Image("start_plate.png");
//		mid = new Image("mid_plate.png");
//		end = new Image("end_plate.png");
//		this.width = width;
//		this.height = start.getHeight();
//		
//		plateImage = new ArrayList<>();
//		plateImage.add(start);
//		for(int i=0; i<width;i++) {
//			plateImage.add(mid);
//		}
//		plateImage.add(end);
//		
//	}
	
	public double getImageWidth() {
		return this.width;
	}
	public double getImageHeight() {
		return PLATE_PART_HEIGHT;
	}
	public Rectangle getPlateImage(){
		return plateImg;
	}
	
//	public List<Image> getPlateImage(){
//		return plateImage;
//	}
}
