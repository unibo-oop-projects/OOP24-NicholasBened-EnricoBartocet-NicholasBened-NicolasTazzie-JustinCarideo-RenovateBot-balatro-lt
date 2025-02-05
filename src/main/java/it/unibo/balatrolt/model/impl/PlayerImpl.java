package it.unibo.balatrolt.model.impl;

import java.util.List;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.model.api.BuffedDeck;
import it.unibo.balatrolt.model.api.Player;
import it.unibo.balatrolt.model.api.PlayerStatus;
import it.unibo.balatrolt.model.api.Slot;
import it.unibo.balatrolt.model.api.cards.specialcard.SpecialCard;
import it.unibo.balatrolt.model.impl.cards.SlotImpl;

/**
 * The implementation of the Player interface.
 */
public final class PlayerImpl implements Player {
    private static final int SLOT_SIZE = 5;
    private final BuffedDeck deck;
    private final Slot specialCardSlot;
    private int currency;

    /**
     * Constructor for the Player.
     * @param deck the deck of the player.
     */
    public PlayerImpl(final BuffedDeck deck) {
        this.deck = deck;
        this.specialCardSlot = new SlotImpl(SLOT_SIZE);
    }

    @Override
    public BuffedDeck getDeck() {
        return this.deck;
    }

    @Override
    public List<SpecialCard> getSpecialCardSlot() {
        return this.specialCardSlot.getCards().stream()
                .map(SpecialCard.class::cast)
                .toList();
    }

    @Override
    public void addSpecialCard(final SpecialCard card) {
        Preconditions.checkNotNull(card, "cannot add null card");
        this.specialCardSlot.addCard(card);
    }

    @Override
    public void addCurrency(final int value) {
        Preconditions.checkArgument(value >= 0, "cannot add negative value");
        this.currency += value;
    }

    @Override
    public void spendCurrency(final int money) {
        Preconditions.checkArgument(money > 0, "cannot remove negative value");
        Preconditions.checkState(this.currency - money >= 0, "Insufficient currency");
        this.currency -= money;
    }

    @Override
    public int getCurrency() {
        return this.currency;
    }

    @Override
    public PlayerStatus getStatus() {
        return new PlayerStatusImpl(deck, getSpecialCardSlot(), currency);
    }

    @Override
    public int getMaxSpecialCards() {
        return SLOT_SIZE;
    }

    @Override
    public void sellSpecialCard(final SpecialCard specialCard) {
        Preconditions.checkNotNull(specialCard, "Cannot sell null card");
        specialCardSlot.remove(specialCard);
        addCurrency(specialCard.getToSellValue());
    }

}
