package evol;

import java.util.Random;

/*
 * A class to construct the chromosomes used in Evolution Strategy.
 */
public class ESChromosome {

    //Class holds a Chromosome c, and the strategy parameters for each Chromosome

    private Chromosome c;
    //variance array for the chromosome
    private Double[] varMatrix;
    //a random number for variance initialization
    private Random rand = new Random();
    
    /**
     * Creates a new ESChromosome
     * @param c new chromosome
     */
    public ESChromosome(Chromosome c) {
        this.c = c;
        int numGenes = c.getNumGenes();
        varMatrix = new Double[numGenes];
        //initialize variance to random Gaussian, stores in a matrix
        for (int i = 0; i < numGenes; i++) {
            varMatrix[i] = rand.nextGaussian();
        }
    }
    
    //getter and setter methods
    public void evaluateFitness() {
        c.evaluate();
    }

    public Double[] getVarMatrix() {
        return varMatrix;
    }

    public double getFitness() {
        return c.getFitness();
    }

    public void setVarMatrix(Double[] varMatrix) {
        this.varMatrix = varMatrix;
    }

    public int getNumGenes() {
        return c.getNumGenes();
    }

    public Double getVar(int i) {
        return varMatrix[i];
    }

    public void setVar(int i, double var) {
        varMatrix[i] = var;
    }

    public Double[] getGenes() {
        return c.getGenes();
    }

    public Double getGene(int i) {
        return c.getGene(i);
    }

    public void setGenes(Double[] newGenes) {
        for (int i = 0; i < newGenes.length; i++) {
            c.setGene(i, newGenes[i]);
        }
    }

    public void setGene(int i, double gene) {
        c.setGene(i, gene);
    }
    public double getAvgError(){
        return c.getAvgError();
    }
}
