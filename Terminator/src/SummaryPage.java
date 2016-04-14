
import javafx.scene.control.Menu;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

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

		
		try {
			FileWriter dataOut = new FileWriter("Point_Data.txt");
			BufferedWriter out = new BufferedWriter(dataOut); 
			PrintWriter fOut = new PrintWriter(out);
			
			for(int i=0;i</*array name*/.length;i++){
				if(/*arrayname*/[i]=null)
					out.write(/*arrayname*/[i]);
				out.write("\n");
			}
			out.close();
			
		}catch (Exception e){
			System.err.println("Error" + e.getMessage());
		}
		/* show save
		 * why is primarystage messed up*/   
		//File file = fileChooser.showSaveDialog(primaryStage);
		
		
	}
}//end SummaryPage