

/**
 * @author Dakodah
 * @version 1.0
 * @created 29-Feb-2016 12:35:51 PM
 */
public class Controller {

	public Controller(){

	}
/*
	public int rotateRobot(int startx, int starty, int finalx, int finaly){
		int angle = 0;
		
		return angle;

	}

	public void translateRobot(int startx, int starty, int finalx, int finaly){
		int newx;
		int newy;

	}
	*/
	
	public static double[] move(double[] oldLocation, double heading, double turnAngle, double distance) {
		double translateAngle = heading + turnAngle;
		double translateX = (distance/2)*Math.cos(Math.toRadians(translateAngle));
		double translateY = (distance/2)*Math.sin(Math.toRadians(translateAngle));
		double[] newLocation = {oldLocation[0]+translateX, oldLocation[1]-translateY};
		return newLocation;
	}
	
}//end Controller