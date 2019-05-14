package gui;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import logic.hanoi.Tower;
import main.App;

public class HanoiCanvas extends Canvas implements Observer {

	private final App app;
	private final int hanoiCanvasWidth;
	private final int hanoiCancasHeight;
	

	public HanoiCanvas(App newApp, int height, int width) {
		super(width,height);
		this.app = newApp;
		hanoiCancasHeight = height;
		hanoiCanvasWidth = width;
	}
	
	public void drawApplication(int amountTowers) {
		GraphicsContext gc = getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, getWidth(), getHeight());

		//Draw Towers

//		Image img = new Image("tower_part.png");
//		System.out.println(img.getProgress());
//		System.out.println(img.isError());
//		System.out.println(img.getException());
//		if(img.isError()) System.out.println("Image Null");
//		else System.out.println("Image Loaded");
//		gc.drawImage(img, 100, 100);
		for(int index=0; index<amountTowers; index++) {
			drawTower(gc, app.getTower(index));	
		}
	}
	
	private void drawTower(GraphicsContext gc, Tower tower) {
		gc.save();
		System.out.println(tower.getHeight());
		TowerImage imgFile = new TowerImage(tower.getHeight());
		List<File> twFile = imgFile.getTowerImage();
		int offsetX = 30;
		int offsetY = 45;
		double newHeight = this.getHeight()-offsetY;
//		gc.strokeText("Images", 100, 600);
		for(File file : twFile) {
			Image twImg = new Image(file.getPath());
			gc.drawImage(twImg, 100, newHeight);
			newHeight -= offsetY;
		}
		
		gc.restore();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}