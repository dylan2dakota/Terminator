

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
	
	public static double[] move(double[] oldLocation, double heading, double turnAngle) {
		double translateAngle = heading + turnAngle;
		double translateX = Math.cos(Math.toRadians(translateAngle));
		double translateY = Math.sin(Math.toRadians(translateAngle));
		double[] newLocation = {oldLocation[1]+translateX, oldLocation[2]+translateY};
		return newLocation;
	}
	
}//end Controller