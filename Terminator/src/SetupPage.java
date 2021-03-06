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
 * The SetupPage class initializes and controls all user inputs. It provides input boundary checks and catches. 
 * It is also responsible for the primary GUI pane where inputs are made to the system and then displays
 * the inputs in an on-screen generated map.
 * 
 * @author Team
 * @version 1.0
 * @created Feb-2016
 */
public class SetupPage extends Application {

	static Integer numberRefPoints;
	static Integer numberNavPoints;
	static Integer sensorAngle;
	static Integer sensorRange;
	static Integer farRangeSensing;
	static Integer midRangeSensing;
	static Integer closeRangeSensing;
	static Integer maxLocationError;
	static Integer robotSpeed;
	static TextField numberRefPointsInput;
	static TextField numberNavPointsInput;
	static TextField sensorAngleInput;
	static TextField sensorRangeInput;
	static TextField farRangeSensingInput;
	static TextField midRangeSensingInput;
	static TextField closeRangeSensingInput;
	static TextField maxLocationErrorInput;
	static TextField robotSpeedInput;
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
		Menu menuFile, menuHelp; // Menus
		MenuItem miReset, miExit, miAbout; // Menu items
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
		numberRefPointsInput = new TextField(); // Number of Reference points text field
		numberRefPointsInput.setPromptText("From 0 to 200 points."); // Boundaries
		numberNavPointsInput = new TextField(); // Number of Navigation points text field
		numberNavPointsInput.setPromptText("From 0 to 200 points."); // Boundaries
		sensorAngleInput = new TextField(); // Sensor angle text field
		sensorAngleInput.setPromptText("From 0 to 180 degrees."); // Boundaries
		sensorRangeInput = new TextField(); // Sensor range text field
		sensorRangeInput.setPromptText("From 0 to 400."); // Boundaries
		farRangeSensingInput = new TextField(); // Far-Range Sensing text field
		farRangeSensingInput.setPromptText("From 0 to 100.");//Boundaries
		midRangeSensingInput = new TextField(); // Mid-Range Sensing text field
		midRangeSensingInput.setPromptText("From 0 to 100.");//Boundaries
		closeRangeSensingInput = new TextField(); // Close-Range Sensing text field
		closeRangeSensingInput.setPromptText("From 0 to 100.");//Boundaries
		maxLocationErrorInput = new TextField(); // Max Location Error text field
		maxLocationErrorInput.setPromptText("Less than Sensor Range.");//Boundaries
		robotSpeedInput = new TextField();// Input for robot speed
		robotSpeedInput.setPromptText("From 0 to 20.");//Boundaries


		Button startButton = new Button("START"); // Start Button
		startButton.setStyle("-fx-background-color: lightgreen;");

		Button generateButton = new Button("GENERATE"); // Generate Button
		generateButton.setStyle("-fx-background-color: lightblue;");

		numberRefPoints = 0;
		numberNavPoints = 0;
		sensorAngle = 0;
		sensorRange = 0;
		farRangeSensing = 0;
		midRangeSensing = 0;
		closeRangeSensing = 0;
		maxLocationError = 0;
		robotSpeed = 0;

		// Create Robot
		robot = new Robot(100, 325, 550);

		initialHeading = 90;

		// Create Sensor
		sensor = new Sensor(375, 600, sensorRange, sensorAngle, initialHeading, closeRangeSensing, midRangeSensing, farRangeSensing, maxLocationError);

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
			if (numberRefPoints > 0) {
				//Remove any previously generated reference points
				for(int i=0;i<numberRefPoints;i++){
					pane.getChildren().remove(referencePoints[i]);
				}
			}
			if (numberNavPoints > 0) {
				//Remove any previously generated navigation points
				for(int j=0;j<numberNavPoints;j++){
					pane.getChildren().remove(navigationPoints[j]);
				}
			}
			
			//Assign values to variables
			numberRefPoints = getNumberRefPoints();
			numberNavPoints = getNumberNavPoints();
			sensorAngle = getSensorAngle();
			sensorRange = getSensorRange();
			farRangeSensing = getFarRangeSensing();
			midRangeSensing = getMidRangeSensing();
			closeRangeSensing = getCloseRangeSensing();
			maxLocationError = getMaxLocationError();
			robotSpeed = getRobotSpeed();
			pane.getChildren().removeAll(sensor,robot);

			//Check Boundaries of all user inputs
			Boolean validRef = checkValidNumber(numberRefPoints,0,200);
			Boolean validNav = checkValidNumber(numberNavPoints,0,200);
			Boolean validAngle = checkValidNumber(sensorAngle,0,180);
			Boolean validRange = checkValidNumber(sensorRange,0,400);
			Boolean validFarError = checkValidNumber(farRangeSensing,0,100);
			Boolean validMidError = checkValidNumber(midRangeSensing,0,100);
			Boolean validCloseError = checkValidNumber(closeRangeSensing,0,100);
			Boolean validLocationError = checkValidNumber(maxLocationError,0,sensorRange);
			Boolean validSpeed = checkValidNumber(robotSpeed,0,20);

			//If any boundary is violated, display error message. Otherwise continue normally.
			if(validRef==true && validNav==true && validAngle==true && validRange==true && validFarError==true &&
					validMidError==true && validCloseError==true && validLocationError==true && validSpeed==true){
				sensor = new Sensor(375, 600, sensorRange, sensorAngle, initialHeading, closeRangeSensing, midRangeSensing, farRangeSensing, maxLocationError);
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
			}else{
				boundaryError();
				//Reinitialize variables to zero
				numberRefPoints = 0;
				numberNavPoints = 0;
				sensorAngle = 0;
				sensorRange = 0;
				farRangeSensing = 0;
				midRangeSensing = 0;
				closeRangeSensing = 0;
				maxLocationError = 0;
				robotSpeed = 0;
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
		grid.add(new Label("Far-Range Sensing Error (%):"), 0, 5);
		grid.add(farRangeSensingInput, 1, 5);
		grid.add(new Label("Mid-Range Sensing Error (%):"), 0, 6);
		grid.add(midRangeSensingInput, 1, 6);
		grid.add(new Label("Close-Range Sensing Error (%):"), 0, 7);
		grid.add(closeRangeSensingInput, 1, 7);
		grid.add(new Label("Max. Location Error:"), 0, 8);
		grid.add(maxLocationErrorInput, 1, 8);
		grid.add(robotSpeedInput, 1, 9);
		grid.add(new Label("Robot Speed:"), 0, 9);
		grid.add(startButton, 1, 10);
		grid.add(generateButton, 0, 10);

		// Create Menu Bar
		menuBar = new MenuBar();
		// Create Menus
		menuFile = new Menu("File");
		menuHelp = new Menu("Help");
		// Create Menu Items
		miReset = new MenuItem("Reset");
		miExit = new MenuItem("Exit");
		miAbout = new MenuItem("About");
		// Add items to respective menus
		menuFile.getItems().addAll(miReset, miExit);
		menuHelp.getItems().add(miAbout);
		// Add menus to menu bar
		menuBar.getMenus().addAll(menuFile, menuHelp);

		// Event handlers
		miExit.setOnAction(e -> Platform.exit()); // Exit button
		miAbout.setOnAction(e -> showAbout()); // Help menu item

		// Create a scene and place it in the stage
		Scene setupScene = new Scene(borderPane, 1000, 700);
		borderPane.setTop(menuBar);
		borderPane.setCenter(grid);
		borderPane.setRight(pane);
		primaryStage.setTitle("Setup Page");
		primaryStage.setScene(setupScene);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	/**
	 * Collect the user input for the number of reference points.
	 * @return number of reference points
	 */
	public static int getNumberRefPoints() {
		numberRefPoints = Integer.valueOf(numberRefPointsInput.getText());
		return numberRefPoints;
	}

	/**
	 * Collect the user input for the number of navigation points.
	 * @return number of navigation points
	 */
	public static int getNumberNavPoints() {
		numberNavPoints = Integer.valueOf(numberNavPointsInput.getText());
		return numberNavPoints;
	}

	/**
	 * Collect the user input for the angle of the sensor area.
	 * @return sensor area angle
	 */
	public static int getSensorAngle() {
		sensorAngle = Integer.valueOf(sensorAngleInput.getText());
		return sensorAngle;
	}

	/**
	 * Collect the user input for the range of the sensor area.
	 * @return sensor area range
	 */
	public static int getSensorRange() {
		sensorRange = Integer.valueOf(sensorRangeInput.getText());
		return sensorRange;
	}

	/**
	 * Collect the user input for the far range sensor error.
	 * @return far range error
	 */
	static int getFarRangeSensing() {
		farRangeSensing = Integer.valueOf(farRangeSensingInput.getText());
		return farRangeSensing;
	}

	/**
	 * Collect the user input for the medium range sensor error.
	 * @return medium range error
	 */
	static int getMidRangeSensing() {
		midRangeSensing = Integer.valueOf(midRangeSensingInput.getText());
		return midRangeSensing;
	}

	/**
	 * Collect the user input for the close range sensor error.
	 * @return close range error
	 */
	static int getCloseRangeSensing() {
		closeRangeSensing = Integer.valueOf(closeRangeSensingInput.getText());
		return closeRangeSensing;
	}

	/**
	 * Collect the user input for the maximum robot location error.
	 * @return maximum location error
	 */
	static int getMaxLocationError() {
		maxLocationError = Integer.valueOf(maxLocationErrorInput.getText());
		return maxLocationError;
	}
	
	/**
	 * Collect the user input for the speed of the robot.
	 * @return robot speed
	 */
	static int getRobotSpeed() {
		robotSpeed = Integer.valueOf(robotSpeedInput.getText());
		return robotSpeed;
	}

	/**
	 * Checks all user inputs against defined input boundaries.
	 * @param input value of any user input
	 * @param lowerBound the lower limit of accepted inputs
	 * @param upperBound the upper limit of accepted inputs
	 * @return boolean true or false is the input passes or violates boundaries
	 */
	public static boolean checkValidNumber(Integer input, double lowerBound, double upperBound) {
		//Make sure number is within boundary limits
		if (input >= lowerBound && input <= upperBound) {
			return true;
		} else{ return false; }
	}
	/**
	 * Displays an error message in a pop-up menu if any input boundary is violated.
	 */
	public void boundaryError(){

		//Create Label
		final String errorText = "Please check values and enter valid integers only.";
		Label boundLabel = new Label();
		boundLabel.setTextAlignment(TextAlignment.CENTER);
		boundLabel.setFont(Font.font("Times New Roman", 14));
		boundLabel.setText(errorText);

		//Add Label to pane and display in a new window
		StackPane errorPane = new StackPane();
		errorPane.getChildren().add(boundLabel);
		Scene scene = new Scene(errorPane,400,50);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("An Input Error Has Occured!");
		stage.setResizable(false);
		stage.show();

	}

	/**
	 * Displays a help message in a pop-up menu if the help menu item is chosen.
	 */
	private void showAbout() {

		final String helpText = "	The purpose of this program is to observe the effects of various system parameters "
				+ "on the performance of a waypoint-navigating robot. "
				+ "This Setup Page allows the user to input system parameters for the navigation simulation. "
				+ "The Number of Reference Points defines the total number of reference points in the environment. "
				+ "Reference points are used by the Sensor to determine the robot's coordinates within the environment. "
				+ "The Number of Navigation Points defines the total number of navigation points in the environment. "
				+ "When the simulation is started, the robot will navigate between these points (in no specific order). "
				+ "The Sensor Angle defines the angle in degrees between the robot's heading and the boundary of the robot's point sensor. "
				+ "The Sensor Range defines the radius of the point sensor. "
				+ "The Sensor Range is divided equally into three zones: Far, Mid, and Close-Range. "
				+ "The Far-Range Sensing Error specifies the percentage chance of the Sensor failing to detect a reference point within the Far-Range. "
				+ "The Mid and Close-Range Sensing Errors have the same function, corresponding to those ranges. "
				+ "The Maximum Location Error specifies the max amount of unit error that can be experienced at the farthest scope of the Sensor Range "
				+ "(meaning the max error decreases as the distance to the detected reference point decreases). "
				+ "The Robot Speed specifies the constant speed of the robot during navigation. This is used to calculate the total navigation time. "
				+ "In order to produce accurate results, the user should use consistant units for distance and speed units (i.e. ft, m, ft/s, m/s). "
				+ "Once the Generate button is pressed, a preview of the environment will be displayed on the right-hand side of the window. "
				+ "To begin the simulation, press the Start button. "
				+ "The simulation will then execute, and a Summary Page will display the performance results, "
				+ "at which time the user will be given the option to save the results to a text file.";

		// Create the text label
		Label helpLabel = new Label();
		helpLabel.setWrapText(true);
		helpLabel.setTextAlignment(TextAlignment.LEFT);
		helpLabel.setFont(Font.font("Times New Roman", 14));
		helpLabel.setText(helpText);

		// Add the label to a StackPane
		StackPane aboutPane = new StackPane();
		aboutPane.setPadding(new Insets(10,10,10,10));
		aboutPane.getChildren().add(helpLabel);
		aboutPane.setPrefSize(400, 400);

		// Create and display said the pane in a new stage
		Scene scene = new Scene(aboutPane, 700, 310);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Navigation Simulation Setup");
		stage.setResizable(false);
		stage.sizeToScene();
		stage.show();
	}

	/**
	 * Returns the navigation points as an array.
	 * 
	 * @return point array of navigation points
	 */
	public static Point[] getNavPoints() {
		return navigationPoints;
	}

	/**
	 * Returns the reference points as an array.
	 * 
	 * @return point array of reference points
	 */
	public static Point[] getRefPoints() {
		return referencePoints;
	}

	/**
	 * Returns the width of the preview area.
	 * 
	 * @return preview area width
	 */
	public static int getMapWidth() {
		return mapWidth;
	}

	/**
	 * Returns the height of the preview area.
	 * 
	 * @return preview area height
	 */
	public static int getMapHeight() {
		return mapHeight;
	}

}