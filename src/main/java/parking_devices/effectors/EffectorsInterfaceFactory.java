package parking_devices.effectors;

/**
 * Factory enables to create implementation of EffectorsInterface.
 */
public class EffectorsInterfaceFactory {

    public static EffectorsInterface getEffectorsInterface(String type) {
        if (type.equalsIgnoreCase(EffectorsInterfaceType.DEFAULT)) {
            return new EffectorsInterfaceImpl();
        }
        return null;
    }
}
