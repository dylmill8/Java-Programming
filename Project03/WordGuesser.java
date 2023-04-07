/**
 * This class implements a word game (Wordle) using a 5 X 5 playing field, round numbers, and a keyword for the
 * solution.
 *
 * @author Dylan Miller
 * @version October 26, 2022
 */

public class WordGuesser {
    private String[][] playingField;
    private int round;
    private String solution;

    public WordGuesser(String solution) {
        this.solution = solution.toLowerCase();
        round = 1;
        playingField = new String[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                playingField[i][j] = " ";
            }
        }
    }

    //accessors
    public String[][] getPlayingField() { return playingField; }
    public int getRound() { return round; }
    public String getSolution() { return solution; }

    //mutators
    public void setPlayingField(String[][] playingField) { this.playingField = playingField; }
    public void setRound(int round) { this.round = round; }
    public void setSolution(String solution) { this.solution = solution; }

    public boolean checkGuess(String guess) throws InvalidGuessException {
        if (guess.length() != 5) { throw new InvalidGuessException("Invalid Guess!"); }
        guess = guess.toLowerCase();
        for (int i = 0; i < 5; i++) {
            if (guess.charAt(i) == solution.charAt(i)) { //checks if the letter is in the correct position
                playingField[round - 1][i] = "'" + guess.charAt(i) + "'";
            } else if (solution.contains(String.valueOf(guess.charAt(i)))) { //checks if the letter in in the solution
                playingField[round - 1][i] = "*" + guess.charAt(i) + "*";
            } else playingField[round - 1][i] = "{" + guess.charAt(i) + "}";
        }
        return guess.equalsIgnoreCase(solution);
    }

    public void printField() {
        for (int i = 0; i < 5; i++) {
            System.out.printf("%s | %s | %s | %s | %s%n",
                    playingField[i][0],
                    playingField[i][1],
                    playingField[i][2],
                    playingField[i][3],
                    playingField[i][4]);
        }
    }
}
