package evol;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class GATest {

//	@Test
//	public void testRandomInitialize() {
//		GA g = new GA(0.5, 10, 30, 3, 0.25);
//		ArrayList<Double> inputs = new ArrayList<Double>();
//		inputs.add(3.0);
//		inputs.add(2.0);
//		inputs.add(.25);
//
//		ArrayList<Double> expectedOutput = new ArrayList<Double>();
//		expectedOutput.add(.8);
//
//		FeedForwardANN net1 = new FeedForwardANN(1, 2, inputs, expectedOutput, true, false);
//
//		g.optimize(net1);
//
//		assertEquals(30, g.getPop().size());
//
//		for (int i = 0; i < g.getPop().size(); i++) {
//			for (int j = 0; j < g.getPop().size(); j++) {
//				if (i != j) {
//					for (int k = 0; k < g.getPop().get(i).getNumGenes(); k++) {
//						assertEquals(false, g.getPop().get(i).getGenes()[k] == g.getPop().get(j).getGenes()[k]);
//					}
//				}
//			}
//		}
//
//	}
//
//	@Test
//	public void testSort() {
//		GA g = new GA(0.5, 10, 30, 3, 0.25);
//		ArrayList<Double> inputs = new ArrayList<Double>();
//		inputs.add(3.0);
//		inputs.add(2.0);
//		inputs.add(.25);
//
//		ArrayList<Double> expectedOutput = new ArrayList<Double>();
//		expectedOutput.add(.8);
//
//		FeedForwardANN net1 = new FeedForwardANN(1, 2, inputs, expectedOutput, true, false);
//
//		g.optimize(net1);
//
//		for (int i = 0; i < g.getPop().size()-1; i++) {
//			assertEquals(true, g.getPop().get(i).getFitness() < g.getPop().get(i+1).getFitness());
//		}
//		
//		for (int i = 0; i < g.getPop().size(); i++) {
//			System.out.println(g.getPop().get(i).getFitness());
//		}
//		
//
//	}
//
//	@Test
//	public void testSelection(){
//		GA g = new GA(0.5, 10, 30, 3, 0.25);
//		ArrayList<Double> inputs = new ArrayList<Double>();
//		inputs.add(3.0);
//		inputs.add(2.0);
//		inputs.add(.25);
//
//		ArrayList<Double> expectedOutput = new ArrayList<Double>();
//		expectedOutput.add(.8);
//
//		FeedForwardANN net1 = new FeedForwardANN(1, 2, inputs, expectedOutput, true, false);
//
//		g.optimize(net1);
//		
//		for (int i = 0; i < 30; i++){
//			g.select();
//		}
//	}
//	
//	@Test
//	public void testCrossover(){		
//		GA g = new GA(0.5, 10, 30, 3, 0.25);
//		ArrayList<Double> inputs = new ArrayList<Double>();
//		inputs.add(3.0);
//		inputs.add(2.0);
//		inputs.add(.25);
//
//		ArrayList<Double> expectedOutput = new ArrayList<Double>();
//		expectedOutput.add(.8);
//
//		FeedForwardANN net1 = new FeedForwardANN(1, 2, inputs, expectedOutput, true, false);
//
//		g.optimize(net1);
//		
//		ArrayList<Chromosome> rents = new ArrayList<Chromosome>();
//		for (int i = 0; i < 3; i++){
//			rents.add(g.select());
//		}
//		
////		System.out.println(rents.get(0).toString());
////		System.out.println(rents.get(1).toString());
////		System.out.println(rents.get(2).toString());
//
//		Chromosome off = g.crossover(rents);
////		System.out.println("Offspring: ");
////		System.out.println(off.toString());
//		
//	}
//	
//	@Test
//	public void testMutate(){
//		GA g = new GA(0.5, 10, 30, 3, 0.25);
//		ArrayList<Double> inputs = new ArrayList<Double>();
//		inputs.add(3.0);
//		inputs.add(2.0);
//		inputs.add(.25);
//
//		ArrayList<Double> expectedOutput = new ArrayList<Double>();
//		expectedOutput.add(.8);
//
//		FeedForwardANN net1 = new FeedForwardANN(1, 2, inputs, expectedOutput, true, false);
//
//		g.optimize(net1);
//		
//		Chromosome c = g.select();
//		
//		System.out.println(c.toString());
//		g.mutate(c);
//		System.out.println(c.toString());
//		
//	}
	
	@Test
	public void testAllTogether(){
		GA g = new GA(0.5, 50, 30, 3, 0.25);
		ArrayList<Double> inputs = new ArrayList<Double>();
		inputs.add(3.0);
		inputs.add(2.0);
		inputs.add(.25);

		ArrayList<Double> expectedOutput = new ArrayList<Double>();
		expectedOutput.add(.8);

		FeedForwardANN net1 = new FeedForwardANN(1, 2, inputs, expectedOutput, true, false);

		g.optimize(net1);
	}
}
