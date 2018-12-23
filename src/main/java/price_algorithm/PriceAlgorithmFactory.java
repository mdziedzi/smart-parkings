package price_algorithm;

public class PriceAlgorithmFactory {

    public static PriceAlgorithm getPriceAlgorithm(String type) {
        if (type.equalsIgnoreCase(PriceAlgorithmType.CONST)) {
            return new PriceAlgorithmConstant();
        } else if (type.equalsIgnoreCase(PriceAlgorithmType.MUTABLE)) {
            return new PriceAlgorithmMutable();
        }
        return null;
    }
}
