package ontology;

import jade.content.AgentAction;

/**
 * Represents part of Parking Ontology.
 * Agents uses this to understand the offer which is sent from parking to driver.
 */

public class ParkingOffer implements AgentAction {

    /**
     * Current price of parking place per hour. The price is in PLN.
     */
    private double price;

    /**
     * Latitude - used for geolocation.
     */
    private double lat;

    /**
     * Longitude - used for geolocation.
     */
    private double lon;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
