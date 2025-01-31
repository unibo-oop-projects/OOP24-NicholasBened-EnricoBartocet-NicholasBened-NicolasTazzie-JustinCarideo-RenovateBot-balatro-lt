package it.unibo.balatrolt.model.impl;

import java.util.List;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.model.api.BuffedDeck;
import it.unibo.balatrolt.model.api.PlayerStatus;
import it.unibo.balatrolt.model.api.cards.specialcard.SpecialCard;

/**
 * An immutable implementation of {@link PlayerStatus}.
 */
public final class PlayerStatusImpl implements PlayerStatus {

    private final BuffedDeck deck;
    private final List<SpecialCard> specialCards;
    private final int currency;

    /**
     * Creates a new PlayerStatus.
     * @param deck the deck used by the player
     * @param specialCards the list of special cards of the player
     * @param currency the currency owned by the player
     */
    public PlayerStatusImpl(final BuffedDeck deck, final List<SpecialCard> specialCards, final int currency) {
        this.deck = Preconditions.checkNotNull(deck);
        this.specialCards = List.copyOf(specialCards);
        this.currency = currency;
    }

    @Override
    public BuffedDeck deck() {
        return this.deck;
    }

    @Override
    public List<SpecialCard> specialCards() {
        return this.specialCards;
    }

    @Override
    public int currency() {
        return this.currency;
    }

}
