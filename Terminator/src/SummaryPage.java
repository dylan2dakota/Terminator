
import javafx.scene.Scene;
import javafx.scene.control.Menu;
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
		// Create a scene and place it in the stage
		Scene summaryScene = new Scene(borderPane, 1000, 700);
		summaryStage.setTitle("Summary");
		summaryStage.setScene(summaryScene);
		summaryStage.show();
	}

	public void exportFile(){

	}
	public void handle(ActionEvent event){
		FileChooser fileChooser = new FileChooser();

		//set extension filter
		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT file (*.txt)","*.txt");
		fileChooser.getExtensionFilters().add(extensionFilter);


		/*try {
			FileWriter dataOut = new FileWriter("Point_Data.txt");
			BufferedWriter out = new BufferedWriter(dataOut); 
			PrintWriter fOut = new PrintWriter(out);

			for(int i=0;i</*array name.length;i++){
				if(/*arrayname[i]=null)
					out.write(/*arrayname[i]);
				out.write("\n");
			}
			out.close();

		}catch (Exception e){
			System.err.println("Error" + e.getMessage());
		}
		 */


	}
}//end SummaryPage