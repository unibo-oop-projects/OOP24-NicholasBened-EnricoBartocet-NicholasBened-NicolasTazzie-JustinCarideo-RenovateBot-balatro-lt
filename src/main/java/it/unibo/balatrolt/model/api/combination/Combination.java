package it.unibo.balatrolt.model.api.combination;

import it.unibo.balatrolt.model.api.Modifier;

/*
 * Interface that models the Combination played.
 * Essentially provides some utilities methods and
 * models internally the combination played, applying
 * modifiers passed and getting the result converted into
 * Chip.
 */
public interface Combination {

    /**
     * Enum for combination type.
     */
    enum CombinationType {
        HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND,
        STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND,
        STRAIGHT_FLUSH, ROYAL_FLUSH
    }

    /**
     * @return the current multiplier applied
     */
    Multiplier getMultiplier();

    /**
     * @return the current basepoints reached
     */
    BasePoints getBasePoints();

    /**
     * applies the modifier passed by input.
     * @param mod to apply
     */
    void applyModifier(Modifier mod);

    /**
     * @return the combination type
     */
    CombinationType getCombinationType();

    /**
     * @return the result in chips
     */
    int getChips();
}
