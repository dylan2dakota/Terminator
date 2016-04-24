
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableView;
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
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * @author Owner
 * @version 1.0
 * @created 29-Feb-2016 12:35:52 PM
 */
public class SummaryPage extends Application{


	private Button resetButton;
	private Button saveFileButton;
	private Button Exit;
	private double[] dataArray = {1,2,3};
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
		time = robotSpeed/navigationDistance;

		
		//create new pane for summary page
		GridPane grid = new GridPane();
		grid.setStyle("-fx-background-color: grey;");
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(10);
		grid.setHgap(5);
		
		//Initialize data list
		final ObservableList data = FXCollections.observableArrayList();
		//create data list	
		final ListView dataList = new ListView(data);
		dataList.setMaxSize(200, 400);
		dataList.setEditable(false);

		//puts array into data list
		for(int i=0;i<dataArray.length;i++){
			data.addAll(i,": ",+ dataArray[i]);
		}

		//show data list
		dataList.setOrientation(Orientation.VERTICAL);
		dataList.setItems(data);
		grid.getChildren().add(dataList);
		dataList.setCenterShape(true);

		//create summary label
		Label summaryLabel = new Label ("Summary Page");

		//create buttons
		Button saveFileButton = new Button ("Save");
		saveFileButton.setStyle("-fx-background-color: green;");
		Button resetButton = new Button ("Reset");
		resetButton.setStyle("-fx-background-color: cyan;");
		Button exitButton = new Button ("Exit");
		exitButton.setStyle("-fx-background-color: red;");

		//add buttons to GUI
		grid.add(saveFileButton, 1, 9);
		grid.add(resetButton, 1, 12);
		grid.add(exitButton, 1, 14);

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
				exportFile(file, dataArray, robotSpeed, robotSpeed, robotSpeed, robotSpeed, robotSpeed, robotSpeed, robotSpeed, robotSpeed, robotSpeed);
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
		Scene summaryScene = new Scene(borderPane, 1000, 700);
		summaryStage.setTitle("Summary");
		summaryStage.setScene(summaryScene);
		summaryStage.show();
	}

	private void exportFile(File file,double[] dataArray, int numberRefPoints, int numberNavPoints
			, int sensorAngle, int farRangeSensing, int midRangeSensing, int closeRangeSensing
			, int maxLocationError, int robotSpeed, int sensorRange){
		//writes each line of data into file
		try{
			FileWriter dataOut = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(dataOut);
			PrintWriter fileOut = new PrintWriter(out);
			//set up layout of save file
			//display user inputs
			out.write("Summary page");
			out.write("User inputs:");
			out.write("Number of Reference Points: "+ numberRefPoints);
			out.write("Number of Navigation Points: "+ numberNavPoints);
			out.write("Sensor Angle: "+ sensorAngle);
			out.write("Sensor Range: "+ sensorRange);
			out.write("Far-Range Sensing Error (%): "+ farRangeSensing);
			out.write("Mid-Range Sensing Error (%): "+ midRangeSensing);
			out.write("Close-Range Sensing Error (%): "+ closeRangeSensing);
			out.write("Max. Location Error: "+ maxLocationError);
			out.write("Robot Speed: "+ robotSpeed);
			
			for(int i=0;i<dataArray.length;i++){
				if(dataArray!=null)
					out.write(dataArray[i]+"\n");
			}
			out.close();

		}catch (Exception error){
			System.err.println(error.getMessage());
		}

	}

	private void showHelp(){
		final String summaryHelp = "This page shows the summary of all the data points"
				+"the robot found during the simulation. The data displayed on this page"
				+"is automatically saved in the RobotData.txt file on your computer"
				+"You can exit the simulation by clicking the exit button in the file"
				+"option on the menu bar or restart the simulation by clicking the"
				+"'Reset' button.";

		// create text label
		Label summaryLabel = new Label();
		summaryLabel.setWrapText(true);
		summaryLabel.setTextAlignment(TextAlignment.CENTER);
		summaryLabel.setFont(Font.font("Times New Roman"));
		summaryLabel.setText(summaryHelp);

		//add Label to Stack Pane
		StackPane helpPane = new StackPane();
		helpPane.getChildren().add(summaryLabel);

		// create and display pane in a new stage
		Scene scene = new Scene(helpPane, 550, 100);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Navigation Simulation SummaryPage");
		stage.setResizable(false);
		stage.show();

	}

}//end SummaryPage