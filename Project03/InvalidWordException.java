/**
 * An exception to throw when the user inputs and invalid word.
 *
 * @author Dylan Miller
 * @version October 26, 2022
 */

public class InvalidWordException extends Exception {
    public InvalidWordException(String message) { super(message); }
}
