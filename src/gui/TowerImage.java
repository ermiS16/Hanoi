package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class TowerImage {
	List<Image> towerImg;
	List<File> imgFiles;
	private File file;
	private Image towerParts;
	private double width;
	
	public TowerImage(int height) {
		file = new File("tower_part.png");
		towerParts = new Image("tower_part.png");
		towerImg = new ArrayList<>();
		imgFiles = new ArrayList<>();
		
		for(int i=0; i<height; i++) {
			towerImg.add(towerParts);
			imgFiles.add(file);
		}
		if(!imgFiles.isEmpty()) {
			width = imgFiles.size();
		}else {
			width = 0;
		}
	}
	
	public List<File> getTowerImage() {
		return imgFiles;
	}
	
	public double getImageHeight() {
		return imgFiles.size();
	}
	
	public double getImageWidth() {
		return this.width;
	}
}
