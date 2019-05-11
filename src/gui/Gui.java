package gui;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.math.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import logic.hanoi.Plate;
import logic.hanoi.Tower;
import main.App;
import main.NumberSystem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


public class Gui extends Application implements Observer{
	// Fix Attributes
//	private final int MINIMUM = 3;
//	private final int MAXIMUM = 10;
	private final int DEFAULT_AMOUNT_TOWERS = 3;
//	private final int DEFAULT_AMOUNT_PLATES = 3;
	private final int DEFAULT_NUMBER = 7; 
	private final int DEFAULT_BITWIDTH = 3;
	private final int DEFAULT_NUMBER_SYSTEM = 2;
	
	// Fix GUI Elemets
	private Button quit;
//	private Button ok;
	private Button info;
	private Button reset;
//	private GridPane root;
	private GridPane base;
//	private GridPane baseFunction;
//	private GridPane baseParameter;
		
	// User pick Elemets
	private TextField varAmountTowers;
	private TextField varNumber;
	private int numberSystem;
	private ComboBox<String> pickNumberSystem;
	// Variable GUI Elements
	private ArrayList<Tower> towers;
//	private Plate[] plates;
//	private Label[][] values;

	// Necessary Stuff
	private App app;
//	private boolean isActive;
	
	@Override
	public void init() {
//		bitWidth = 3;
		numberSystem = DEFAULT_NUMBER_SYSTEM;
		varAmountTowers = new TextField();
		varAmountTowers.setPromptText("Anzahl der Türme");
		varNumber = new TextField();
		varNumber.setPromptText("Zahl");
		quit = new Button("exit");
//		ok = new Button("ok");
		info = new Button("info");
		reset = new Button("reset");
		pickNumberSystem = new ComboBox<>(FXCollections.observableArrayList(
				"1","2","3","4","5","6","7","8","9",
				"10","11","12","13","14","15","16"));
		pickNumberSystem.setValue("2");
//		root = new GridPane();
		base = new GridPane();
		base.add(info, 0, 0);
		base.add(quit, 1, 0);
		base.add(reset, 2, 0);
		base.add(varAmountTowers, 0, 2);
		base.add(varNumber, 0, 3);
		base.add(pickNumberSystem, 0, 4);
//		baseFunction = new GridPane();
//		baseParameter = new GridPane();
//		root.setVgap(10d);
//		root.setAlignment(Pos.TOP_LEFT);
//		baseFunction.setAlignment(Pos.TOP_CENTER);
//		baseFunction.add(info, 0, 0);
//		baseFunction.add(quit, 1, 0);
//		baseFunction.add(reset, 3, 0);
//		baseParameter.setAlignment(Pos.TOP_CENTER);
//		baseParameter.add(varAmountTowers, 0, 0);
//		baseParameter.add(varNumber, 1, 0);
//		baseParameter.add(pickNumberSystem, 2, 0);
//		base.add(baseParameter, 0, 0);
//		base.add(baseFunction, 0, 1);
		
		app = createScene();
		setInitObjects(app);
	}
	
	private void setInitObjects(App application) {
//		values = new Label[application.getAmountTowers()]
//				[application.getAmountTowers()];
		towers = new ArrayList<>();
		for(Tower t : application.getTowers()) {
			towers.add(t);
		}
	}
	
	public App createScene(){
		App application = new App();
		Tower t;
		for(int i=0; i<DEFAULT_AMOUNT_TOWERS;i++) {
			if(i==0) {
				t = new Tower(DEFAULT_BITWIDTH,
						DEFAULT_NUMBER_SYSTEM, true);
				application.addTower(t);
			}else {
				t = new Tower(DEFAULT_BITWIDTH,
						DEFAULT_NUMBER_SYSTEM, false);
				application.addTower(t);
			}
		}
		return application;
	}

	
	@Override
	public void start(Stage primaryStage) {
		HanoiCanvas canvas = new HanoiCanvas(1000,600,app);
		app = new App();
		app.addObserver(canvas);
		canvas.drawApplication(app.getAmountTowers());
		
		BorderPane root = new BorderPane();
		root.setCenter(canvas);
		root.setRight(base);
		
		quit.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Platform.exit();
				System.exit(0);
			}
		});
		
		info.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				String contentText = "Autoren: Eric Misfeld, Jonathan Dransfeld\n"
						+ "Version 1.0, 12.05.2019\n\n"
						+ "Dieses Programm veranschaunlicht das Zählen\n"
						+ "in verschiedenen Zahlensystemen anhand der\n"
						+ "Türme von Hanoi.";
				Alert information = new Alert(AlertType.INFORMATION);
				information.setTitle("Information");
				information.setHeaderText("Informationen zum Programm");
				information.setContentText(contentText);
				information.showAndWait();
			}
		});
		
		primaryStage.setScene(new Scene(root,1500,600));
		primaryStage.show();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
