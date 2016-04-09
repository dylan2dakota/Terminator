public class NavigationSimulation {

	public NavigationSimulation(){

	}
	
	Point[] navPoints;
	double[] robotLocation;
	Sensor sensor;
	double heading;

	public void navigate() {
		//Collect array of all Navigation Points
		navPoints = SetupPage.getNavPoints();
		//Create sensor
		heading = 90;
		sensor = new Sensor(375, 600, SetupPage.getSensorRange(), SetupPage.getSensorAngle(), heading); 
		
		for (int i=1; i<navPoints.length+1; i++) {
			
			//Locate Navigation Point (Define internal map coordinates)
			//double[] navPoint = {navPoints[i].getCenterX(), navPoints[i].getCenterY()};
			Point navPoint = navPoints[i];
			double[] navCoords = {navPoints[i].getCenterX(), navPoints[i].getCenterY()};
			
			while (robotLocation != navCoords) {
				
				//Search for closest Reference point
				//If no points in sensor area, move forward.
				Point[] pointsDetected = sensor.detectPoints();
				if (pointsDetected.length == 0) {
					Controller.move(0);
				}
				
				//Determine Robot's position based on closest reference point
				Point refPoint = pointsDetected[1]; //Defines coordinates of closest reference point
				robotLocation = sensor.locateRobot(refPoint); //Defines robot's coordinates based on closest reference point
				double turnAngle = sensor.measureAngle(navPoint); //Measures angle robot must turn
				robotLocation = Controller.move(turnAngle); //Move Robot toward Navigation Point
				heading = heading + turnAngle;
				sensor = new Sensor(robotLocation[1], robotLocation[2], SetupPage.getSensorRange(), SetupPage.getSensorAngle(), heading);
			}
		}
	}
}//end NavigationSimulation