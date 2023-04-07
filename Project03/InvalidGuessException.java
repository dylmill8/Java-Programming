/**
 * An exception to throw when the user inputs and invalid guess.
 *
 * @author Dylan Miller
 * @version October 26, 2022
 */

public class InvalidGuessException extends Exception {
    public InvalidGuessException(String message) { super(message); }
}
