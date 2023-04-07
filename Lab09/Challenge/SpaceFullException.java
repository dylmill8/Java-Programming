import java.io.Serializable;

/**
 * An exception to be thrown when a park tries to add land beyond the maximum limit.
 *
 * @author Dylan Miller
 * @version October 21, 2022
 */


public class SpaceFullException extends Exception implements Serializable {
    public SpaceFullException(String message) { super(message); }
}
