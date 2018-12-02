package parking;

import agents.util.Localization;

public interface ParkingDevices {

    int getCapacity();

    int getNumOfOccupiedPlaces();

    Localization getLocalization();
}
