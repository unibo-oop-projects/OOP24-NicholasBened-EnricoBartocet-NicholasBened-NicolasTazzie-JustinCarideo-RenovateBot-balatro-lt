package it.unibo.balatrolt.model.impl;

import java.util.Optional;

import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.PlayableCard;

public class PlayableCardImpl implements PlayableCard {

    private final Pair<Rank, Suit> card;

    public PlayableCardImpl(final Pair<Rank, Suit> card) {
        this.card = card;
    }

    @Override
    public Rank getRank() {
        return this.card.get1();
    }

    @Override
    public Suit getSuit() {
        return this.card.get2();
    }

    @Override
    public Optional<Modifier> getModifier() {
        return Optional.empty();
    }
}
