public class NavigationSimulation {

	public NavigationSimulation(){

	}
/*
	Point[] navPoints;
	double[] robotLocation;
	Sensor sensor;
	double heading;
	double navigationDistance;
	*/

	public void navigate() {
		Point[] navPoints;
		double[] robotLocation = new double[2];
		Sensor sensor;
		double heading;
		double navigationDistance = 10;
		//Collect array of all Navigation Points
		navPoints = SetupPage.getNavPoints();
		//Create sensor
		heading = 90;
		robotLocation[0] = 325; robotLocation[1] = 550;
		sensor = new Sensor(375, 600, SetupPage.getSensorRange(), SetupPage.getSensorAngle(), heading); 

		for (int i=0; i<navPoints.length; i++) {

			//Locate Navigation Point (Define internal map coordinates)
			Point navPoint = navPoints[i];
			double[] navCoords = {navPoints[i].getCenterX(), navPoints[i].getCenterY()};

			while (navigationDistance > 1) {

				//Search for closest Reference point
				//If no points in sensor area, move forward.
				Point[] pointsDetected = sensor.detectPoints();
				if (pointsDetected.length == 0) {
					robotLocation = Controller.move(robotLocation, heading, 0);
					sensor = new Sensor(robotLocation[0], robotLocation[1], SetupPage.getSensorRange(), SetupPage.getSensorAngle(), heading);
				} else {

					//Determine Robot's position based on closest reference point
					Point refPoint = pointsDetected[0]; //Defines coordinates of closest reference point
					robotLocation = sensor.locateRobot(refPoint); //Defines robot's coordinates based on closest reference point
					double turnAngle = sensor.measureAngle(navPoint); //Measures angle robot must turn
					robotLocation = Controller.move(robotLocation, heading, turnAngle); //Move Robot toward Navigation Point
					heading = heading + turnAngle; //Redefine Robot heading
					//Reposition Sensor
					sensor = new Sensor(robotLocation[0], robotLocation[1], SetupPage.getSensorRange(), SetupPage.getSensorAngle(), heading);
					navigationDistance = sensor.measureDistance(navPoint);
				}
			}//End while loop (move to next point)
			int numFound = i+1;
			System.out.println("Found "+numFound+" points!");
			System.out.println(navigationDistance);
		}//End for loop (navigation complete)
	}
}//end NavigationSimulation