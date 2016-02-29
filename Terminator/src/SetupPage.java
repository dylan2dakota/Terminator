import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

import javafx.geometry.Insets;

public class SetupPage extends Application{

	private BorderPane borderPane;//Border Pane for scene
	private MenuBar menuBar;// MenuBar
	private Menu menuFile, menuOptions, menuHelp; //Menus
	private MenuItem miReset, miExit, miAbout; //Menu items
	private CheckMenuItem miRandom; //Check menu items

	@Override //Override start method in Application class
	public void start(Stage primaryStage) {
		
		//Create a BorderPane container for scene layout
		borderPane = new BorderPane();
		//Create a GridPane container for user inputs/buttons
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		//Create labels and buttons
		grid.add(new Label("Point Density:"), 0, 0);
		grid.add(new TextField(), 1, 0);
		grid.add(new Label("Sensor Angle:"), 0, 1); 
		grid.add(new TextField(), 1, 1);
		grid.add(new Label("Sensor Range:"), 0, 2);
		grid.add(new TextField(), 1, 2);
		grid.add(new Label("Time Limit:"), 0, 3);
		grid.add(new TextField(), 1, 3);
		grid.add(new Button("Start"), 1, 4);
		//Create Menu Bar
		menuBar = new MenuBar();
		//Create Menus
		menuFile = new Menu("File");
		menuOptions = new Menu("Options");
		menuHelp = new Menu("Help");
		//Create Menu Items
		miReset = new MenuItem("Reset");
		miExit = new MenuItem("Exit");
		miAbout = new MenuItem("About");
		//Add items to respective menus
		menuFile.getItems().addAll(miReset,miExit);
		menuOptions.getItems().addAll(miRandom);
		menuHelp.getItems().add(miAbout);
		//Add menus to menu bar
		//menuBar.getMenus().addAll(menuFile, menuOptions, menuHelp);
		
		//Create a scene and place it in the stage
		Scene scene = new Scene(borderPane, 1000, 700);
		borderPane.setTop(menuBar);
		borderPane.setCenter(grid);
		primaryStage.setTitle("Setup Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
