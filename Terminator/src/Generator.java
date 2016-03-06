import java.util.Random;

/**
 * @author Owner
 * @version 1.0
 * @created 29-Feb-2016 12:35:51 PM
 */
public class Generator {

	private static int[] x;
	private static int[] y;
	public static Point[] points;
	int density;

	public Generator(){
		
	}

	public void finalize() throws Throwable {

	}
	
	public static Point[] generatePoints(int density){
		int i,j,k;
		//random object for generator
		Random rn = new Random();
		
		//generate random points
		for(i=0;i<density;i++){
			x[i] = (rn.nextInt(999))+1; //x-coordinates
		}
		for(j=0;j<density;j++){
			y[j] = (rn.nextInt(700)); //y-coordinates
		}
		
		for(k=0;k<density;k++){
			points[k]= new Point(x[k],y[k]); //array of points
		}
		
		return points;
	}
	
}//end Generator