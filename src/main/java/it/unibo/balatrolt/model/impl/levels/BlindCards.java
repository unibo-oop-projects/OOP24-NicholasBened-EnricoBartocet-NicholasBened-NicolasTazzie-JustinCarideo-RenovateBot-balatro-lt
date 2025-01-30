package it.unibo.balatrolt.model.impl.levels;

import java.util.List;
import java.util.stream.Collectors;

import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.Slot;
import it.unibo.balatrolt.model.impl.DeckImpl;
import it.unibo.balatrolt.model.impl.SlotImpl;

public class BlindCards {
    private static final int HAND_SIZE = 7;
    private List<PlayableCard> deck;
    private Slot handSlot;

    public BlindCards() {
        this.deck = new DeckImpl().getDeck();
        this.handSlot = new SlotImpl(HAND_SIZE);
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
        for (final PlayableCard card : toDiscard) {
            deck.remove(card);
            handSlot.remove(card);
        }
    }
}
