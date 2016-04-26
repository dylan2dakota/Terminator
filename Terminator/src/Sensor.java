import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

public class Sensor extends Arc {

	public double xCenter;
	public double yCenter;
	public double sensorRange;
	public double sensorAngle;
	public double heading;
	public int closeError;
	public int midError;
	public int farError;
	public int maxLocationError;

	public Sensor() {

	}

	//Sensor constructor
	public Sensor(double xCenter, double yCenter, int sensorRange, int sensorAngle, double heading, int closeError, int midError, int farError, int maxLocationError) {

		this.xCenter = xCenter;
		this.yCenter = yCenter;
		this.sensorRange = sensorRange;
		this.sensorAngle = sensorAngle;
		this.heading = heading;
		this.closeError = closeError;
		this.midError = midError;
		this.farError = farError;
		this.maxLocationError = maxLocationError;
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

	//Method to form array of points inside sensor area with no error
	public ArrayList<Point> detectPointsNoError() {
		Point[] refPoints;
		ArrayList<Point> pointsDetected = new ArrayList<>();

		//Get reference points from SetupPage (user input)
		refPoints = SetupPage.getRefPoints();

		//For loop to evaluate each reference point
		for (int i=0; i<refPoints.length; i++) {

			//Measure the distance and angle to reference point
			double distance = measureDistance(refPoints[i]);
			double angle = measureAngle(refPoints[i]);

			//If reference point is in Sensor Area, add to pointsDetected
			if (distance <= this.sensorRange && Math.abs(angle)<= this.sensorAngle) {
				pointsDetected.add(refPoints[i]);
			}
		}
		return pointsDetected;
	}

	//Method to form array of points inside sensor area
	public ArrayList<Point> detectPoints() {
		Random random = new Random();
		double error;
		Point[] refPoints;
		ArrayList<Point> pointsDetected = new ArrayList<>();

		//Get reference points from SetupPage (user input)
		refPoints = SetupPage.getRefPoints();

		//For loop to evaluate each reference point
		for (int i=0; i<refPoints.length; i++) {

			//Measure the distance and angle to reference point
			double distance = measureDistance(refPoints[i]);
			double angle = measureAngle(refPoints[i]);

			//Determine Sensor error percentage based on distance to point
			if (distance <= this.sensorRange/3){error = this.closeError;}
			else if (distance > this.sensorRange/3 &&distance <= this.sensorRange*(2/3)) {
				error = this.midError;
			}else {error = this.farError;}

			//Random number (between 1 & 100) to determine if point is detected
			int detect = random.nextInt(100);

			//If reference point is in Sensor Area, add to pointsDetected
			if (distance <= this.sensorRange && Math.abs(angle)<= this.sensorAngle) {
				//If random number greater than error percentage, point detected
				if (detect >= error) {
					pointsDetected.add(refPoints[i]);
				}
			}
		}
		return pointsDetected;
	}

	//Method to return robot's location in map, based off reference point
	public double[] locateRobot(Point refPoint) {
		Random random = new Random();
		//Reference point coordinates
		double xRef = refPoint.getCenterX();
		double yRef = refPoint.getCenterY();
		//Measure x and y distances
		double xMeasure = Math.abs(xRef-this.xCenter);
		double yMeasure = Math.abs(yRef-this.yCenter);
		//Calculate max error for this distance;
		double xMaxError = xMeasure*this.maxLocationError/this.sensorRange;
		double yMaxError = yMeasure*this.maxLocationError/this.sensorRange;
		//Errors for x and y distance
		double xError = random.nextDouble()*2*xMaxError-xMaxError;
		double yError = random.nextDouble()*2*yMaxError-yMaxError;
		//Sensor-estimated robot location
		double xDistance = (xRef-this.xCenter)+xError; //Give this an error
		double yDistance = (yRef-this.yCenter)+yError; //Give this an error
		double[] location = {xRef-xDistance, yRef-yDistance};
		return location;
	}

	//Method to measure the straight-line distance to a point
	public double measureDistance(Point refPoint) {
		//Point coordinates
		double yRef = refPoint.getCenterY();
		double xRef = refPoint.getCenterX();
		//X and y distances
		double yDistance = Math.abs(yRef-this.yCenter);
		double xDistance = Math.abs(xRef-this.xCenter);
		//Straight-line distance
		double distance = Math.sqrt((xDistance*xDistance)+(yDistance*yDistance));
		return distance;
	}

	//Method to measure the angle between the robot's heading and a point
	public double measureAngle(Point refPoint) {
		double angle;
		double turn;
		//Point x and y coordinates
		double yRef = refPoint.getCenterY();
		double xRef = refPoint.getCenterX();
		//X and y distances to point
		double yDistance = yRef-this.yCenter;
		double xDistance = xRef-this.xCenter;
		//Calculate angle between current location and point
		if (xDistance >= 0 && yDistance <= 0) { //1st quadrant
			angle = -Math.toDegrees(Math.atan(yDistance/xDistance));
		}else if (xDistance < 0 && yDistance <= 0) { //2nd quadrant
			angle = 180 - Math.toDegrees(Math.atan(yDistance/xDistance));
		}else if (xDistance < 0 && yDistance > 0) { //3rd quadrant
			angle = 180 - Math.toDegrees(Math.atan(yDistance/xDistance));
		}else { //4th quadrant
			angle = 360 - Math.toDegrees(Math.atan(yDistance/xDistance));
		}
		//Angle between robot's current heading and point
		turn = angle-this.heading;
		//Angles greater than 180 expressed as negative angle
		if (turn > 180) {turn = -(360-turn);}
		return turn;
	}

}// end Sensor