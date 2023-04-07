/**
 * Lab06Challenge -- Maze Navigator
 *
 * This program creates a maze object using a boolean 2D array by tracking the position (row & column) of the player
 * and the position of the end goal, treasure.
 *
 * @author Dylan Miller, lab sec L11
 *
 * @version October 9, 2022
 *
 */

public class Maze {
    private boolean[][] maze;
    private int playerRow;
    private int playerColumn;
    private int treasureRow;
    private int treasureColumn;

    //constructor
    public Maze(boolean[][] maze, int treasureRow, int treasureColumn) {
        this.maze = maze;
        this.treasureRow = treasureRow;
        this.treasureColumn = treasureColumn;
        playerRow = 0;
        playerColumn = 0;
    }

    //accessors
    public boolean[][] getMaze() { return maze; }
    public int getPlayerRow() { return playerRow; }
    public int getPlayerColumn() { return playerColumn; }
    public int getTreasureRow() { return treasureRow; }
    public int getTreasureColumn() { return treasureColumn; }

    //mutators
    public void setMaze(boolean[][] maze) { this.maze = maze; }
    public void setPlayerRow(int playerRow) { this.playerRow = playerRow; }
    public void setPlayerColumn(int playerColumn) { this.playerColumn = playerColumn; }
    public void setTreasureRow(int treasureRow) { this.treasureRow = treasureRow; }
    public void setTreasureColumn(int treasureColumn) { this.treasureColumn = treasureColumn; }

    public boolean checkWin() {
        return (playerRow == treasureRow && playerColumn == treasureColumn);
    }
}
