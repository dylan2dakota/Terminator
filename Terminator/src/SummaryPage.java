
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

	private Menu fileMenu;
	private Menu helpMenu;
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
		//create menu bar items
		Menu menuFile, menuHelp;
		MenuItem miExit, miHelp;
		//create menus
		menuBar = new Menu();
		menuFile = new Menu("File");
		menuFile = new Menu("Help");
		//create menu items
		miExit = new MenuItem("Exit");
		
		
		// Create a scene and place it in the stage
		Scene summaryScene = new Scene(borderPane, 1000, 700);
		summaryStage.setTitle("Summary");
		summaryStage.setScene(summaryScene);
		summaryStage.show();
	}

	public void exportFile(){

	}
	
}//end SummaryPage