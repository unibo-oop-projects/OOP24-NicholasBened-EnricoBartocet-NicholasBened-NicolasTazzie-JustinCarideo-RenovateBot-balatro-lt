package it.unibo.balatrolt.controller.api;

import java.util.List;

import it.unibo.balatrolt.controller.api.communication.DeckInfo;
import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;
import it.unibo.balatrolt.model.api.PlayerStatus;
import it.unibo.balatrolt.model.api.cards.specialcard.SpecialCard;


/**
 * Part of the controller that has to deal with the {@link it.unibo.balatrolt.model.api.Player}.
 */
public interface PlayerController {

    /**
     * Adds money to the player.
     * @param reward the amount of money to add
     */
    void addCurrency(int reward);

    /**
     * Remove some money to the player.
     * @param money the amount to decrement
     */
    void spendCurrency(int money);

    /**
     * Add a special card to the player.
     * @param card the card to add
     */
    void addSpecialCard(SpecialCard card);

    /**
     * Sell a special card.
     * @param card the card to sell
     */
    void sellSpecialCard(SpecialCardInfo card);

    /**
     * @return the list of the special cards of the Player
     */
    List<SpecialCardInfo> getSpecialCards();

    /**
     * @return the status of the player
     */
    PlayerStatus getPlayerStatus();

    /**
     * @return the maximum number of special cards that the player can have
     */
    int getMaxSpecialCards();

    /**
     * @return the amount of money that the player has
     */
    int getCurrency();

    /**
     * @return the Deck of the player
     */
    DeckInfo getDeck();
}
