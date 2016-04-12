

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

public class Sensor extends Arc {
	
	public double xCenter;
	public double yCenter;
	public double sensorRange;
	public double sensorAngle;
	public double heading;
	//Point[] pointsDetected;

	public Sensor() {

	}

	//Sensor constructor
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

	//Method to form array of points inside sensor area
	public ArrayList<Point> detectPoints() {
		Point[] refPoints;
		ArrayList<Point> pointsDetected = new ArrayList<>();
		//Get reference points from SetupPage (user input)
		refPoints = SetupPage.getRefPoints();
		
		//For loop to evaluate each reference point
		for (int i=0; i<refPoints.length; i++) {
			double distance = measureDistance(refPoints[i]);
			double angle = measureAngle(refPoints[i]);
			//If reference point is in Sensor Area, add to pointsDetected
			if (distance <= this.sensorRange && Math.abs(angle)<= this.sensorAngle) {
				pointsDetected.add(refPoints[i]);
			}
		}
		return pointsDetected;
	}

	//Method to return robot's location in map, based off reference point
	public double[] locateRobot(Point refPoint) {
		double yRef = refPoint.getCenterY();
		double xRef = refPoint.getCenterX();
		double yDistance = yRef-this.yCenter; //Give this an error
		double xDistance = xRef-this.xCenter; //Give this an error
		double[] location = {yRef-yDistance, xRef-xDistance};
		return location;
	}
	
	//Method to measure the straight-line distance to a point
	public double measureDistance(Point refPoint) {
		double yRef = refPoint.getCenterY();
		double xRef = refPoint.getCenterX();
		double yDistance = Math.abs(yRef-this.yCenter);
		double xDistance = Math.abs(xRef-this.xCenter);
		double distance = Math.sqrt((xDistance*xDistance)+(yDistance*yDistance));
		return distance;
	}

	//Method to measure the angle between the robot's heading and a point
	public double measureAngle(Point refPoint) {
		double angle;
		double turn;
		double yRef = refPoint.getCenterY(); //y-coord. of point
		double xRef = refPoint.getCenterX(); //x-coord. of point
		double yDistance = yRef-this.yCenter; //y-distance to point
		double xDistance = xRef-this.xCenter; //x-distance to point
		System.out.println("xDistance: "+xDistance+" yDistance: "+yDistance);
		//Calculate angle between current location and point
		if (xDistance >= 0 && yDistance <= 0) { //1st quadrant
			angle = Math.toDegrees(Math.atan(yDistance/xDistance));
			turn = -(this.heading-angle);
		}else if (xDistance < 0 && yDistance <= 0) { //2nd quadrant
			angle = 180 + Math.toDegrees(Math.atan(yDistance/xDistance));
			turn = angle-this.heading;
		}else if (xDistance < 0 && yDistance > 0) { //3rd quadrant
			angle = 180 + Math.toDegrees(Math.atan(yDistance/xDistance));
			turn = angle-this.heading;
		}else { //4th quadrant
			angle = 360 + Math.toDegrees(Math.atan(yDistance/xDistance));
			turn = (360-angle)+this.heading;
		}
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