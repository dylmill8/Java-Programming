/**
 * Exception to be thrown when MusicRecommender cannot recommend the user any music based on their preferences.
 *
 * @author Dylan Miller
 * @version October 13, 2022
 */

public class NoRecommendationException extends Exception {
    public NoRecommendationException(String message) {
        super(message);
    }
}
