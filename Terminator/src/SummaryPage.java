
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * @author Owner
 * @version 1.0
 * @created 29-Feb-2016 12:35:52 PM
 */
public class SummaryPage extends Application{

	private Button resetButton;
	private Button saveFileButton;

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
		
		// Create a scene and place it in the stage
		Scene summaryScene = new Scene(borderPane, 1000, 700);
		summaryStage.setTitle("Summary");
		summaryStage.setScene(summaryScene);
		summaryStage.show();
	}

	public void exportFile(){

	}
	
	private void showHelp(){
		final String summaryHelp = "This page shows the summary of all the data points"
				+"the robot found during the simulation. The data displayed on this page"
				+"is automatically saved in the RobotData.txt file on your computer"
				+"You can exit the simulation by clicking the exit button in the file"
				+"option on the menu bar or restart the simulation by clicking the"
				+"'Reset' button.";
		
	}
	
}//end SummaryPage