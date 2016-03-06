import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Label;
import javafx.geometry.Insets;

public class SetupPage extends Application{

	@Override //Override start method in Application class
	public void start(Stage primaryStage) {

		//Declare panes/menus/menu items
		BorderPane borderPane;//Border Pane for scene
		GridPane grid;//Grid Pane for inputs/start button
		Pane pane;//Pane for Robot
		MenuBar menuBar;// MenuBar
		Menu menuFile, menuOptions, menuHelp; //Menus
		MenuItem miReset, miExit, miAbout; //Menu items
		CheckMenuItem miRandom; //Check menu items

		//Create Robot
		Robot robot = new Robot(100,325,550);
		
		//Create Sensor
		Sensor sensor = new Sensor(375,600,150,50);

		//Create Pane container for Robot
		pane = new Pane();
		pane.setStyle("-fx-background-color: green;");
		pane.setPrefWidth(650);
		pane.getChildren().addAll(sensor,robot);
		
		//Create a BorderPane container for scene layout
		borderPane = new BorderPane();

		//Create a GridPane container for user inputs/buttons
		grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(10);
		grid.setHgap(5);
		
		//Create labels and buttons
		Label simulationLabel = new Label("Simulation"); //Label for Setup Page title
		simulationLabel.setFont(Font.font("Times New Roman", 32));
		grid.add(simulationLabel,0,0);
		Label setupLabel = new Label("Setup"); //Label for Setup Page title
		setupLabel.setFont(Font.font("Times New Roman", 32));
		grid.add(setupLabel,1,0);
		grid.add(new Label("Point Density:"), 0, 1); //Point Density UI
		grid.add(new TextField(), 1, 1);
		grid.add(new Label("Sensor Angle:"), 0, 2); //Sensor Angle UI
		grid.add(new TextField(), 1, 2);
		grid.add(new Label("Sensor Range:"), 0, 3); //Sensor Range UI
		grid.add(new TextField(), 1, 3);
		grid.add(new Label("Time Limit:"), 0, 4); //Time Limit UI
		grid.add(new TextField(), 1, 4);
		grid.add(new Button("Start"), 1, 5); //Start Button

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
		miRandom = new CheckMenuItem("Random Path");
		miRandom.setSelected(true);
		//Add items to respective menus
		menuFile.getItems().addAll(miReset,miExit);
		menuOptions.getItems().addAll(miRandom);
		menuHelp.getItems().add(miAbout);
		//Add menus to menu bar
		menuBar.getMenus().addAll(menuFile, menuOptions, menuHelp);

		//Event handlers
		miExit.setOnAction(e -> Platform.exit()); //Exit button
		miAbout.setOnAction(e -> showAbout()); //Help menu item

		//Create a scene and place it in the stage
		Scene scene = new Scene(borderPane, 1000, 700);
		borderPane.setTop(menuBar);
		borderPane.setCenter(grid);
		borderPane.setRight(pane);
		primaryStage.setTitle("Setup Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	private void showAbout(){

		final String helpText = "This Setup page allows the user to input Point Density, Sensor Angle, Sensor Range, and Time Limit for the navigation simulation."
				+ "The Point Density defines the number of points per unit area of the environment."
				+ "The Sensor Angle defines the angle between the robot's heading and the boundary of the robot's point sensor."
				+ "The Sensor Range defines the radius of the point sensor."
				+ "The Time Limit specifies the maximum amount of time the simulation can run."
				+ "To begin the simulation, press the Start button.";

		// Create the text label
		Label helpLabel = new Label();
		helpLabel.setWrapText(true);
		helpLabel.setTextAlignment(TextAlignment.CENTER);
		helpLabel.setFont(Font.font("Times New Roman", 14));
		helpLabel.setText(helpText);

		// Add the label to a StackPane
		StackPane aboutPane = new StackPane();
		aboutPane.getChildren().add(helpLabel);

		// Create and display said the pane in a new stage 	
		Scene scene = new Scene(aboutPane, 550, 100);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Navigation Simulation Setup");
		stage.setResizable(false);
		stage.show();
	}

}