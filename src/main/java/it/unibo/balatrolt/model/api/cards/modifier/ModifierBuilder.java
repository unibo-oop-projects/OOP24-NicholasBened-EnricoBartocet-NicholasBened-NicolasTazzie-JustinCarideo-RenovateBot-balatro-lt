package it.unibo.balatrolt.model.api.cards.modifier;

import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.combination.Combination.CombinationType;

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
     * Merges existing modifiers.
     * If it's called more than once, then the function application
     * follows the method calling order:
     *
     * e.g. defined:
     * f: m1 functions
     * g: m2 functions
     * h: m3 functions
     * Builder.merge(m1).merge(m2).merge(m3).build() returns a modifier that returns a function
     * i = h(g(f(x))).
     *
     * If other decorators are added to the builer (i.e. condition or multiplier/basePoints functions)
     * then these are all applied in the end, no matter of which order those are put in the chain call.
     *
     * @param toMerge modifier to merge
     * @return current modifierBuilder status
     */
    ModifierBuilder merge(CombinationModifier toMerge);

    /**
     * Builds the modifier.
     *
     * If it's merged with an existing modifier and decorators are added (i.e. condition or multiplier/basePoints functions)
     * then these are all applied in the end, no matter of which order those are put in the chain call.
     * To see how the merge works, refer to its documentation.
     * @return modifier
     */
    CombinationModifier build();
}
