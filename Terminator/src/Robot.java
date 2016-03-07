

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Robot extends ImageView {

	public Robot() {
	}

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