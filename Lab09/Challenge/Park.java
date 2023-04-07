import java.util.ArrayList;

/**
 * An interface for parks with essential variables and methods such as admission cost, amount of land, name, rides, etc.
 *
 * @author Dylan Miller
 * @version October 21, 2022
 */

public interface Park {
    void addRide(Ride ride) throws WrongRideException;

    void close();

    void enlarge(double addedLand, double maxLand, boolean addedIndoor, boolean addedOutdoor) throws SpaceFullException;

    double getAdmissionCost();

    double getLand();

    String getName();

    ArrayList<Ride> getRides();

    boolean[] getSeasons();

    boolean	isIndoor();

    boolean	isOutdoor();

    void removeRide(Ride ride);

    void setAdmissionCost(double admissionCost);

    void setName(String name);

    void setSeasons(boolean[] seasons);
}
