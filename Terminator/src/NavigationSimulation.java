import java.util.ArrayList;
import java.util.Random;

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
		double navigationDistance;
		int mapWidth = SetupPage.getMapWidth();
		int mapHeight = SetupPage.getMapHeight();
		int closeError = SetupPage.getCloseRangeSensing();
		int midError = SetupPage.getMidRangeSensing();
		int farError = SetupPage.getFarRangeSensing();
		int maxLocationError = SetupPage.getMaxLocationError();
		Random randomAngle = new Random();
		
		//Collect array of all Navigation Points
		navPoints = SetupPage.getNavPoints();
		//Create sensor
		heading = 90;
		robotLocation[0] = 375; robotLocation[1] = 600;
		sensor = new Sensor(robotLocation[0], robotLocation[1], sensorRange, sensorAngle, heading, closeError, midError, farError, maxLocationError); 
		int loops = 0;
		
		for (int i=0; i<navPoints.length; i++) {
			
			navigationDistance = 10;

			//Locate Navigation Point (Define internal map coordinates)
			Point navPoint = navPoints[i];

			while (navigationDistance >= 1) {
				loops++;
				System.out.println(loops);
				//Search for closest Reference point
				//If no points in sensor area, move forward.
				ArrayList<Point> pointsDetected = sensor.detectPoints();
				if (pointsDetected.size() == 0) {
					robotLocation = Controller.move(robotLocation, heading, 0, 10);
					
					//Make sure robot stays in map
					if (robotLocation[0] >= mapWidth){heading = randomAngle.nextInt(178)+91;}
					if (robotLocation[0] <= 0){
						heading = randomAngle.nextInt(178)-89;
						if (heading<0){heading = heading+360;}
					}
					if (robotLocation[1] >= mapHeight){heading = randomAngle.nextInt(178)+1;}
					if (robotLocation[1] <= 0){heading = randomAngle.nextInt(178)+181;}
					
					sensor = new Sensor(robotLocation[0], robotLocation[1], sensorRange, sensorAngle, heading, closeError, midError, farError, maxLocationError);
					System.out.println("None Detected");
					navigationDistance = sensor.measureDistance(navPoint);
					System.out.println("Distance to Navigation Point: "+navigationDistance);
				} else {
					System.out.println("Number of Points detected: "+pointsDetected.size());
					System.out.println("Location: "+robotLocation[0]+", "+robotLocation[1]);
					System.out.println("Heading: "+heading);
					//Determine Robot's position based on closest reference point
					Point refPoint = pointsDetected.get(0); //Defines coordinates of closest reference point
					robotLocation = sensor.locateRobot(refPoint); //Defines robot's coordinates based on closest reference point
					double turnAngle = sensor.measureAngle(navPoint); //Measures angle robot must turn
					System.out.println("Turn Angle: "+turnAngle);
					navigationDistance = sensor.measureDistance(navPoint);
					System.out.println("Distance to Navigation Point: "+navigationDistance);
					
					robotLocation = Controller.move(robotLocation, heading, turnAngle, navigationDistance); //Move Robot toward Navigation Point
					System.out.println("New Location: "+robotLocation[0]+", "+robotLocation[1]);
					heading = heading + turnAngle; //Redefine Robot heading			
					System.out.println("New heading: "+heading);
					//Reposition Sensor
					sensor = new Sensor(robotLocation[0], robotLocation[1], sensorRange, sensorAngle, heading, closeError, midError, farError, maxLocationError);
					navigationDistance = sensor.measureDistance(navPoint);
				}
			}//End while loop (move to next point)
			int numFound = i+1;
			System.out.println("Found "+numFound+" points!");
			System.out.println(navigationDistance);
		}//End for loop (navigation complete)
	}
}//end NavigationSimulation