import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.geometry.Insets;

public class SetupPage extends Application{

	@Override //Override start method in Application class
	public void start(Stage primaryStage) {

		

		//Declare panes/menus/menu items
		BorderPane borderPane;//Border Pane for scene
		GridPane grid;//Grid Pane for inputs/start button
		MenuBar menuBar;// MenuBar
		Menu menuFile; //Menus
		Menu menuOptions;
		Menu menuHelp;
		MenuItem miReset; //Menu items
		MenuItem miExit;
		MenuItem miAbout;
		CheckMenuItem miRandom; //Check menu items
		
		//create robot image on button
		Image robo = new Image("file:/home/dakodah/Downloads/robot.resized.png");
		Button robo1 = new Button();
		robo1.setGraphic(new ImageView(robo));

		//Create a BorderPane container for scene layout
		borderPane = new BorderPane();

		//Create a GridPane container for user inputs/buttons
		grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		//Create labels and buttons
		grid.add(new Label("Point Density:"), 0, 0);
		grid.add(new TextField(), 1, 0);
		grid.add(new Label("Sensor Angle:"), 0, 1); 
		grid.add(new TextField(), 1, 1);
		grid.add(new Label("Sensor Range:"), 0, 2);
		grid.add(new TextField(), 1, 2);
		grid.add(new Label("Time Limit:"), 0, 3);
		grid.add(new TextField(), 1, 3);
		grid.add(new Button("Start"), 1, 4);
		
		//create viewing of robot
		grid.add(robo1, 70,75 );


		//Create Menu Bar
		menuBar = new MenuBar();
		//Create Menus
		menuFile = new Menu("File");
		menuOptions = new Menu("Options");
		menuHelp = new Menu("Help");
		//Create Menu Items
		miReset = new MenuItem("Reset");
		miExit = new MenuItem("Exit");
		miAbout = new MenuItem("About");
		miRandom = new CheckMenuItem("Random");
		//Add items to respective menus
		menuFile.getItems().addAll(miReset,miExit);
		menuOptions.getItems().addAll(miRandom);
		menuHelp.getItems().add(miAbout);
		//Add menus to menu bar
		menuBar.getMenus().addAll(menuFile, menuOptions, menuHelp);

		//Event handlers
		miExit.setOnAction(e -> Platform.exit()); //Exit button
		
		//Create a scene and place it in the stage
		Scene scene = new Scene(borderPane, 1000, 700);
		borderPane.setTop(menuBar);
		borderPane.setCenter(grid);
		primaryStage.setTitle("Setup Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}