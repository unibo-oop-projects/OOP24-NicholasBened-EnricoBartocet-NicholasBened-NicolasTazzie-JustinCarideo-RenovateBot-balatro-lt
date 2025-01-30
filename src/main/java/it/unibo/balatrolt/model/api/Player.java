package it.unibo.balatrolt.model.api;

import java.util.List;

/**
 * Represents a player in the game.
 */
public interface Player {

    /**
     * Returns the player's deck.
     * @return the player's deck
     */
    Deck getDeck();

    /**
     * Returns the player's special cards.
     * @return
     */
    List<SpecialCard> getSpecialCardSlot();

    /**
     * Adds a special card to the player's special card slot.
     * @param card to add.
     */
    void addSpecialCard(final SpecialCard card);

    /**
     * Adds money to the player's total wealth.
     * @param value the amount of money to add.
     */
    void addCurrency(final int value);

    /**
     * Returns the player's money.
     * @return the player's money.
     */
    int getCurrency();
}
