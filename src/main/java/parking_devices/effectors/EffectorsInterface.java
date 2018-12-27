package parking_devices.effectors;

import parking_devices.ConnectionCallback;

public interface EffectorsInterface {

    void checkConnection(ConnectionCallback connectionCallback);

    void blockParkingPlace();
}
