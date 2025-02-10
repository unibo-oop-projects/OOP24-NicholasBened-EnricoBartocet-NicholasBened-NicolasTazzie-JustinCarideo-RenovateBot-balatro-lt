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
        /** Error card combination. */
        EMPTY_CARD("Empty Card"),
        /** High card combination. */
        HIGH_CARD("High Card"),
        /** A pair combination. */
        PAIR("Pair"),
        /** Two pairs combination. */
        TWO_PAIR("Two Pair"),
        /** Three of a kind combination. */
        THREE_OF_A_KIND("Three of a kind"),
        /** Straight combination. */
        STRAIGHT("Straight"),
        /** Flush combination. */
        FLUSH("Flush"),
        /** Full house combination. */
        FULL_HOUSE("Full House"),
        /** Four of a kind combination. */
        FOUR_OF_A_KIND("Four of a kind"),
        /** Straight flush combination. */
        STRAIGHT_FLUSH("Straight Flush"),
        /** Royal flush combination. */
        ROYAL_FLUSH("Royal Flush");

        private final String name;

        CombinationType(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
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
