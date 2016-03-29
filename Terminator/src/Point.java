

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Point extends Circle {

	public Point() {

	}

	public Point(double xCoord, double yCoord) {
		setCenterX(xCoord); // Defined by Generator
		setCenterY(yCoord); // Defined by Generator
		setRadius(5);
		setFill(Color.BLACK);

	}
}// end Point