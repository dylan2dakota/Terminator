
import javafx.scene.control.Menu;
import javafx.stage.FileChooser;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 * @author Owner
 * @version 1.0
 * @created 29-Feb-2016 12:35:52 PM
 */
public class SummaryPage {

	private Menu fileMenu;
	private Menu helpMenu;
	private Button resetButton;
	private Button saveFileButton;
	
	public SummaryPage(){

	}

	public void finalize() throws Throwable {

	}
	public String displayData(){
		return "";
	}

	public void exportFile(){

	}
	public void handle(ActionEvent event){
		FileChooser fileChooser = new FileChooser();
		
		//set extension filter
		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT file (*.txt)","*.txt");
		fileChooser.getExtensionFilters().add(extensionFilter);
		
		//show save
		//File file = fileChooser.showSaveDialog(primaryStage);
		
		
	}
}//end SummaryPage