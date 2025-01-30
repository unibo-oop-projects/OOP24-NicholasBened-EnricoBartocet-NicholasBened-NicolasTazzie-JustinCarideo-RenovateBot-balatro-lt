package it.unibo.balatrolt.model.impl;

import java.util.List;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.model.api.Deck;
import it.unibo.balatrolt.model.api.Player;
import it.unibo.balatrolt.model.api.Slot;
import it.unibo.balatrolt.model.api.SpecialCard;

/**
 * The implementation of the Player interface.
 */
public class PlayerImpl implements Player {
    private static final int SLOT_SIZE = 5;
    private final Deck deck;
    private final Slot specialCardSlot;
    private int currency;

    /**
     * Constructor for the Player.
     * @param deck the deck of the player.
     */
    public PlayerImpl(final Deck deck) {
        this.deck = deck;
        this.specialCardSlot = new SlotImpl(SLOT_SIZE);
    }

    @Override
    public Deck getDeck() {
        return this.deck;
    }

    @Override
    public List<SpecialCard> getSpecialCardSlot() {
        return this.specialCardSlot.getCards().stream()
                .map(SpecialCard.class::cast)
                .toList();
    }

    @Override
    public void addSpecialCard(SpecialCard card) {
        Preconditions.checkNotNull(card, "cannot add null card");
        this.specialCardSlot.addCard(card);
    }

    @Override
    public void addCurrency(int value) {
        Preconditions.checkArgument(value >= 0, "cannot add negative value");
        this.currency += value;
    }

    @Override
    public int getCurrency() {
        return this.currency;
    }
}
