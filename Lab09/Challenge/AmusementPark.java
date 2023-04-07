import java.util.ArrayList;

/**
 * A class for managing an amusement park's admission cost, land, rides, etc.
 *
 * @author Dylan Miller
 * @version October 21, 2022
 */

public class AmusementPark implements Park {
    private double admissionCost;
    private boolean arcade;
    private boolean bowling;
    private boolean indoor;
    private double land;
    private String name;
    private boolean outdoor;
    private ArrayList<Ride> rides;
    private boolean[] seasons;

    public AmusementPark(String name, double admissionCost, double land, ArrayList<Ride> rides, boolean indoor,
                         boolean outdoor, boolean arcade, boolean bowling, boolean[] seasons) {
        this.name = name;
        this.admissionCost = admissionCost;
        this.land = land;
        this.rides = rides;
        this.indoor = indoor;
        this.outdoor = outdoor;
        this.arcade = arcade;
        this.bowling = bowling;
        this.seasons = seasons;
    }

    @Override
    public void addRide(Ride ride) throws WrongRideException {
        if (ride instanceof Rollercoaster) {
            rides.add(ride);
        } else throw new WrongRideException("An amusement park can only have rollercoaster rides!");
    }

    @Override
    public void close() {
        name = "";
        admissionCost = 0;
        land = 0;
        rides = null;
        seasons = null;
        indoor = false;
        outdoor = false;
        bowling = false;
        arcade = false;
    }

    @Override
    public void enlarge(double addedLand, double maxLand, boolean addedIndoor, boolean addedOutdoor)
            throws SpaceFullException {
        if (maxLand > land + addedLand) {
            land += addedLand;
            if (!indoor && addedIndoor) { indoor = true; }
            if (!outdoor && addedOutdoor) { outdoor = true; }
        } else throw new SpaceFullException("There is no more land to use for this park!");
    }

    @Override
    public double getAdmissionCost() { return admissionCost; }

    @Override
    public double getLand() { return land; }

    @Override
    public String getName() { return name; }

    @Override
    public ArrayList<Ride> getRides() { return rides; }

    @Override
    public boolean[] getSeasons() { return seasons; }

    @Override
    public boolean isIndoor() { return indoor; }

    @Override
    public boolean isOutdoor() { return outdoor; }

    public boolean isArcade() { return arcade; }

    public boolean isBowling() { return bowling; }

    @Override
    public void removeRide(Ride ride) { rides.remove(ride); }

    @Override
    public void setAdmissionCost(double admissionCost) { this.admissionCost = admissionCost; }

    public void setBowling(boolean bowling) { this.bowling = bowling; }

    @Override
    public void setName(String name) { this.name = name; }

    @Override
    public void setSeasons(boolean[] seasons) { this.seasons = seasons; }

    public void setArcade(boolean arcade) { this.arcade = arcade; }

    public void modifyRide(Ride ride, String newName, String newColor, int newMinHeight, int newMaxRiders,
                           boolean newSimulated) {
        int index = rides.indexOf(ride);
        Rollercoaster rollercoaster = new Rollercoaster(newName, newColor, newMinHeight, newMaxRiders, newSimulated);
        rides.set(index, rollercoaster);
    }
}