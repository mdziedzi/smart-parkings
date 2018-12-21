package parking_manager_agent;

import parking_manager_agent.util.Localization;

public class ParkingAgentDataRepository {

    private int capacity;

    private int nOccupiedPlaces;

    private double priceInDollars;

    private Localization localization;

    public ParkingAgentDataRepository(int capacity, int nOccupiedPlaces, double priceInDollars, Localization localization) {
        this.capacity = capacity;
        this.nOccupiedPlaces = nOccupiedPlaces;
        this.priceInDollars = priceInDollars;
        this.localization = localization;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getnOccupiedPlaces() {
        return nOccupiedPlaces;
    }

    public void setnOccupiedPlaces(int nOccupiedPlaces) {
        this.nOccupiedPlaces = nOccupiedPlaces;
    }

    public double getPriceInDollars() {
        return priceInDollars;
    }

    public void setPriceInDollars(double priceInDollars) {
        this.priceInDollars = priceInDollars;
    }

    public Localization getLocalization() {
        return localization;
    }

    public void setLocalization(Localization localization) {
        this.localization = localization;
    }
}
