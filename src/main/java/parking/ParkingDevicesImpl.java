package parking;

import agents.util.Localization;

public class ParkingDevicesImpl implements ParkingDevices {

    private int capacity;

    private int numOfOccupiedPlaces;

    private Localization localization;

    public ParkingDevicesImpl(int capacity, int numOfOccupiedPlaces, Localization localization) {
        this.capacity = capacity;
        this.numOfOccupiedPlaces = numOfOccupiedPlaces;
        this.localization = localization;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getNumOfOccupiedPlaces() {
        return numOfOccupiedPlaces;
    }

    @Override
    public Localization getLocalization() {
        return localization;
    }
}
