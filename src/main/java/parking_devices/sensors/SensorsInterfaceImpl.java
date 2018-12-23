package parking_devices.sensors;

import parking_devices.ConnectionCallback;
import parking_manager_agent.util.Localization;

public class SensorsInterfaceImpl implements SensorsInterface {

    private int capacity;
    private int nOccupiedPlaces;
    private double priceInDollars;
    private Localization localization;

    SensorsInterfaceImpl(int capacity, int nOccupiedPlaces, double priceInDollars, Localization localization) {
        this.capacity = capacity;
        this.nOccupiedPlaces = nOccupiedPlaces;
        this.priceInDollars = priceInDollars;
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
    public double getPriceInDollars() {
        return priceInDollars;
    }

    @Override
    public Localization getLocalization() {
        return localization;
    }
}
