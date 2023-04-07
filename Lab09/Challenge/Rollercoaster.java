import java.util.Objects;

/**
 * A subclass of Ride that stores variables related to a roller coaster ride such as whether the coaster is simulated,
 * the minimum required height, the maximum number of rides, the color, and the name of the ride.
 *
 * @author Dylan Miller
 * @version October 21, 2022
 */

public class Rollercoaster extends Ride {
    private boolean simulated;

    public Rollercoaster(String name, String color, int minHeight, int maxRiders, boolean simulated) {
        super(name, color, minHeight, maxRiders);
        this.simulated = simulated;
    }

    public boolean isSimulated() { return simulated; }

    public void setSimulated(boolean simulated) { this.simulated = simulated; }

    @Override
    public boolean equals(Object o) {
        return o instanceof Rollercoaster && Objects.equals(o.toString(), this.toString());
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nColor: %s\nMinHeight: %d inches\nMaxRiders: %d\nSimulated: %b",
                this.getName(), this.getColor(), this.getMinHeight(), this.getMaxRiders(), simulated);
    }
}
