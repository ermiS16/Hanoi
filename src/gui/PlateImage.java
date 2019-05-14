package gui;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class PlateImage {
	List<Image> plateImage;
	private final Image start;
	private final Image mid;
	private final Image end;
	private final double height;
	private final double width;
	
	
	public PlateImage(int width) {
		start = new Image("start_plate.png");
		mid = new Image("mid_plate.png");
		end = new Image("end_plate.png");
		this.width = width;
		this.height = start.getHeight();
		
		plateImage = new ArrayList<>();
		plateImage.add(start);
		for(int i=0; i<width;i++) {
			plateImage.add(mid);
		}
		plateImage.add(end);
		
	}
	
	public double getImageWidth() {
		return this.width;
	}
	public double getImageHeight() {
		return this.height;
	}
	public List<Image> getPlateImage(){
		return plateImage;
	}
}
