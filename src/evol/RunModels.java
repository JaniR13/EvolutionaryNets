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
        } else if (choice.equals("ga")){
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
            
        } else if (choice.equals("es")){
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
                    
            System.out.println("Output Data: " + filePathOut);
            
        } else if (choice.equals("de")){
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
            
        }else {
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

}
