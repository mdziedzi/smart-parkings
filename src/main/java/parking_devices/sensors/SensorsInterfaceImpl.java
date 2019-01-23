package parking_devices.sensors;

import parking_devices.ConnectionCallback;
import parking_manager_agent.util.Localization;

/**
 * Default implementation of SensorsInterface. Simple model class.
 */
public class SensorsInterfaceImpl implements SensorsInterface {

    /**
     * Capacity of parking.
     */
    private int capacity;

    /**
     * Number of occupied place on the parking.
     */
    private int nOccupiedPlaces;

    /**
     * Price in dollars for one hour of stay.
     */
    private double price;

    /**
     * Localization of the parking.
     */
    private Localization localization;

    SensorsInterfaceImpl(int capacity, int nOccupiedPlaces, double price, Localization localization) {
        this.capacity = capacity;
        this.nOccupiedPlaces = nOccupiedPlaces;
        this.price = price;
        this.localization = localization;
    }

    @Override
    public void checkConnection(ConnectionCallback connectionCallback) {

    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getNOccupiedPlaces() {
        return nOccupiedPlaces;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public Localization getLocalization() {
        return localization;
    }
}
