import java.util.ArrayList;

/**
 * A class for managing a water park's admission cost, land, rides, etc.
 *
 * @author Dylan Miller
 * @version October 21, 2022
 */

public class WaterPark implements Park {
    private double admissionCost;
    private boolean indoor;
    private double land;
    private boolean lazyRiver;
    private String name;
    private boolean outdoor;
    private ArrayList<Ride> rides;
    private boolean[] seasons;
    private boolean wavePool;

    public WaterPark(String name, double admissionCost, double land, ArrayList<Ride> rides, boolean indoor,
                     boolean outdoor, boolean lazyRiver, boolean wavePool, boolean[] seasons) {
        this.name = name;
        this.admissionCost = admissionCost;
        this.land = land;
        this.rides = rides;
        this.indoor = indoor;
        this.outdoor = outdoor;
        this.lazyRiver = lazyRiver;
        this.wavePool = wavePool;
        this.seasons = seasons;
    }

    @Override
    public void addRide(Ride ride) throws WrongRideException {
        if (ride instanceof Waterslide) {
            rides.add(ride);
        } else throw new WrongRideException("A waterpark can only have waterslide rides!");
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
        lazyRiver = false;
        wavePool = false;
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

    public boolean isLazyRiver() { return lazyRiver; }

    public boolean isWavePool() { return wavePool; }

    @Override
    public void removeRide(Ride ride) { rides.remove(ride); }

    @Override
    public void setAdmissionCost(double admissionCost) { this.admissionCost = admissionCost; }

    public void setLazyRiver(boolean lazyRiver) { this.lazyRiver = lazyRiver; }

    @Override
    public void setName(String name) { this.name = name; }

    @Override
    public void setSeasons(boolean[] seasons) { this.seasons = seasons; }

    public void setWavePool(boolean wavePool) { this.wavePool = wavePool; }

    public void modifyRide(Ride ride, String newName, String newColor, int newMinHeight, int newMaxRiders,
                           double newSplashDepth) {
        int index = rides.indexOf(ride);
        Waterslide waterslide = new Waterslide(newName, newColor, newMinHeight, newMaxRiders, newSplashDepth);
        rides.set(index, waterslide);
    }
}
