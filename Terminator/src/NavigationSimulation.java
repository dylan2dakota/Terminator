import java.util.ArrayList;

public class NavigationSimulation {

	public NavigationSimulation(){

	}

	public void navigate() {
		Point[] navPoints;
		double[] robotLocation = new double[2];
		Sensor sensor;
		int sensorRange = SetupPage.getSensorRange();
		int sensorAngle = SetupPage.getSensorAngle();
		double heading;
		double navigationDistance = 10;
		//Collect array of all Navigation Points
		navPoints = SetupPage.getNavPoints();
		//Create sensor
		heading = 90;
		robotLocation[0] = 375; robotLocation[1] = 600;
		sensor = new Sensor(robotLocation[0], robotLocation[1], sensorRange, sensorAngle, heading); 

		for (int i=0; i<navPoints.length; i++) {

			//Locate Navigation Point (Define internal map coordinates)
			Point navPoint = navPoints[i];

			while (navigationDistance > 1) {

				//Search for closest Reference point
				//If no points in sensor area, move forward.
				ArrayList<Point> pointsDetected = sensor.detectPoints();
				if (pointsDetected.size() == 0) {
					robotLocation = Controller.move(robotLocation, heading, 0, 10);
					sensor = new Sensor(robotLocation[0], robotLocation[1], sensorRange, sensorAngle, heading);
				} else {
					System.out.println("Number of Points detected: "+pointsDetected.size());
					//Determine Robot's position based on closest reference point
					Point refPoint = pointsDetected.get(0); //Defines coordinates of closest reference point
					robotLocation = sensor.locateRobot(refPoint); //Defines robot's coordinates based on closest reference point
					double turnAngle = sensor.measureAngle(navPoint); //Measures angle robot must turn
					System.out.println("Turn Angle: "+turnAngle);
					navigationDistance = sensor.measureDistance(navPoint);
					System.out.println("Distance to Navigation Point: "+navigationDistance);
					robotLocation = Controller.move(robotLocation, heading, turnAngle, navigationDistance); //Move Robot toward Navigation Point
					heading = heading + turnAngle; //Redefine Robot heading
					//Reposition Sensor
					sensor = new Sensor(robotLocation[0], robotLocation[1], sensorRange, sensorAngle, heading);
					navigationDistance = sensor.measureDistance(navPoint);
				}
			}//End while loop (move to next point)
			int numFound = i+1;
			System.out.println("Found "+numFound+" points!");
			System.out.println(navigationDistance);
		}//End for loop (navigation complete)
	}
}//end NavigationSimulation