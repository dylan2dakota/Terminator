import javafx.application.Application;
import javafx.stage.Stage;

public class Terminator extends Application{

		public static void main(String[] args) {
			// TODO Auto-generated method stub
			Application.launch(args);
		}

		@Override
		public void start(Stage arg0) throws Exception {
			// TODO Auto-generated method stub
			SetupPage startPage = new SetupPage();
			startPage.start(arg0);
			
			
		}

}//end Terminator