package it.unibo.balatrolt.model.api.combination;

/**
 * Interface that models the concept of
 * evaluating multiple type of combination
 * with the creation of calculators. It's
 * based on the class {@link CombinationCalculator}
 */
public interface CombinationCalculatorFactory {

    /**
     * @return a CombinationCalculator for empty card
     */
    CombinationCalculator emptyCardCalculator();

    /**
     * @return a CombinationCalculator for high card
     */
    CombinationCalculator highCardCalculator();

    /**
     * @return a CombinationCalculator for pair
     */
    CombinationCalculator pairsCalculator();

    /**
     * @return a CombinationCalculator for three of a kind
     */
    CombinationCalculator threeOfAKindCalculator();

        /**
     * @return a CombinationCalculator for four of a kind
     */
    CombinationCalculator fourOfAKindCalculator();

    /**
     * @return a CombinationCalculator for straight flush
     */
    CombinationCalculator fiveCardsCalculator();
}
