package main;

import logic.hanoi.*;
import gui.Gui;
import javafx.application.*;

public class App{
	
	private TowerSet towerSet;
	private int amountTowers;
	private int towerHeight;
//	private int numberSystem;

	public App() {
		this.towerSet = new TowerSet();
//		this.numberSystem = TowerSet.getDefaultNumberSystem();
		this.amountTowers = TowerSet.getDefaultAmount();
		this.towerHeight = TowerSet.getDefaultHeight();
	}

	public App(int amountTowers, int towerHeight) {
		this.amountTowers = amountTowers;
		this.towerHeight = towerHeight;
		this.towerSet = new TowerSet(amountTowers, towerHeight);
	}
	
	public int getAmountTowers() {
		return this.amountTowers;
	}
	public int getTowerHeight() {
		return this.towerHeight;
	}
	
	public TowerSet getTowerSet() {
		return this.towerSet;
	}
	
	public static void main(String args[]) {
		Application.launch(Gui.class);
	}

	
}
