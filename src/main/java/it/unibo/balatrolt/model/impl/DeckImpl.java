package it.unibo.balatrolt.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.balatrolt.model.api.Deck;
import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.PlayableCard.Suit;

/**
 * Implementation of a normal 52 card deck.
 * @author Benedetti Nicholas
 */
public class DeckImpl implements Deck {

    private final List<PlayableCard> deck;

    /**
     * Constructor that generates a 52 card deck.
     */
    public DeckImpl() {
        this.deck = new ArrayList<>();
        for (final Suit suit : Suit.values()) {
            for (final Rank rank : Rank.values()) {
                this.deck.add(new PlayableCardImpl(new Pair<>(rank, suit)));
            }
        }
    }

    @Override
    public final List<PlayableCard> getCards() {
        return List.copyOf(this.deck);
    }

    @Override
    public final List<PlayableCard> getShuffledCards() {
        final List<PlayableCard> shuffledCards = new ArrayList<>();
        shuffledCards.addAll(deck);
        Collections.shuffle(shuffledCards);
        return shuffledCards;
    }
}
