package evol;

import java.util.ArrayList;
import java.util.Collections;

public class GA extends TrainingStrategy {

	// crossover rate
	// private double cRate;

	// mutation rate
	private double mRate;
	// number of generations
	private int gens;
	// population size
	private int popSize;
	// pts = 5 -> 5-point crossover
	private int pts;
	// percent of pop to replace in each generation
	private double repl;
	// number of parents for crossover
	private int numParents = 2;

	// actual population
	private ArrayList<Chromosome> pop;

	// neural net
	private FeedForwardANN net;
	// training set
	private ArrayList<TrainingInstance> trainingSet;

	/**
	 * Creates a new GA instance
	 * 
	 * @param cRate
	 *            crossover rate between 0 and 1
	 * @param mRate
	 *            mutation rate between 0 and 1
	 * @param gens
	 *            number of generations
	 * @param pop
	 *            population size
	 * @param pts
	 *            number of points for cross over (e.g. pts = 5 -> 5-point
	 *            crossover)
	 * @param repl
	 *            percentage of population to replace each generation, between 0
	 *            and 1
	 */
	public GA(double mRate, int gens, int popSize, int pts, double repl) {
		// this.cRate = cRate;
		this.mRate = mRate;
		this.gens = gens;
		this.popSize = popSize;
		this.pts = pts;
		this.repl = repl;

	}

	/** Creates new population of random individuals */
	protected void initPopulation() {
		pop = new ArrayList<Chromosome>();
		for (int i = 0; i < popSize; i++) {
			pop.add(new Chromosome(net, trainingSet));
		}
	}

	/**
	 * Calls optimizer on weights of the given neural net
	 */
	protected void optimize(FeedForwardANN net) {
		this.net = net;

		initPopulation();

		run();
	}
	
	/**
	 * Must be called before optimization can be run
	 */
	public void setTrainingSet(ArrayList<TrainingInstance> trainingSet){
		this.trainingSet = trainingSet;
	}

	/**
	 * Runs optimization
	 */
	private void run() {
		System.out.println("--------------- STARTING! ---------------");
		System.out.println("Initial fitness: " + (pop.get(pop.size()-1)).getFitness());
		System.out.println("Initial error: " + (pop.get(pop.size()-1)).getAvgError());
		
		// runs for specified number of generations
		for (int g = 0; g < gens; g++) {
			System.out.println("> Generation " + g);
			// sorts population in ascending order
			// lower index -> lower 1/error -> higher error -> lower fitness
			Collections.sort(pop);

			// create popSize * repl offspring
			int numOffspring = (int) Math.floor(popSize * repl);
			ArrayList<Chromosome> newGen = new ArrayList<Chromosome>();

			for (int o = 0; o < numOffspring; o++) {
				// step 1: select() numParents parents
				ArrayList<Chromosome> parents = new ArrayList<Chromosome>();
				for (int p = 0; p < numParents; p++) {
					parents.add(select());
				}

				// step 2: conduct crossover() with parents
				Chromosome offspring = crossover(parents);

				// step 3: mutate() offspring
				mutate(offspring);

				// step 4: evaluate offspring
				offspring.evaluate();

				newGen.add(offspring);
			}
			
			// recall: lower index -> lower fitness
			// replace least fit individuals with all the offspring
			for (int i = 0; i < newGen.size(); i++){
				pop.set(i, newGen.get(i));
			}
		}
		
		Collections.sort(pop);
		
		// TODO: return fittest
		System.out.println("--------------- FINISHED!---------------");
		System.out.println("Final fitness: " + (pop.get(pop.size()-1)).getFitness());
		System.out.println("Final error: " + (pop.get(pop.size()-1)).getAvgError());

	}

	/**
	 * Selects a single Chromosome for crossover/mutation
	 * 
	 * @return selected Chromosome
	 */
	protected Chromosome select() {
		// sum fitness in population
		double totalFitness = 0;
		for (Chromosome c : pop) {
			totalFitness += c.getFitness();
		}

		// make "roulette wheel" of upper probability bounds (normalized)
		double[] roulette = new double[popSize];
		for (int i = 0; i < popSize; i++) {
			roulette[i] = (pop.get(i).getFitness() / totalFitness);
			if (i > 0) {
				roulette[i] += roulette[i - 1];
			}
		}

		// make a random "spin"
		double spin = Math.random();

		// find where spin lives in population
		for (int r = 0; r < roulette.length; r++) {
			if (spin < roulette[r]) {
				//System.out.println((r));
				return pop.get(r);
			}
		}
		// this should never be reached
		return null;
	}

	/**
	 * Creates an offspring Chromosome using uniform crossover
	 * 
	 * @param parents
	 *            an arraylist of parent Chromosomes
	 */
	protected Chromosome crossover(ArrayList<Chromosome> parents) {
		double parentSize = parents.size();
		double selectProb = (1 / parentSize);

		double[] parentProbs = new double[parents.size()];

		// vector of upper bounds on parent probabilities (uniform)
		parentProbs[0] = selectProb;
		for (int i = 1; i < parents.size(); i++) {
			parentProbs[i] = parentProbs[i - 1] + selectProb;
		}

		Chromosome offspring = new Chromosome(net, trainingSet);
		// probabilistically selects each gene
		for (int i = 0; i < parents.get(0).getNumGenes(); i++) {
			double p = Math.random();

			for (int j = 0; j < parentProbs.length; j++) {
				if (p < parentProbs[j]) {
					offspring.setGene(i, parents.get(j).getGene(i));
					break;
				}
			}

		}

		return offspring;
	}

	/**
	 * Randomly mutates the given chromosome
	 */
	protected Chromosome mutate(Chromosome c) {
		for (int i = 0; i < c.getNumGenes(); i++) {
			double p = Math.random();
			if (p < mRate) {
				//System.out.println("Mutated " + i);
				double sign = Math.random();
				if (sign < 0.5) {
					c.setGene(i, c.getGene(i) + Math.random() / 10);
				} else {
					c.setGene(i, c.getGene(i) - Math.random() / 10);
				}
			}
		}
		return c;
	}

	//
	// below here are just getters and setters
	//

	// public double getcRate() {
	// return cRate;
	// }
	//
	// public void setcRate(double cRate) {
	// this.cRate = cRate;
	// }

	public double getmRate() {
		return mRate;
	}

	public void setmRate(double mRate) {
		this.mRate = mRate;
	}

	public int getGens() {
		return gens;
	}

	public void setGens(int gens) {
		this.gens = gens;
	}

	public int getPopSize() {
		return popSize;
	}

	public void setPopSize(int popSize) {
		this.popSize = popSize;
	}

	public int getPts() {
		return pts;
	}

	public void setPts(int pts) {
		this.pts = pts;
	}

	public double getRepl() {
		return repl;
	}

	public void setRepl(double repl) {
		this.repl = repl;
	}

	public ArrayList<Chromosome> getPop() {
		return pop;
	}

	public void setPop(ArrayList<Chromosome> pop) {
		this.pop = pop;
	}

	public FeedForwardANN getNet() {
		return net;
	}

	public void setNet(FeedForwardANN net) {
		this.net = net;
	}

}
