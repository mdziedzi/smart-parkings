package agents;

import static agents.util.Constants.BASE_PRICE;

public class PriceAlgorithmConstant implements PriceAlgorithm {
    @Override
    public double calculatePrice(double numOfOccupiedPlaces, double capacity) {
        return BASE_PRICE;
    }
}
