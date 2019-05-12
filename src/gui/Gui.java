package gui;
import main.App;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import logic.hanoi.Tower;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class Gui extends Application implements Observer{
	// Fix Attributes
	private final int DEFAULT_AMOUNT_TOWERS = 3;
	private final int DEFAULT_NUMBER = 7; 
	private final int DEFAULT_BITWIDTH = 3;
	private final int DEFAULT_NUMBER_SYSTEM = 2;
	
	// Fix GUI Elemets
	private Button quit;
	private Button info;
	private Button reset;
	private GridPane base;
		
	// User pick Elemets
	private TextField varAmountTowers;
	private TextField varNumber;
	private int numberSystem;
	private ComboBox<String> pickNumberSystem;

	// Variable GUI Elements
	private ArrayList<Tower> towers;

	// Necessary Stuff
	private App app;
	
	@Override
	public void init() {
		numberSystem = DEFAULT_NUMBER_SYSTEM;
		varAmountTowers = new TextField();
		varAmountTowers.setPromptText("Anzahl der Türme");
		varNumber = new TextField();
		varNumber.setPromptText("Zahl");
		quit = new Button("exit");
		info = new Button("info");
		reset = new Button("reset");
		pickNumberSystem = new ComboBox<>(FXCollections.observableArrayList(
				"1","2","3","4","5","6","7","8","9",
				"10","11","12","13","14","15","16"));
		pickNumberSystem.setValue("2");
		base = new GridPane();
		base.add(info, 0, 0);
		base.add(quit, 1, 0);
		base.add(reset, 2, 0);
		base.add(varAmountTowers, 0, 2);
		base.add(varNumber, 0, 3);
		base.add(pickNumberSystem, 0, 4);
		
		app = createScene();
		setInitObjects(app);
	}
	
	private void setInitObjects(App application) {
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
		HanoiCanvas canvas = new HanoiCanvas(app, 1500, 600);
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
		
		primaryStage.setTitle("Türme von Hanoi");
		primaryStage.setScene(new Scene(root,1500,600));
		primaryStage.show();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
