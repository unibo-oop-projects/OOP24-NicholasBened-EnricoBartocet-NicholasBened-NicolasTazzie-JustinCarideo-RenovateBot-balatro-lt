package it.unibo.balatrolt.model.api;

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
        HIGHCARD, PAIR, TWOPAIR, THREEOFAKIND,
        STRAIGHT, FLUSH, FULLHOUSE, FOUROFAKIND,
        STRAIGHTFLUSH, ROYALFLUSH
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
