import java.util.Arrays;
import java.util.Scanner;

/**
 * Lab06Challenge -- Maze Navigator
 *
 * This program allows the user to create maze and navigate it to find hidden treasure.
 *
 * @author Dylan Miller, lab sec L11
 *
 * @version October 9, 2022
 *
 */

public class MazeNavigator {

    public static final String WELCOME = "Welcome to the Maze Navigator!";
    public static final String INITIALIZE_MAZE = "Initializing maze...";
    public static final String MAZE_DIMENSIONS = "Please enter the maze dimensions:";
    public static final String MAZE_VALUES = "Please enter the values for the maze's row %d:";
    public static final String TREASURE_LOCATION = "Please enter the expected treasure location:";
    public static final String READY = "Ready to start?";
    public static final String CURRENT_POSITION = "Player's Position: %d,%d";
    public static final String MOVE_SELECT = "Please enter a move:";
    public static final String[] MOVES = {"Up", "Down", "Left", "Right"};
    public static final String INVALID_MOVE = "Invalid move! Select another direction.";
    public static final String TREASURE_FOUND = "Treasure found!";
    public static final String FAREWELL = "Thank you for playing!";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(WELCOME + "\n" + INITIALIZE_MAZE + "\n" + MAZE_DIMENSIONS + "\n");
        String[] split = scanner.nextLine().split(",");
        int row = Integer.parseInt(split[0]);
        int column = Integer.parseInt(split[1]);
        boolean[][] tempMaze = new boolean[row][column];

        for (int i = 0; i < row; i++) {
            System.out.printf(MAZE_VALUES + "\n", i);
            split = scanner.nextLine().split(",");
            for (int j = 0; j < split.length; j++) { tempMaze[i][j] = Boolean.parseBoolean(split[j]); }
        }

        System.out.println(TREASURE_LOCATION);
        split = scanner.nextLine().split(",");
        row = Integer.parseInt(split[0]);
        column = Integer.parseInt(split[1]);
        System.out.println(READY);
        String ready = scanner.nextLine();
        if (ready.equalsIgnoreCase("YES")) {
            Maze maze = new Maze(tempMaze, row, column);

            int rowLength = maze.getMaze().length;
            int columnLength = maze.getMaze()[0].length;
            int playerRow = 0;
            int playerColumn = 0;
            while (!maze.checkWin()) {
                System.out.printf(CURRENT_POSITION + "\n", maze.getPlayerRow(), maze.getPlayerColumn());
                System.out.println(MOVE_SELECT);
                for (int i = 0; i < MOVES.length; i++) System.out.println(i + 1 + ". " + MOVES[i]);
                String option = scanner.nextLine();

                if (option.equalsIgnoreCase("UP")) {
                    if (maze.getPlayerRow() - 1 < 0) playerRow = columnLength - 1;
                    else playerRow = maze.getPlayerRow() - 1;
                } else if (option.equalsIgnoreCase("DOWN")) {
                    playerRow = (maze.getPlayerRow() + 1) % columnLength;
                } else if (option.equalsIgnoreCase("LEFT")) {
                    if (maze.getPlayerColumn() - 1 < 0) playerColumn = rowLength - 1;
                    else playerColumn = maze.getPlayerColumn() - 1;
                } else if (option.equalsIgnoreCase("RIGHT")) {
                    playerColumn = (maze.getPlayerColumn() + 1) % rowLength;
                }

                if (maze.getMaze()[playerRow][playerColumn]) {
                    maze.setPlayerRow(playerRow);
                    maze.setPlayerColumn(playerColumn);
                } else {
                    System.out.println(INVALID_MOVE);
                    playerRow = maze.getPlayerRow();
                    playerColumn = maze.getPlayerColumn();
                }
            }
            System.out.println(TREASURE_FOUND);
        }
        System.out.println(FAREWELL);
    }
}