package parking;

import parking_manager_agent.util.Localization;

public interface ParkingDevices {

    int getCapacity();

    int getNumOfOccupiedPlaces();

    Localization getLocalization();
}
