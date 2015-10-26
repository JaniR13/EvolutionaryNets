package evol;

import java.util.ArrayList;

public class FitnessFunction {

	/** Calculates MSE */
	public double evaluate(ArrayList<Double> expected, ArrayList<Double> observed){
		double sum = 0;
		for (int i = 0; i < expected.size(); i++){
			sum += ((expected.get(i) - observed.get(i))*(expected.get(i) - observed.get(i)));
		}		
		return (sum/expected.size());
	}
	
}
