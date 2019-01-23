package price_algorithm;

import static parking_manager_agent.util.Constants.*;

/**
 * Implementation of PriceAlgorithm. The calculated price depends of the occupancy of the parking.
 */
public class PriceAlgorithmMutable implements PriceAlgorithm {
    @Override
    public double calculatePrice(int numOfOccupiedPlaces, int capacity) {
        return Math.floor(BASE_PRICE * (double) numOfOccupiedPlaces / (double) capacity * FLOOR_FACTOR + MIN_PARKING_PRICE) / FLOOR_FACTOR + STARTING_PRICE;
    }
}
