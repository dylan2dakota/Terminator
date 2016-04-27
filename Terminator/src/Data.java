import java.util.ArrayList;
/**
 * This class calculates the total navigation distance the robot travels during the simulation.
 * this class calculates the average error percentage of the sensor 
 * @author dakodah Davis
 * @version 1.0
 */
public class Data {

	public Data(){

	}
/**
 * This method adds up the distance traveled to each point to find the total distance traveled in the simulation
 * @param totalNavigationDistance total navigation distance of each point
 * @return total sum of distance traveled
 */
	//Method to calculate total navigation distance in a simulation
	public static double sumDistance(ArrayList<Double> totalNavigationDistance) {
		double sum = 0;
		for (int i=0; i<totalNavigationDistance.size(); i++){
			sum = sum + totalNavigationDistance.get(i);
		}
		return sum;
	}
	/**
	 * this method calculates the average error of every point the senor finds 
	 * @param totalError the average of the sensor on each point 
	 * @return average of sensor error
	 */
	//Method to calculate average error percentage
	public static double errorAverage(ArrayList<Double> totalError) {
		double average;
		double sum = 0;
		for (int i=0; i<totalError.size(); i++){
			sum = sum + totalError.get(i);
		}
		average = sum/totalError.size();
		return average;
	}
	
}//end Data