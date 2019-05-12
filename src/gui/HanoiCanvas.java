package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
		Image img = new Image("file:/assets/test_block.png");
		for(int index=0; index<amountTowers; index++) {
			drawTower(gc, app.getTower(index));	
		}
	}
	
	private void drawTower(GraphicsContext gc, Tower tower) {
		gc.save();
		int offsetX = 10;
		int offsetY = 30;
		gc.strokeText("Images", 100, 100);
		gc.strokeLine(tower.getValue()*offsetX,550, tower.getValue()*10, 450);
		gc.restore();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}