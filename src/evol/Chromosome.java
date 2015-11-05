package evol;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Chromosome implements Comparable {

	private int numGenes;
	private FeedForwardANN net;

	private Double[] genes;
	private double fitness;
	private double networkError;
	private FitnessFunction f;
	private ArrayList<Double> outputs;
	private ArrayList<TrainingInstance> trainSet;
	private double avgError = 0; 

	public Chromosome(FeedForwardANN net, ArrayList<TrainingInstance> trainingSet) {

		this.net = net;

		calcNumGenes();

		genes = new Double[numGenes];

		f = new FitnessFunction();
		this.trainSet = trainingSet;

		// sets random initial gene values
		randomInit();
		evaluate();
	}

	/**
	 * Calculates the number of genes in this chromosome
	 */
	private void calcNumGenes() {
		// per layer
		for (int i = 0; i < net.getNumLayers(); i++) {
			// per node
			for (int j = 0; j < net.getLayer(i).size(); j++) {
				numGenes += net.getLayer(i).get(j).getNumWeights();
			}
		}

	}

	/**
	 * Randomly initializes gene (weight) values
	 */
	private void randomInit() {
		for (int i = 0; i < numGenes; i++) {
			genes[i] = Math.random();
		}
	}

	/**
	 * Calculates the fitness of this chromosome using a subset of the training set
	 */
	public void evaluate() {

		//
		// Step 1: assign weights using gene values
		//
		int placeholder = 0;

		// by layer
		for (int l = 1; l < net.getNumLayers(); l++) {
			// by node in layer
			for (int n = 0; n < net.getLayer(l).size(); n++) {
				ArrayList<Double> newWeights = new ArrayList<Double>();
				// adds appropriate section of chromosome as this node's weights
				for (int i = placeholder; i < net.getLayer(l).get(n).getNumWeights() + placeholder; i++) {
					newWeights.add(genes[i]);
				}
				// modify placeholder
				placeholder += newWeights.size();
				net.getLayer(l).get(n).setWeights(newWeights);
			}
		}

		//
		// Step 2: generate outputs using all the new weights
		//
		
		double cumulativeError = 0;
		for (TrainingInstance t : trainSet) {
			// clear net inputs
			net.clearInputs();
			
			// set inputs and expected outputs
			net.setInputs(t.getInputs());
			net.setTargetOutputs(t.getOutput());
			
			// generate network output
			net.generateOutput();
			
			// save error
			cumulativeError += net.calcNetworkError();
		}

		//
		// Step 3: calculate & assign fitness
		//
		avgError = cumulativeError/trainSet.size();

		if (avgError > 0) {
			fitness = (1 / avgError);
		} else {
			fitness = Double.MAX_VALUE;
		}
	}

	public int getNumGenes() {
		return numGenes;
	}

	public Double[] getGenes() {
		return genes;
	}

	public double getFitness() {
		return fitness;
	}

	public double getNetworkError() {
		return networkError;
	}

	public void setGene(int index, double gene) {
		genes[index] = gene;
	}

	public double getGene(int index) {
		return genes[index];
	}

	@Override
	public int compareTo(Object o) {
		// compareTo should return < 0 if this is supposed to be
		// less than other, > 0 if this is supposed to be greater than
		// other and 0 if they are supposed to be equal
		if (o.equals(null)) {
			return 0;
		} else {
			return Double.compare(this.fitness, ((Chromosome) o).getFitness());
		}
	}

	public String toString() {
		DecimalFormat threeDForm = new DecimalFormat("#.###");

		String s = "< ";
		for (int i = 0; i < numGenes - 1; i++) {
			s += (Double.valueOf(threeDForm.format(genes[i])) + " ~ ");
		}

		s += (Double.valueOf(threeDForm.format(genes[numGenes - 1])) + ">");
		return s;
	}
	
	public double getAvgError(){
		return avgError;
	}
}
