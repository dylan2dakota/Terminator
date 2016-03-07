package src;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Point extends Circle{

	Circle sensorPoint = new Circle();				//Creates circle for GUI
	
	public Point(){

	}

	public Point(double xCoord, double yCoord){
		sensorPoint.setCenterX(xCoord);				//Defined by generator
		sensorPoint.setCenterY(yCoord);				//Defined by generator
		sensorPoint.setRadius(5);
		sensorPoint.setFill(Color.BLACK);
		
	}
}//end Point