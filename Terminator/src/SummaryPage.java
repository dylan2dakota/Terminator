import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * ThE SummaryPage class tracks and displays all the data in the simulation. 
 * It shows the user inputs and the average sensor error, navigation error and total navigation distance
 * @author Brandon
 * @version 1.0
 * @created 29-Feb-2016 12:35:52 PM
 */
public class SummaryPage extends Application{

	private Button resetButton;
	private Button saveFileButton;
	private Button Exit;
	private double time;

	public SummaryPage(){

	}

	@Override
	public void start(Stage summaryStage) {
		//Collect user inputs from SetupPage
		Integer numberRefPoints = SetupPage.getNumberRefPoints();
		Integer numberNavPoints = SetupPage.getNumberNavPoints();
		Integer sensorAngle = SetupPage.getSensorAngle();
		Integer sensorRange = SetupPage.getSensorRange();
		Integer farRangeSensing = SetupPage.getFarRangeSensing();
		Integer midRangeSensing = SetupPage.getMidRangeSensing();
		Integer closeRangeSensing = SetupPage.getCloseRangeSensing();
		Integer maxLocationError = SetupPage.getMaxLocationError();
		//Integer robotSpeed = SetupPage
		Integer robotSpeed = SetupPage.getRobotSpeed();
		//Collect simulation results from NavigationSimulation
		double navigationDistance = NavigationSimulation.getNavigationDistance();
		double detectionErrorAverage = NavigationSimulation.getAverageDetectionError();
		double locationErrorAverage = NavigationSimulation.getAverageLocationError();

		//time robot took to complete path
		time = navigationDistance/robotSpeed;

		//create new pane for summary page
		GridPane grid = new GridPane();
		grid.setStyle("-fx-background-color: lightgrey;");
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(10);
		grid.setHgap(5);

		//Initialize data list
		final ObservableList data = FXCollections.observableArrayList();
		//Add simulation results to data
		data.add("Number of Reference Points: "+numberRefPoints);
		data.add("Number of Navigation Points: "+numberNavPoints);
		data.add("Sensor Angle: "+sensorAngle);
		data.add("Sensor Range: "+sensorRange);
		data.add("Far-Range Sensing Error: "+farRangeSensing);
		data.add("Mid-Range Sensing Error: "+midRangeSensing);
		data.add("Close-Range Sensing Error: "+closeRangeSensing);
		data.add("Max Location Error: "+maxLocationError);
		data.add("Robot Speed: "+robotSpeed);
		data.add("Detection Error Average: "+detectionErrorAverage);
		data.add("Location Error Average: "+locationErrorAverage);
		data.add("Total Navigation Distance: "+navigationDistance);
		data.add("Estimated time to complete simulation: "+time);
		//create data list	
		final ListView dataList = new ListView(data);
		dataList.setPrefSize(380, 310);
		dataList.setEditable(false);

		//show data list
		dataList.setOrientation(Orientation.VERTICAL);
		dataList.setItems(data);
		grid.add(dataList, 0, 3);
		dataList.setCenterShape(true);

		//create summary label
		Label summaryLabel = new Label ("Summary Page");
		summaryLabel.setFont(Font.font("Times New Roman", 32));
		grid.add(summaryLabel, 0, 1);

		//create buttons
		Button saveFileButton = new Button ("Save");
		saveFileButton.setStyle("-fx-background-color: green;");
		Button resetButton = new Button ("Reset");
		resetButton.setStyle("-fx-background-color: cyan;");
		Button exitButton = new Button ("Exit");
		exitButton.setStyle("-fx-background-color: red;");

		//add buttons to GUI
		grid.add(saveFileButton, 0, 5);
		grid.add(resetButton, 0, 7);
		grid.add(exitButton, 0, 9);

		//create menu bar 
		MenuBar menuBar;
		Menu menuFile, menuHelp;
		MenuItem miExit, miHelp;
		//create menu
		menuBar = new MenuBar();
		//create menu options
		menuFile = new Menu("File");
		menuHelp = new Menu("Help");
		//create menu items
		miExit = new MenuItem("Exit");
		miHelp = new MenuItem("About");
		//add items to menus
		menuFile.getItems().addAll(miExit);
		menuHelp.getItems().addAll(miHelp);
		//add menu to menu bar
		menuBar.getMenus().addAll(menuFile,menuHelp);

		// Event Handler
		miExit.setOnAction(e -> Platform.exit());
		miHelp.setOnAction(e -> showHelp());
		exitButton.setOnAction(e -> Platform.exit());
		saveFileButton.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();

			//set extension filter
			FileChooser.ExtensionFilter extensionFiler = 
					new FileChooser.ExtensionFilter("TXT file (*.txt)", "*.txt");
			fileChooser.getExtensionFilters().add(extensionFiler);

			//show save file dialog
			File file = fileChooser.showSaveDialog(summaryStage);

			if(file !=null){
				exportFile(file, numberRefPoints, numberNavPoints, sensorAngle, 
						farRangeSensing, midRangeSensing, closeRangeSensing, 
						maxLocationError,detectionErrorAverage,locationErrorAverage,
						robotSpeed, sensorRange,time,navigationDistance);
			}
		});

		//reset button handler
		resetButton.setOnAction(e -> {
			SetupPage newSetup = new SetupPage();
			Stage setupStage = new Stage();
			newSetup.start(setupStage);
		});

		// Create BorderPane to place GUI items
		BorderPane borderPane = new BorderPane();
		borderPane.setLeft(grid);
		borderPane.setTop(menuBar);

		// Create a scene and place it in the stage
		Scene summaryScene = new Scene(borderPane, 400, 600);
		summaryStage.setTitle("Summary");
		summaryStage.setScene(summaryScene);
		summaryStage.setResizable(false);
		summaryStage.sizeToScene();
		summaryStage.show();
	}

	private void exportFile(File file, int numberRefPoints, int numberNavPoints
			, int sensorAngle, int farRangeSensing, int midRangeSensing, int closeRangeSensing
			, int maxLocationError, double detectionError, double locationError, int robotSpeed, int sensorRange, double time, double navigationDistance){
		//writes each line of data into file
		try{
			FileWriter dataOut = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(dataOut);
			PrintWriter fileOut = new PrintWriter(out);
			//set up layout of save file
			//display user inputs
			out.write("\n\nSummary page\n\n");
			out.newLine();
			out.write("User inputs:\n");
			out.newLine();
			out.write("Number of Reference Points: "+ numberRefPoints + "\n");
			out.newLine();
			out.write("Number of Navigation Points: "+ numberNavPoints + "\n");
			out.newLine();
			out.write("Sensor Angle: "+ sensorAngle + "\n");
			out.newLine();
			out.write("Sensor Range: "+ sensorRange + "\n");
			out.newLine();
			out.write("Far-Range Sensing Error (%): "+ farRangeSensing + "\n");
			out.newLine();
			out.write("Mid-Range Sensing Error (%): "+ midRangeSensing + "\n");
			out.newLine();
			out.write("Close-Range Sensing Error (%): "+ closeRangeSensing + "\n");
			out.newLine();
			out.write("Max. Location Error: "+ maxLocationError + "\n");
			out.newLine();
			out.write("Robot Speed: "+ robotSpeed + "\n");
			out.newLine();
			out.write("Detection Error Average: " + detectionError + "\n");
			out.newLine();
			out.write("Location Error Average: " + locationError + "\n");
			out.newLine();
			out.write("Total Navigation Distance: "+navigationDistance+"\n");
			out.newLine();
			out.write("Estimated time to complete simulation: " + time + "\n");
			out.close();

		}catch (Exception error){
			System.err.println(error.getMessage());
		}

	}

	private void showHelp(){
		final String summaryHelp = "	This page provides data collected during the setup and simulation. "
				+"To save the data to a text file, press the Save button. "
				+"You may exit the program by clicking the Exit button. "
				+"You may begin a new simulation by pressing the Reset button.";

		// create text label
		Label summaryLabel = new Label();
		summaryLabel.setWrapText(true);
		summaryLabel.setTextAlignment(TextAlignment.LEFT);
		summaryLabel.setFont(Font.font("Times New Roman, 14"));
		summaryLabel.setText(summaryHelp);

		//add Label to Stack Pane
		StackPane helpPane = new StackPane();
		helpPane.getChildren().add(summaryLabel);
		helpPane.setPadding(new Insets(10,10,10,10));

		// create and display pane in a new stage
		Scene scene = new Scene(helpPane, 550, 80);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Navigation Simulation Summary");
		stage.setResizable(false);
		stage.sizeToScene();
		stage.show();

	}

}//end SummaryPage