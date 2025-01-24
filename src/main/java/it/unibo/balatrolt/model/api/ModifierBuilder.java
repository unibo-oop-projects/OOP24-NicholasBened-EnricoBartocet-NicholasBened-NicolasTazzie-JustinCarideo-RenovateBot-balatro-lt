package it.unibo.balatrolt.model.api;

import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

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
     * @param condition
     * @return current modifierBuilder status
     */
    ModifierBuilder addPlayedCardBound(Predicate<Set<PlayableCard>> condition);

    /**
     * Builds the modifier.
     * @return modifier
     */
    Modifier build();
}
