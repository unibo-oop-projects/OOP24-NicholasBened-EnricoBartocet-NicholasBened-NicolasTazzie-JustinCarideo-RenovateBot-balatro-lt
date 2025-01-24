package it.unibo.balatrolt.model.impl;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.Card;
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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PlayableCardImpl other = (PlayableCardImpl) obj;
        if (card == null) {
            if (other.card != null)
                return false;
        } else if (!card.equals(other.card))
            return false;
        return true;
    }
}
