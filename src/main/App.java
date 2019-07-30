package main;
import java.util.Observable;

import logic.hanoi.*;
import gui.Gui;
import javafx.application.*;

public class App extends Observable{
	
	private TowerSet towerSet;
	private int amountTowers;
	private int towerHeight;
	private int numberSystem;

	public App() {
		towerSet = new TowerSet();
	}

	public App(int amountTowers, int towerHeight, int numberSystem) {
		this.amountTowers = amountTowers;
		this.towerHeight = towerHeight;
		this.numberSystem = numberSystem;
		this.towerSet = new TowerSet(amountTowers, towerHeight, numberSystem);
	}
	
	public int getAmountTowers() {
		return this.amountTowers;
	}
	public int getTowerHeight() {
		return this.towerHeight;
	}
	public int getNumberSystem() {
		return this.numberSystem;
	}
	
	public TowerSet getTowerSet() {
		return this.towerSet;
	}
	
	public static void main(String args[]) {
		Application.launch(Gui.class);
	}

	
}
