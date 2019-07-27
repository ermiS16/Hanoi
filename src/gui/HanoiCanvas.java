package gui;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import logic.hanoi.Tower;
import logic.hanoi.TowerSet;
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
	
	public void drawApplication(TowerSet towerSet) {
		GraphicsContext gc = getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, getWidth(), getHeight());
		
		
		//Draw Towers

		for(int index=0; index<towerSet.getTowerSetLength(); index++) {
			drawTower(gc, towerSet.getTower(index));	
		}
	}
	
	private void drawTower(GraphicsContext gc, Tower tower) {
		gc.save();
		System.out.println(tower.getHeight());
		List<Image> twFile = tower.getTowerImage().getImage();
		int groundOffsetX = 30;
		int groundOffsetY = 100;
		int towerHeightOffset = 45;
		int towerWidthOffset = 40;
		double newHeight = this.getHeight()-groundOffsetY;
//		gc.strokeText("Images", 100, 600);
		for(Image img : twFile) {
			gc.drawImage(img, 100, newHeight);
			newHeight -= towerHeightOffset;
			drawPlate(gc, tower);
		}
		
		gc.restore();
	}
	
	public boolean towerClicked() {
		
		return false;
	}
	
	private void drawPlate(GraphicsContext gc, Tower tower) {
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
