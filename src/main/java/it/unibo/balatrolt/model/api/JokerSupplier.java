package it.unibo.balatrolt.model.api;

/**
 * It's used to retrieve a casual {@link Joker}.
 */
public interface JokerSupplier {
    /**
     * It returns a random {@link Joker}.
     * @return a Joker
     */
    Joker getRandom();
}
