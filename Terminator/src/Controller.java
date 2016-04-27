

/**
 * this class tracks and moves the robots location based on the navigation point
 * @author Dakodah
 * @version 1.0
 * @created 29-Feb-2016 12:35:51 PM
 */
public class Controller {

	public Controller(){

	}
/**
 * This method controls the location of the robot. the robot will move based on the location of the reference 
 * points and the navigation point.
 * @param oldLocation last location of robot
 * @param heading direction the robot is facing
 * @param turnAngle the angle the robot has to turn to face the point it is naviaging to
 * @param distance how far away the navigation point is from the robot
 * @return new location of the robot
 */
	
	public static double[] move(double[] oldLocation, double heading, double turnAngle, double distance) {
		double translateAngle = heading + turnAngle;
		double translateX = (distance/2)*Math.cos(Math.toRadians(translateAngle));
		double translateY = (distance/2)*Math.sin(Math.toRadians(translateAngle));
		double[] newLocation = {oldLocation[0]+translateX, oldLocation[1]-translateY};
		return newLocation;
	}
	
}//end Controller