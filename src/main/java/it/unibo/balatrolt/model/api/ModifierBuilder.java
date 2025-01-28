package it.unibo.balatrolt.model.api;

import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import it.unibo.balatrolt.model.api.Combination.CombinationType;

/**
 * Builder for modifier. It allows the to create a modifier with bound
 */
public interface ModifierBuilder {
    /**
     * Adds a multiplier function to the modifier.
     * @param multiplierFun multiplier function
     * @return current modifierBuilder status
     */
    ModifierBuilder addMultiplierModifier(UnaryOperator<Double> multiplierFun);

    /**
     * Adds a base point function to the modifier.
     * @param bPFun base point funtion
     * @return current modifierBuilder status
     */
    ModifierBuilder addBasePointsModifier(UnaryOperator<Integer> bPFun);

    /**
     * Adds a bound on played cards.
     * @param condition condition to verify to get the modifier functions
     * @return current modifierBuilder status
     */
    ModifierBuilder addPlayedCardBound(Predicate<Set<PlayableCard>> condition);

    /**
     * Adds a bound on holding cards.
     * @param condition condition to verify to get the modifier functions
     * @return current modifierBuilder status
     */
    ModifierBuilder addHoldingCardBound(Predicate<Set<PlayableCard>> condition);

    /**
     * Adds a bound on the current combination.
     * @param condition condition to verify to get the modifier functions
     * @return current modifierBuilder status
     */
    ModifierBuilder addCombinationBound(Predicate<CombinationType> condition);

    /**
     * Adds a bound on the current currency.
     * @param condition condition to verify to get the modifier functions
     * @return current modifierBuilder status
     */
    ModifierBuilder addCurrentCurrencyBound(Predicate<Integer> condition);

    /**
     * Merges the current modifier with toMerge.
     * It can be called only once.
     * @param toMerge modifier to merge
     * @return curret modifierBuilder status
     * @throws IllegalStateException when this method is called more than once.
     */
    ModifierBuilder merge(Modifier toMerge);

    /**
     * Builds the modifier.
     * @return modifier
     */
    Modifier build();
}
