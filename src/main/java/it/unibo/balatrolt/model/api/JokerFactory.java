package it.unibo.balatrolt.model.api;

/**
 * A {@link Joker} factory.
 */
public interface JokerFactory {
    /**
     * Creates a {@link Joker} with a name and a description, but no effect.
     * @param name Joker's name
     * @param description Joker's description
     * @return joker without effect.
     */
    Joker standardJoker(String name, String description);

    /**
     * Creates a {@link Joker} with a effect.
     * @param name Joker's name
     * @param description Joker's description
     * @param modifier Joker's effect
     * @return joker with a modifier
     */
    Joker withModifier(String name, String description, Modifier modifier);

    /**
     * Creates a {@link Joker} by merging two exixting jokers.
     * @param newName new name to assign to the merged jokers
     * @param newDescription new description to assigned to the merged jokers
     * @param j1 first Joker
     * @param j2 second Joker
     * @return a joker with a modifier which is the combination of the two.
     */
    Joker merge(String newName, String newDescription, Joker j1, Joker j2);
}
