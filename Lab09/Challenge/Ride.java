import java.util.Objects;

/**
 * A class for amusement park rides that stores the ride's color, maximum number of rides, minimum required height, and
 * the name of the ride.
 *
 * @author Dylan Miller
 * @version October 21, 2022
 */

public class Ride {
    private String color;
    private int maxRiders;
    private int minHeight;
    private String name;

    //constructors
    public Ride() {
        name = "";
        color = "";
        maxRiders = 0;
        minHeight = 0;
    }
    public Ride(String name, String color, int minHeight, int maxRiders) {
        this.name = name;
        this.color = color;
        this.minHeight = minHeight;
        this.maxRiders = maxRiders;
    }

    //accessors
    public String getColor() { return color; }
    public int getMaxRiders() { return maxRiders; }
    public int getMinHeight() { return minHeight; }
    public String getName() { return name; }

    //mutators
    public void setColor(String color) { this.color = color; }
    public void setMaxRiders(int maxRiders) { this.maxRiders = maxRiders; }
    public void setMinHeight(int minHeight) { this.minHeight = minHeight; }
    public void setName(String name) { this.name = name; }

    public boolean equals(Object o) { return o instanceof Ride && Objects.equals(o.toString(), this.toString()); }

    @Override
    public String toString() {
        return String.format("Name: %s\nColor: %s\nMinHeight: %d inches\nMaxRiders: %d",
                name, color, minHeight, maxRiders);
    }
}
