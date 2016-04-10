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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Label;
import javafx.geometry.Insets;

/**
 * @author Team
 *
 */
public class SetupPage extends Application {

	Integer numberRefPoints;
	Integer numberNavPoints;
	static Integer sensorAngle;
	static Integer sensorRange;
	Integer farRangeSensing;
	Integer midRangeSensing;
	Integer closeRangeSensing;
	Integer maxLocationError;
	TextField numberRefPointsInput;
	TextField numberNavPointsInput;
	static TextField sensorAngleInput;
	static TextField sensorRangeInput;
	TextField farRangeSensingInput;
	TextField midRangeSensingInput;
	TextField closeRangeSensingInput;
	TextField maxLocationErrorInput;
	static Point[] referencePoints;
	static Point[] navigationPoints;
	Sensor sensor;
	Robot robot;
	double initialHeading;
	static int mapWidth;
	static int mapHeight;


	@Override
	// Override start method in Application class
	public void start(Stage primaryStage) {

		// Declare panes/menus/menu items
		BorderPane borderPane;// Border Pane for scene
		GridPane grid;// Grid Pane for inputs/start button
		Pane pane;// Pane for Robot
		MenuBar menuBar;// MenuBar
		Menu menuFile, menuOptions, menuHelp; // Menus
		MenuItem miReset, miExit, miAbout; // Menu items
		CheckMenuItem miRandom; // Check menu items
		mapWidth = 650; //Width of 2-D environment
		mapHeight = 700; //Height of 2-D environment

		// Create a BorderPane container for scene layout
		borderPane = new BorderPane();

		// Create a GridPane container for user inputs/buttons
		grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(10);
		grid.setHgap(5);

		// Create text fields and buttons
		Label simulationLabel = new Label("Simulation"); // Label for Setup Page
		// title
		simulationLabel.setFont(Font.font("Times New Roman", 32));
		Label setupLabel = new Label("Setup"); // Label for Setup Page title
		setupLabel.setFont(Font.font("Times New Roman", 32));
		numberRefPointsInput = new TextField("0"); // Number of Points text field
		numberNavPointsInput = new TextField("0");
		sensorAngleInput = new TextField("0"); // Sensor angle text field
		sensorRangeInput = new TextField("0"); // Sensor range text field
		farRangeSensingInput = new TextField("0"); // Far-Range Sensing text field
		midRangeSensingInput = new TextField("0"); // Mid-Range Sensing text field
		closeRangeSensingInput = new TextField("0"); // Close-Range Sensing text field
		maxLocationErrorInput = new TextField("0"); // Max Location Error text field
		Button startButton = new Button("START"); // Start Button
		startButton.setStyle("-fx-background-color: lightgreen;");
		Button generateButton = new Button("GENERATE"); // Generate Button
		generateButton.setStyle("-fx-background-color: lightblue;");
		numberRefPoints = Integer.valueOf(numberRefPointsInput.getText());
		numberNavPoints = Integer.valueOf(numberNavPointsInput.getText());
		sensorAngle = Integer.valueOf(sensorAngleInput.getText());
		sensorRange = Integer.valueOf(sensorRangeInput.getText());
		farRangeSensing = Integer.valueOf(farRangeSensingInput.getText());
		midRangeSensing = Integer.valueOf(midRangeSensingInput.getText());
		closeRangeSensing = Integer.valueOf(closeRangeSensingInput.getText());
		maxLocationError = Integer.valueOf(maxLocationErrorInput.getText());

		// Create Robot
		robot = new Robot(100, 325, 550);

		initialHeading = 90;
		
		// Create Sensor
		sensor = new Sensor(375, 600, sensorRange, sensorAngle, initialHeading);

		// Generate Points
		referencePoints = Generator.generatePoints(numberRefPoints);
		navigationPoints = Generator.generatePoints(numberNavPoints);

		// Create Pane container for Preview
		pane = new Pane();
		pane.setStyle("-fx-background-color: green;");
		pane.setPrefWidth(mapWidth);
		pane.setPrefHeight(mapHeight);
		for(int i=0;i<numberRefPoints;i++){
			pane.getChildren().add(referencePoints[i]);
		}
		for(int j=0;j<numberNavPoints;j++){
			navigationPoints[j].setFill(Color.RED);
			pane.getChildren().add(navigationPoints[j]);
		}
		pane.getChildren().addAll(sensor, robot);


		/* (non-Javadoc)
		 * @see javafx.application.Application#start(javafx.stage.Stage)
		 * 
		 * Handler for generate button
		 */
		generateButton.setOnAction(e -> {
			for(int i=0;i<numberRefPoints;i++){
				pane.getChildren().remove(referencePoints[i]);
			}
			for(int j=0;j<numberNavPoints;j++){
				pane.getChildren().remove(navigationPoints[j]);
			}
			numberRefPoints = getnumberRefPoints();
			numberNavPoints = getnumberNavPoints();
			sensorAngle = getSensorAngle();
			sensorRange = getSensorRange();
			farRangeSensing = getFarRangeSensing();
			midRangeSensing = getMidRangeSensing();
			closeRangeSensing = getCloseRangeSensing();
			maxLocationError = getMaxLocationError();
			pane.getChildren().removeAll(sensor,robot);


			sensor = new Sensor(375, 600, sensorRange, sensorAngle, initialHeading);
			robot = new Robot(100, 325, 550);
			referencePoints = Generator.generatePoints(numberRefPoints);
			navigationPoints = Generator.generatePoints(numberNavPoints);

			pane.getChildren().addAll(sensor,robot);
			for(int k=0;k<numberRefPoints;k++){
				pane.getChildren().add(referencePoints[k]);
			}
			for(int l=0;l<numberNavPoints;l++){
				navigationPoints[l].setFill(Color.RED);
				pane.getChildren().add(navigationPoints[l]);
			}

		});
		
		startButton.setOnAction(e -> {
			NavigationSimulation simulation = new NavigationSimulation();
			simulation.navigate();
		});

		// Add labels, buttons and text fields to grid
		grid.add(simulationLabel, 0, 0);
		grid.add(setupLabel, 1, 0);
		grid.add(new Label("Number of Reference Points:"), 0, 1);
		grid.add(numberRefPointsInput, 1, 1);
		grid.add(new Label("Number of Navigation Points:"), 0, 2);
		grid.add(numberNavPointsInput, 1, 2);
		grid.add(new Label("Sensor Angle:"), 0, 3);
		grid.add(sensorAngleInput, 1, 3);
		grid.add(new Label("Sensor Range:"), 0, 4);
		grid.add(sensorRangeInput, 1, 4);
		grid.add(new Label("Far-Range Sensing (Detection %):"), 0, 5);
		grid.add(farRangeSensingInput, 1, 5);
		grid.add(new Label("Mid-Range Sensing (Detection %):"), 0, 6);
		grid.add(midRangeSensingInput, 1, 6);
		grid.add(new Label("Close-Range Sensing (Detection %):"), 0, 7);
		grid.add(closeRangeSensingInput, 1, 7);
		grid.add(new Label("Max. Location Error:"), 0, 8);
		grid.add(maxLocationErrorInput, 1, 8);
		grid.add(startButton, 1, 9);
		grid.add(generateButton, 0, 9);

		// Create Menu Bar
		menuBar = new MenuBar();
		// Create Menus
		menuFile = new Menu("File");
		menuOptions = new Menu("Options");
		menuHelp = new Menu("Help");
		// Create Menu Items
		miReset = new MenuItem("Reset");
		miExit = new MenuItem("Exit");
		miAbout = new MenuItem("About");
		miRandom = new CheckMenuItem("Random Path");
		miRandom.setSelected(true);
		// Add items to respective menus
		menuFile.getItems().addAll(miReset, miExit);
		menuOptions.getItems().addAll(miRandom);
		menuHelp.getItems().add(miAbout);
		// Add menus to menu bar
		menuBar.getMenus().addAll(menuFile, menuOptions, menuHelp);

		// Event handlers
		miExit.setOnAction(e -> Platform.exit()); // Exit button
		miAbout.setOnAction(e -> showAbout()); // Help menu item

		// Create a scene and place it in the stage
		Scene scene = new Scene(borderPane, 1000, 700);
		borderPane.setTop(menuBar);
		borderPane.setCenter(grid);
		borderPane.setRight(pane);
		primaryStage.setTitle("Setup Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
*/
	// get user input for number of reference points
	private int getnumberRefPoints() {
		numberRefPoints = Integer.valueOf(numberRefPointsInput.getText());
		return numberRefPoints;
	}
	
	// get user input for number of navigation points
		private int getnumberNavPoints() {
			numberNavPoints = Integer.valueOf(numberNavPointsInput.getText());
			return numberNavPoints;
		}

	// get user input for sensor angle
	public static int getSensorAngle() {
		sensorAngle = Integer.valueOf(sensorAngleInput.getText());
		return sensorAngle;
	}

	// get user input for sensor range
	public static int getSensorRange() {
		sensorRange = Integer.valueOf(sensorRangeInput.getText());
		return sensorRange;
	}

	// get user input for far-range sensing
	private int getFarRangeSensing() {
		farRangeSensing = Integer.valueOf(farRangeSensingInput.getText());
		return farRangeSensing;
	}

	// get user input for mid-range sensing
	private int getMidRangeSensing() {
		midRangeSensing = Integer.valueOf(midRangeSensingInput.getText());
		return midRangeSensing;
	}

	// get user input for close-range sensing
	private int getCloseRangeSensing() {
		closeRangeSensing = Integer.valueOf(closeRangeSensingInput.getText());
		return closeRangeSensing;
	}

	// get user input for max location error
	private int getMaxLocationError() {
		maxLocationError = Integer.valueOf(maxLocationErrorInput.getText());
		return maxLocationError;
	}

	// help menu for user
	private void showAbout() {

		final String helpText = "This Setup page allows the user to input Number of Points, Sensor Angle, Sensor Range, and Time Limit for the navigation simulation."
				+ "The Number of Points defines the total number of navigation points the environment."
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

	public static Point[] getNavPoints() {
		return navigationPoints;
	}
	
	public static Point[] getRefPoints() {
		return referencePoints;
	}
	
	public static int getMapWidth() {
		return mapWidth;
	}
	
	public static int getMapHeight() {
		return mapHeight;
	}

}