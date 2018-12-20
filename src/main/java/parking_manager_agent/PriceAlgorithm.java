package parking_manager_agent;

public interface PriceAlgorithm {

    double calculatePrice(double numOfOccupiedPlaces, double capacity);
}
