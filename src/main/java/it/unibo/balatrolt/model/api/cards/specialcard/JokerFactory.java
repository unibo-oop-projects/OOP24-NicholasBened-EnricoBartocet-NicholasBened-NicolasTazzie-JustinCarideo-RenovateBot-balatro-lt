package it.unibo.balatrolt.model.api.cards.specialcard;

import java.util.Set;
import java.util.function.Predicate;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;

/**
 * A {@link Joker} factory.
 */
public interface JokerFactory {
    /**
     * Creates a {@link Joker} with a name, a description, a random price, but no effect.
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
     * @param basePrice Joker's price in the shop
     * @return joker with a modifier
     */
    Joker withModifier(String name, String description, int basePrice, CombinationModifier modifier);

    /**
     * Creates a {@link Joker} with a effect and a random price.
     * @param name Joker's name
     * @param description Joker's description
     * @param modifier Joker's effect
     * @return joker with a modifier
     */
    Joker withModifierAndRandomPrice(String name, String description, CombinationModifier modifier);

    /**
     * Creates a new {@link Joker} from an existing one, adding a playable card bound.
     * @param newName new Joker's name
     * @param newDescription new Joker's description
     * @param j joker which add bound
     * @param bound bound to add
     * @return new Joker with the old's effect and with the specified bound
     */
    Joker addPlayableCardBoundToJoker(
            String newName,
            String newDescription,
            Joker j,
            Predicate<Set<PlayableCard>> bound);

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
