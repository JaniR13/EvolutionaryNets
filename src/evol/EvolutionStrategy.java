package evol;

import java.util.ArrayList;

public class EvolutionStrategy extends TrainingStrategy {
    // number of generations
	private int gens;
	// population size
	private int mu;
        //number of children per generation and number to be removed each generation
        private int lambda;
	// parents = 5 -> 5 parent crossover
	private int parents;
        //Population
        private ArrayList<ESChromosome> pop;
        //parameters for mutating strategy parameters
        private double tauOverall;
        private double tauInd;
        // neural net to train
	private FeedForwardANN net;
        /** 
         * Each member of the population will need:
         * a Chromosome, 
         * a rotation vector, 
         * a variance matrix
         */
        public EvolutionStrategy(){
            
        }
        
        protected void initPop(){
            //TODO initialize the population
        }
        
        private void run(){
		// runs for specified number of generations
		for (int g = 0; g < gens; g++){
			
		}
	}
        
        public void setGens(int gens) {
		this.gens = gens;
	}
        public int getGens() {
		return gens;
	}
    
}
