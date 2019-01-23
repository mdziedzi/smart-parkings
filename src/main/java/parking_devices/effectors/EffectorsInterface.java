package parking_devices.effectors;

import parking_devices.ConnectionCallback;

/**
 * Interface of simulation of effectors on the parking.
 */
public interface EffectorsInterface {

    /**
     * Checks connection to the devices.
     *
     * @param connectionCallback Callback
     */
    void checkConnection(ConnectionCallback connectionCallback);

    /**
     * Blocks parking place on the parking.
     */
    void blockParkingPlace();

    /**
     * Checks if the boking is premitted;
     */
    boolean isBookingPermitted();
}
