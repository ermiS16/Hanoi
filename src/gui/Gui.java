package gui;

import main.App;
import logic.hanoi.Plate;
import logic.hanoi.Tower;
import logic.hanoi.TowerSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.canvas.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ContextMenuEvent;


public class Gui extends Application implements Observer{
		
	// Fix Attributes
	private final double REL_WINDOW_SIZE_FACTOR = 0.7;		//70% of screen size

	// Setting Attributes
	private Rectangle2D screenBounds;
	private double WindowWidth;
	private double WindowHeight;
	
	//Mouse Context Menu
	private ContextMenu mouseContextMenu;
	
	// Fix GUI Elemets
	private MenuItem quit;
	private MenuItem info;
	private MenuItem help;
	private MenuItem reset;
	private MenuItem newEntry;
	private MenuItem save;
	private MenuItem saveAs;
	private MenuItem export;
	private MenuItem exportAs;
	private MenuItem open;
	private MenuItem showParameters;
	private MenuBar menu;
	private Menu file;
	private Menu settings;
	private Menu about;
		
	//New Entry Windowelements
	private GridPane newEntryWindow;

	// User pick Elemets
	private Separator seperator;
	private Button newEntryOk;
	private Button newEntryCancel;
	private TextField varAmountTowers;
	private TextField varNumber;
	private ComboBox<String> pickNumberSystem;

	// Necessary Stuff
	private App app;
	private Tower[] towerSet;
	private int amountPlatesHit;
	private Tower platesFrom;
	
	/**
	 * Initialization of GUI Elements.
	 */
	@Override
	public void init() {
		
		// Setting Window Resolution
		screenBounds = Screen.getPrimary().getVisualBounds();
		WindowWidth = screenBounds.getWidth()*REL_WINDOW_SIZE_FACTOR;
		WindowHeight = screenBounds.getHeight()*REL_WINDOW_SIZE_FACTOR;
		
//		//For New Entry
		newEntryWindow = new GridPane();
		varAmountTowers = new TextField();
		varAmountTowers.setPromptText("Anzahl der Tuerme");
		varNumber = new TextField();
		varNumber.setPromptText("Zahl");
		pickNumberSystem = new ComboBox<>(FXCollections.observableArrayList(
				"2","3","4","5","6","7","8","9",
				"10","11","12","13","14","15","16"));
		pickNumberSystem.setValue(pickNumberSystem.getItems().get(0));
		
		newEntryOk = new Button ("OK");
		newEntryCancel = new Button("Cancel");
		seperator = new Separator();
		seperator.setPrefHeight(50);
		
		newEntryWindow.add(varAmountTowers, 0, 0);
		newEntryWindow.add(varNumber,0, 1);
		newEntryWindow.add(pickNumberSystem, 0, 2);
		newEntryWindow.add(seperator,0, 3);
		newEntryWindow.add(newEntryOk, 0, 4);
		newEntryWindow.add(newEntryCancel, 1, 4);

		//Mouse Context Menu
		mouseContextMenu = new ContextMenu();
		
		//Menu Items
		quit = new MenuItem("exit");
		info = new MenuItem("info");
		reset = new MenuItem("reset");
		newEntry = new MenuItem("new");
		save = new MenuItem("save");
		saveAs = new MenuItem("save as...");
		export = new MenuItem("export");
		exportAs = new MenuItem("export as...");
		open = new MenuItem("open");
		showParameters = new MenuItem("Show Parameters");
		quit = new MenuItem("quit");
		reset = new MenuItem("reset");
		info = new MenuItem("info");
		help = new MenuItem("help");
		
		mouseContextMenu.getItems().addAll(newEntry, reset);
		
		//Menu
		menu = new MenuBar();
		file = new Menu("File");
		file.getItems().addAll(newEntry, open, save, saveAs, export, exportAs, quit);
		settings = new Menu("Settings");
		settings.getItems().addAll(reset, showParameters);
		about = new Menu("About Us");
		about.getItems().addAll(info, help);
		menu.getMenus().addAll(file,settings,about);
		
		amountPlatesHit = 0;
		platesFrom = null;
		app = createScene();
		setInitObjects(app);
	}
	
	/**
	 * Inits dynamic attributes, that got changed during the Session.
	 * @param application A new Application with a new TowerSet.
	 */
	private void setInitObjects(App application) {	
		this.towerSet = app.getTowerSet().getTowers();
	}
	
	/**
	 * Creates an Application with standard values for the Towers.
	 * @return the current Application
	 */
	public App createScene(){
		App application = new App();
		return application;
	}
	
	/**
	 * Draws all Towers from a TowerSet with its containing Plates
	 * 
	 * @param gc GraphicContext of the Canvas on which the Towers are drawn.
	 * @param towerSet Set of Towers, which will be drawn.
	 */
	public void drawTower(GraphicsContext gc, TowerSet towerSet) {
		
		int setLength = towerSet.getTowerSetLength();
		
		//Scalingfactor for x-axis.
		final double scalingFactorX = setLength+1;
		final double scalingFactorY = 3;
		
		//Distance between Towers
		final double groundOffsetX = gc.getCanvas().getWidth()*Math.pow(scalingFactorX, -1);
		
		//Resolution of the Canvas
		final double canvasHeight = gc.getCanvas().getHeight();
		final double canvasWidth = gc.getCanvas().getWidth();
		
		//Physical Shape parameter of the Tower
		final double towerPhysicalWidth = 20;
			//Also begin on y-axis
		final double towerPhysicalHeight = gc.getCanvas().getHeight()/scalingFactorY;
		
		//Gap between Tower and Text
		double textGap = 30;
		
		//To Center the Text
		double textCenterOffset = towerPhysicalWidth/2;
		
		// Start for the drawing of the Towers
		double newY = towerPhysicalHeight;
		double newX = groundOffsetX;
		
		int towerIndex = 1;
		
		//Draw all Towers
		for(Tower t : this.towerSet) {
			gc.setFill(Color.BLACK);
			
			//Hitbox for ClickEvents
			t.getHitbox().setHitbox(newX, newX+towerPhysicalWidth, newY, newY-towerPhysicalHeight);
			
			//Save Position for Tower
			t.getPosition().setPosition(newX, newY);
			
			//Save Physical Height and Width for Tower
			t.setPhysicalParameters(towerPhysicalHeight, towerPhysicalWidth);
			
			//Draw new Tower
			if(t.getHitbox().isHit()) gc.setFill(Color.YELLOW);
			gc.setLineWidth(towerPhysicalWidth);
			gc.fillRect(newX, newY, towerPhysicalWidth, towerPhysicalHeight);
			
			//Name of Tower
			gc.setLineWidth(0.1);
			gc.strokeText("Tower "+towerIndex, newX-textCenterOffset, newY+towerPhysicalHeight+textGap);
			
			drawPlates(gc, t);

			//New Position for the next Tower
			newX += groundOffsetX;
			newY = towerPhysicalHeight;
			
			towerIndex++;
		}
	}
	
	public void setHits(){
		
	}
	
	/**
	 * Draws the Plates from a specific Tower on the Canvas
	 * 
	 * @param gc The GraphicContext of the Canvas
	 * @param tower Tower wich Plates shall be Drawn
	 */
	public void drawPlates(GraphicsContext gc, Tower tower) {
		List<Plate> plateList = tower.getPlates();
		
		//Plates must exist
		if(!plateList.isEmpty()) {

			//Plate Attributes
			double plateGap = 2;
			double width = 150;
			double height = 15;
			double plateWidthFactor = .7;
			
			//Initializing Startposition
			double newX = 0;
			double newY = tower.getPosition().getY()+tower.getPhysicalHeight();
	
			gc.setLineWidth(height);
			
			for(Plate p : plateList) {
				if(p.isGhost()) gc.setFill(Color.GRAY);
				else gc.setFill(Color.BLACK);
				
				//Set Color if Hitbox is hit
				if(p.getHitbox().isHit()) gc.setFill(Color.YELLOW);
				
				//Save Physical Height and Width for Tower
				p.setPhysicalParameters(width, height);
				
				//Set the Position on the X-Axis
				newX = tower.getPosition().getX()+(tower.getPhysicalWidth()/2)-(width/2);
				
				//Draw the Plate
				gc.fillRect(newX, newY, p.getPhysicalWidth(), p.getPhysicalHeight());
				
				//Set the Hitbox for the Plate
				p.getHitbox().setHitbox(newX, newX+width, newY, newY-height);

				//Setting the new Startposition
				width *= plateWidthFactor;
				newY -= height+plateGap;
			}
		}
	}
	
	public int getAmountPlatesHit() {
		return this.amountPlatesHit;
	}
	
	public void countAmountPlatesHit() {
		this.amountPlatesHit++;
	}
	
	public void resetAmountPlatesHit() {
		this.amountPlatesHit = 0;
	}
	
	public void platesFrom(Tower t) {
		this.platesFrom = t;
	}
	
	public Tower getPlatesFrom() {
		return this.platesFrom;
	}
	
	public boolean movePlates(Tower from, Tower to, int amount) {
		if (to == from) {
			System.out.println("same");
			return false;
		}
		
		int lsba = from.getLSBAValue();
		
		//if the smallest plate on the receiving tower is smaller than this lsba, this can't be done
		if (lsba > to.getLSBAValue()) {
			System.out.println("bigger on other");
			return false;
		}
		
		//check if enough plates can be removed
		int amountOfValue = from.getAmount(lsba);
		
		if (amountOfValue < amount) {
			return false;
		}
		
		from.removeOfValue(amount, lsba, amountOfValue == amount);
		
		//if there are only ghosts on this tower, remove them
		from.clearGhostTower();
		
		//add plates to receiving tower
		to.addPlates(lsba, amount);
		
		//
		from.recalculate();
		to.recalculate();
		
		return true;
	}
	
	/**
	 * Start for the GUI
	 * Interface for Useractivity
	 */
	@Override
	public void start(Stage primaryStage) {
		
		//Main Elements
		GridPane root = new GridPane();
		Canvas showCase = new Canvas(WindowWidth, WindowHeight);
		GraphicsContext gc = showCase.getGraphicsContext2D();

		root.add(menu, 0, 0);
		root.add(showCase, 0, 1);

		//Show mouse context menu on right click.
		showCase.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			public void handle(ContextMenuEvent e) {
				mouseContextMenu.show(primaryStage, e.getScreenX(), e.getScreenY());
			}
		});
		
		//Reset the towerSet as to begin of the session.
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				gc.clearRect(0, 0, showCase.getWidth(), showCase.getHeight());
				for(Tower t : towerSet) {
					t.getHitbox().setHit(false);
				}
			}
		});

		
		Stage newWindow = new Stage();
		newWindow.setScene(new Scene(newEntryWindow,200,200));
		newEntry.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				newWindow.show();

				newEntryOk.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent e) {
						gc.clearRect(0, 0, showCase.getWidth(), showCase.getHeight());
						
						//Read Input
						String amountTowersString = varAmountTowers.getCharacters().toString();
						String numberString = varNumber.getCharacters().toString();
						String pickedSystemString = pickNumberSystem.getValue().toString();
						
						//Checks if Input is a Integer.
						try {
							//Parse Input into real Numbers if possible
							int amountTowers = Integer.parseInt(amountTowersString);
							int number = Integer.parseInt(numberString);
							int pickedSystem = Integer.parseInt(pickedSystemString);
							
							//Creating a new App and initializie it
							app = new App(amountTowers, number, pickedSystem);
							setInitObjects(app);
							newWindow.close();
	
						//Input is not a Integer
						}catch(NumberFormatException exception) {
	
							//Alert if Input is can't parse into an Integer
							Alert invalidInput = new Alert(AlertType.WARNING);
							invalidInput.setTitle("Warnung!");
							invalidInput.setHeaderText("Falsche Eingabe");
							invalidInput.show();
						}
					}
				});
				
				newEntryCancel.setOnAction(new EventHandler<ActionEvent>() {
					 @Override public void handle(ActionEvent ev) {
						 newWindow.close();
					 }
				});
			}
		});

		showCase.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {
					boolean towerHit = false;
					Tower from = null;
					gc.clearRect(0, 0, showCase.getWidth(), showCase.getHeight());
					gc.setFill(Color.BLACK);
					gc.fillOval(e.getX()-5, e.getY()-5, 10, 10);
					int towerIndex = 1;
					int plateIndex = 1;
					for(Tower t : towerSet) {
						if(!t.getPlates().isEmpty()) {
							for(Plate p : t.getPlates()) {
								if(p.getHitbox().contains(e.getX(), e.getY())) {
									if(!p.getHitbox().isHit()) {
										platesFrom(t);
										p.getHitbox().setHit(true);
										countAmountPlatesHit();
									}
									else {
										p.getHitbox().setHit(false);
									}
								}
								plateIndex++;
							}
						}
						if(t.getHitbox().contains(e.getX(), e.getY())) {
							if(!t.getHitbox().isHit()) {
								t.getHitbox().setHit(true);
								from = getPlatesFrom();
								movePlates(from, t, getAmountPlatesHit());
//								from.movePlates(t, getAmountPlatesHit());
								resetAmountPlatesHit();
							}
							else t.getHitbox().setHit(false);
						}
					}
			}
		});
		
		showCase.setOnMouseDragEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				
			}
		});
		
		quit.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Platform.exit();
				System.exit(0);
			}
		});
		
		info.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				String contentText = "Autoren: Eric Misfeld, Jonathan Dransfeld\n"
						+ "Version 0.5, 26.07.2019\n\n"
						+ "Dieses Programm veranschaunlicht das Zaehlen\n"
						+ "in verschiedenen Zahlensystemen anhand der\n"
						+ "Tuerme von Hanoi.";
				Alert information = new Alert(AlertType.INFORMATION);
				information.setTitle("Information");
				information.setHeaderText("Informationen zum Programm");
				information.setContentText(contentText);
				information.showAndWait();
			}
		});
		
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				drawTower(gc,app.getTowerSet());
			}
		}.start();
		
		primaryStage.setTitle("Tuerme von Hanoi");
		primaryStage.setScene(new Scene(root,WindowWidth, WindowHeight));
		primaryStage.show();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
