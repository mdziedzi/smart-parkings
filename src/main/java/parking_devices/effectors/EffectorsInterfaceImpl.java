package parking_devices.effectors;

import parking_devices.ConnectionCallback;

/**
 * Default implementation of EffectorsInterface. All methods do nothing.
 */
public class EffectorsInterfaceImpl implements EffectorsInterface {
    @Override
    public void checkConnection(ConnectionCallback connectionCallback) {
        // do nothing
    }

    @Override
    public void blockParkingPlace() {
        // do nothing
    }

    @Override
    public boolean isBookingPermitted() {
        // todo
        return true;
    }
}
