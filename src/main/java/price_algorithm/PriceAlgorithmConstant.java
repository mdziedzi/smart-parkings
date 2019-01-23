package price_algorithm;

import static parking_manager_agent.util.Constants.BASE_PRICE;

/**
 * Implementation of PriceAlgorithm. The calculated price is always constant.
 */
public class PriceAlgorithmConstant implements PriceAlgorithm {
    @Override
    public double calculatePrice(int numOfOccupiedPlaces, int capacity) {
        return BASE_PRICE;
    }
}
