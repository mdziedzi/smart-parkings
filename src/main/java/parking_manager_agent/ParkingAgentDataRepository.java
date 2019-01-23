package parking_manager_agent;

import parking_manager_agent.util.Localization;

/**
 * Data repository of the parking agent. Agent can store his state such as: capacity, occupied places, price, and localization.
 */
public class ParkingAgentDataRepository {

    private int capacity;

    private int nOccupiedPlaces;

    private double price;

    private Localization localization;

    public ParkingAgentDataRepository(int capacity, int nOccupiedPlaces, double price, Localization localization) {
        this.capacity = capacity;
        this.nOccupiedPlaces = nOccupiedPlaces;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Localization getLocalization() {
        return localization;
    }

    public void setLocalization(Localization localization) {
        this.localization = localization;
    }
}
