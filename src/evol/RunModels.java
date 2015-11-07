package evol;
/*
 * The class that contains the main method. From here user can call a method to 
 * convert a dataset to test and training sets, or test/train a FFNN with backpropagation. 
 * Additionally, the user can choose one of three evolutionary approaches to train
 * the weights for the FFNN. 
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
        System.out.println("To convert output files 'con'");
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

            FeedForwardExperiment test1 = new FeedForwardExperiment(filePathTrain, filePathTest, filePathOut);
        } else if (choice.equals("ga")) {
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
            ArrayList<TrainingInstance> trainData = createTrainingInstance(filePathTrain);
            FeedForwardANN net = new FeedForwardANN(2, 5, trainData.get(0).getInputs(), trainData.get(0).getOutput(), true, false);
            GA ga = new GA(0.5, 100, 50, 3, 0.25);
            ga.setTrainingSet(trainData);
            ga.optimize(net);
        } else if (choice.equals("es")) {
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
            filePathOut += File.separator + keyWord + "Out.txt";

//            System.out.println("Output Data: " + filePathOut);
//            int[] numGens = {5, 10, 25, 50, 100, 300, 500, 800, 1000};
//            for (int i = 0; i < numGens.length; i++) {//Tune number of generations
//                ArrayList<TrainingInstance> trainData = createTrainingInstance(filePathTrain);
//                FeedForwardANN net = new FeedForwardANN(2, 5, trainData.get(0).getInputs(), trainData.get(0).getOutput(), true, false);
//                EvolutionStrategy es = new EvolutionStrategy(numGens[i], 5, 5, 2, net, trainData);
//                System.out.println("number of generations: " + numGens[i]);
//                es.run();
//            }//100-300 is best number of generations
//            int[] popsize = {2, 5, 10, 25, 50, 100};
//            for (int i = 0; i < popsize.length; i++) {//Tune number of generations
//                for (int j = 0; j < popsize.length; j++) {
//                    if (i <= j) {
//                        ArrayList<TrainingInstance> trainData = createTrainingInstance(filePathTrain);
//                        FeedForwardANN net = new FeedForwardANN(2, 5, trainData.get(0).getInputs(), trainData.get(0).getOutput(), true, false);
//                        EvolutionStrategy es = new EvolutionStrategy(100, popsize[i], popsize[j], 2, net, trainData);//TODO use best results of previous tuning
//                        System.out.println("population size: " + popsize[i] + ", generation size: " + popsize[j]);
//                        es.run();
//                    }
//                }
//            }//best mu = 50, best lambda = 100
            //second best mu = 25, second best lambda = 50
//            int[] rhosize = {2, 3, 4, 5, 6, 7, 8, 9, 10};
//            for (int i = 0; i < rhosize.length; i++) {
//                ArrayList<TrainingInstance> trainData = createTrainingInstance(filePathTrain);
//                FeedForwardANN net = new FeedForwardANN(2, 5, trainData.get(0).getInputs(), trainData.get(0).getOutput(), true, false);
//                EvolutionStrategy es = new EvolutionStrategy(100, 50, 100, rhosize[i], net, trainData);//TODO use best results of previous tuning
//                System.out.println("rho size: " + rhosize[i]);
//                es.run();
//            }//rho of 2, 4 or 5 produces best results. 4 is best
            ArrayList<TrainingInstance> trainData = createTrainingInstance(filePathTrain);
            ArrayList<TrainingInstance> testData = createTrainingInstance(filePathTest);
            FeedForwardANN net = new FeedForwardANN(2, 5, trainData.get(0).getInputs(), trainData.get(0).getOutput(), true, false);
            //net.print();
            EvolutionStrategy es = new EvolutionStrategy(10000, 50, 100, 4, net, trainData);
            net = es.run(0.01);
            //net.print();
            System.out.println("Generations: " + es.genCount);
        } else if (choice.equals("de")) {
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
            filePathOut = getFileLocation();
            filePathOut += File.separator + keyWord + "Out.txt";

            System.out.println("Output Data: " + filePathOut);
            
            //int[] popsize = {10,25,50,100,250,500,1000};
            //double[] betas = {1, 5, 10, 25, 50};//gotta be honest, I have no idea what a good range is for this
            //double[] prs = {0.001, 0.005, 0.01, 0.05, 0.1, 0.5};
//            System.out.println("Training population size");
//            for (int i = 0; i < popsize.length; i++) {
//                ArrayList<TrainingInstance> trainData = createTrainingInstance(filePathTrain);
//                FeedForwardANN net = new FeedForwardANN(2, 5, trainData.get(0).getInputs(), trainData.get(0).getOutput(), true, false);
//                DifferentialEvolution de = new DifferentialEvolution(50, popsize[i], 10, 0.1, net, trainData);
//                System.out.println("population size: " + popsize[i]);
//                de.run();
//            }
//            System.out.println("");
//            System.out.println("----------");
//            System.out.println("Training beta");
//            
//            for (int i = 0; i < betas.length; i++) {
//                ArrayList<TrainingInstance> trainData = createTrainingInstance(filePathTrain);
//                FeedForwardANN net = new FeedForwardANN(2, 5, trainData.get(0).getInputs(), trainData.get(0).getOutput(), true, false);
//                DifferentialEvolution de = new DifferentialEvolution(50, 250, betas[i], 0.1, net, trainData);
//                DifferentialEvolution de2 = new DifferentialEvolution(50, 250, betas[i], 0.05, net, trainData);
//                System.out.println("beta: " + betas[i] + ", Pr  = 0.1");
//                de.run();
//                System.out.println("beta: " + betas[i] + ", Pr = 0.05");
//                de2.run();
//            }//ideal settings: population size of 100 or 250,
                    //beta of 25
                    //Pr of 0.1
//            System.out.println("");
//            System.out.println("----------");
//            System.out.println("Training Pr");
//            for (int i = 0; i < prs.length; i++) {
//                ArrayList<TrainingInstance> trainData = createTrainingInstance(filePathTrain);
//                FeedForwardANN net = new FeedForwardANN(2, 5, trainData.get(0).getInputs(), trainData.get(0).getOutput(), true, false);
//                DifferentialEvolution de = new DifferentialEvolution(50, 250, 10, prs[i], net, trainData);
//                System.out.println("Pr: " + prs[i]);
//                de.run();
//            }
            ArrayList<TrainingInstance> trainData = createTrainingInstance(filePathTrain);
            FeedForwardANN net = new FeedForwardANN(2, 5, trainData.get(0).getInputs(), trainData.get(0).getOutput(), true, false);
            DifferentialEvolution de = new DifferentialEvolution(10000, 100, 25, 0.1, net, trainData);
            de.run(0.01);
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
    public static ArrayList<Double> runTestData(ArrayList<TrainingInstance> testData, FeedForwardANN net){
        ArrayList<Double> error = new ArrayList<Double>();
        for(int i = 0; i < testData.size(); i++){
            net.clearInputs();
            net.setInputs(testData.get(i).getInputs());
            net.generateOutput();
            error.add(net.calcNetworkError());
        }
        return error;
    }

}
