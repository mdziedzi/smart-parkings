package price_algorithm;

/**
 * Represents the price algorithm used when agent have to calculate new price.
 */
public interface PriceAlgorithm {

    /**
     * Calculates new price of the parking place per hour.
     *
     * @param numOfOccupiedPlaces Nuber of occupied places.
     * @param capacity            Capacity of the whole parking.
     * @return Price in PLN.
     */
    double calculatePrice(int numOfOccupiedPlaces, int capacity);
}
