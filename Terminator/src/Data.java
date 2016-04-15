import java.util.ArrayList;

public class Data {

	public Data(){

	}

	//Method to calculate total navigation distance in a simulation
	public static double sumDistance(ArrayList<Double> totalNavigationDistance) {
		double sum = 0;
		for (int i=0; i<totalNavigationDistance.size(); i++){
			sum = sum + totalNavigationDistance.get(i);
		}
		return sum;
	}
	
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