package main;

import logic.hanoi.*;
import gui.Gui;
import javafx.application.*;

/**
 * Main Class of the Project. Represents an Application.
 * @author Eric
 * @verion 1.0
 *
 */

public class App{
	
	private static final GameModes STANDARD_GAMEMODE = GameModes.FREE;
	private static final DifficultyLevel STANDARD_DIFFICULTY = DifficultyLevel.MIDDLE;
	private static final long GAME_TIMER_START = Timer.getGameTimerMiddle();
	
	private TowerSet towerSet;
	private int amountTowers;
	private int towerHeight;
	private GameModes gameMode;
	private DifficultyLevel difficulty;
	private long gameTime;

	public App() {
		this.towerSet = new TowerSet();
		this.amountTowers = TowerSet.getDefaultAmount();
		this.towerHeight = TowerSet.getDefaultHeight();
		this.gameMode = STANDARD_GAMEMODE;
		this.difficulty = STANDARD_DIFFICULTY;
		this.gameTime = GAME_TIMER_START;
	}

	public App(int amountTowers, int towerHeight, GameModes gm, DifficultyLevel di, long gt) {
		this.amountTowers = amountTowers;
		this.towerHeight = towerHeight;
		this.towerSet = new TowerSet(amountTowers, towerHeight);
		this.gameMode = gm;
		this.difficulty = di;
		this.gameTime = gt;
		
	}
	
	public App(int amountTowers, int towerHeight, TowerSet towerSet, GameModes gm, DifficultyLevel di, long gt) {
		this.amountTowers = amountTowers;
		this.towerHeight = towerHeight;
		this.towerSet = towerSet;
		this.gameMode = gm;
		this.difficulty = di;
		this.gameTime = gt;
	}
	
	public long getTimer() {
		return this.gameTime;
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
	
	public GameModes getGameMode() {
		return this.gameMode;
	}
	
	public DifficultyLevel getDifficulty() {
		return this.difficulty;
	}
	
	public static void main(String args[]) {
		Application.launch(Gui.class);
	}

	
}
