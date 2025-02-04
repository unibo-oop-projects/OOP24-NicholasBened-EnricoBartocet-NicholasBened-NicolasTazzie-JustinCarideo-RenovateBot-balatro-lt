package it.unibo.balatrolt.model.api.cards.specialcard;

/**
 * It's a {@link SpecialCard} which only provides a modifier.
 */
public interface Joker extends SpecialCard {
    /**
     * It returns the {@link JokerTier} of the Joker.
     * @return joker's tier.
     */
    JokerTier getTier();
}
