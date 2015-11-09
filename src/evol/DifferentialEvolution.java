package evol;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/*
 *  Uses a Differential Evolution to train weights for a FFNN
 */

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
    private ArrayList<Chromosome> pop = new ArrayList<Chromosome>();
    private Random rand = new Random();
    private int numGenes;
    private ArrayList<TrainingInstance> trainingSet;
    private String filePathOut;
    public int genCount;

    /**
     * Creates a new Differential Evolution instance
     *
     * @param gens number of generations
     * @param popSize population size
     * @param beta mutation adjustment parameter
     * @param pr crossover rate
     * @param net net to train on
     * @param trainingSet training data
     * @param filePathOut the output location for the specified file
     */
    public DifferentialEvolution(int gens, int popSize, double beta, double pr,
            FeedForwardANN net, ArrayList<TrainingInstance> trainingSet, String filePathOut) {
        this.gens = gens;
        this.popSize = popSize;
        this.beta = beta;
        this.pr = pr;
        this.net = net;
        this.trainingSet = trainingSet;
        this.filePathOut = filePathOut;
    }

    public FeedForwardANN run(double conf) {
        //initialize the population
        initPop();
        //determine starting fitness
        Chromosome best = returnBest();
        double err = best.getAvgError();

        PrintWriter writer = null;

        //constructs output file
        try {
            writer = new PrintWriter(filePathOut);
        } catch (FileNotFoundException e1) {
            // Auto-generated catch block
            e1.printStackTrace();
        }

        System.out.println("Would you like to print sample runs for this dataset? Type y for yes, n for no.");
        Scanner in = new Scanner(System.in);
        String choice = "";
        choice = in.nextLine();

        System.out.println("--------------- STARTING! ---------------");
        System.out.println("Initial fitness: " + best.getFitness());
        System.out.println("Initial error: " + err);

        if (choice.equals("y")) {
            writer.write("population size: " + popSize + ", beta: "+ beta + ", Pr: " + pr);
            writer.close();
//            writer.write("Initial fitness: " + best.getFitness());
//            writer.println();
//            writer.write("Initial error: " + err);
//            writer.println();
        }

        genCount = 0;
        while (err > conf && genCount < gens) {
//            if (choice.equals("y")) {
//                // outputs the fitness and error at each generation
//                writer.write("Fitness at generation " + genCount + " :" + best.getFitness());
//                writer.println();
//                writer.write("Error at generation " + genCount + " :" + err);
//                writer.println();
//            }

            //generate a child for each member of the population
            for (int p = 0; p < popSize; p++) {
                //determine fitness of parent
                pop.get(p).evaluate();
                double fitparent = pop.get(p).getFitness();
                //create a trial vector
                Chromosome trialvect = new Chromosome(net, trainingSet);
                //mutate trial vector
                trialvect = mutation(trialvect, pop.get(p), p);
                //new child is recombination of trialvector and parent
                trialvect = crossover(trialvect, pop.get(p));
                //determine fitness of child
                trialvect.evaluate();
                double fitchild = trialvect.getFitness();
                //replace the parent with the child if the child has better fitness
                if (fitchild > fitparent) {
                    pop.set(p, trialvect);
                }
            }
           best = returnBest();
           best.evaluate();
           err = best.getAvgError();
            genCount++;
        }
        //return the best individual
        best = returnBest();
        best.evaluate();
        System.out.println("--------------- FINISHED!---------------");
        System.out.println("Final fitness: " + best.getFitness());
        System.out.println("Final error: " + best.getAvgError());

        
        
//        if (choice.equals("y")) {
//            writer.write("Final fitness: " + best.getFitness());
//            writer.println();
//            writer.write("Final error: " + best.getAvgError());
//            writer.println();
//            writer.write("Training completed");
//            writer.println();
//            writer.close();
//        }

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
   private Chromosome mutation(Chromosome child, Chromosome parent, int index) {
        int r1 = (int)Math.random() * popSize;
        int r2 = (int)Math.random() * popSize;
        int r3 = (int)Math.random() * popSize;
        while(r1 == index || r2 == index || r1 == r2 || r3 == index || r3 == r1 || r3 == r2){
            r1 = (int)(Math.random() * popSize);
            r2 = (int)(Math.random() * popSize);
            r3 = (int)Math.random() * popSize;
        }
        Chromosome p1 = pop.get(r1);
        Chromosome p2 = pop.get(r2);
        Chromosome p3 = pop.get(r3);
        for (int i = 0; i < numGenes; i++) {
            double adj = p1.getGene(i) + beta * (p2.getGene(i) - p3.getGene(i));
            child.setGene(i, adj);
        }
        return child;
    }

    private Chromosome crossover(Chromosome child, Chromosome parent) {//Binomial crossover
        Chromosome child1 = new Chromosome(net, trainingSet);
        int jstar = (int)(Math.random() * numGenes);
        child1.setGene(jstar, child.getGene(jstar));
        for (int j = 1; j < numGenes; j++) {
            double rando = Math.random();
            if (rando < pr && j != jstar) {
                child1.setGene(j, child.getGene(j));
            } else {
                child1.setGene(j, parent.getGene(j));
            }
        }
        return child1;
    }

    private Chromosome returnBest() {
        //loop through population and find the best child
        double highFit = pop.get(0).getFitness();
        int highIndex = 0;
        for (int i = 0; i < pop.size(); i++) {
            double curFit = pop.get(i).getFitness();
            if (curFit > highFit) {
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
