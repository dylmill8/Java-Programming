import java.io.*;
import java.util.Random;

/**
 * This program generates a library of words from an input file, implements a method to randomly select a word, and
 * allows the user to choose a set seed for the randomizer.
 *
 * @author Dylan Miller
 * @version October 26, 2022
 */

//Assignment prevented the use of ArrayList

public class WordLibrary {

    private String[] library;
    private int seed;
    private Random random;
    private String fileName;
    
    public WordLibrary(String fileName) {
        this.fileName = fileName;
        File file;
        FileReader fileReader;
        BufferedReader bufferedReader;
        int counter = 0;

        //count number of lines in the file
        try {
            file = new File(fileName);
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

            //getting random seed number
            String line = bufferedReader.readLine();
            seed = Integer.parseInt(line.split(" ")[1]);

            line = bufferedReader.readLine();
            while (line != null) {
                line = bufferedReader.readLine();
                counter++;
            }
        } catch (IOException e) { e.printStackTrace(); }

        //set library array size, set random number generator using the given seed, and reset counter
        library = new String[counter];
        random = new Random(seed);
        counter = 0;

        //read in words and at them to list
        try {
            file = new File(fileName);
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

            //reading line by line to fill library array
            String line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            while (line != null) {
                library[counter] = line;
                line = bufferedReader.readLine();
                counter++;
            }
        } catch (IOException e) { e.printStackTrace(); }

        try {
            processLibrary();
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    public void verifyWord(String word) throws InvalidWordException {
        if (word == null || word.length() != 5) {
            throw new InvalidWordException("Invalid word!");
        }
    }
    
    public void processLibrary() {
        String[] tempLibrary = library; //holds the edited array that will be passed to library after words are removed
        String[] arrayBuilder; //list used for copying tempLibrary without the unverified word
        int index; //tracks the index of arrayBuilder to keep items in order and eliminate empty indices

        for (int i = 0; i < library.length; i++) {
            try {
                verifyWord(library[i]);
            } catch (InvalidWordException e) {
                index = 0;
                arrayBuilder = new String[tempLibrary.length - 1]; //create a new array 1 less than the length
                for (String j : tempLibrary) {
                    if (!j.equals(library[i])) { //checks if the current word is the one marked as invalid
                        arrayBuilder[index] = j;
                        index++; //only increases index if a word is added to arrayBuilder
                    }
                }
                tempLibrary = arrayBuilder; //copies arrayBuilder to tempLibrary after a single word was removed
                System.out.println(e.getMessage());
            }
        }
        library = tempLibrary; //copies tempLibrary to library after all invalid words have been removed
    }

    public String chooseWord() {
        return library[random.nextInt(library.length)];
    }

    public String[] getLibrary() {
        return library;
    }

    public void setLibrary(String[] library) {
        this.library = library;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }
}
