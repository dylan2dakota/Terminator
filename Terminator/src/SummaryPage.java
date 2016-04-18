
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	
	public SummaryPage(){

	}

	@Override
	public void start(Stage summaryStage) {
		GridPane grid = new GridPane();
		grid.setStyle("-fx-background-color: grey;");
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(10);
		grid.setHgap(5);
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(grid);
		
		//create buttons
		Button saveFileButton = new Button ("Save");
		saveFileButton.setStyle("-fx-background-color: green;");
		Button resetButton = new Button ("Reset");
		resetButton.setStyle("-fx-background-color: blue;");
		Button exitButton = new Button ("Exit");
		exitButton.setStyle("-fx-background-color: red;");
		
		//add buttons to GUI
		grid.add(saveFileButton, 1, 9);
		grid.add(resetButton, 1, 14);
		grid.add(exitButton, 4, 9);
		
		//create menu bar 
		Menu menuBar;
		//create menu bar
		Menu menuFile, menuHelp;
		MenuItem miExit, miHelp;
		//create menu
		menuBar = new Menu();
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
				exportFile(file, dataArray);
			}
		});
//reset button handler
		resetButton.setOnAction(e -> {
			
		});
		// Create a scene and place it in the stage
		Scene summaryScene = new Scene(borderPane, 1000, 700);
		summaryStage.setTitle("Summary");
		summaryStage.setScene(summaryScene);
		summaryStage.show();
	}

	private void exportFile(File file,double dataArray){
		try{
			FileWriter dataOut = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(dataOut);
			PrintWriter fileOut = new PrintWriter(out);
			
			for(int i=0;i<dataArray;i++){
				if(dataArray[i]!=null)
					out.write(dataArray[i]);
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