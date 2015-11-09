package evol;

public class MeanSquaredError {
	
	// Calculates the mean squared error, in order to determine efficacy 
        // of network
    
	/**
	 * Calculates the derivative of error with respect to output
	 * @param output the neuron's output
	 * @param target the target output
	 * @return
	 */
	public Double calcDerivwrtOutput(Double output, Double target){
		//System.out.println("********** (" + target + " - " + output + ") **********");
		return (output - target);
	}

}