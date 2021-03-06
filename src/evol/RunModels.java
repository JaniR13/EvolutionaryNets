package evol;
/*
 * The class that contains the main method. From here user can call a method to 
 * convert a dataset to test and training sets, or test/train a FFNN with backpropagation. 
 * Additionally, the user can choose one of three evolutionary approaches (GA, ES and 
 * DE) to train the weights for the FFNN. 
 */

import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.util.*;

public class RunModels {

    public static void main(String[] args) throws IOException {
        //A scanner to record user input (will be used for choice and keyword variables)
        Scanner in = new Scanner(System.in);
        String choice = "";

        //gives the user a series of choices
        System.out.println("Please pick from one of the following options");
        System.out.println("To convert a single data file into input and output files 'con'");
        System.out.println("To run the feed-forward neural net (with backprop) type 'ffnn'");
        System.out.println("To train the neural net with a genetic algorithm type 'ga'");
        System.out.println("To train the neural net with an evolution strategy type 'es'");
        System.out.println("To train the neural net with differential evolution type 'de'");
        System.out.println("Type 'x' to exit");

        choice = in.nextLine();

        //If the user wants to convert a dataset to test and training sets
        if (choice.equals("con")) {
            System.out.println("Converting Data");
            //used to let the user define the name of their test/training files
            String keyWord = "";
            //String to hold the filepath of the entire dataset
            String filePath = "";
            //Strings to hold the outTest filepath and outTrain filepaths respectively
            String outTest = "";
            String outTrain = "";

            System.out.println("Select location of dataset to be split.");
            filePath = getFileLocation();

            System.out.println("Enter the keyword you would like to use to label your training and test files.");
            keyWord = in.nextLine();

            System.out.println("Select location where you would like to save your output files.");
            outTest = getFileLocation();
            outTrain = outTest;

            outTest += File.separator + keyWord + "Test.txt";
            outTrain += File.separator + keyWord + "Train.txt";

            //Makes the appropriate call to convert the data for one file
            ConvertData cd = new ConvertData(filePath, outTrain, outTest);

        } else if (choice.equals("ffnn")) {
            //gets necessary info for performing Feed Forward Experiments	
            System.out.println("Performing Feed Forward Experiments");
            // gets the os for the computer this program is run on
            String os = System.getProperty("os.name").toLowerCase();
            // gets the home location
            String home = System.getProperty("user.home");
            // starts building the file path
            String filePathTrain = home;
            String filePathTest = home;
            String keyWord = "";
            //String to hold the filepath of the entire dataset
            String filePathOut = "";

            // uses file separator so is operating system agnostic
            if (os.startsWith("windows")) { // Windows
                filePathTrain += File.separator;
                filePathTest += File.separator;
                filePathOut += File.separator;
            } else if (os.startsWith("mac")) { // Mac
                filePathTrain += File.separator;
                filePathTest += File.separator;
                filePathOut += File.separator;
            } else {
                // everything else
                filePathTrain += File.separator;
                filePathTest += File.separator;
                filePathOut += File.separator;
            }

            // calls the file chooser, returns the updated file path
            System.out.println("Select Training Data Location");
            filePathTrain = callFileChooser(filePathTrain);
            System.out.println("Training Data: " + filePathTrain);
            System.out.println("Select Test Data Location");
            filePathTest = callFileChooser(filePathTest);
            System.out.println("Test Data: " + filePathTest);

            System.out.println("Enter the keyword you would like to use to label your output files.");
            keyWord = in.nextLine();

            System.out.println("Select location where you would like to save your output files.");
            filePathOut = getFileLocation();
            filePathOut += File.separator + keyWord + "Out.txt";

            System.out.println("Output Data: " + filePathOut);

            //the ffnn settings decided on after the tuning process
            Boolean momentum = false;
            int numHiddenNodes = 5;
            Double eta = 0.6;
            FeedForwardExperiment test1 = new FeedForwardExperiment(filePathTrain, filePathTest, filePathOut, momentum, numHiddenNodes, eta);

        } else if (choice.equals("ga")) {
            //gets necessary info for genetic algorithm
            System.out.println("Training with Genetic Algorithm");
            // gets the os for the computer this program is run on
            String os = System.getProperty("os.name").toLowerCase();
            // gets the home location
            String home = System.getProperty("user.home");
            // starts building the file path
            String filePathTrain = home;
            String filePathTest = home;
            String keyWord = "";
            //String to hold the filepath of the entire dataset
            String filePathOut = "";

            // uses file separator so is operating system agnostic
            if (os.startsWith("windows")) { // Windows
                filePathTrain += File.separator;
                filePathTest += File.separator;
                filePathOut += File.separator;
            } else if (os.startsWith("mac")) { // Mac
                filePathTrain += File.separator;
                filePathTest += File.separator;
                filePathOut += File.separator;
            } else {
                // everything else
                filePathTrain += File.separator;
                filePathTest += File.separator;
                filePathOut += File.separator;
            }

            // calls the file chooser, returns the updated file path
            System.out.println("Select Training Data Location");
            filePathTrain = callFileChooser(filePathTrain);
            System.out.println("Training Data: " + filePathTrain);
            System.out.println("Select Test Data Location");
            filePathTest = callFileChooser(filePathTest);
            System.out.println("Test Data: " + filePathTest);

            System.out.println("Enter the keyword you would like to use to label your output files.");
            keyWord = in.nextLine();

            System.out.println("Select location where you would like to save your output files.");
            filePathOut = getFileLocation();
            filePathOut += File.separator + keyWord + "Out.txt";

            System.out.println("Output Data: " + filePathOut);

            PrintWriter writer = null;

            //constructs output file
            try {
                writer = new PrintWriter(filePathOut);
            } catch (FileNotFoundException e1) {
                // Auto-generated catch block
                e1.printStackTrace();
            }

            //variables for ffnn, as decided in tuning process
            Boolean momentum = false;
            int numHiddenNodes = 5;
            Double eta = 0.6;

            //variables for GA, as decided in tuning process
            Double mutationRate = .75;
            int popSize = 100;
            Double replace = .50;
            int numParents = 4;

            //Runs the GA for the user provided data set
            ArrayList<TrainingInstance> trainData = normalizeTrainOutputs(createTrainingInstance(filePathTrain));
            ArrayList<TrainingInstance> testData = normalizeTrainOutputs(createTrainingInstance(filePathTest));
            FeedForwardANN net = new FeedForwardANN(2, 5, trainData.get(0).getInputs(), trainData.get(0).getOutput(), true, momentum, eta);
            GA ga = new GA(mutationRate, 1000, popSize, replace, numParents);
            ga.setTrainingSet(trainData);
            net = ga.optimize(net);

            ArrayList<Double> error = runTestData(testData, net);

            for (int i = 0; i < error.size(); i++) {
                writer.write(error.get(i).toString());
                writer.println();
            }

            writer.close();

        } else if (choice.equals("es")) {
            //gets the necessary information for the ES
            System.out.println("Training with Evolution Strategy");
            // gets the os for the computer this program is run on
            String os = System.getProperty("os.name").toLowerCase();
            // gets the home location
            String home = System.getProperty("user.home");
            // starts building the file path
            String filePathTrain = home;
            String filePathTest = home;
            String keyWord = "";
            //String to hold the filepath of the entire dataset
            String filePathOut = "";

            // uses file separator so is operating system agnostic
            if (os.startsWith("windows")) { // Windows
                filePathTrain += File.separator;
                filePathTest += File.separator;
                filePathOut += File.separator;
            } else if (os.startsWith("mac")) { // Mac
                filePathTrain += File.separator;
                filePathTest += File.separator;
                filePathOut += File.separator;
            } else {
                // everything else
                filePathTrain += File.separator;
                filePathTest += File.separator;
                filePathOut += File.separator;
            }

            // calls the file chooser, returns the updated file path
            System.out.println("Select Training Data Location");
            filePathTrain = callFileChooser(filePathTrain);
            System.out.println("Training Data: " + filePathTrain);
            System.out.println("Select Test Data Location");
            filePathTest = callFileChooser(filePathTest);
            System.out.println("Test Data: " + filePathTest);

            System.out.println("Enter the keyword you would like to use to label your output files.");
            keyWord = in.nextLine();

            System.out.println("Select location where you would like to save your output files.");
            filePathOut = getFileLocation();

            PrintWriter writer = null;

            filePathOut += File.separator + keyWord + "Out.txt";

            //constructs output file
            try {
                writer = new PrintWriter(filePathOut);
            } catch (FileNotFoundException e1) {
                // Auto-generated catch block
                e1.printStackTrace();
            }

            Boolean momentum = false;
            int numHiddenNodes = 5;
            Double eta = 0.6;
            int numGenerations = 1000;

            //the parameters for the ES, as decided by the tuning process
            int mu = 100;
            int lambda = 100;
            int rho = 4;

            ArrayList<TrainingInstance> trainData = normalizeTrainOutputs(createTrainingInstance(filePathTrain));
            ArrayList<TrainingInstance> testData = normalizeTrainOutputs(createTrainingInstance(filePathTest));
            FeedForwardANN net = new FeedForwardANN(2, 5, trainData.get(0).getInputs(), trainData.get(0).getOutput(), true, momentum, eta);
            EvolutionStrategy es = new EvolutionStrategy(numGenerations, mu, lambda, rho, net, trainData, filePathOut);
            net = es.run(0.0001);
            ArrayList<Double> error = runTestData(testData, net);

            for (int i = 0; i < error.size(); i++) {
                writer.write(error.get(i).toString());
                writer.println();
            }

            System.out.println("Generations: " + es.genCount);
            System.out.println("ES finished for " + keyWord + " dataset");

            writer.close();
        } else if (choice.equals("de")) {
            //gets the information for the DE 
            System.out.println("Training with Differential Evolution");
            // gets the os for the computer this program is run on
            String os = System.getProperty("os.name").toLowerCase();
            // gets the home location
            String home = System.getProperty("user.home");
            // starts building the file path
            String filePathTrain = home;
            String filePathTest = home;
            String keyWord = "";
            //String to hold the filepath of the entire dataset
            String filePathOut = "";

            // uses file separator so is operating system agnostic
            if (os.startsWith("windows")) { // Windows
                filePathTrain += File.separator;
                filePathTest += File.separator;
                filePathOut += File.separator;
            } else if (os.startsWith("mac")) { // Mac
                filePathTrain += File.separator;
                filePathTest += File.separator;
                filePathOut += File.separator;
            } else {
                // everything else
                filePathTrain += File.separator;
                filePathTest += File.separator;
                filePathOut += File.separator;
            }

            // calls the file chooser, returns the updated file path
            System.out.println("Select Training Data Location");
            filePathTrain = callFileChooser(filePathTrain);
            System.out.println("Training Data: " + filePathTrain);
            System.out.println("Select Test Data Location");
            filePathTest = callFileChooser(filePathTest);
            System.out.println("Test Data: " + filePathTest);

            System.out.println("Enter the keyword you would like to use to label your output files.");
            keyWord = in.nextLine();

            System.out.println("Select location where you would like to save your output files.");

            String filePathOut1 = getFileLocation();

            filePathOut = filePathOut1 + File.separator + keyWord + "DEOut.txt";
            System.out.println("Output Data: " + filePathOut);
            ArrayList<TrainingInstance> trainData = normalizeTrainOutputs(createTrainingInstance(filePathTrain));
            ArrayList<TrainingInstance> testData = normalizeTrainOutputs(createTrainingInstance(filePathTest));
            FeedForwardANN net = new FeedForwardANN(2, 5, trainData.get(0).getInputs(), trainData.get(0).getOutput(), true, false, 0.6);

            DifferentialEvolution de = new DifferentialEvolution(500, 50, 1, 0.05, net, trainData, filePathOut);
            net = de.run(0.05);
            ArrayList<Double> error = runTestData(testData, net);

            PrintWriter printWriter = null;

            File file = new File(filePathOut);

            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                printWriter = new PrintWriter(new FileOutputStream(filePathOut, true));
                //printWriter.append("Begin test data");
                for (int i = 0; i < error.size(); i++) {
                    printWriter.append("" + error.get(i));
                    printWriter.println();
                }

            } catch (IOException ioex) {
                ioex.printStackTrace();
            } finally {
                if (printWriter != null) {
                    printWriter.flush();
                    printWriter.close();
                }
            }

            System.out.println("Generations: " + de.genCount);

        } else {
            System.exit(0);
        }
    }

    // calls a window with a pop up box that lets the user choose their exact
    // file location (with input from file string that gives user's home
    // directory.
    public static String callFileChooser(String filePath) {
        // builds a JFrame
        JFrame frame = new JFrame("Folder Selection Pane");
        frame.setAlwaysOnTop(true);
        // string to score the path
        String thisPath = "";

        // JFrame look and feel
        frame.setPreferredSize(new Dimension(400, 200));
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button = new JButton("Select Folder");

        // sets up the file chooser
        JFileChooser fileChooser = new JFileChooser();
        // uses file path as a starting point for file browsing
        fileChooser.setCurrentDirectory(new File(filePath));
        // choose only from directories
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int fileChosen = fileChooser.showOpenDialog(null);

        // returns either the file path, or nothing (based on user choice)
        if (fileChosen == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            thisPath = selectedFile.getAbsolutePath();
            return thisPath;
        } else {
            return null;
        }
    }

    //gets the file location specified by the user, brings up file chooser to help
    //them select the appropriate location
    public static String getFileLocation() {
        // gets the os for the computer this program is run on
        String os = System.getProperty("os.name").toLowerCase();
        // gets the home location
        String home = System.getProperty("user.home");
        // starts building the file path
        String filePath = home;

        // uses file separator so is operating system agnostic
        if (os.startsWith("windows")) { // Windows
            filePath += File.separator;
        } else if (os.startsWith("mac")) { // Mac
            filePath += File.separator;
        } else {
            // everything else
            filePath += File.separator;
        }

        // calls the file chooser, returns the updated file path
        filePath = callFileChooser(filePath);
        return filePath;
    }
    
    //creates a training instance from the data, for use by GA, ES and DE
    public static ArrayList<TrainingInstance> createTrainingInstance(String fname) {
        BufferedReader br = null; // read from data
        String line = "";
        String cvsSplitBy = ",";
        ArrayList<TrainingInstance> data = new ArrayList<TrainingInstance>();
        try {
            br = new BufferedReader(new FileReader(fname));

            while ((line = br.readLine()) != null) {
                ArrayList<Double> inputs = new ArrayList<Double>();
                ArrayList<Double> output = new ArrayList<Double>();
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

                if (!output.contains(o)) {
                    output.add(o);
                }
                TrainingInstance tIn = new TrainingInstance(inputs, output);
                data.add(tIn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("Done reading in training data");

        return data;
    }

    //once the ffnn has been trained, runs the test data - constructs an arraylist of errors which 
    //is printed out in the respective strategy (for GA, DE, ES)    
    public static ArrayList<Double> runTestData(ArrayList<TrainingInstance> testData, FeedForwardANN net) {
        ArrayList<Double> error = new ArrayList<Double>();
        for (int i = 0; i < testData.size(); i++) {
            net.clearInputs();
            net.setInputs(testData.get(i).getInputs());
            net.setTargetOutputs(testData.get(i).getOutput());
            net.generateOutput();
            error.add(net.calcNetworkError());
        }
        return error;
    }

    //normalizes the test and training outputs, so they can be used with the activation function
    public static ArrayList<TrainingInstance> normalizeTrainOutputs(ArrayList<TrainingInstance> trainoutputs) {
        ArrayList<TrainingInstance> norm = new ArrayList<TrainingInstance>();
        // adds all outputs to a new arraylist
        for (int i = 0; i < trainoutputs.size(); i++) {
            norm.add(trainoutputs.get(i));
        }
        Double maxOut = norm.get(0).getOutput().get(0);
        Double maxIn = norm.get(0).getInputs().get(0);
        Double minOut = norm.get(0).getOutput().get(0);
        Double minIn = norm.get(0).getInputs().get(0);
        int outsize = norm.get(0).getOutput().size();
        int insize = norm.get(0).getInputs().size();
        for (int i = 0; i < norm.size(); i++) { //for each training instance
            TrainingInstance inst = norm.get(i);
            ArrayList<Double> out = inst.getOutput();
            ArrayList<Double> in = inst.getInputs();
            for (int j = 0; j < out.size(); j++) {
                Double curOut = out.get(j);
                if (curOut > maxOut) {
                    maxOut = curOut;
                }
                if (curOut < minOut) {
                    minOut = curOut;
                }
            }
            for (int j = 0; j < in.size(); j++) {
                Double curIn = in.get(j);
                if (curIn > maxIn) {
                    maxIn = curIn;
                }
                if (curIn < minIn) {
                    minIn = curIn;
                }
            }
        }
        for (int i = 0; i < norm.size(); i++) { //for each training instance
            ArrayList<Double> newout = new ArrayList<Double>();
            ArrayList<Double> newin = new ArrayList<Double>();
            ArrayList<Double> oldout = norm.get(i).getOutput();
            ArrayList<Double> oldin = norm.get(i).getInputs();
            for (int j = 0; j < outsize; j++) {
                Double newval = (oldout.get(j) - minOut) / (maxOut - minOut);
                newout.add(newval);
            }
            for (int j = 0; j < insize; j++) {
                Double newval = (oldin.get(j) - minIn) / (maxIn - minIn);
                newin.add(newval);
            }
            norm.get(i).setInputs(newin);
            norm.get(i).setOutput(newout);
        }
        return norm;
//        
    }
}
