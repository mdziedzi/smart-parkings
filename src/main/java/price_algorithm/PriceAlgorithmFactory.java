package price_algorithm;

/**
 * Factory pattern used to produce PriceAlgorithm object used to init agents.
 */
public class PriceAlgorithmFactory {

    /**
     * Produces the PriceAlgorithm objects.
     *
     * @param type Type of price algorithm.
     * @return Specific implementation of PriceAlgorithm.
     */
    public static PriceAlgorithm getPriceAlgorithm(String type) {
        if (type.equalsIgnoreCase(PriceAlgorithmType.CONST)) {
            return new PriceAlgorithmConstant();
        } else if (type.equalsIgnoreCase(PriceAlgorithmType.MUTABLE)) {
            return new PriceAlgorithmMutable();
        }
        return null;
    }
}
