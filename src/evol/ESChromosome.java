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
    //
    
    public ESChromosome(Chromosome c){
        this.c = c;
        //TODO: Initialize varMetrix and rotationVector
   }
   
    public void mutateC(){
        //TODO: vector xj (t+1) = element j in Chromosome at time t
        //  + variance of element j at time t times N(0,1) (this is mutation)
        
    }
    
    public void recombineC(){
        //TODO: Decide on Uniform or Intermediate Crossover and implement
    }
    
    public void mutateParams(){
        //TODO: sigma j at time t + 1 = sigma j at time t * 
        //      e^(overal tau * some number + this tau * some number)
    }
}
