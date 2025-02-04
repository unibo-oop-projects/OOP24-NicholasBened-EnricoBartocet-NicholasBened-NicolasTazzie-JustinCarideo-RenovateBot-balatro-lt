package it.unibo.balatrolt.model.api;

import java.util.List;

import it.unibo.balatrolt.model.api.cards.specialcard.SpecialCard;

/**
 * Represents a player in the game.
 */
public interface Player {

    /**
     * Returns the player's deck.
     * @return the player's deck
     */
    BuffedDeck getDeck();

    /**
     * Returns the player's special cards.
     * @return a list containing the player's special cards
     */
    List<SpecialCard> getSpecialCardSlot();

    /**
     * Adds a special card to the player's special card slot.
     * @param card to add
     */
    void addSpecialCard(SpecialCard card);

    /**
     * Adds money to the player's total wealth.
     * @param value the amount of money to add
     */
    void addCurrency(int value);

    void spendCurrency(int money);

    /**
     * Returns the player's money.
     * @return the player's money
     */
    int getCurrency();

    /**
     * Returns the status of the Player.
     * @return the player's status
     */
    PlayerStatus getStatus();

    int getMaxSpecialCards();

    void sellSpecialCard(SpecialCard specialCard);
}
