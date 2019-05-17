package main;
import java.util.Observable;

import logic.hanoi.*;
import gui.Gui;
import javafx.application.*;
import javafx.scene.image.Image;

public class App extends Observable{
	
	private TowerSet towerSet;

	public App() {
		towerSet = new TowerSet();
	}

	public App(int amountTowers, int towerHeight, int numberSystem) {
		towerSet = new TowerSet(amountTowers, towerHeight, numberSystem);
	}
	
	public TowerSet getTowerSet() {
		return this.towerSet;
	}
	
	public Image getTowerImage() {
		return getTowerImage();
	}
	
	public static void main(String args[]) {
		Application.launch(Gui.class);
	}

	
}
