package it.unibo.balatrolt.model.api.cards.specialcard;

import java.util.List;

/**
 * It's used to retrieve a casual {@link Joker}.
 */
public interface JokerSupplier {
    /**
     * It returns a random {@link Joker}.
     * @return a Joker
     */
    Joker getRandom();

    /**
     * It returns the list of all {@link Joker}.
     * @return the list of Jokers
     */
    List<Joker> getJokerList();
}
