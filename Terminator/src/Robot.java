

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class gets a robot image and puts it on the GUI
 * @author Dylan Cox Dakodah Davis
 * @version 1.0
 */
public class Robot extends ImageView {


	public Robot() {
	}

	/**
	 * this method creates the image and puts the robot image on the main GUI
	 * @param width the witch of the robot image
	 * @param xCoord the x coordinate of the image
	 * @param yCoord the y coordination of the image
	 * @URL  https://upload.wikimedia.org/wikipedia/commons/thumb/0/05/Robot_icon.svg/2000px-Robot_icon.svg.png
	 */
	public Robot(double width, double xCoord, double yCoord) {
		// Load the image
		Image image = new Image(
				"https://upload.wikimedia.org/wikipedia/commons/thumb/0/05/Robot_icon.svg/2000px-Robot_icon.svg.png");

		// Display image
		setImage(image);
		setFitWidth(width);
		setPreserveRatio(true);
		setX(xCoord);
		setY(yCoord);
	}

}// end Robot