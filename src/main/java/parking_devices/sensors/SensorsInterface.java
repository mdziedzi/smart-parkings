package parking_devices.sensors;

import parking_devices.ConnectionCallback;
import parking_manager_agent.util.Localization;

/**
 * Inteface of the symulation of Sensors on the parking.
 */
public interface SensorsInterface {

    /**
     * Checks connection to the devices.
     *
     * @param connectionCallback Callback
     */
    void checkConnection(ConnectionCallback connectionCallback);

    /**
     * Returns the capacity of the parking.
     */
    int getCapacity();

    /**
     * Returns the number of occupied places.
     * @return Number of occupied parking spots.
     */
    int getNOccupiedPlaces();

    /**
     * Reruns the price for the parking place per hour. Price is in PLN.
     *
     * @return Price for the parking place per hour in PLN.
     */
    double getPrice();

    /**
     * Returns the localization of parking.
     * @return Localization of parking.
     */
    Localization getLocalization();
}
