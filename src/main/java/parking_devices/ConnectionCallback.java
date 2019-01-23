package parking_devices;

import exceptions.EffectorsConnectionFailure;
import exceptions.SensorsConnectionFailure;

/**
 * Defines the callback used during connection checking.
 */
public interface ConnectionCallback {

    /**
     * Method is invoked after connection succes.
     */
    void onConnectionSuccess();

    /**
     * Method is invoked after connection failure.
     *
     * @throws SensorsConnectionFailure   Failure while connecting to sensors.
     * @throws EffectorsConnectionFailure Failure while connecting to effectors.
     */
    void onConnectionFailure() throws SensorsConnectionFailure, EffectorsConnectionFailure;

}
