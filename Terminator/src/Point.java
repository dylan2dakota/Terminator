/**
 * The Point class is used to create a point in the form of a circle that can displayed in the preview area.
 * The points contain a unique coordinate location.
 * 
 * @author Dylan Cox
 * @author Rhett Watson
 * @version 1.0
 */

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Point extends Circle {

	public Point() {

	}

	/**
	 * Creates a circular point at a given location in the preview area.
	 * 
	 * @param xCoord the x-coordinate of a reference or navigation point
	 * @param yCoord the y-coordinate of a reference of navigation point
	 */
	public Point(double xCoord, double yCoord) {
		setCenterX(xCoord); // Defined by Generator
		setCenterY(yCoord); // Defined by Generator
		setRadius(5);
		setFill(Color.BLACK);

	}
}// end Point