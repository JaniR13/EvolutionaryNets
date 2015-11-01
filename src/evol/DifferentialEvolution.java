package evol;

import java.util.ArrayList;
import java.util.Random;

public class DifferentialEvolution extends TrainingStrategy {
    private int gens;
    private int popSize;
    private double beta;
    private double pr;
    private FeedForwardANN net;
    private ArrayList <Chromosome> pop = new ArrayList<Chromosome>();
    private Random rand = new Random();
    private int numGenes;
    
    public DifferentialEvolution(){
        
    }
    public Chromosome run(){
        initPop();
        for(int g = 0; g < gens; g++){
            for(int p = 0; p < popSize; p++){
                pop.get(p).evaluate();
                double fitparent = pop.get(p).getFitness();
                Chromosome trialvect = new Chromosome(net);
                trialvect = mutation(trialvect, pop.get(p));
                trialvect = crossover(trialvect, pop.get(p));
                trialvect.evaluate();
                double fitchild = trialvect.getFitness();
                if(fitchild > fitparent){
                    pop.set(p, trialvect);
                }
            }
        }
        return returnBest();
    }
    
    private void initPop(){
        numGenes = pop.get(0).getNumGenes();
        
    }
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
        Chromosome child1 = new Chromosome(net);
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
