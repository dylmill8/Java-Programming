/**
 * A program that creates a class to track a player's individual progress in a Lego set competition
 *
 * @author Dylan Miller
 * @version October 13, 2022
 */

public class CompetitionLog {
    //class variables
    private int playerNumber;
    private String completeSets;
    private String incompleteSets;
    private int piecesBuilt;

    //constructor
    public CompetitionLog(int playerNumber, String completeSets, String incompleteSets, int piecesBuilt) {
        this.playerNumber = playerNumber;
        this.completeSets = completeSets;
        this.incompleteSets = incompleteSets;
        this.piecesBuilt = piecesBuilt;
    }

    //accessors
    public int getPlayerNumber() { return playerNumber; }
    public String getCompleteSets() { return completeSets; }
    public String getIncompleteSets() { return incompleteSets; }
    public int getPiecesBuilt() { return piecesBuilt; }

    //mutators
    public void setCompleteSets(String completeSets) { this.completeSets = completeSets; }
    public void setIncompleteSets(String incompleteSets) { this.incompleteSets = incompleteSets; }
    public void setPiecesBuilt(int piecesBuilt) { this.piecesBuilt = piecesBuilt; }

    //string method
    public String toString() {
        return String.format("Player %d completed the following sets: %s\n" +
                "Player %d did not complete the following sets: %s\n" +
                "Player %d built a total of %d pieces\n",
                playerNumber, completeSets, playerNumber, incompleteSets, playerNumber, piecesBuilt);
    }
}
