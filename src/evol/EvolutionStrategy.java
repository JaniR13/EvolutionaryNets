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
    private int numGenes;

    /**
     * Each member of the population will need: a Chromosome, and 
     * a variance matrix
     */
    public EvolutionStrategy(FeedForwardANN net, int lambda, int mu, int rho, int gens) {
        this.net = net;
        this.lambda = lambda;
        this.mu = mu;
        this.rho = rho;
        this.gens = gens;   
    }

    protected void initPop() {
        for (int i = 0; i < mu; i++){
            ESChromosome es = new ESChromosome(new Chromosome(net));
            pop.add(es);
        }
    }

    public void run() {
        initPop();
        numGenes = pop.get(0).getVarMatrixSize();
        ESChromosome[] pool = new ESChromosome[rho];
        // runs for specified number of generations
        for (int g = 0; g < gens; g++) {

            for (int l = 0; l < lambda; l++) {
                ESChromosome yl = new ESChromosome(new Chromosome(net));
                pool = marriage(pool);

                yl = recombineParams(pool, yl);
                yl = recombineC(pool, yl);

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
                if (curFit < lowfit) {
                    lowIndex = j;
                    lowfit = curFit;
                }
            }
            pop.remove(lowIndex);
        }
    }

    private ESChromosome[] marriage(ESChromosome[] pool) {
        for(int i = 0; i < rho; i++){
            pool[i] = pop.get(rand.nextInt(mu));
        }
        return pool;
    }

    private ESChromosome mutateC(ESChromosome start) {
        Double[] newGenes = start.getGenes();
        for (int i = 0; i < numGenes; i++) {
            newGenes[i] += start.getVar(i) * rand.nextGaussian();
        }
        start.setGenes(newGenes);
        return start;
    }

    private ESChromosome recombineC(ESChromosome[] pool, ESChromosome child) {
        for (int i = 0; i < numGenes; i++) {
            child.setGene(i, pool[rand.nextInt(rho)].getGene(i));
        }
        return child;
    }

    private ESChromosome mutateParams(ESChromosome start) {
        Double[] newVar = start.getVarMatrix();
        for (int i = 0; i < numGenes; i++) {
            double r1 = rand.nextGaussian();
            double r2 = rand.nextGaussian();
            newVar[i] = (newVar[i] * Math.exp((tauOverall * r1) + (tauInd * r2)));
        }
        start.setVarMatrix(newVar);
        return start;
    }

    private ESChromosome recombineParams(ESChromosome[] pool, ESChromosome child) {
        for (int i = 0; i < numGenes; i++) {
            child.setVar(i, pool[rand.nextInt(rho)].getVar(i));
        }
        return child;

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
        tauOverall = 1/Math.sqrt(2*mu);
        tauInd = 1/Math.sqrt(2*Math.sqrt(mu));
    }

    public double getTauInd() {
        return tauInd;
    }

    public double getTauOVerall() {
        return tauOverall;
    }
}
