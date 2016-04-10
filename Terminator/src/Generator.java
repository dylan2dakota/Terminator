

import java.util.Random;

/**
 * @author Dylan and Dakodah
 * @version 1.0
 * @created 29-Feb-2016 12:35:51 PM
 */
public class Generator {

	private static int[] x;
	private static int[] y;
	public static Point[] points;
	int density;

	public Generator() {

	}

	public void finalize() throws Throwable {

	}

	/**
	 * @param user specified density
	 * @return array of points 
	 */
	public static Point[] generatePoints(int density) {
		int i, j, k;
		int mapWidth = SetupPage.getMapWidth();
		int mapHeight = SetupPage.getMapHeight();
		// Random object for generator
		Random rn = new Random();
		x = new int[density];
		y = new int[density];
		points = new Point[density];

		// Generate random points
		for (i = 0; i < density; i++) {
			x[i] = (rn.nextInt(mapWidth)) + 2; // x-coordinates
		}
		for (j = 0; j < density; j++) {
			y[j] = (rn.nextInt(mapHeight)); // y-coordinates
		}

		for (k = 0; k < density; k++) {
			points[k] = new Point(x[k], y[k]); // array of points
		}

		return points;
	}

}// end Generator