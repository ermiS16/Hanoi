package main;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import gui.Gui;
import javafx.application.*;
import javafx.scene.image.Image;
import logic.hanoi.Tower;

public class App extends Observable{
	
	private ArrayList<Tower> tower;

	public App() {
		tower = new ArrayList<>();
	}
		
	public void addTower(Tower newTower) {
		tower.add(newTower);
	}
	
	public int getTowerHeight(int index) {
		return tower.get(index).getHeight();
	}
	
	public Tower getTower(int index) {
		Tower t = tower.get(index);
		return t;
	}
	
	public List<Tower> getTowers(){
		return this.tower;
	}
	
	public int getAmountTowers() {
		return tower.size();
	}
	
	public Image getTowerImage() {
		return getTowerImage();
	}
	
	public static void main(String args[]) {
		Application.launch(Gui.class);
	}

	
}
