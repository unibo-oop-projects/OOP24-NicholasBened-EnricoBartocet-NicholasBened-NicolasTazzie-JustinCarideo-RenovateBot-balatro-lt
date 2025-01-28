package it.unibo.balatrolt.model.api;

/**
 * Interface that models the concept of
 * evaluating multiple type of combination
 * with the creation of calculators.
 */
public interface CombinationCalculatorFactory {

    CombinationCalculator highCardCalculator();

    CombinationCalculator pairCalculator();

    CombinationCalculator twoPairCalculator();

    CombinationCalculator threeOfAKindCalculator();

    CombinationCalculator straightCalculator();

    CombinationCalculator flushCalculator();

    CombinationCalculator fullHouseCalculator();

    CombinationCalculator fourOfAKindCalculator();

    CombinationCalculator straightFlushCalculator();

    CombinationCalculator royalFlushCalculator();
}
