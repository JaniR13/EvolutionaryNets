package evol;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class ChromosomeTest {

	@Test
	public void testChromosomeSize() {
		ArrayList<Double> inputs = new ArrayList<Double>();
		inputs.add(3.0);
		inputs.add(2.0);
		inputs.add(.25);

		ArrayList<Double> expectedOutput = new ArrayList<Double>();
		expectedOutput.add(.8);
		
		FeedForwardANN net1 = new FeedForwardANN(1, 2, inputs, expectedOutput, true, false);		
		Chromosome c1 = new Chromosome(net1);
		
		FeedForwardANN net2 = new FeedForwardANN(1, 3, inputs, expectedOutput, true, false);		
		Chromosome c2 = new Chromosome(net2);
		
		FeedForwardANN net3 = new FeedForwardANN(2, 2, inputs, expectedOutput, true, false);		
		Chromosome c3 = new Chromosome(net3);
		
		assertEquals(11, c1.getNumGenes());
		assertEquals(16, c2.getNumGenes());
		assertEquals(17, c3.getNumGenes());

	}
	
	@Test
	public void testEvalute(){
		ArrayList<Double> inputs = new ArrayList<Double>();
		inputs.add(3.0);
		inputs.add(2.0);
		inputs.add(.25);

		ArrayList<Double> expectedOutput = new ArrayList<Double>();
		expectedOutput.add(.8);
		
		FeedForwardANN net1 = new FeedForwardANN(1, 2, inputs, expectedOutput, true, false);		
		
		net1.generateOutput();
		net1.clearInputs();
		
		double output1 = net1.getOutputs().get(0);
		double error1 = net1.calcNetworkError();
		
		Chromosome c1 = new Chromosome(net1);
		c1.evaluate();
		
		double output2 = net1.getOutputs().get(0);
		double error2 = net1.calcNetworkError();
		
		assertEquals(true, output2 != output1);
		assertEquals(true, error2 != error1);


	}
	
	@Test
	public void testCompare(){
		ArrayList<Double> inputs = new ArrayList<Double>();
		inputs.add(3.0);
		inputs.add(2.0);
		inputs.add(.25);

		ArrayList<Double> expectedOutput = new ArrayList<Double>();
		expectedOutput.add(.8);
		
		FeedForwardANN net1 = new FeedForwardANN(1, 2, inputs, expectedOutput, true, false);
		
		Chromosome c1 = new Chromosome(net1);
		c1.evaluate();
		
		Chromosome c2 = new Chromosome(net1);
		c2.evaluate();
		
		if (c1.getFitness() > c2.getFitness()){
			assertEquals(1, c1.compareTo(c2));
		} else {
			assertEquals(-1, c1.compareTo(c2));
		}
		
		
		
	}

}
