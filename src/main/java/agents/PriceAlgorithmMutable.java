package agents;

import static agents.util.Constants.*;

public class PriceAlgorithmMutable implements PriceAlgorithm {
    @Override
    public double calculatePrice(double numOfOccupiedPlaces, double capacity) {
        return Math.floor(BASE_PRICE * numOfOccupiedPlaces / capacity * FLOOR_FACTOR + MIN_PARKING_PRICE) / FLOOR_FACTOR + STARTING_PRICE;
    }
}
