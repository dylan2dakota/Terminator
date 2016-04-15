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
		double actualDistance;
		double locationError;
		double locationErrorPercentage;
		double detectionError;
		ArrayList<Double> collectNavigationDistance = new ArrayList<>();
		ArrayList<Double> collectDetectionError = new ArrayList<>();
		ArrayList<Double> collectLocationErrorPercentage = new ArrayList<>();
		
		//Collect array of all Navigation Points
		navPoints = SetupPage.getNavPoints();
		//Create sensor with initial parameters
		heading = 90;
		robotLocation[0] = 375; robotLocation[1] = 600;
		sensor = new Sensor(robotLocation[0], robotLocation[1], sensorRange, sensorAngle, heading, closeError, midError, farError, maxLocationError); 
		int loops = 0; //Count iterations
		
		for (int i=0; i<navPoints.length; i++) {
			
			navigationDistance = 10; //Initial value for navigation distance

			//Determine navigation point
			Point navPoint = navPoints[i];

			while (navigationDistance >= 1) {
				loops++;
				System.out.println(loops);
				//Search for reference points (includes sensor detection error)
				ArrayList<Point> pointsDetected = sensor.detectPoints();
				//Actual number of reference points in sensor area (doesn't include sensor detection error)
				ArrayList<Point> pointsInSensor = sensor.detectPointsNoError();
				//Calculate detection error
				if (pointsInSensor.size() > 0){
				detectionError = ((pointsInSensor.size()-pointsDetected.size())/pointsInSensor.size())*100;
				collectDetectionError.add(detectionError);
				}
				//If no points in sensor area, move forward.
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
					//Reposition sensor
					sensor = new Sensor(robotLocation[0], robotLocation[1], sensorRange, sensorAngle, heading, closeError, midError, farError, maxLocationError);
					System.out.println("None Detected");
					//Measure distance from robot to point
					navigationDistance = sensor.measureDistance(navPoint);
					System.out.println("Distance to Navigation Point: "+navigationDistance);
				} else {
					System.out.println("Number of Points detected: "+pointsDetected.size());
					System.out.println("Location: "+robotLocation[0]+", "+robotLocation[1]);
					System.out.println("Heading: "+heading);
					//Determine Robot's position based on reference point
					Point refPoint = pointsDetected.get(0); //Defines coordinates of reference point
					//Measure the distance from robot's location to navigation point
					actualDistance = sensor.measureDistance(navPoint);
					System.out.println("Actual Distance: "+actualDistance);
					robotLocation = sensor.locateRobot(refPoint); //Defines robot's coordinates based on reference point
					//Reposition sensor based on new robot location
					sensor = new Sensor(robotLocation[0], robotLocation[1], sensorRange, sensorAngle, heading, closeError, midError, farError, maxLocationError);
					//Measure angle between robot heading and navigation point
					double turnAngle = sensor.measureAngle(navPoint);
					System.out.println("Turn Angle: "+turnAngle);
					//Measure distance from robot's sensor-estimated location to navigation point
					navigationDistance = sensor.measureDistance(navPoint);
					System.out.println("Distance to Navigation Point: "+navigationDistance);
					//Calculate Sensor location error and error percentage
					locationError = Math.abs(actualDistance-navigationDistance);
					locationErrorPercentage = Math.abs((actualDistance-navigationDistance)/actualDistance)*100;
					collectLocationErrorPercentage.add(locationErrorPercentage);
					System.out.println("Location Error: "+locationError+" ("+locationErrorPercentage+"%)");
					//Move robot toward navigation point (half the measured navigation distance)
					robotLocation = Controller.move(robotLocation, heading, turnAngle, navigationDistance);
					//Collect distance navigated
					collectNavigationDistance.add(navigationDistance/2);
					System.out.println("New Location: "+robotLocation[0]+", "+robotLocation[1]);
					//Determine new heading
					heading = heading + turnAngle; //Redefine Robot heading			
					System.out.println("New heading: "+heading);
					//Reposition Sensor
					sensor = new Sensor(robotLocation[0], robotLocation[1], sensorRange, sensorAngle, heading, closeError, midError, farError, maxLocationError);
					//Remeasure distance to navigation point
					navigationDistance = sensor.measureDistance(navPoint);
				}
			}//End while loop (move to next point)
			int numFound = i+1;
			System.out.println("Found "+numFound+" points!");
			System.out.println(navigationDistance);
		}//End for loop (navigation complete)
		//Sum total navigation distance
		double sumDistance = Data.sumDistance(collectNavigationDistance);
		System.out.println("Total Navigation Distance: "+sumDistance+" units");
		//Average detection error percentage
		double detectionErrorAverage = Data.errorAverage(collectDetectionError);
		System.out.println("Average Detection Error: "+detectionErrorAverage+"%");
		//Average location error percentage
		double locationErrorAverage = Data.errorAverage(collectLocationErrorPercentage);
		System.out.println("Average Location Error: "+locationErrorAverage+"%");
	}//End navigate() method
}//end NavigationSimulation