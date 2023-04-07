import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This program implements a word game (Wordle) where the user can guess 5-letter words and use clues to determine what
 * the solution is.
 *
 * @author Dylan Miller
 * @version October 26, 2022
 */

public class WordGame {

    public static String welcome = "Ready to play?";
    public static String yesNo = "1.Yes\n2.No";
    public static String noPlay = "Maybe next time!";
    public static String currentRoundLabel = "Current Round: ";
    public static String enterGuess = "Please enter a guess!";
    public static String winner = "You got the answer!";
    public static String outOfGuesses = "You ran out of guesses!";
    public static String solutionLabel = "Solution: ";
    public static String incorrect = "That's not it!";
    public static String keepPlaying = "Would you like to make another guess?";
    public static String fileNameInput = "Please enter a filename";

    public static void updateGameLog(String solution, String[] guesses, boolean solved) {
        ArrayList<String> gameLog = new ArrayList<>();

        String stringSolved;
        if (solved) {
            stringSolved = "Yes";
        } else { stringSolved = "No"; }

        String stringGuesses = guesses[0];
        for (int i = 1; i < guesses.length; i++) {
            if (guesses[i] != null) {
                stringGuesses = stringGuesses + "," + guesses[i];
            }
        }

        File file = new File("gamelog.txt");

        try {
            if (!file.exists()) { //case where file doesn't already exist
                PrintWriter printWriter = new PrintWriter(new FileWriter(file));

                String gameData = String.format("Game 1\n" +
                        "- Solution: " + solution + "\n" +
                        "- Guesses: " + stringGuesses + "\n" +
                        "- Solved: " + stringSolved);

                printWriter.println("Games Completed: 1\n" + gameData);
                printWriter.close();
            } else { //case where file does exist
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line = bufferedReader.readLine();
                int gamesCompleted = Integer.parseInt(line.split(" ")[2]);

                gameLog.add(String.format("Games Completed: %d", gamesCompleted + 1));
                line = bufferedReader.readLine();
                while (line != null) {
                    gameLog.add(line);
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();

                String gameData = String.format("Game " + (gamesCompleted + 1) + "\n" +
                        "- Solution: " + solution + "\n" +
                        "- Guesses: " + stringGuesses + "\n" +
                        "- Solved: " + stringSolved);
                gameLog.add(gameData);

                PrintWriter printWriter = new PrintWriter(new FileWriter(file));
                for (String i : gameLog) {
                    printWriter.println(i);
                }
                printWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(fileNameInput);
        String fileName = scanner.nextLine();
        WordLibrary wordLibrary = new WordLibrary(fileName);

        while (true) {
            System.out.println(welcome);
            System.out.println(yesNo);
            int option = scanner.nextInt();
            scanner.nextLine();
            if (option != 1) {
                System.out.println(noPlay);
                break;
            }

            WordGuesser wordGuesser = new WordGuesser(wordLibrary.chooseWord());
            String solution = wordGuesser.getSolution();
            String[] guesses = new String[5];
            boolean solved = false;
            while (true) {
                System.out.println(currentRoundLabel + wordGuesser.getRound());
                wordGuesser.printField();
                System.out.println(enterGuess);
                String guess = scanner.nextLine();
                guesses[wordGuesser.getRound() - 1] = guess;

                try {
                    boolean winCondition = wordGuesser.checkGuess(guess);
                    if (winCondition) {
                        System.out.println(winner);
                        wordGuesser.printField();
                        solved = true;
                        break;
                    } else if (wordGuesser.getRound() > 4) {
                        System.out.println(outOfGuesses);
                        System.out.println(solutionLabel + wordGuesser.getSolution());
                        wordGuesser.printField();
                        break;
                    } else { System.out.println(incorrect); }
                } catch (InvalidGuessException e) {
                    System.out.println(e.getMessage());
                }

                wordGuesser.setRound(wordGuesser.getRound() + 1);

                System.out.println(keepPlaying);
                System.out.println(yesNo);
                option = scanner.nextInt();
                scanner.nextLine();
                if (option != 1) {
                    wordGuesser.printField();
                    break;
                }
            }
            updateGameLog(solution, guesses, solved);
        }
    }
}
