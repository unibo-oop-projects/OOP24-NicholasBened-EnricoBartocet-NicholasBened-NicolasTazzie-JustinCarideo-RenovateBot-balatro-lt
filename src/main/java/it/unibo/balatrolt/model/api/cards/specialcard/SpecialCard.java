package it.unibo.balatrolt.model.api.cards.specialcard;

import it.unibo.balatrolt.model.api.cards.Card;

/**
 * It models the concept of a SpecialCard.
 * It has a name and a descripion. Extension of this interface should specify its behaviour.
 */
public interface SpecialCard extends Card {
    /**
     * @return card name.
     */
    String getName();

    /**
     * @return a description of what the special card does.
     */
    String getDescription();

    /**
     * @return the price shown in the shop.
     */
    int getShopPrice();

    /**
     * @return the value of which the card can be sold.
     */
    int getToSellValue();
}
