import java.util.Objects;

/**
 * A subclass of Ride that stores variables related to a water slide ride such as the splash depth,
 * the minimum required height, the maximum number of rides, the color, and the name of the ride.
 *
 * @author Dylan Miller
 * @version October 21, 2022
 */

public class Waterslide extends Ride {
    private double splashDepth;

    public Waterslide(String name, String color, int minHeight, int maxRiders, double splashDepth) {
        super(name, color, minHeight, maxRiders);
        this.splashDepth = splashDepth;
    }

    public double getSplashDepth() { return splashDepth; }

    public void setSplashDepth(double splashDepth) { this.splashDepth = splashDepth; }

    @Override
    public boolean equals(Object o) {
        return o instanceof Waterslide && Objects.equals(o.toString(), this.toString());
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nColor: %s\nMinHeight: %d inches\nMaxRiders: %d\nSplashDepth: %.1f feet",
                this.getName(), this.getColor(), this.getMinHeight(), this.getMaxRiders(), splashDepth);
    }
}
