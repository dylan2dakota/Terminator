import java.util.Random;

/**
 * @author Owner
 * @version 1.0
 * @created 29-Feb-2016 12:35:51 PM
 */
public class Generator {

	private double[] x;
	private double[] y;

	public Generator(){
		int i,j;
		//random object for generator
		Random rn = new Random();
		
		//generate random points
		for(i=0;i<100;i++){
			x[i] = (rn.nextDouble()*999)+1;
		}
		for(j=0;j<100;j++){
			y[j] = (rn.nextDouble()*700);
		}		

	}

	public void finalize() throws Throwable {

	}
	public void generatePoints(){

	}

	public int getPointDensity(){
		return 0;
	}
}//end Generator