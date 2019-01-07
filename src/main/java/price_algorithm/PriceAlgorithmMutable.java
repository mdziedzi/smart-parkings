package price_algorithm;

import static parking_manager_agent.util.Constants.*;

public class PriceAlgorithmMutable implements PriceAlgorithm {
    @Override
    public double calculatePrice(int numOfOccupiedPlaces, int capacity) {
        return Math.floor(BASE_PRICE * numOfOccupiedPlaces / capacity * FLOOR_FACTOR + MIN_PARKING_PRICE) / FLOOR_FACTOR + STARTING_PRICE;
    }
}
