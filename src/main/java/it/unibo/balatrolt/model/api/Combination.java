package it.unibo.balatrolt.model.api;

/*
 * Interface that models the Combination played.
 * Essentially provides some utilities methods and
 * models internally the combination played, applying
 * modifiers passed and getting the result converted into
 * Chip
 */
public interface Combination {
    
    /**
     * @return the current multiplier applied
     */
    Multiplier getMultiplier();

    /**
     * @return the current basepoints reached
     */
    BasePoint getBasePoints();

    /**
     * applies the modifier passed by input
     * @param mod to apply
     */
    void applyModifier(Modifier mod);

    /**
     * @return the result in chips
     */
    Chip getChips();
}
