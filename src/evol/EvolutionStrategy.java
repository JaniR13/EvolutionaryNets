package evol;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
    private ArrayList<ESChromosome> pop = new ArrayList<ESChromosome>();
    //parameters for mutating strategy parameters
    private double tauOverall;
    private double tauInd;
    // neural net to train
    private FeedForwardANN net;
    private Random rand = new Random();
    private int numGenes;
    private ArrayList<TrainingInstance> trainingSet;
    private String filePathOut;
    public int genCount;

    /**
     * Creates a new evolution strategy instance
     *
     * @param gens number of generations
     * @param mu population size
     * @param lambda generation size (number of children)
     * @param rho number of parents in crossover
     * @param net net to train on
     * @param trainingSet training data
     * @param filePathOut the output location for the specified file
     */
    public EvolutionStrategy(int gens, int mu, int lambda, int rho,  FeedForwardANN net,
            ArrayList<TrainingInstance> trainingSet, String filePathOut) {
        this.net = net;
        this.lambda = lambda;
        this.mu = mu;
        this.rho = rho;
        this.gens = gens;
        this.trainingSet = trainingSet;
        this.filePathOut = filePathOut;

    }
//Randomly initializes the population

    private void initPop() {
        for (int i = 0; i < mu; i++) {
            ESChromosome es = new ESChromosome(new Chromosome(net, trainingSet));
            pop.add(es);
        }
        numGenes = pop.get(0).getNumGenes();
    }

    public FeedForwardANN run(double conf) {
        //initialize population
        initPop();
        //determine best element
        ESChromosome best = returnBest();
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
            writer.write("mu: " + mu + ", lambda: "+ lambda + ", rho: " + rho);
            writer.close();
//            writer.write("Initial error: " + err);
//            writer.println();
        }

        genCount = 0;
        //Array of size rho for the making children
        ESChromosome[] pool = new ESChromosome[rho];
        while (err > conf && genCount < gens) {
//            if (choice.equals("y")) {
//                // outputs the fitness and error at each generation
//                writer.write("Fitness at generation " + genCount + " :" + best.getFitness());
//                writer.println();
//                writer.write("Error at generation " + genCount + " :" + err);
//                writer.println();
//            }

            // runs for specified number of generations
            // for (int g = 0; g < gens; g++) {
            //System.out.println("> Generation " + g);
            for (int l = 0; l < lambda; l++) {
                //generate a random child
                ESChromosome yl = new ESChromosome(new Chromosome(net, trainingSet));
                //select parents
                pool = marriage(pool);
                //recombine parameters and genes
                yl = recombineParams(pool, yl);
                yl = recombineC(pool, yl);
                //mutate parameters and genes
                yl = mutateParams(yl);
                yl = mutateC(yl);
                //add child to population
                yl.evaluateFitness();
                pop.add(yl);
            }
            //cut the lambda least fit children from the population
            prunePop(lambda);
            best = returnBest();
            err = best.getAvgError();
            genCount++;
        }
        //determine best child
        best = returnBest();
        System.out.println("--------------- FINISHED!---------------");
        System.out.println("Final fitness: " + best.getFitness());
        System.out.println("Final error: " + best.getAvgError());
        best.evaluateFitness();
        
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

    private ESChromosome returnBest() {
        double highFit = pop.get(0).getFitness();
        int highIndex = 0;
        // loop through population and find highest fitness
        for (int i = 0; i < pop.size(); i++) {
            double curFit = pop.get(i).getFitness();
            if (curFit > highFit) {
                highIndex = i;
                highFit = curFit;
            }
        }
        return pop.get(highIndex);
    }

    private void prunePop(int pruneBy) {
        //
        for (int i = 0; i < pruneBy; i++) {
            //loop through population and cut lowest individual a certain number of times
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
        //randomly select rho individuals for making babies
        for (int i = 0; i < rho; i++) {
            pool[i] = pop.get(rand.nextInt(mu));
        }
        return pool;
    }

    private ESChromosome mutateC(ESChromosome start) {
        //add a random number (gaussian) to each gene in an individual
        Double[] newGenes = start.getGenes();
        for (int i = 0; i < numGenes; i++) {
            newGenes[i] += start.getVar(i) * rand.nextGaussian();
        }
        start.setGenes(newGenes);
        return start;
    }

    private ESChromosome recombineC(ESChromosome[] pool, ESChromosome child) {
        //select a random parent to pull each gene from
        for (int i = 0; i < numGenes; i++) {
            child.setGene(i, pool[rand.nextInt(rho)].getGene(i));
        }
        return child;
    }

    private ESChromosome mutateParams(ESChromosome start) {
        //mutate the parameters by following the equation:
        //new parameter = old parameter* e^((TauOverall*Gaussian) + (TauIndividual * Gaussian))
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
        //select gene from random parent
        for (int i = 0; i < numGenes; i++) {
            child.setVar(i, pool[rand.nextInt(rho)].getVar(i));
        }
        return child;

    }

    //getter and setter methods
    public void setTrainingSet(ArrayList<TrainingInstance> trainingSet) {
        this.trainingSet = trainingSet;
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
        tauOverall = 1 / Math.sqrt(2 * mu);
        tauInd = 1 / Math.sqrt(2 * Math.sqrt(mu));
    }

    public double getTauInd() {
        return tauInd;
    }

    public double getTauOVerall() {
        return tauOverall;
    }
}
