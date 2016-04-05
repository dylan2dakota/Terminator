

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

public class Sensor extends Arc {

	SetupPage robotInfo = new SetupPage();
	Point[] points = robotInfo.generatedPoints;
	int sensorAngle = robotInfo.sensorAngle;
	int sensorRange = robotInfo.sensorRange;
	int robotX;
	int robotY;
	
	public Sensor() {

	}

	public Sensor(int xCenter, int yCenter, int radius, int angle) {

		setCenterX(xCenter);
		setCenterY(yCenter);
		setRadiusX(radius);
		setRadiusY(radius);
		setStartAngle(90 - angle);
		setLength(2 * angle);
		setType(ArcType.ROUND);
		setFill(Color.rgb(1, 1, 1, 0.3));
		setStroke(Color.BLACK);
		setStrokeWidth(5);

	}
	
	public int[] pointDistance(Point[] points, int robotX, int robotY){
		
		int[] nearestLocation = {0,0};
		double nearestDistance = 0;
		
		int i;
		while(i < points.length){
			Point interestPoint =points[i];
			double xDist = Math.abs(interestPoint.getCenterX()-robotX);
			double yDist = Math.abs(interestPoint.getCenterY()-robotY);
			
			double straightDistance = Math.sqrt( (Math.pow(xDist,2)+Math.pow(yDist,2)) );
			
			if(straightDistance<nearestDistance){
				nearestDistance = straightDistance;
				nearestLocation = {xDist,yDist};
			}
			i++;
		}
		
		return nearestLocation;
	}//end pointDistance
	
	public Boolean pointSensed(int sensorAngle, int sensorRange){ //determines if nearest point is within sensor area
		
		Boolean sensed = false;
		
		return sensed;
	}
	
}// end Sensor