package parking_devices;

import exceptions.EffectorsConnectionFailure;
import exceptions.SensorsConnectionFailure;

public interface ConnectionCallback {

    void onConnectionSuccess();

    void onConnectionFailure() throws SensorsConnectionFailure, EffectorsConnectionFailure;

}
