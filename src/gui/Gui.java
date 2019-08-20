package gui;

import main.App;
import main.MenuFile;
import logic.hanoi.DifficultyLevel;
import logic.hanoi.GameModes;
import logic.hanoi.MoveHandler;
import logic.hanoi.Plate;
import logic.hanoi.Tower;
import java.io.File;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.canvas.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ContextMenuEvent;

/**
 * The GUI of the Application
 * @author Eric
 * @version 1.0
 */


public class Gui extends Application {

	// Fix Attributes
	private final double REL_WINDOW_SIZE_FACTOR = 0.7; // 70% of screen size

	// Setting Attributes
	private Rectangle2D screenBounds;
	private double windowWidth;
	private double windowHeight;
	private boolean sameSave;
	private boolean sameExport;

	// Mouse Context Menu
	private ContextMenu mouseContextMenu;

	// Fix GUI Elemets
	private MenuItem quit;
	private MenuItem info;
	private MenuItem help;
	private MenuItem reset;
	private MenuItem newSession;
	private MenuItem save;
	private MenuItem saveAs;
	private MenuItem export;
	private MenuItem exportAs;
	private MenuItem open;
	private MenuItem towerValue;
	private MenuItem plateValue;
	private MenuBar menu;
	private Menu file;
	private Menu settings;
	private Menu about;

	// New Entry Windowelements
	private GridPane newSessionWindow;
	
	//File relevated stuff
	private FileChooser fileChooserOpen;
	private FileChooser fileChooserSave;
	private FileChooser fileChooserExport;
	private File saveDirectory;
	private File exportDirectory;

	// User pick Elemets
	private Slider bitWidth;
	private Label bitWidthValue;
	private Slider amountTower;
	private Label amountTowerValue;
	private Separator separator0;
	private Separator separator1;
	private Separator separator2;
	private Separator separator3;
	private Button newSessionOk;
	private Button newSessionCancel;
	private TextField varAmountTowers;
	private Label labelAmountTowers;
	private Label labelBitWidth;
	private Label labelNumber;
	private TextField varBitWidth;
	private Label varNumber;
	private Rectangle2D dragSelect;

	
	//Tower relevated stuff
	private static final int MAX_AMOUNT_TOWERS = 6;
	private static final int MIN_AMOUNT_TOWERS = 3;
	private static final int MAX_BITWIDTH = 16;
	private static final int MIN_BITWIDTH = 3;
	private Tower[] towerSet;
	private boolean showTowerValue;
	
	//Plate relevated stuff
	private boolean showPlateValue;

	// Necessary Stuff
	private App app;
	private MoveHandler moveHandler;

	//Gamemodestuff
	private GameModes gameMode;
	private DifficultyLevel difficulty;
	private double gameTimer;
	
	
	
	/**
	 * Initialization of GUI Elements.
	 */
	@Override
	public void init() {

		// Setting Window Resolution
		screenBounds = Screen.getPrimary().getVisualBounds();
		windowWidth = (screenBounds.getWidth() * REL_WINDOW_SIZE_FACTOR);
		windowHeight = screenBounds.getHeight() * REL_WINDOW_SIZE_FACTOR;

		
//		//For New Entry
		newSessionWindow = new GridPane();
		varAmountTowers = new TextField();
		varAmountTowers.setPromptText("max: "+MAX_AMOUNT_TOWERS);
		varBitWidth = new TextField();
		varBitWidth.setPromptText("max: "+MAX_BITWIDTH);
		varNumber = new Label(""+calculateMaxNumber(MIN_BITWIDTH));

		bitWidth = new Slider();
		bitWidth.setMax(MAX_BITWIDTH);
		bitWidth.setMin(MIN_BITWIDTH);
		bitWidth.setShowTickMarks(true);
		bitWidth.setShowTickLabels(true);
		bitWidth.setMinorTickCount(1);
		bitWidth.setMajorTickUnit(1);
		bitWidth.setValue(MIN_AMOUNT_TOWERS);
		bitWidth.setBlockIncrement(1);
		bitWidthValue = new Label("" + bitWidth.getMin());

		amountTower = new Slider();
		amountTower.setMax(MAX_AMOUNT_TOWERS);
		amountTower.setMin(MIN_AMOUNT_TOWERS);
		amountTower.setShowTickMarks(true);
		amountTower.setShowTickLabels(true);
		amountTower.setMinorTickCount(1);
		amountTower.setMajorTickUnit(1);
		amountTower.setValue(MIN_AMOUNT_TOWERS);
		amountTower.setBlockIncrement(1);
		amountTowerValue = new Label("" + amountTower.getMin());

		labelAmountTowers = new Label("Anzahl Tuerme: ");
		labelBitWidth = new Label("Bitbreite: ");
		labelNumber = new Label("Groesste Zahl: ");

		newSessionOk = new Button("OK");
		newSessionCancel = new Button("Cancel");
		separator0 = new Separator();
		separator0.setMinHeight(10);
		separator0.visibleProperty().setValue(false);

		newSessionWindow.add(separator0, 0, 0);
		newSessionWindow.add(labelAmountTowers, 0, 1);
		newSessionWindow.add(amountTower, 1, 1);
		newSessionWindow.add(amountTowerValue, 2, 1);

		separator1 = new Separator();
		separator1.setMinHeight(20);
		separator1.visibleProperty().setValue(false);
		newSessionWindow.add(separator1, 0, 2);

		newSessionWindow.add(labelBitWidth, 0, 3);
		newSessionWindow.add(bitWidth, 1, 3);
		newSessionWindow.add(bitWidthValue, 2, 3);

		separator2 = new Separator();
		separator2.setMinHeight(20);
		separator2.visibleProperty().setValue(false);
		newSessionWindow.add(separator2, 0, 4);

		newSessionWindow.add(labelNumber, 0, 5);
		newSessionWindow.add(varNumber, 1, 5);

		separator3 = new Separator();
		separator3.setMinHeight(30);
		separator3.visibleProperty().setValue(false);
		newSessionWindow.add(separator3, 0, 6);
		newSessionWindow.add(newSessionOk, 0, 7);
		newSessionWindow.add(newSessionCancel, 1, 7);

		// Mouse Context
		mouseContextMenu = new ContextMenu();
		dragSelect = new Rectangle2D(0, 0, 0, 0);

		// Menu Items
		quit = new MenuItem("exit");
		info = new MenuItem("info");
		reset = new MenuItem("reset");
		newSession = new MenuItem("new");
		save = new MenuItem("save");
		saveAs = new MenuItem("save as...");
		export = new MenuItem("export");
		exportAs = new MenuItem("export as...");
		open = new MenuItem("open");
		towerValue = new MenuItem("Show Tower Value");
		plateValue = new MenuItem("Show Bit Significance");
		quit = new MenuItem("quit");
		reset = new MenuItem("reset");
		info = new MenuItem("info");
		help = new MenuItem("help");

		mouseContextMenu.getItems().addAll(newSession, reset);

		// Menu
		menu = new MenuBar();
		file = new Menu("File");
		file.getItems().addAll(newSession, open, save, saveAs, export, exportAs, quit);
		settings = new Menu("Settings");
		settings.getItems().addAll(reset, towerValue, plateValue);
		about = new Menu("About Us");
		about.getItems().addAll(info, help);
		menu.getMenus().addAll(file, settings, about);

		//FileChooser for Open
		fileChooserOpen = new FileChooser();
		fileChooserOpen.setTitle("Datei Auswaehlen");
		fileChooserOpen.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooserOpen.getExtensionFilters().addAll(
							new FileChooser.ExtensionFilter("All Files (\"*.*\")", "*.*"),
							new FileChooser.ExtensionFilter("Properties (\"*.properties\")", "*.properties"));

		//FileChooser for Save
		fileChooserSave = new FileChooser();
		fileChooserSave.setTitle("Speicherort Auswaehlen");
		fileChooserSave.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooserSave.initialFileNameProperty().set("save.properties");
		fileChooserSave.getExtensionFilters().add(new FileChooser.ExtensionFilter("Properties (\"*.properties\")", "*.properties"));

		//FileChooser for Export
		fileChooserExport = new FileChooser();
		fileChooserExport.setTitle("Speicherort Auswaehlen");
		fileChooserExport.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooserExport.initialFileNameProperty().set("image.png");
		fileChooserExport.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
		
		//Session Stuff
		app = createScene();
		
		sameSave = false;
		sameExport = false;
		showPlateValue = true;
		showTowerValue = true;
		
		moveHandler = new MoveHandler();
		gameMode = GameModes.FREE;
		difficulty = DifficultyLevel.MIDDLE;
		gameTimer = 60;							//in seconds
		
		setInitObjects(app);
	}

	/**
	 * Inits dynamic attributes, that got changed during the Session.
	 * 
	 * @param application A new Application with a new TowerSet.
	 */
	private void setInitObjects(App application) {
		this.towerSet = app.getTowerSet().getTowers();

		// Physical Shape parameter of the Tower
		final double towerWidth = Tower.getMinWidth();
		// Also begin on y-axis
//		final double towerHeight = windowHeight / SCALING_FACTOR_TOWER_Y;
		final double towerHeight = Tower.getMinHeight() * app.getTowerHeight();
//		final double towerHeight = Tower.getMinHeight() * app.getTowerHeight();
		
		double plateWidth = Plate.getMaxWidth();
		double plateHeight = Plate.getMaxHeight();
		double WidthMaxMinDiff = Plate.getMaxWidth() - Plate.getMinWidth();

		// Calculates the right width for the Plate
		double widthScalingStep = WidthMaxMinDiff / app.getTowerHeight();
		for (Tower t : this.towerSet) {
			t.setPhysicalParameters(towerHeight, towerWidth);
			for (Plate p : t.getPlates()) {
				p.setPhysicalParameters(plateWidth, plateHeight);
				plateWidth -= widthScalingStep;
			}
		}
	}
	
	public App restoreDate(App application) {
		return this.app;
	}
	
	/**
	 * Creates an Application with standard values for the Towers.
	 * 
	 * @return the current Application
	 */
	private App createScene() {
		return new App();
	}

//-------------------Helper Methods--------------//




	
	private void setSaveDirectory(File file) {
		this.saveDirectory = file;
	}
	private File getSaveDirectory() {
		return this.saveDirectory;
	}
	
	private void setExportDirectory(File file) {
		this.exportDirectory = file;
	}
	private File getExportDirectory() {
		return this.exportDirectory;
	}
	
	private void setShowPlateValue(boolean show) {
		this.showPlateValue = show;
	}
	private boolean getShowPlateValue() {
		return this.showPlateValue;
	}
	
	private void setShowTowerValue(boolean show) {
		this.showTowerValue = show;
	}
	private boolean getShowTowerValue() {
		return this.showTowerValue;
	}

	
	public DifficultyLevel getDifficulty() {
		return this.difficulty;
	}
	public void setDifficulty(DifficultyLevel level) {
		this.difficulty = level;
	}
	public GameModes getGameMode() {
		return this.gameMode;
	}
	public void setGameMode(GameModes mode) {
		this.gameMode = mode;
	}
	
	
//----------------------------------------------------//



	/**
	 * Calculates the maximal number that can reprensented with a bitlength
	 * 
	 * @param bitlength
	 * @return max Number, that can be represented
	 */
	private int calculateMaxNumber(int bitlength) {
		int max = 1;
		max <<= (int) bitlength;
		return max - 1;
	}
	

	private void waitToCount() {
		
	}
	
	private void timer() {
		
	}
	/**
	 * Start for the GUI Interface for Useractivity
	 */
	@Override
	public void start(Stage primaryStage) {

		// Main Elements
		GridPane root = new GridPane();
		Canvas showCase = new Canvas(windowWidth, windowHeight);
		GraphicsContext gc = showCase.getGraphicsContext2D();
		
		root.add(menu, 0, 0);
		root.add(showCase, 0, 1);

		// Display of Bitlength Value in the newEntryWindow
		bitWidth.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				bitWidthValue.setText(String.format("%.0f", newValue));
				varNumber.setText("" + calculateMaxNumber((int) bitWidth.getValue()));
			}
		});

		// Display of the toweramount in the newEntryWindow
		amountTower.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				amountTowerValue.setText(String.format("%.0f", newValue));
			}
		});
		// Show mouse context menu on right click.
		showCase.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			public void handle(ContextMenuEvent e) {
				mouseContextMenu.show(primaryStage, e.getScreenX(), e.getScreenY());
			}
		});

		// Reset the towerSet as it was to begin of the session.
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				gc.clearRect(0, 0, showCase.getWidth(), showCase.getHeight());
				app = new App(app.getAmountTowers(), app.getTowerHeight());
				setInitObjects(app);
			}
		});

		//New Session Section
		Stage newWindow = new Stage();
		newWindow.setScene(new Scene(newSessionWindow, 350, 250));
		newSession.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				newWindow.show();

				newSessionOk.setOnAction(new EventHandler<ActionEvent>() {
					@Override 
					public void handle(ActionEvent e) {
						gc.clearRect(0, 0, showCase.getWidth(), showCase.getHeight());

						// Read Input
						String amountTowersString = String.valueOf(amountTower.getValue());
						String bitWidthString = String.valueOf(bitWidth.getValue());

						// Checks if Input is a Integer.
						try {
							// Parse Input into real Numbers if possible
							int amountTowers = (int) Double.parseDouble(amountTowersString);
							int newBitWidth = (int) Double.parseDouble(bitWidthString);

							// Creating a new App and initializie it
							app = new App(amountTowers, newBitWidth);
							setInitObjects(app);
							newWindow.close();
							sameSave = false;
							sameExport = false;
							
							// Input is not a Integer
						} catch (NumberFormatException exception) {

							// Alert if Input is can't parse into an Integer
							Alert invalidInput = new Alert(AlertType.WARNING);
							invalidInput.setTitle("Warnung!");
							invalidInput.setHeaderText("Falsche Eingabe");
							invalidInput.show();
							exception.printStackTrace();
						}
					}
				});

				//new Parameters are not taken over, and newSession Window is closed.
				newSessionCancel.setOnAction(new EventHandler<ActionEvent>() {
					@Override 
					public void handle(ActionEvent ev) {
						newWindow.close();
					}
				});
			}
		});
		
		//Save a Session
		saveAs.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				boolean saved = false;
				
				//Set the Path for the savefile
				setSaveDirectory(fileChooserSave.showSaveDialog(primaryStage));
				File dir = getSaveDirectory();
				
				//Only if Directory exist
				if(dir != null) {
					//Saving the current Session with it's parameters
					saved = MenuFile.save(app.getTowerSet(), app.getAmountTowers(), 
						app.getTowerHeight(), dir.getAbsolutePath());						
				}
			}
		});
		
		//Nearly Same as saveAs, only to handle what will happen when first clicked
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent e) {
				File dir = null;
				boolean saved = false;
				
				//If it's the same session, the SaveDirectory is already known
				if(sameSave) {
					dir = getSaveDirectory();
				//If it's the a "first" session, than the User has to determine a location
				}else {
					setSaveDirectory(fileChooserSave.showSaveDialog(primaryStage));
					dir = getSaveDirectory();
					sameSave = true;
				}
				
				//Only if Directory exist
				if(dir != null) {
					//Saving the current Session with it's parameters
					saved = MenuFile.save(app.getTowerSet(),app.getAmountTowers(),
							app.getTowerHeight(), dir.getAbsolutePath());
				}
			}
		});
		
		
		//Handles the Open of a Save
		open.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				File file = fileChooserOpen.showOpenDialog(primaryStage);
				if(file != null) {
					try {
						app = MenuFile.open(file, app);	
					}catch (NullPointerException npe) {
						npe.printStackTrace();
					}
					sameSave = false;
					sameExport = false;
					gc.clearRect(0, 0, showCase.getWidth(), showCase.getHeight());
					setInitObjects(app);
				}
			}
		});
		
		//Handle the Export as an Image. Mostly same as exportAs. but handles what happen, when first clicked.
		export.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e) {
				File file = null;
				boolean exported = false;
				if(sameExport) {
					file = getExportDirectory();
				}else {
					setExportDirectory(fileChooserExport.showSaveDialog(primaryStage));
					file = getExportDirectory();
					sameExport = true;
				}
				if(file != null) {
					exported = MenuFile.export(file, showCase);
				}
			}
		});
		
		//Handle the Export of the canvas as an Image.
		exportAs.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				File file = fileChooserExport.showSaveDialog(primaryStage);
				boolean exported = false;
				if(file != null) {
					exported = MenuFile.export(file,showCase);
				}
			}
		});
		
		//Quit and ends the Session
		quit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Platform.exit();
				System.exit(0);
			}
		});

		towerValue.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e) {
				if(showTowerValue) setShowTowerValue(false);
				else setShowTowerValue(true);
			}
		});
		
		plateValue.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(showPlateValue) setShowPlateValue(false);
				else setShowPlateValue(true);
			}
		});
		
		//Info about the Program, the authors and the version.
		info.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String contentText = "Autoren: Eric Misfeld, Jonathan Dransfeld\n" + "Version 1.0, 12.08.2019\n\n"
						+ "Dieses Programm veranschaunlicht das Zaehlen\n" + "im Binaersystem anhand der\n"
						+ "Tuerme von Hanoi.";
				Alert information = new Alert(AlertType.INFORMATION);
				information.setTitle("Information");
				information.setHeaderText("Informationen zum Programm");
				information.setContentText(contentText);
				information.showAndWait();
			}
		});
		
		
		
		//Handles the Mouseclicks on Plates an Towers.
		showCase.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				boolean moved = false;
				Tower from = null;
				gc.clearRect(0, 0, showCase.getWidth(), showCase.getHeight());
				gc.setFill(Color.BLACK);
//				System.out.println("X: " + e.getX() + ", Y: " + e.getY());
				//A small Oval for showing, where the user clicked on the canvas.
				gc.fillOval(e.getX() - 5, e.getY() - 5, 10, 10);
				switch(getDifficulty()) {
				case EASY: moveHandler.clickLogicEasy(towerSet, e.getX(), e.getY());
							break;
				case MIDDLE: moveHandler.clickLogicMiddle(towerSet, e.getX(), e.getY());
							break;
				case HARD : moveHandler.clickLogicHard(towerSet, e.getX(), e.getY());
							break;
				case IMPOSSIBLE: break;
				default: break;
				}

				
			}
		});

		showCase.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				moveHandler.setDragStartX(e.getX());
				moveHandler.setDragStartY(e.getY());
				System.out.println("X: " + e.getX() + ", Y: " + e.getY());	
			}
		});
		
		showCase.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				
				moveHandler.setDragEndX(e.getX());
				moveHandler.setDragEndY(e.getY());
				double width = moveHandler.getDragEndX() - moveHandler.getDragEndX();
				double height = moveHandler.getDragStartY() - moveHandler.getDragEndY();
				gc.clearRect(0, 0, showCase.getWidth(), showCase.getHeight());
				CanvasUtilitys.drawMouseSelect(gc, moveHandler.getDragStartX(), moveHandler.getDragEndX(), 
													moveHandler.getDragStartY(), moveHandler.getDragEndY());
			}
		});
		
		showCase.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("Mousedrag Exit");
			}
		});
		
		double time = 0;
		
		//A Timer, that creates an "endless" loop
		new AnimationTimer() {
			@Override
			public void handle(long now) {
//				switch(gamemode) {
//				case FREE: break;
//				case CHALLENGE: break;
//				}
//				drawTower(gc, app.getTowerSet());
				
				CanvasUtilitys.drawTower(gc, app.getTowerSet().getTowers(), showTowerValue);
				CanvasUtilitys.drawPlates(gc, app.getTowerSet().getTowers(), showPlateValue);
				CanvasUtilitys.drawCountdown(gc, time);
			}
		}.start();

		//Set up the Main Window.
		primaryStage.setTitle("Tuerme von Hanoi");
		primaryStage.setScene(new Scene(root, windowWidth, windowHeight));
		primaryStage.show();
	}
}
