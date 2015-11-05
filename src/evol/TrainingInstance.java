package evol;

import java.util.ArrayList;

public class TrainingInstance {
	
	private ArrayList<Double> output;
	private ArrayList<Double> inputs;
	
	/**
	 * Stores one input-output pair for a target function
	 */
	public TrainingInstance(ArrayList<Double> inputs, ArrayList<Double> output){
		this.inputs = inputs;
		this.output = output;
	}

	public ArrayList<Double> getOutput() {
		return output;
	}

	public void setOutput(ArrayList<Double> output) {
		this.output = output;
	}

	public ArrayList<Double> getInputs() {
		return inputs;
	}

	public void setInputs(ArrayList<Double> inputs) {
		this.inputs = inputs;
	}
	
	

}
