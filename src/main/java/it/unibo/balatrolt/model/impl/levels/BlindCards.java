package it.unibo.balatrolt.model.impl.levels;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.Slot;
import it.unibo.balatrolt.model.impl.DeckImpl;
import it.unibo.balatrolt.model.impl.SlotImpl;

public class BlindCards {
    private static final int HAND_SIZE = 7;
    private List<PlayableCard> deck;
    private Slot handSlot;

    public BlindCards() {
        this.deck = new DeckImpl().getShuffledCards();
        this.handSlot = new SlotImpl(HAND_SIZE);
        for (int i = 0; i < HAND_SIZE; i++) {
            this.handSlot.addCard(deck.removeFirst());
        }
    }

    public List<PlayableCard> getRemainingDeckCards() {
        return List.copyOf(deck);
    }

    public List<PlayableCard> getHandCards() {
        return List.copyOf(handSlot.getCards().stream()
                .map(card -> (PlayableCard) card)
                .collect(Collectors.toList()));
    }

    public void discardCards(final List<PlayableCard> toDiscard) {
        Preconditions.checkNotNull(toDiscard);
        Preconditions.checkArgument(toDiscard.size() > 0, "You need to discard at least one card");
        for (final PlayableCard card : toDiscard) {
            deck.remove(card);
            handSlot.remove(card);
            handSlot.addCard(deck.removeFirst());
        }
    }
}
