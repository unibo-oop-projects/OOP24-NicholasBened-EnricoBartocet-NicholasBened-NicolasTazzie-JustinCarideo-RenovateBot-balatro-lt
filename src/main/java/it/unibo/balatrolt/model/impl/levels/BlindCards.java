package it.unibo.balatrolt.model.impl.levels;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.model.api.Slot;
import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.impl.DeckImpl;
import it.unibo.balatrolt.model.impl.SlotImpl;

/**
 * Implementation of the blind which holds the
 * player's hand: creation and management.
 * @author Benedetti Nicholas
 */
public class BlindCards {
    private static final int HAND_SIZE = 7;
    private final List<PlayableCard> deck;
    private final Slot handSlot;

    /**
     * Sets all the fields.
     */
    public BlindCards() {
        this.deck = new DeckImpl().getShuffledCards();
        this.handSlot = new SlotImpl(HAND_SIZE);
        for (int i = 0; i < HAND_SIZE; i++) {
            this.handSlot.addCard(deck.removeFirst());
        }
    }

    /**
     * Returns the remaining cards in the deck.
     *
     * @return remaining cards in the deck.
     */
    public List<PlayableCard> getRemainingDeckCards() {
        return List.copyOf(deck);
    }

    /**
     * returns the cards holded by the player.
     *
     * @return the cards in the player's hand.
     */
    public List<PlayableCard> getHandCards() {
        return List.copyOf(handSlot.getCards().stream()
                .map(card -> (PlayableCard) card)
                .collect(Collectors.toList()));
    }

    /**
     * Discards the cards given into the player's hand.
     *
     * @param toDiscard card to discard.
     */
    public void discardCards(final List<PlayableCard> toDiscard) {
        Preconditions.checkNotNull(toDiscard);
        Preconditions.checkArgument(!toDiscard.isEmpty(), "You need to discard at least one card");
        for (final PlayableCard card : toDiscard) {
            deck.remove(card);
            handSlot.remove(card);
            handSlot.addCard(deck.removeFirst());
        }
    }
}
