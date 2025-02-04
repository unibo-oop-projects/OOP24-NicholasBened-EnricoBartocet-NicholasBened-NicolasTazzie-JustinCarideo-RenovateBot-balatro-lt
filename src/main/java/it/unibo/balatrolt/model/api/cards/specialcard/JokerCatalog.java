package it.unibo.balatrolt.model.api.cards.specialcard;

import java.util.List;

import com.google.common.base.Optional;

/**
 * It contains a catalog of {@link Joker}.
 */
public interface JokerCatalog {
    /**
     * It returns the list of jokers contained in the catalog.
     * @return joker list
     */
    List<Joker> getJokerList();

    /**
     * It returns a specified joker. Implementations should be case insensitive
     * @param name name of the joker
     * @return joker if present in the catalog, otherwise Optional.absent().
     */
    Optional<Joker> getJoker(String name);
}
