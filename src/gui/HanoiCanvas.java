package gui;

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
	

	public HanoiCanvas(int width, int height, App newApp) {
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
		for(int i=0; i<amountTowers; i++) {
			for(int k=0; k<app.getTowerHeight(i);k++) {
				drawTower(gc,app.getTower(i));				
			}
		}
		
	}
	
	private void drawTower(GraphicsContext gc, Tower tower) {
		TowerImage towerImg = new TowerImage();
		gc.drawImage(towerImg.getTowerImage(), 100, 50);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
