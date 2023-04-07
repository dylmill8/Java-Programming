import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class test {
    public static void updateGameLog(String solution, String[] guesses, boolean solved) {
        ArrayList<String> gameLog = new ArrayList<>();

        File file = new File("gamelog.txt");

        try {
            if (!file.exists()) { //case where file doesn't already exist
                PrintWriter printWriter = new PrintWriter(new FileWriter(file));

                String gameData = String.format("Game 1\n" +
                        "- Solution: " + solution + "\n" +
                        "- Guesses: " + Arrays.toString(guesses) + "\n" +
                        "- Solved: " + solved);

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
                        "- Guesses: " + Arrays.toString(guesses) + "\n" +
                        "- Solved: " + solved);
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
        int counter = 0;
        while (true) {

            counter++;
            if (counter > 1) { break; }

            String solution = "class";
            String[] guesses = new String[5];
            for (int i = 0; i < 5; i++) {
                guesses[i] = counter + "test" + (i + 1);
            }
            boolean solved = false;
            updateGameLog(solution, guesses, solved);
            //System.out.print("");
        }
    }
}
