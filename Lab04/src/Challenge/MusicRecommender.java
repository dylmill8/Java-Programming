import java.util.Scanner;

/**
 * Lab04Challenge -- Music Recommender
 *
 * This program asks the user "yes" or "no" questions to deduce their current mood and recommends them a fitting song
 *
 * @author Dylan Miller, lab sec L11
 *
 * @version September 15, 2022
 *
 */

public class MusicRecommender {

    public static final String WELCOME_MESSAGE = "Welcome to the Music Recommender!";
    public static final String INITIAL_SONG = "Do you want to listen to a song?";
    public static final String HAPPY = "Are you feeling happy?";
    public static final String CLAP_ALONG = "Do you want to clap along?";
    public static final String SING_OUT = "Do you want to sing out?";
    public static final String DANCE = "Do you want to dance?";
    public static final String SAD = "Are you feeling sad?";
    public static final String LYRICS = "Do you want lyrics?";
    public static final String WORRIED = "Are you feeling worried?";
    public static final String CALM = "Are you feeling calm?";
    public static final String COMPLICATED = "Are your feelings more complicated than this program can handle?";
    public static final String CONTINUE_WORRIED = "Do you want to keep feeling worried?";
    public static final String GOODBYE_MESSAGE = "Thank you for using the Music Recommender!";

    public static final String SONG_ONE = "Play \"Happy\" by Pharrell Williams";
    public static final String SONG_TWO = "Play \"If you want to Sing Out, Sing Out\" by Cat Stevens";
    public static final String SONG_THREE = "Play \"Uptown Funk\" by Mark Ronson featuring Bruno Mars";
    public static final String SONG_FOUR = "Play \"La finta giardiniera, K. 196: Ouverture. Allegro molto\" by Mozart";
    public static final String SONG_FIVE = "Play \"Hurt\" by Trent Reznor, as performed by Johnny Cash";
    public static final String SONG_SIX = "Play Theme from Schindler's List";
    public static final String SONG_SEVEN = "Play \"Aben bog\" by Bremer/McCoy";
    public static final String SONG_EIGHT = "Play \"Complicated\" by Avril Lavigne";
    public static final String SONG_NINE = "Play \"A Witch Stole Sam\" by Mark Korven" +
            " from The Witch Original Soundtrack";
    public static final String SONG_TEN = "Play \"Don't worry. Be Happy\" by Bobby McFerrin";

    // ------------------------- DO NOT MODIFY ABOVE -------------------------

    // IMPLEMENT YOUR SOLUTION HERE

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println(WELCOME_MESSAGE);
        System.out.println(INITIAL_SONG);
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("yes")) {
            System.out.println(HAPPY);
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("yes")) {
                System.out.println(CLAP_ALONG);
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("yes")) {
                    System.out.println(SONG_ONE);
                } else {
                    System.out.println(SING_OUT);
                    input = scanner.nextLine();
                    if (input.equalsIgnoreCase("yes")) {
                        System.out.println(SONG_TWO);
                    } else {
                        System.out.println(DANCE);
                        input = scanner.nextLine();
                        if (input.equalsIgnoreCase("yes")) {
                            System.out.println(SONG_THREE);
                        } else {
                            System.out.println(SONG_FOUR);
                        }
                    }
                }
            } else {
                System.out.println(SAD);
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("yes")) {
                    System.out.println(LYRICS);
                    input = scanner.nextLine();
                    if (input.equalsIgnoreCase("yes")) {
                        System.out.println(SONG_FIVE);
                    } else {
                        System.out.println(SONG_SIX);
                    }
                } else {
                    System.out.println(WORRIED);
                    input = scanner.nextLine();
                    if (input.equalsIgnoreCase("yes")) {
                        System.out.println(CONTINUE_WORRIED);
                        input = scanner.nextLine();
                        if (input.equalsIgnoreCase("yes")) {
                            System.out.println(SONG_NINE);
                        } else {
                            System.out.println(SONG_TEN);
                        }
                    } else {
                        System.out.println(CALM);
                        input = scanner.nextLine();
                        if (input.equalsIgnoreCase("yes")) {
                            System.out.println(SONG_SEVEN);
                        } else {
                            System.out.println(COMPLICATED);
                            input = scanner.nextLine();
                            if (input.equalsIgnoreCase("yes")) {
                                System.out.println(SONG_EIGHT);
                            }
                        }
                    }
                }
            }
            //System.out.println(GOODBYE_MESSAGE);
        }
        System.out.println(GOODBYE_MESSAGE);
    }
}