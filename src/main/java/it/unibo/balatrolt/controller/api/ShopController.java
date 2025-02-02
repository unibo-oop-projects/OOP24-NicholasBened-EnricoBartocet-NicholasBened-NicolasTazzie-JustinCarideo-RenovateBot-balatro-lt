package it.unibo.balatrolt.controller.api;

import java.util.Map;

import it.unibo.balatrolt.model.api.cards.specialcard.SpecialCard;

/**
 * Controller that manages the interaction with the {@link Shop}.
 */
public interface ShopController {
    /**
     * Method that signals whether a card is bought or not.
     * @param card Card to buy.
     * @param currentMoney Current quantity of money.
     * @return Whether the card is bought or not.
     */
    boolean buyCard(SpecialCard card, int currentMoney);

    /**
     * It opens the {@link Shop}, supplying it with new cards.
     */
    void openShop();

    /**
     * Returns the {@link SpecialCard} sold in the shop and its value.
     * @return special cards sold in the shop
     */
    Map<SpecialCard, Integer> getCards();
}
