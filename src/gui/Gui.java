package gui;
import main.App;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import logic.hanoi.Tower;
import logic.hanoi.TowerSet;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
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
		System.out.println(WindowWidth+", "+WindowHeight);
		
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

//	private void drawTower(TowerSet towerSet) {
//		int coloumn = 0;
//		int rows = 0;
//		BorderPane bp = new BorderPane();
//		
//		int setLength = towerSet.getTowerSetLength();
//		double coloumnConstraint = (Math.pow(setLength, -1));
//		System.out.println(coloumnConstraint+", "+setLength);
//		baseShowcase.setGridLinesVisible(true);
//		
//		Tower[] tower = towerSet.getTowerSet();
//		
//		ColumnConstraints col = new ColumnConstraints();
//		col.setMinWidth(coloumnConstraint*baseShowcase.getPrefWidth());
////		col.setPercentWidth(coloumnConstraint);
//		System.out.println(col.getPercentWidth());
//		for(Tower t : tower) {
//			bp.setCenter(t.getTowerImage());
//			baseShowcase.getColumnConstraints().add(col);
//			System.out.println(baseShowcase.getColumnConstraints());
//			baseShowcase.add(t.getTowerImage(), coloumn, rows);
//			coloumn += 1;
////			rows += 1;
//		}
//	}
	
	public void drawTower(GraphicsContext gc, TowerSet towerSet) {
		
		
		int groundOffsetX = 100;
		int groundOffsetY = 100;
		int towerHeightOffset = 45*2;
		int towerWidthOffset = 40;
		double canvasHeight = gc.getCanvas().getHeight();
		double canvasWidth = gc.getCanvas().getWidth();
		System.out.println(canvasWidth+", "+canvasHeight);
		double newY = canvasHeight-towerHeightOffset;
		double newX = groundOffsetX;
//		gc.strokeText("Images", 100, 600);
		
		Tower[] tower = towerSet.getTowerSet();
		int setLength = towerSet.getTowerSetLength();
		
		double towerIntervall = gc.getCanvas().getWidth()*Math.pow(setLength, -1);
		int i = 0;
		for(Tower t : tower) {
			List<Image> twFile = t.getTowerImage().getImage();
			for(Image img : twFile) {
				System.out.println("newX: " + newX + ", newY: " + newY);
				gc.drawImage(img, newX, newY);
				newY -= towerHeightOffset;
				drawPlate(gc, t);
				gc.fillText("Part "+i, newX+20, newY+towerHeightOffset);
				i += 1;
			}
			i = 0;
			newY = canvasHeight-towerHeightOffset;
			newX += groundOffsetX;
		}
		
	}
	
	public void drawPlate(GraphicsContext gc, Tower tower) {
		
	}
	
	@Override
	public void start(Stage primaryStage) {

		BorderPane root = new BorderPane();
		Canvas showCase = new Canvas(WindowWidth, WindowHeight);
		GraphicsContext gc = showCase.getGraphicsContext2D();
		drawTower(gc, app.getTowerSet());
		root.setCenter(showCase);
		root.setTop(menu);
		
		//Draw Towers ToDo
//		drawTower(app.getTowerSet());

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
		

		primaryStage.setTitle("Tuerme von Hanoi");
		primaryStage.setScene(new Scene(root,WindowWidth, WindowHeight));
		primaryStage.show();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
