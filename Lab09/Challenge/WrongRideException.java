import java.io.Serializable;

/**
 * An exception to be thrown when a park tries to add the wrong type of ride.
 *
 * @author Dylan Miller
 * @version October 21, 2022
 */

public class WrongRideException extends Exception implements Serializable {
    public WrongRideException(String message) { super(message); }
}
