package evol;

import java.io.*;
import java.util.Random;

/*
 * Convert the whole data files into test and training sets. Allows user to
 * pick location of data set, as well as location where they would like to store
 * the output (test and train files).
 */
public class ConvertData {

    public ConvertData(String filePath, String outTrain, String outTest) throws IOException {
        //a reader to read the file of all inputs/outputs
        BufferedReader reader = null;

        try {
            //the reader for reading the entire dataset
            reader = new BufferedReader(new FileReader(filePath));

            //file to write a subset of the inputs/outputs to
            BufferedWriter trainWriter = null;
            BufferedWriter testWriter = null;
            FileWriter testFileWriter = new FileWriter(outTest);
            FileWriter trainFileWriter = new FileWriter(outTrain);
            trainWriter = new BufferedWriter(trainFileWriter);
            testWriter = new BufferedWriter(testFileWriter);

            //keeps track of the current line in the reader file
            String currentLine = "";

            // a random number to decide which line goes to the appropriate file
            int randomNumber = 0;

            //as long as there are lines left to be read
            while ((reader.readLine()) != null) {
                //generate a random number
                randomNumber = randInt(0, 100);
                
                currentLine = reader.readLine();

                //compares these 25 numbers to the random number generated above, so the chance of putting
                //data in the test set is 25%
                if (randomNumber == 38 || randomNumber == 21 || randomNumber == 55 || randomNumber == 3
                        || randomNumber == 33 || randomNumber == 73 || randomNumber == 60 || randomNumber == 81
                        || randomNumber == 68 || randomNumber == 59 || randomNumber == 95 || randomNumber == 90
                        || randomNumber == 37 || randomNumber == 34 || randomNumber == 25 || randomNumber == 41
                        || randomNumber == 78 || randomNumber == 74 || randomNumber == 70 || randomNumber == 54
                        || randomNumber == 42 || randomNumber == 40 || randomNumber == 18 || randomNumber == 64
                        || randomNumber == 88) {
                    //Write data in test file
                    if (currentLine != null) {
                        testWriter.write(currentLine);
                        testWriter.newLine();
                    }
                } else {
                    //The chance of putting data in the training set is 75%
                    if (currentLine != null) {
                        trainWriter.write(currentLine);
                        trainWriter.newLine();
                    }
                }
            } // end while

            System.out.println("Test and train data generated.");

            try {
                reader.close();
                trainWriter.close();
                testWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // generates a random number, used to determine if line should go in test or
    // train set
    public static int randInt(int min, int max) {
        Random rand = new Random();
        // generates a random number between two inclusive values
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
