

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

public class Sensor extends Arc {
	
	static Point[] refPoints;
	public static double xCenter;
	public static double yCenter;
	public static double sensorRange;
	public static double sensorAngle;
	public static double heading;
	static Point[] pointsDetected;

	public Sensor() {

	}

	public Sensor(double xCenter, double yCenter, int sensorRange, int sensorAngle, double heading) {

		this.xCenter = xCenter;
		this.yCenter = yCenter;
		this.sensorRange = sensorRange;
		this.sensorAngle = sensorAngle;
		this.heading = heading;
		setCenterX(xCenter);
		setCenterY(yCenter);
		setRadiusX(sensorRange);
		setRadiusY(sensorRange);
		setStartAngle(heading - sensorAngle);
		setLength(2 * sensorAngle);
		setType(ArcType.ROUND);
		setFill(Color.rgb(1, 1, 1, 0.3));
		setStroke(Color.BLACK);
		setStrokeWidth(5);

	}

	public Point[] detectPoints() {
		
		refPoints = SetupPage.getRefPoints();
		int j = 0;
		
		//For loop to evaluate each reference point
		for (int i=1; i<refPoints.length+1; i++) {
			double distance = measureDistance(refPoints[i]);
			double angle = measureAngle(refPoints[i]);
			//If reference point is in Sensor Area, add to pointsDetected
			if (distance <= sensorRange && angle <= heading+sensorAngle && angle >= heading-sensorAngle) {
				pointsDetected[j] = refPoints[i];
				j++;
			}
		}
		//Sort pointsDetected in ascending order (by distance from robot)
		
		return pointsDetected;
	}

	public double[] locateRobot(Point refPoint) {
		double yRef = refPoint.getCenterY();
		double xRef = refPoint.getCenterX();
		double yDistance = yRef-yCenter; //Give this an error
		double xDistance = xRef-xCenter; //Give this an error
		double[] location = {yRef-yDistance, xRef-xDistance};
		return location;
	}
	
	public static double measureDistance(Point refPoint) {
		double yRef = refPoint.getCenterY();
		double xRef = refPoint.getCenterX();
		double yDistance = Math.abs(yRef-yCenter);
		double xDistance = Math.abs(xRef-xCenter);
		double distance = Math.sqrt((xDistance*xDistance)+(yDistance*yDistance));
		return distance;
	}

	public double measureAngle(Point refPoint) {
		double angle;
		double turn;
		double yRef = refPoint.getCenterY();
		double xRef = refPoint.getCenterX();
		double yDistance = yRef-yCenter;
		double xDistance = xRef-xCenter;
		if (xDistance >= 0 && yDistance >= 0) { //1st quadrant
			angle = Math.toDegrees(Math.atan(yDistance/xDistance));
		}else if (xDistance < 0 && yDistance >= 0) { //2nd quadrant
			angle = 180 - Math.toDegrees(Math.atan(yDistance/xDistance));
		}else if (xDistance < 0 && yDistance < 0) { //3rd quadrant
			angle = 180 + Math.toDegrees(Math.atan(yDistance/xDistance));
		}else { //4th quadrant
			angle = 360 - Math.toDegrees(Math.atan(yDistance/xDistance));
		}
		turn = angle - heading;
		return turn;
	}
	
/*
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
				//nearestLocation = {xDist,yDist};
			}
			i++;
		}
		
		return nearestLocation;
	}//end pointDistance
	
	public Boolean pointSensed(int sensorAngle, int sensorRange){ //determines if nearest point is within sensor area
		
		Boolean sensed = false;
		
		return sensed;
	}
	*/
}// end Sensor