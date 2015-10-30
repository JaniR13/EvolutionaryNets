/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evol;

/**
 *
 * @author Janette
 */
public class ESChromosome {
    //Class holds a Chromosome c, and the strategy parameters for each Chromosome
    private Chromosome c;
    //variance array for the chromosome
    private Double[][] varMatrix;
    //The rotation vector
    private Double[] rotationVector;//will be of length (n(n-1))/2 where n is number of features
    private double fitness;
    
    public ESChromosome(Chromosome c){
        this.c = c;
        //TODO: Initialize varMetrix and rotationVector
   }
   
    public void evaluateFitness(){
        c.evaluate();
    }
    public Double[][] getVarMatrix(){
        return varMatrix;
    }
    public Double[] getRotVector(){
        return rotationVector;
    }
    public double getFitness(){
        return c.getFitness();
    }
    public void setVarMatrix(Double[][] varMatrix){
        this.varMatrix = varMatrix;
    }
    public void getRotVector(Double[] rotationVector){
        this.rotationVector = rotationVector;
    }
    public int getVarMatrixSize(){
        return c.getNumGenes();
    }
    
    
    
}
