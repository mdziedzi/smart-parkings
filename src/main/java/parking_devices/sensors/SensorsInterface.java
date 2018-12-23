package parking_devices.sensors;

import parking_devices.ConnectionCallback;
import parking_manager_agent.util.Localization;

public interface SensorsInterface {

    void checkConnection(ConnectionCallback connectionCallback);

    int getCapacity();

    int getNOccupiedPlaces();

    double getPriceInDollars(); // todo: change for Dollars class

    Localization getLocalization();
}
