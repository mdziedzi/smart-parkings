package price_algorithm;

import static parking_manager_agent.util.Constants.BASE_PRICE;

public class PriceAlgorithmConstant implements PriceAlgorithm {
    @Override
    public double calculatePrice(double numOfOccupiedPlaces, double capacity) {
        return BASE_PRICE;
    }
}
