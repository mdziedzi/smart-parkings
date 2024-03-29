package parking_manager_agent.util;

/**
 * Model class describes geographical location in the project.
 */
public class Localization {

    private double latitude;
    private double longitude;

    public Localization(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
