import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

public class Sensor extends Arc{

	public Sensor(){

	}

	public Sensor(double xCenter, double yCenter, double radius, double angle){
		
		setCenterX(xCenter);
		setCenterY(yCenter);
		setRadiusX(radius);
		setRadiusY(radius);
		setStartAngle(90-angle);
		setLength(2*angle);
		setType(ArcType.ROUND);
		setFill(Color.rgb(1,1,1,0.3));
		setStroke(Color.BLACK);
		setStrokeWidth(5);
	
	}
}//end Sensor