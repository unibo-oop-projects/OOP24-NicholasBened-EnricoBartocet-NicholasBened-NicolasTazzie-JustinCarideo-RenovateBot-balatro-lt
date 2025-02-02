package it.unibo.balatrolt.model.api;

import java.util.Map;

import it.unibo.balatrolt.model.api.cards.specialcard.SpecialCard;

/**
 * Interface that models the concept of a shop. It's used to buy the special cards
 * used by the player to power-up combinations.
 * NOTE: when a card is bought, it will be taken off the shop.
 */
public interface Shop {
    /**
     * @return a map containing all sellable {@link SpecialCards} and how much does
     * it cost
     */
    Map<SpecialCard, Integer> getSellableSpecialCards();

    /**
     * It buys a card from the shop. If it doesn't extist or it cannot be afford, then false is returned and 
     * the card is not taken off from the shop.
     * @param toBuy card to buy
     * @param money current quantity of money
     * @return true if the card is bought and taken off the shop, false otherwise
     */
    boolean buy(SpecialCard toBuy, int money);

    /**
     * Supplies the shop with brand new SpecialCards.
     */
    void supply();

    /**
     * It resets the shop.
     */
    void reset();
}
