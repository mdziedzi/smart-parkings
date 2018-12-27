package ontology;

import jade.content.AgentAction;

public class ParkingOffer implements AgentAction {

    //todo: add parking and agents IDs

    private double price;

    private double lat;

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
