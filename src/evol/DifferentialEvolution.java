package evol;

import java.util.ArrayList;
import java.util.Random;

public class DifferentialEvolution extends TrainingStrategy {
    //number of generations
    private int gens;
    //population size
    private int popSize;
    //beta adjusts size of mutation
    private double beta;
    //pr is crossover rate
    private double pr;
    private FeedForwardANN net;
    private ArrayList <Chromosome> pop = new ArrayList<Chromosome>();
    private Random rand = new Random();
    private int numGenes;
    private ArrayList<TrainingInstance> trainingSet;
    public int genCount;
    
    /**
     * Creates a new Differential Evolution instance
     * @param gens number of generations
     * @param popSize population size
     * @param beta mutation adjustment parameter
     * @param pr crossover rate
     * @param net net to train on
     * @param trainingSet training data
    */
    public DifferentialEvolution(int gens, int popSize, double beta, double pr, 
            FeedForwardANN net, ArrayList<TrainingInstance> trainingSet){
        this.gens = gens;
        this.popSize = popSize;
        this.beta = beta;
        this.pr = pr;
        this.net = net;
        this.trainingSet = trainingSet;
    }
    public FeedForwardANN run(double conf){
        //initialize the population
        initPop();
        //determine starting fitness
        Chromosome best = returnBest();
        double err = best.getAvgError();
        System.out.println("--------------- STARTING! ---------------");
        System.out.println("Initial fitness: " + best.getFitness());
        System.out.println("Initial error: " + err);
        genCount = 0;
        while(err > conf && genCount < gens){
        //for(int g = 0; g < gens; g++){
            //System.out.println("> Generation " + g);
            //generate a child for each member of the population
            for(int p = 0; p < popSize; p++){
                //determine fitness of parent
                pop.get(p).evaluate();
                double fitparent = pop.get(p).getFitness();
                //create a trial vector
                Chromosome trialvect = new Chromosome(net, trainingSet);
                //mutate trial vector
                trialvect = mutation(trialvect, pop.get(p));
                //new child is recombination of trialvector and parent
                trialvect = crossover(trialvect, pop.get(p));
                //determine fitness of child
                trialvect.evaluate();
                double fitchild = trialvect.getFitness();
                //replace the parent with the child if the child has better fitness
                if(fitchild > fitparent){
                    pop.set(p, trialvect);
                }
            }
            err = returnBest().getAvgError();
            genCount++;
        }
        //return the best individual
        best = returnBest();
        System.out.println("--------------- FINISHED!---------------");
	System.out.println("Final fitness: " + best.getFitness());
	System.out.println("Final error: " + best.getAvgError());
        best.evaluate();
        return net;
    }
    //randomly initialize the population
    private void initPop() {
        for (int i = 0; i < popSize; i++) {
            Chromosome de = new Chromosome(net, trainingSet);
            pop.add(de);
        }
        numGenes = pop.get(0).getNumGenes();
    }
    //trial vector = parent  + beta*(difference between 2 random parents
    private Chromosome mutation(Chromosome child, Chromosome parent){
        Chromosome p2 = pop.get(rand.nextInt(popSize));
        Chromosome p3 = pop.get(rand.nextInt(popSize));
        for(int i = 0; i < numGenes; i++){
            double adj = parent.getGene(i)+ beta*(p2.getGene(i) - p3.getGene(i));
            child.setGene(i, adj);
        }
        return child;
    }
    private Chromosome crossover(Chromosome child, Chromosome parent){//Binomial crossover
        Chromosome child1 = new Chromosome(net, trainingSet);
        int jstar = rand.nextInt(numGenes);
        child1.setGene(jstar, child.getGene(jstar));
        for(int j = 1; j < numGenes; j++){
            double rando = rand.nextDouble();
            if(rando < pr && j != jstar){
                child1.setGene(j, child.getGene(j));
            }else{
                child1.setGene(j, parent.getGene(j));
            }
        }
        return child1;
    }
    private Chromosome returnBest(){
        //loop through population and find the best child
        double highFit = pop.get(0).getFitness();
        int highIndex = 0;
        for(int i = 0; i < pop.size(); i++){
            double curFit = pop.get(i).getFitness();
            if(curFit > highFit){
                highIndex = i;
                highFit = curFit;
            }
        }
        return pop.get(highIndex);
    }
    
    //getter and setter methods
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

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getPr() {
        return pr;
    }

    public void setPr(double pr) {
        this.pr = pr;
    }
}
