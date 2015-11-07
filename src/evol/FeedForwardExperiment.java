package evol;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * A class to perform the feed forward neural network experiments. Constructs, 
 * trains, and tests the ANN. 
 */
public class FeedForwardExperiment {

    // TRAINING DATA
    // goes through training file, splits into input and output values
    ArrayList<ArrayList<Double>> traininputs = new ArrayList<ArrayList<Double>>();
    ArrayList<ArrayList<Double>> trainoutputs = new ArrayList<ArrayList<Double>>();

    // TEST DATA
    // goes  through test data, split into input and output 
    ArrayList<ArrayList<Double>> testinputs = new ArrayList<ArrayList<Double>>();
    ArrayList<ArrayList<Double>> testoutputs = new ArrayList<ArrayList<Double>>();

    // number of hidden layers in the network
    int numHidden = 2;

    //the feed forward artificial neural network used to train/test data
    FeedForwardANN neuralNet;
    FeedForwardANN testNet;

    // a string to hold the error value, for use in passing to R
    String error = "";

    // network error values
    ArrayList<Double> errors = new ArrayList<Double>();

    public FeedForwardExperiment(String filePathTrain, String filePathTest, String filePathOut) {
        // feed training data into trainExamplesFromFile, to split into input/output 
        trainExamplesFromFile(filePathTrain);

        // feed testing data into testExamplesFromFile, to split into input/output
        testExamplesFromFile(filePathTest);

        //normalizes the training outputs(
        //normalizeTrainOutputs();

        // normalizes the testing outputs
        //normalizeTestOutputs();

        // create training net
        createTrainingNet();

        // run test instances through the trained net
        runTestInstances(filePathOut);
    }

    //runs the test instances on the trained network, prints the error to the
    //appropriate file
    private void runTestInstances(String filePathOut) {
        PrintWriter writer = null;
        testNet = neuralNet;

        //constructs output file
        try {
            writer = new PrintWriter(filePathOut);
        } catch (FileNotFoundException e1) {
            // Auto-generated catch block
            e1.printStackTrace();
        }

        // runs through each test example
        for (int i = 0; i < testinputs.size() - 1; i++) {
            testNet.clearInputs();

            // runs through ANN with test example's inputs and outputs
            testNet.setInputs(testinputs.get(i));
            testNet.setTargetOutputs(testoutputs.get(i));
            testNet.generateOutput();

            // adds error to appropriate arraylist
            errors.add(testNet.calcNetworkError());
            error = Double.toString(testNet.calcNetworkError());

            writer.write(error);
            writer.println();
        }

//        for (int i = 0; i < errors.size(); i++) {
//            System.out.println(errors.get(i));
//        }
        
        writer.close();

        System.out.println(
                "Test data has been evaluated");
        System.out.println(
                "Check your output file at " + filePathOut);
    }

    //Builds training net with specified parameters
    private void createTrainingNet() {
        //builds a neural net for training
        neuralNet = new FeedForwardANN(numHidden, 20, traininputs.get(0), trainoutputs.get(0),
                true, false);

        // runs through ANN with training example's inputs and outputs
        for (int i = 0; i < traininputs.size(); i++) {
            neuralNet.clearInputs();
            neuralNet.setInputs(traininputs.get(i));
            neuralNet.setTargetOutputs(trainoutputs.get(i));
            neuralNet.train();
        }

        System.out.println("End create training nets");
    }

    private void normalizeTestOutputs() {
        Double[] twodcopy = new Double[testoutputs.size()];

        // adds all outputs to a new arraylist
        for (int i = 0; i < testoutputs.size(); i++) {
            twodcopy[i] = testoutputs.get(i).get(0);
        }
        // sorts new arraylist
        Arrays.sort(twodcopy);
        // gets max and min
        Double max = twodcopy[twodcopy.length - 1];
        Double min = twodcopy[0];

        // normalizes each output
        for (ArrayList<Double> a : testoutputs) {
            Double newval = (a.get(0) - min) / (max - min);
            a.set(0, newval);
        }

        System.out.println("End normalizing testing data");
    }

    private void normalizeTrainOutputs() {
        Double[] twodcopy = new Double[trainoutputs.size()];
        // adds all outputs to a new arraylist
        for (int i = 0; i < trainoutputs.size(); i++) {
            twodcopy[i] = trainoutputs.get(i).get(0);
        }
        // sorts new arraylist
        Arrays.sort(twodcopy);
        // gets max and min
        Double max = twodcopy[twodcopy.length - 1];
        Double min = twodcopy[0];
        // normalizes each output
        for (ArrayList<Double> a : trainoutputs) {
            Double newval = (a.get(0) - min) / (max - min);
            a.set(0, newval);
        }

        System.out.println("End normalizing training data");
    }

    //gets training examples from file, breaks up by size
    private void trainExamplesFromFile(String fname) {
        BufferedReader br = null; // read from data
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(fname));

            while ((line = br.readLine()) != null) {
                ArrayList<Double> inputs = new ArrayList<Double>();
                String[] example = line.split(cvsSplitBy);

                // adds inputs (all but last number on line)
                for (int i = 0; i < example.length - 1; i++) {
                    Double in = Double.parseDouble(example[i]);
                    inputs.add(in);
                }

                // puts input array into correctly-sized ArrayList of examples
                int size = inputs.size();

                // gets output
                String stringoutput = example[example.length - 1];
                Double o = Double.parseDouble(stringoutput);
                ArrayList<Double> output = new ArrayList<Double>();

                if (!output.contains(o)) {
                    output.add(o);
                }

                traininputs.add(inputs);
                trainoutputs.add(output);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done reading in training data");
    }

    //gets testing examples from file, breaks up by size
    private void testExamplesFromFile(String fname) {
        BufferedReader br = null;// read from data
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(fname));
            while ((line = br.readLine()) != null) {
                ArrayList<Double> inputs = new ArrayList<Double>();
                String[] example = line.split(cvsSplitBy);

                // adds inputs (all but last number on line)
                for (int i = 0; i < example.length - 1; i++) {
                    Double in = Double.parseDouble(example[i]);
                    inputs.add(in);
                }

                // gets output
                String stringoutput = example[example.length - 1];
                Double o = Double.parseDouble(stringoutput);
                ArrayList<Double> output = new ArrayList<Double>();
                output.add(o);

                // puts input array into correctly-sized ArrayList of examples
                int size = inputs.size();

                testinputs.add(inputs);
                testoutputs.add(output);

                System.out.println("Output size is " + output.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Test Output size is " + testoutputs.size());
        System.out.println("Done reading in testing data");
    }

}
