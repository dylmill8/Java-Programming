import java.util.Scanner;

/**
 * A program that runs a Lego competition where players can input how many pieces they have built over a series of
 * days and the first player to complete all three Lego sets is declared the winner.
 *
 * @author Dylan Miller
 * @version October 13, 2022
 */

public class LegoSetCompetition {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Lego Set Competition!");

        CompetitionLog p1 = new CompetitionLog(1, "None", "None", 0);
        CompetitionLog p2 = new CompetitionLog(2, "None", "None", 0);
        int duration = 0;
        String incompleteSets = "";
        String p1CompleteSets = "";
        String p2CompleteSets = "";
        while (true) {
            System.out.println("Enter the name of Lego Set 1");
            String setOne = scanner.nextLine();
            System.out.println("Enter the number of pieces in Lego Set 1");
            int setOnePieces = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter the name of Lego Set 2");
            String setTwo = scanner.nextLine();
            System.out.println("Enter the number of pieces in Lego Set 2");
            int setTwoPieces = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter the name of Lego Set 3");
            String setThree = scanner.nextLine();
            System.out.println("Enter the number of pieces in Lego Set 3");
            int setThreePieces = scanner.nextInt();
            scanner.nextLine();

            int p1CurrentPieces = 0;
            int p2CurrentPieces = 0;
            int totalPieces = setOnePieces + setTwoPieces + setThreePieces;

            while (p1CurrentPieces < totalPieces && p2CurrentPieces < totalPieces) {
                duration++;
                System.out.printf("Enter the number of pieces player %d used for building on day %d\n",
                        p1.getPlayerNumber(), duration);
                p1CurrentPieces = p1CurrentPieces + scanner.nextInt();
                scanner.nextLine();
                System.out.printf("Enter the number of pieces player %d used for building on day %d\n",
                        p2.getPlayerNumber(), duration);
                p2CurrentPieces = p2CurrentPieces + scanner.nextInt();
                scanner.nextLine();
            }

            p1.setPiecesBuilt(p1.getPiecesBuilt() + p1CurrentPieces);
            if (p1CurrentPieces >= setOnePieces) {
                p1CompleteSets = p1CompleteSets + setOne;
                incompleteSets = setTwo + ", " + setThree;
                if (p1CurrentPieces >= setOnePieces + setTwoPieces) {
                    p1CompleteSets = p1CompleteSets + ", " + setTwo;
                    incompleteSets = setThree;
                    if (p1CurrentPieces >= totalPieces) {
                        p1CompleteSets = p1CompleteSets + ", " + setThree;
                        incompleteSets = "None";
                    }
                }
            } else {
                p1CompleteSets = "None";
                incompleteSets = setOne + ", " + setTwo + ", " + setThree;
            }
            p1.setCompleteSets(p1CompleteSets);
            p1.setIncompleteSets(incompleteSets);

            p2.setPiecesBuilt(p2.getPiecesBuilt() + p2CurrentPieces);
            if (p2CurrentPieces >= setOnePieces) {
                p2CompleteSets = p2CompleteSets + setOne;
                incompleteSets = setTwo + ", " + setThree;
                if (p2CurrentPieces >= setOnePieces + setTwoPieces) {
                    p2CompleteSets = p2CompleteSets + ", " + setTwo;
                    incompleteSets = setThree;
                    if (p2CurrentPieces >= totalPieces) {
                        p2CompleteSets = p2CompleteSets + ", " + setThree;
                        incompleteSets = "None";
                    }
                }
            } else {
                p2CompleteSets = "None";
                incompleteSets = setOne + ", " + setTwo + ", " + setThree;
            }
            p2.setCompleteSets(p2CompleteSets);
            p2.setIncompleteSets(incompleteSets);

            if (p1CurrentPieces >= totalPieces && p2CurrentPieces >= totalPieces) {
                System.out.println("The competition ended in a tie! There will be a tiebreaker round");
                p1CompleteSets = p1CompleteSets + ", ";
                p2CompleteSets = p2CompleteSets + ", ";
            } else break;
        }

        if (p1.getPiecesBuilt() > p2.getPiecesBuilt()) {
            System.out.printf("Congratulations to player %d for winning the Lego Set Competition!\n",
                    p1.getPlayerNumber());
        } else {
            System.out.printf("Congratulations to player %d for winning the Lego Set Competition!\n",
                    p2.getPlayerNumber());
        }
        System.out.println("Additional information about the competition results is below");
        System.out.print(p1);
        System.out.print(p2);
        System.out.printf("The competition lasted %d days", duration);
    }
}
