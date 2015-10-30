package evol;

import java.util.ArrayList;
import java.util.Random;

public class EvolutionStrategy extends TrainingStrategy {

    // number of generations
    private int gens;
    // population size
    private int mu;
    //number of children per generation and number to be removed each generation
    private int lambda;
    //number of parents in crossover
    private int rho;
    //Population
    private ArrayList<ESChromosome> pop;
    //parameters for mutating strategy parameters
    private double tauOverall;
    private double tauInd;
    // neural net to train
    private FeedForwardANN net;
    private Random rand = new Random();
    /**
     * Each member of the population will need: a Chromosome, a rotation vector,
     * a variance matrix
     */
    public EvolutionStrategy() {

    }

    protected void initPop() {
        //TODO initialize the population
    }

    public void run() {
        initPop();
        ESChromosome[] pool = new ESChromosome[rho];
        // runs for specified number of generations
        for (int g = 0; g < gens; g++) {

            for (int l = 0; l < lambda; l++) {
                ESChromosome yl = new ESChromosome(new Chromosome(net));
                pool = marriage(pool);

                yl = recombineParams(pool);
                yl = recombineC(pool);

                yl = mutateParams(yl);
                yl = mutateC(yl);
                yl.evaluateFitness();
                pop.add(yl);
            }
            prunePop(lambda);
        }
    }

    private void prunePop(int pruneBy) {

        for (int i = 0; i < pruneBy; i++) {
            double lowfit = pop.get(0).getFitness();
            int lowIndex = 0;
            for (int j = 1; j < pop.size(); j++) {
                double curFit = pop.get(j).getFitness();
                if(curFit < lowfit){
                    lowIndex = j;
                    lowfit = curFit;
                }
            }
            pop.remove(lowIndex);
        }
    }

    private ESChromosome[] marriage(ESChromosome[] pool) {
        //TODO figure out selection
        return pool;
    }

    private ESChromosome mutateC(ESChromosome start) {
        //TODO: vector xj (t+1) = element j in Chromosome at time t
        //  + variance of element j at time t times N(0,1) (this is mutation)
        return start;
    }

    private ESChromosome recombineC(ESChromosome[] pool) {
        //TODO: Decide on Uniform or Intermediate Crossover and implement
        return pool[0];
    }

    private ESChromosome mutateParams(ESChromosome start) {
        Double[][] newVar = start.getVarMatrix();
        //initialize newVar
        for(int i = 0; i < start.getVarMatrixSize(); i++){
            double r1 = rand.nextGaussian();
            double r2 = rand.nextGaussian();
            newVar[i][i] = (newVar[i][i]*Math.exp((tauOverall*r1)  + (tauInd*r2)));
        }
        //TODO: sigma j at time t + 1 = sigma j at time t * 
        //      e^(overal tau * some number + this tau * some number)
        start.setVarMatrix(newVar);
        return start;
    }

    private ESChromosome recombineParams(ESChromosome[] pool) {
        //TODO make this method do machine learning things
        return pool[0];
    }

    public void setGens(int gens) {
        this.gens = gens;
    }

    public int getGens() {
        return gens;
    }

    public void setMu(int mu) {
        this.mu = mu;
    }

    public int getMu() {
        return mu;
    }

    public void setLambda(int lambda) {
        this.lambda = lambda;
    }

    public int getLambda() {
        return lambda;
    }

    public void setRho(int rho) {
        this.rho = rho;
    }

    public int getRho() {
        return rho;
    }

    public void setTau() {

    }

    public double getTauInd() {
        return tauInd;
    }

    public double getTauOVerall() {
        return tauOverall;
    }
}
