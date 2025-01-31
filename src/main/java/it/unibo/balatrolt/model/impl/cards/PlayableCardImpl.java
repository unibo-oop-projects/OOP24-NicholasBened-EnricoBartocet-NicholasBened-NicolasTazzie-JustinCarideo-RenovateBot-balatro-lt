package it.unibo.balatrolt.model.impl.cards;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;
import it.unibo.balatrolt.model.impl.Pair;

/**
 * Implements a PlayableCard, made of a Rank and a Suit.
 * @author Benedetti Nicholas
 */
public final class PlayableCardImpl implements PlayableCard {

    private final Pair<Rank, Suit> card;
    private final Rank rank;
    private final Suit suit;

    /**
     * Building a PlayableCard.
     *
     * @param card
     */
    public PlayableCardImpl(final Pair<Rank, Suit> card) {
        this.card = card;
        this.rank = card.e1();
        this.suit = card.e2();
    }

    @Override
    public Rank getRank() {
        return this.card.e1();
    }

    @Override
    public Suit getSuit() {
        return this.card.e2();
    }

    @Override
    public Optional<CombinationModifier> getModifier() {
        return Optional.absent();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rank == null) ? 0 : rank.hashCode());
        result = prime * result + ((suit == null) ? 0 : suit.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PlayableCardImpl other = (PlayableCardImpl) obj;
        return (rank == other.rank) && (suit == other.suit);
    }
}
