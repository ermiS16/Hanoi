package gui;

import main.App;
import main.MenuFile;
import logic.hanoi.Plate;
import logic.hanoi.PlateComperator;
import logic.hanoi.Tower;
import logic.hanoi.TowerSet;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.DirectoryChooser;
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
import javafx.scene.control.TextArea;
import javafx.scene.canvas.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ContextMenuEvent;

public class Gui extends Application {

	// Fix Attributes
	private final double REL_WINDOW_SIZE_FACTOR = 0.7; // 70% of screen size

	// Setting Attributes
	private Rectangle2D screenBounds;
	private double WindowWidth;
	private double WindowHeight;

	// Mouse Context Menu
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

	// New Entry Windowelements
	private GridPane newEntryWindow;
	
	//FileChooser
	private FileChooser fileChooserOpen;
	private FileChooser FileChooserSave;
	private TextArea textArea;

	// User pick Elemets
	private Slider bitWidth;
	private Label bitWidthValue;
	private Slider amountTower;
	private Label amountTowerValue;
	private Separator separator0;
	private Separator separator1;
	private Separator separator2;
	private Separator separator3;
	private Button newEntryOk;
	private Button newEntryCancel;
	private TextField varAmountTowers;
	private Label labelAmountTowers;
	private Label labelBitWidth;
	private Label labelNumber;
	private TextField varBitWidth;
	private Label varNumber;

	// Necessary Stuff
	private App app;
	private Tower[] towerSet;
	private int amountPlatesHit;
	private Tower platesFrom;
	private List<Plate> platesToMove;
	private final double SCALING_FACTOR_Y = 3;

	/**
	 * Initialization of GUI Elements.
	 */
	@Override
	public void init() {

		// Setting Window Resolution
		screenBounds = Screen.getPrimary().getVisualBounds();
		WindowWidth = screenBounds.getWidth() * REL_WINDOW_SIZE_FACTOR;
		WindowHeight = screenBounds.getHeight() * REL_WINDOW_SIZE_FACTOR;

//		//For New Entry
		newEntryWindow = new GridPane();
		varAmountTowers = new TextField();
		varAmountTowers.setPromptText("max: 6");
		varBitWidth = new TextField();
		varBitWidth.setPromptText("max: 8");
		varNumber = new Label("7");

		bitWidth = new Slider();
		bitWidth.setMax(8);
		bitWidth.setMin(3);
		bitWidth.setShowTickMarks(true);
		bitWidth.setShowTickLabels(true);
		bitWidth.setMinorTickCount(1);
		bitWidth.setMajorTickUnit(1);
		bitWidth.setValue(3);
		bitWidth.setBlockIncrement(1);
		bitWidthValue = new Label("" + bitWidth.getMin());

		amountTower = new Slider();
		amountTower.setMax(6);
		amountTower.setMin(3);
		amountTower.setShowTickMarks(true);
		amountTower.setShowTickLabels(true);
		amountTower.setMinorTickCount(1);
		amountTower.setMajorTickUnit(1);
		amountTower.setValue(3);
		amountTower.setBlockIncrement(1);
		amountTowerValue = new Label("" + amountTower.getMin());

		labelAmountTowers = new Label("Anzahl Tuerme: ");
		labelBitWidth = new Label("Bitbreite: ");
		labelNumber = new Label("Groeßte Zahl: ");

		newEntryOk = new Button("OK");
		newEntryCancel = new Button("Cancel");
		separator0 = new Separator();
		separator0.setMinHeight(10);
		separator0.visibleProperty().setValue(false);

		newEntryWindow.add(separator0, 0, 0);
		newEntryWindow.add(labelAmountTowers, 0, 1);
		newEntryWindow.add(amountTower, 1, 1);
		newEntryWindow.add(amountTowerValue, 2, 1);

		separator1 = new Separator();
		separator1.setMinHeight(20);
		separator1.visibleProperty().setValue(false);
		newEntryWindow.add(separator1, 0, 2);

		newEntryWindow.add(labelBitWidth, 0, 3);
		newEntryWindow.add(bitWidth, 1, 3);
		newEntryWindow.add(bitWidthValue, 2, 3);

		separator2 = new Separator();
		separator2.setMinHeight(20);
		separator2.visibleProperty().setValue(false);
		newEntryWindow.add(separator2, 0, 4);

		newEntryWindow.add(labelNumber, 0, 5);
		newEntryWindow.add(varNumber, 1, 5);

		separator3 = new Separator();
		separator3.setMinHeight(30);
		separator3.visibleProperty().setValue(false);
		newEntryWindow.add(separator3, 0, 6);
		newEntryWindow.add(newEntryOk, 0, 7);
		newEntryWindow.add(newEntryCancel, 1, 7);

		// Mouse Context Menu
		mouseContextMenu = new ContextMenu();

		// Menu Items
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

		// Menu
		menu = new MenuBar();
		file = new Menu("File");
		file.getItems().addAll(newEntry, open, save, saveAs, export, exportAs, quit);
		settings = new Menu("Settings");
		settings.getItems().addAll(reset, showParameters);
		about = new Menu("About Us");
		about.getItems().addAll(info, help);
		menu.getMenus().addAll(file, settings, about);

		//FileChooser
		fileChooserOpen = new FileChooser();
		fileChooserOpen.setTitle("Datei Auswaehlen");
		fileChooserOpen.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooserOpen.getExtensionFilters().addAll(
							new FileChooser.ExtensionFilter("All Files", "*.*"),
							new FileChooser.ExtensionFilter("Properties", "*.properties"));
		textArea = new TextArea();
		textArea.setMinHeight(70);

		//DirectoryChooser
		FileChooserSave = new FileChooser();
		FileChooserSave.setTitle("Speicherort Auswaehlen");
		FileChooserSave.setInitialDirectory(new File(System.getProperty("user.home")));
		
		amountPlatesHit = 0;
		platesFrom = null;
		platesToMove = new ArrayList<>();
		app = createScene();
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
		final double towerWidth = 20;
		// Also begin on y-axis
		final double towerHeight = WindowHeight / SCALING_FACTOR_Y;

		double plateWidth = Plate.getMaxWidth();
		double plateHeight = Plate.getHeight();
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
	
	public void restoreData(App app) {
		this.app = app;
	}

	/**
	 * Creates an Application with standard values for the Towers.
	 * 
	 * @return the current Application
	 */
	private App createScene() {
//		App application = new App();
		return new App();
//		return application;
	}

//-------------------Helper Methods--------------//

	private void addPlateToMove(Plate p) {
		this.platesToMove.add(p);
	}

	private List<Plate> getPlateToMove() {
		return this.platesToMove;
	}

	private void removePlateToMove(Plate p) {
		if (this.platesToMove.contains(p)) this.platesToMove.remove(p);
	}

	private void removePlateToMoveAll(List<Plate> plates) {
		this.platesToMove.removeAll(plates);
	}

	private int getAmountPlatesHit() {
		return this.amountPlatesHit;
	}

	private void increaseAmountPlatesHit() {
		this.amountPlatesHit++;
	}

	private void decreaseAmountPlatesHit() {
		if (this.amountPlatesHit > 0) this.amountPlatesHit--;
	}

	private void resetAmountPlatesHit() {
		this.amountPlatesHit = 0;
	}

	private void platesFrom(Tower t) {
		this.platesFrom = t;
	}

	private void resetPlatesFrom() {
		this.platesFrom = null;
	}

	private Tower getPlatesFrom() {
		return this.platesFrom;
	}
//----------------------------------------------------//

	/**
	 * Draws all Towers from a TowerSet with its containing Plates
	 * 
	 * @param gc       GraphicContext of the Canvas on which the Towers are drawn.
	 * @param towerSet Set of Towers, which will be drawn.
	 */
	public void drawTower(GraphicsContext gc, TowerSet towerSet) {

		int setLength = towerSet.getTowerSetLength();

		// Scalingfactor for x-axis.
		final double scalingFactorX = setLength + 1;

		// Distance between Towers
		final double groundOffsetX = gc.getCanvas().getWidth() * Math.pow(scalingFactorX, -1);

		// Gap between Tower and Text
		double textGap = 30;

		// To Center the Text
		double textCenterOffset = towerSet.getTowers()[0].getPhysicalWidth() / 2;

		// Start for the drawing of the Towers
		double newY = towerSet.getTowers()[0].getPhysicalHeight();
		double newX = groundOffsetX;

		int towerIndex = 1;

		// Draw all Towers
		for (Tower t : this.towerSet) {
			gc.setFill(Color.BROWN);

			// Hitbox for ClickEvents
			t.getHitbox().setHitbox(newX, newX + t.getPhysicalWidth(), newY, newY - t.getPhysicalHeight());

			// Save Position for Tower
			t.getPosition().setPosition(newX, newY);

			// Draw new Tower
			if (t.getHitbox().isHit()) gc.setFill(Color.YELLOW);
			gc.setLineWidth(t.getPhysicalWidth());
			gc.fillRect(newX, newY, t.getPhysicalWidth(), t.getPhysicalHeight());

			// Name of Tower
			gc.setLineWidth(0.1);
			gc.strokeText("Tower " + towerIndex, newX - textCenterOffset, newY + t.getPhysicalHeight() + textGap);

			// Represantation of Tower in Bits
			gc.strokeText(t.getRepresentation(), newX - textCenterOffset, newY + t.getPhysicalHeight() + (textGap * 2));

			// Represantation of Tower as Value
			gc.strokeText(t.getValue() + "", newX - textCenterOffset, newY + t.getPhysicalHeight() + (textGap * 3));

			drawPlates(gc, t);

			// New Position for the next Tower
			newX += groundOffsetX;
			newY = t.getPhysicalHeight();

			towerIndex++;
		}
	}

	/**
	 * Draws the Plates from a specific Tower on the Canvas
	 * 
	 * @param gc    The GraphicContext of the Canvas
	 * @param tower Tower wich Plates shall be Drawn
	 */
	public void drawPlates(GraphicsContext gc, Tower tower) {
		List<Plate> plateList = tower.getPlates();

		// Plates must exist
		if (!plateList.isEmpty()) {

			// Plate Attributes
			double plateGap = 2;

			// Initializing Startposition
			double newX = 0;
			double newY = tower.getPosition().getY() + tower.getPhysicalHeight();

			for (Plate p : plateList) {
				if (p.getHitbox().isHit()) gc.setFill(Color.YELLOW);
				else gc.setFill(Color.RED);

				// Set the Position on the X-Axis
				newX = tower.getPosition().getX() + (tower.getPhysicalWidth() / 2) - (p.getPhysicalWidth() / 2);

				// Draw the Plate
				gc.setLineWidth(p.getPhysicalHeight());
				gc.fillRect(newX, newY, p.getPhysicalWidth(), p.getPhysicalHeight());

				// Set the Hitbox for the Plate
				p.getHitbox().setHitbox(newX, newX + p.getPhysicalWidth(), newY, newY - p.getPhysicalHeight());

				// Setting the new Startposition
				newY -= p.getPhysicalHeight() + plateGap;
			}
		}
	}

	private boolean movePlates(Tower from, Tower to, List<Plate> plateMoveList) {
		boolean moved = false;
		int platesAmount = platesToMove.size();
		if (to == from) {
			System.out.println("same");
			return false;
		}

		int lsba = from.getLSBAValue();

		// if the smallest plate on the receiving tower is smaller than this lsba, this
		// can't be done
		if (lsba > to.getLSBAValue()) {
			System.out.println("bigger on other");
			return false;
		}

		List<Plate> moveList = new ArrayList<>();
		if (movable(from, getPlateToMove())) {
			for (Plate p : getPlateToMove()) {
				from.removeOfValue(platesAmount, from.getLSBAValue(), false);
				p.getHitbox().setHit(false);
				moveList.add(p);
			}

			// add plates to receiving tower
			to.addPlates(lsba, platesAmount, moveList);

			// Remove all Plates that has moved
			removePlateToMoveAll(getPlateToMove());

			//
			from.recalculate();
			to.recalculate();

			moved = true;

		} else {
			removePlateToMoveAll(getPlateToMove());
			moved = false;
		}
		return moved;
	}

	/**
	 * Checks if a Hitbox is Hit by the Cursor.
	 * 
	 * @param hitbox The Hitbox to be checked
	 * @param x      Coordinate on X-Axis
	 * @param y      Coordinate on y-Axis
	 * @return true if Hit, false otherwise
	 */
	private boolean hitMatch(Hitbox hitbox, double x, double y) {
		if (hitbox.contains(x, y))
			return true;
		else
			return false;
	}

	/**
	 * Checks if a List of Plates is Moable as a whole from a certain Tower
	 * 
	 * @param tower     whose Plates shall be checked
	 * @param plateList List of Plates, that shall be moved.
	 * @return true if Plates can be moved as a whole, false otherwise.
	 */
	private boolean movable(Tower tower, List<Plate> plateList) {
		PlateComperator sortingOrder = new PlateComperator();
		plateList.sort(sortingOrder.PLATE_SORTING_ORDER);

		// Value of Plate on the Top.
		int lsba = tower.getLSBAValue();
		boolean movable = true;

		// Special Cases
		if (tower.getPlates().size() < plateList.size())
			return false;
		else if (tower.getPlates().size() < 1)
			return false;
		else if (tower.getPlates().size() == 1)
			return true;

		// Iterates from the Plate on the Top down, to check if following plates
		// are Contained in the List of Plates, that has to move.
		else {
			for (int i = lsba, k = tower.getPlates().size() - 1; i < plateList.size(); i++, k--) {
				if (plateList.get(i) != tower.getPlates().get(k)) {
					movable = false;
				}
			}
		}
		return movable;
	}

	/**
	 * Calculates the maximal number that can reprensented with a bitlength
	 * 
	 * @param bitlength
	 * @return max Number, that can be represented
	 */
	public int calculateMaxNumber(int bitlength) {
		int max = 1;
		max <<= (int) bitlength;
		return max - 1;
	}
	
	/**
	 * Start for the GUI Interface for Useractivity
	 */
	@Override
	public void start(Stage primaryStage) {
		calculateMaxNumber(8);

		// Main Elements
		GridPane root = new GridPane();
		Canvas showCase = new Canvas(WindowWidth, WindowHeight);
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

		// Reset the towerSet as to begin of the session.
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				gc.clearRect(0, 0, showCase.getWidth(), showCase.getHeight());
				app = new App(app.getAmountTowers(), app.getTowerHeight());
				setInitObjects(app);
			}
		});

		Stage newWindow = new Stage();
		newWindow.setScene(new Scene(newEntryWindow, 350, 250));
		newEntry.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				newWindow.show();

				newEntryOk.setOnAction(new EventHandler<ActionEvent>() {
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

				newEntryCancel.setOnAction(new EventHandler<ActionEvent>() {
					@Override 
					public void handle(ActionEvent ev) {
						newWindow.close();
					}
				});
			}
		});
		
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent e) {
//				MenuFile file = new MenuFile();
				textArea.clear();
				FileChooserSave.initialFileNameProperty().set("save.properties");
				File dir = FileChooserSave.showSaveDialog(primaryStage);
				if(dir != null) {
					textArea.setText(dir.getAbsolutePath());
					MenuFile.save(app.getAmountTowers(), app.getTowerHeight(),
							app.getTowerSet(), dir.getAbsolutePath());
				}else {
					textArea.setText(null);
				}
			}
		});
		
		open.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				textArea.clear();
				List<File> files = new ArrayList<>();
				File file = fileChooserOpen.showOpenDialog(primaryStage);
				if(file != null) {
//					files = Arrays.asList(file);
					MenuFile menuFile = new MenuFile();
					try {
						app = MenuFile.open(file, app);	
					}catch (NullPointerException npe) {
						npe.printStackTrace();
					}
					gc.clearRect(0, 0, showCase.getWidth(), showCase.getHeight());
					setInitObjects(app);
					
				}
			}
		});
		
		showCase.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				boolean moved = false;
				Tower from = null;
				gc.clearRect(0, 0, showCase.getWidth(), showCase.getHeight());
				gc.setFill(Color.BLACK);
				gc.fillOval(e.getX() - 5, e.getY() - 5, 10, 10);
				for (Tower t : towerSet) {
					if (!t.getPlates().isEmpty()) {
						if (t == getPlatesFrom() || getPlatesFrom() == null) {
							for (Plate p : t.getPlates()) {
								// Checks if Hitbox of a Plate is hit
								if (hitMatch(p.getHitbox(), e.getX(), e.getY())) {
									// Checks if Plates already Hit
									if (!p.getHitbox().isHit()) {
										// Saves Tower, from which Plate shall be moved.
										platesFrom(t);
										// Set Hit for Hitbox on true
										p.getHitbox().setHit(true);
										increaseAmountPlatesHit();
										addPlateToMove(p);
									} else {
										p.getHitbox().setHit(false);
										decreaseAmountPlatesHit();
										removePlateToMove(p);
										if (platesToMove.isEmpty())
											platesFrom(null);
									}
								} // if
							} // for
						} // if
					} // if
					if (hitMatch(t.getHitbox(), e.getX(), e.getY())) {
						if (!t.getHitbox().isHit() && getAmountPlatesHit() != 0) {
							t.getHitbox().setHit(true);
							from = getPlatesFrom();
							try {
								moved = movePlates(from, t, getPlateToMove());
								if (moved) {
									resetAmountPlatesHit();
									t.getHitbox().setHit(false);
									from.getHitbox().setHit(false);
									resetPlatesFrom();
								} else {
									t.getHitbox().setHit(false);
								}
							} catch (NullPointerException exception) {
								exception.printStackTrace();
							}
						} else
							t.getHitbox().setHit(false);
					}
				}
			}
		});

		showCase.setOnMouseDragEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {

			}
		});

		quit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Platform.exit();
				System.exit(0);
			}
		});

		info.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String contentText = "Autoren: Eric Misfeld, Jonathan Dransfeld\n" + "Version 0.8, 12.08.2019\n\n"
						+ "Dieses Programm veranschaunlicht das Zaehlen\n" + "im Binaersystem anhand der\n"
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
				drawTower(gc, app.getTowerSet());
			}
		}.start();

		primaryStage.setTitle("Tuerme von Hanoi");
		primaryStage.setScene(new Scene(root, WindowWidth, WindowHeight));
		primaryStage.show();
	}
}
