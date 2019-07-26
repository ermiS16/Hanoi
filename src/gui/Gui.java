package gui;
import main.App;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;



public class Gui extends Application implements Observer{
	// Fix Attributes

	private final double REL_WINDOW_SIZE_FACTOR = 0.7;		//70% of screen size
	
	private TowerSet towerSet;
	
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
	private TextField varAmountTowers;
	private TextField varNumber;
	private ComboBox<String> pickNumberSystem;



	// Necessary Stuff
	private App app;
	
	@Override
	public void init() {

		//For New Entry

		varAmountTowers = new TextField();
		varAmountTowers.setPromptText("Anzahl der Tuerme");
		varNumber = new TextField();
		varNumber.setPromptText("Zahl");

		pickNumberSystem = new ComboBox<>(FXCollections.observableArrayList(
				"Dual","3","4","5","6","7","Oktal","9",
				"Decimal","11","12","13","14","15","Hexadecimal"));
		pickNumberSystem.setValue(pickNumberSystem.getItems().get(0));
		
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

		
		app = createScene();
		setInitObjects(app);
	}
	
	private void setInitObjects(App application) {
		
	}
	
	public App createScene(){
		App application = new App();
		return application;
	}

	private void drawTower(TowerSet towerSet) {
		int coloumn = 0;
		int rows = 0;
		BorderPane bp = new BorderPane();
		
		int setLength = towerSet.getTowerSetLength();
		double coloumnConstraint = (Math.pow(setLength, -1));
		System.out.println(coloumnConstraint+", "+setLength);
		baseShowcase.setGridLinesVisible(true);
		
		Tower[] tower = towerSet.getTowerSet();
		
		ColumnConstraints col = new ColumnConstraints();
		col.setPercentWidth(coloumnConstraint);
		System.out.println(col.getPercentWidth());
		baseShowcase.getColumnConstraints().add(col);
		
		for(Tower t : tower) {

			baseShowcase.add(t.getTowerImage(), coloumn, rows);
			coloumn += 1;
			rows += 1;
		}
	}
	
	@Override
	public void start(Stage primaryStage) {

		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		BorderPane root = new BorderPane();

		root.setCenter(baseShowcase);
		root.setTop(menu);
		
		//Draw Towers ToDo
		drawTower(app.getTowerSet());

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
		primaryStage.setScene(new Scene(root,screenBounds.getWidth()*REL_WINDOW_SIZE_FACTOR,
										screenBounds.getHeight()*REL_WINDOW_SIZE_FACTOR));
		primaryStage.show();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
