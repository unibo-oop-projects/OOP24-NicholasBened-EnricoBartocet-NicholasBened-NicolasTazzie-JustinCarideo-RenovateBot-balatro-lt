package it.unibo.balatrolt.model.api.combination;

import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;

/**
 * Interface that models the Combination played.
 * Provides utility methods to apply modifiers and get the result in chips.
 */
public interface Combination {

    /**
     * Enum representing the different types of poker combinations.
     */
    enum CombinationType {
        /** High card combination. */
        HIGH_CARD,
        /** A pair combination. */
        PAIR,
        /** Two pairs combination. */
        TWO_PAIR,
        /** Three of a kind combination. */
        THREE_OF_A_KIND,
        /** Straight combination. */
        STRAIGHT,
        /** Flush combination. */
        FLUSH,
        /** Full house combination. */
        FULL_HOUSE,
        /** Four of a kind combination. */
        FOUR_OF_A_KIND,
        /** Straight flush combination. */
        STRAIGHT_FLUSH,
        /** Royal flush combination. */
        ROYAL_FLUSH
    }

    /**
     * Gets the current multiplier applied.
     * @return the multiplier
     */
    Multiplier getMultiplier();

    /**
     * Gets the current base points reached.
     * @return the base points
     */
    BasePoints getBasePoints();

    /**
     * Applies the specified modifier to the combination.
     * @param mod the modifier to be applied
     */
    void applyModifier(CombinationModifier mod);

    /**
     * Gets the type of combination.
     * @return the combination type
     */
    CombinationType getCombinationType();

    /**
     * Gets the result in chips.
     * @return the number of chips
     */
    int getChips();
}
