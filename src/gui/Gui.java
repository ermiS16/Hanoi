package gui;
import main.App;

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
import logic.hanoi.Plate;
import logic.hanoi.Tower;
import logic.hanoi.TowerSet;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.canvas.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;


public class Gui extends Application implements Observer{
		
	// Fix Attributes
	private final double REL_WINDOW_SIZE_FACTOR = 0.7;		//70% of screen size
	private final double TOWER_PART_IMAGE_HEIGHT = 45;
	private final double PLATE_PART_IMAGE_WIDTH = 40;
	private final double PLATE_PART_IMAGE_HEIGHT = 35;
	
	// Setting Attributes
	private Rectangle2D screenBounds;
	private double WindowWidth;
	private double WindowHeight;
	
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
	private GridPane baseShowcase;
		

	// User pick Elemets
//	private TextField varAmountTowers;
//	private TextField varNumber;
//	private ComboBox<String> pickNumberSystem;



	// Necessary Stuff
	private App app;
//	private TowerSet towerSet;
	
	@Override
	public void init() {
		
		// Setting Screen Resolution
		screenBounds = Screen.getPrimary().getVisualBounds();
		WindowWidth = screenBounds.getWidth()*REL_WINDOW_SIZE_FACTOR;
		WindowHeight = screenBounds.getHeight()*REL_WINDOW_SIZE_FACTOR;
//		System.out.println(WindowWidth+", "+WindowHeight);
		
//		//For New Entry
//		varAmountTowers = new TextField();
//		varAmountTowers.setPromptText("Anzahl der Tuerme");
//		varNumber = new TextField();
//		varNumber.setPromptText("Zahl");
//		pickNumberSystem = new ComboBox<>(FXCollections.observableArrayList(
//				"Dual","3","4","5","6","7","Oktal","9",
//				"Decimal","11","12","13","14","15","Hexadecimal"));
//		pickNumberSystem.setValue(pickNumberSystem.getItems().get(0));
		
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
		
		//Menu
		menu = new MenuBar();
		file = new Menu("File");
		file.getItems().addAll(newEntry, open, save, saveAs, export, exportAs, quit);
		settings = new Menu("Settings");
		settings.getItems().addAll(reset, showParameters);
		about = new Menu("About Us");
		about.getItems().addAll(info, help);
		menu.getMenus().addAll(file,settings,about);
		

		baseShowcase = new GridPane();
		baseShowcase.setPrefWidth(WindowWidth);
		
		app = createScene();
		setInitObjects(app);
	}
	
	private void setInitObjects(App application) {
		
	}
	
	public App createScene(){
		App application = new App();
		return application;
	}
	
	public void drawTower(GraphicsContext gc, TowerSet towerSet) {
		
		Tower[] tower = towerSet.getTowerSet();
		int setLength = towerSet.getTowerSetLength();
		
		//Gap between Tower and Text
		double textGap = 30;
		
		//To Center the Text
		double textCenterOffset = gc.getLineWidth()/2;
		
		//Scalingfactor for x-axis.
		double scalingFactorX = setLength+1;
		double scalingFactorY = 3;
		
		//Distance between Towers
		double groundOffsetX = gc.getCanvas().getWidth()*Math.pow(scalingFactorX, -1);
		//Height of the Tower
		double groundOffsetY = gc.getCanvas().getHeight()/scalingFactorY;
		
		//Resolution of the Canvas
		double canvasHeight = gc.getCanvas().getHeight();
		double canvasWidth = gc.getCanvas().getWidth();
		

		double towerWidth = 20;
		double towerHeight = gc.getCanvas().getHeight()/scalingFactorY;
//		System.out.println(canvasWidth+", "+canvasHeight);
		
		// Start for the drawing of the Towers
		double newY = groundOffsetY;
		double newX = groundOffsetX;
		
		int towerIndex = 1;
		
		//Draw all Towers
		for(Tower t : tower) {
			
			if(t.isHit()) gc.setFill(Color.YELLOW);
			else gc.setFill(Color.BLACK);
			
			//Hitbox for ClickEvents
			t.setHitbox(newX, newX+towerWidth, newY, newY-towerHeight);

			//Draw new Tower
			gc.setLineWidth(towerWidth);
			gc.fillRect(newX, newY, towerWidth, towerHeight);
//			gc.strokeLine(newX, newY+groundOffsetY, newX, newY);
			
			//Name of Tower
			gc.setLineWidth(1);
			gc.strokeText("Tower "+towerIndex, newX-textCenterOffset, newY+towerHeight+textGap);
			
			//New Position for the next Tower
			newX += groundOffsetX;
			newY = groundOffsetY;
			
			towerIndex++;
		}

	}
	
//	public void drawTower(GraphicsContext gc, TowerSet towerSet) {
//		
//		Tower[] tower = towerSet.getTowerSet();
//		int setLength = towerSet.getTowerSetLength();
//		
//		//Scalingfactor for x-axis.
//		double factorForScale = setLength+1;
//		//Distance between Towers
//		double groundOffsetX = gc.getCanvas().getWidth()*Math.pow(factorForScale, -1);
//		//Distance for TowerParts
//		double groundOffsetY = gc.getCanvas().getHeight()/2;								
//		
//		//Resolution of Canvas
//		double canvasHeight = gc.getCanvas().getHeight();
//		double canvasWidth = gc.getCanvas().getWidth();
//		System.out.println(canvasWidth+", "+canvasHeight);
//		
//		//For Placing new Tower
//		double newY = groundOffsetY;
//		double newX = groundOffsetX;		
//		
//		//For tracking the Towers
//		int partIndex = 1;
//		int towerIndex = 1;
//		double towerNameOffsetY = 20;
//		double partNameOffsetX = TOWER_PART_IMAGE_HEIGHT;
//		
//		for(Tower t : tower) {
//			drawPlate(gc, t, newX, newY);
//			gc.fillText("Tower"+towerIndex, newX, newY+TOWER_PART_IMAGE_HEIGHT+towerNameOffsetY);
//			
//			//List of Towerparts for ervery Tower
//			List<Image> twFile = t.getTowerImage().getImage();
//			
//			//Drawing the Tower on Canvas
//			for(Image img : twFile) {
//				System.out.println("newX: " + newX + ", newY: " + newY);
//				
//				//Drawing the current Towerpart and name it
//				gc.drawImage(img, newX, newY);
//				gc.fillText("Part "+partIndex, newX+20, newY+partNameOffsetX);
//				
//				//New Position for the next Towerpart
//				newY -= TOWER_PART_IMAGE_HEIGHT;
//				partIndex += 1;				
//			}
//			partIndex = 1;
//			towerIndex += 1;
//			
//			//Set Position for the next Tower
//			newY = groundOffsetY;
//			newX += groundOffsetX;
//		}
//		
//	}
	
//	public void drawPlate(GraphicsContext gc, Tower tower, double xAxis, double yAxis) {
//		List<Plate> plate = tower.getPlates();
//		for(Plate p : plate) {
//			List<Image> plFile = p.getPlateImage().getImage();
//		}
//	}
	
	@Override
	public void start(Stage primaryStage) {
		GridPane root = new GridPane();
//		BorderPane root = new BorderPane();
		Canvas showCase = new Canvas(WindowWidth, WindowHeight);
		GraphicsContext gc = showCase.getGraphicsContext2D();
//		drawTest(gc,app.getTowerSet());
//		drawTower(gc, app.getTowerSet());
//		root.setCenter(showCase);
//		root.setTop(menu);
		root.add(menu, 0, 0);
		root.add(showCase, 0, 1);
		Tower[] towerSet = app.getTowerSet().getTowerSet();
		
		
		showCase.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {
				gc.clearRect(0, 0, showCase.getWidth(), showCase.getHeight());
				gc.setFill(Color.BLACK);
				gc.fillOval(e.getX()-5, e.getY()-5, 10, 10);
				int towerIndex = 1;
				System.out.println("CX: " + e.getX()+", CY: " + e.getY());
				for(Tower t : towerSet) {
					System.out.println("Tower " + towerIndex + ":");
					System.out.println("HX: " + t.getHitbox().getX()+", HY: "+ t.getHitbox().getY());
					System.out.println("HRX: " + t.getHitbox().getLayoutBounds().getMaxX() + ", HRY: "
										+t.getHitbox().getLayoutBounds().getMaxY());
					if(t.getHitbox().contains(e.getX(), e.getY())) {
						if(!t.isHit()) t.setHit(true);
						else t.setHit(false);
					}
					towerIndex++;
				}
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
				//Clearing the Canvas
//				gc.clearRect(0, 0, showCase.getWidth(), showCase.getHeight());
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
