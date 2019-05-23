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
import logic.hanoi.TowerSet;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class Gui extends Application implements Observer{
	// Fix Attributes
	private final int DEFAULT_AMOUNT_TOWERS = 3;
	private final int DEFAULT_NUMBER = 7; 
	private final int DEFAULT_BITWIDTH = 3;
	private final int DEFAULT_NUMBER_SYSTEM = 2;
	
	private TowerSet towerSet;
	
	// Fix GUI Elemets
	private Button quit;
	private Button info;
	private Button reset;
	private GridPane base;
	private GridPane baseFunction;
	private GridPane baseParameters;
	private GridPane baseInfo;
	private GridPane towerHitBox;
	private Label showParameter;	
	
	// User pick Elemets
	private TextField varAmountTowers;
	private TextField varNumber;
	private static int numberSystem;
	private ComboBox<String> pickNumberSystem;

	// Variable GUI Elements
	private ArrayList<Tower> towers;
	private String labelBackgroundClicked;
	private String labelBackgroundUnclicked;
	private boolean labelClicked;
	
	// Necessary Stuff
	private App app;
	
	@Override
	public void init() {
		labelClicked = false;
		labelBackgroundClicked = "-fx-background-color: green; -fx-text-fill: white";
		labelBackgroundUnclicked = "-fx-background.color: grey; -fx-text-fill: black;";
		numberSystem = TowerSet.getDefaultNumberSystem();
		varAmountTowers = new TextField();
		varAmountTowers.setPromptText("Anzahl der Türme");
		varNumber = new TextField();
		varNumber.setPromptText("Zahl");
		quit = new Button("exit");
		info = new Button("info");
		reset = new Button("reset");
		showParameter = new Label("TestLabel");
		pickNumberSystem = new ComboBox<>(FXCollections.observableArrayList(
				"Dual","3","4","5","6","7","Oktal","9",
				"Decimal","11","12","13","14","15","Hexadecimal"));
		pickNumberSystem.setValue(pickNumberSystem.getItems().get(0));
		base = new GridPane();
		baseFunction = new GridPane();
		baseParameters = new GridPane();
		baseInfo = new GridPane();
		towerHitBox = new GridPane();
		baseFunction.add(info, 0, 0);
		baseFunction.add(quit, 1, 0);
		baseFunction.add(reset, 2, 0);
		baseParameters.add(varAmountTowers, 0, 2);
		baseParameters.add(varNumber, 0, 3);
		baseParameters.add(pickNumberSystem, 0, 4);
		baseInfo.add(showParameter, 0, 4);
		base.add(baseFunction, 0, 0);
		base.add(baseParameters, 0, 1);
		base.add(baseInfo, 0, 2);
//		base.add(info, 0, 0);
//		base.add(quit, 1, 0);
//		base.add(reset, 2, 0);
//		base.add(varAmountTowers, 0, 2);
//		base.add(varNumber, 0, 3);
//		base.add(pickNumberSystem, 0, 4);
		
		app = createScene();
		setInitObjects(app);
	}
	
	private void setInitObjects(App application) {
		
	}
	
	public App createScene(){
		App application = new App();
		return application;
	}

	
	@Override
	public void start(Stage primaryStage) {
		HanoiCanvas canvas = new HanoiCanvas(app, 650, 1150);
		canvas.drawApplication(app.getTowerSet());

		BorderPane root = new BorderPane();
		root.setCenter(canvas);
		root.setRight(base);
		
		showParameter.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				if(!labelClicked) {
					showParameter.setStyle(labelBackgroundClicked);	
					labelClicked = true;
				}else {
					showParameter.setStyle(labelBackgroundUnclicked);
					labelClicked = false;
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
		primaryStage.setScene(new Scene(root,1500,800));
		primaryStage.show();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
