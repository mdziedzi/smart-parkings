package parking_devices.sensors;

import parking_manager_agent.util.Localization;

public class SensorsInterfaceFactory {

    public static SensorsInterface getSensorsInterface(String type, int capacity, int nOccupiedPlaces, double priceInDollars, Localization localization) {
        if (type.equalsIgnoreCase(SensorsInterfaceType.DEFAULT)) {
            return new SensorsInterfaceImpl(capacity, nOccupiedPlaces, priceInDollars, localization);
        }
        return null;
    }

}
