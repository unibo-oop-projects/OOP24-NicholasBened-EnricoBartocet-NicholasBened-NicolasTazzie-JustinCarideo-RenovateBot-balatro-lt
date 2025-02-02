package it.unibo.balatrolt.controller.api;

import java.util.Set;

import com.google.common.base.Optional;

import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;
import it.unibo.balatrolt.model.api.Shop;
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
    boolean buyCard(SpecialCardInfo card, int currentMoney);

    /**
     * It opens the {@link Shop}, supplying it with new cards.
     */
    void openShop();

    /**
     * Returns the {@link SpecialCardInfo} sold in the shop.
     * @return special cards sold in the shop
     */
    Set<SpecialCardInfo> getCards();

    /**
     * Translates the {@link SpecialCardInfo} into a real {@link SpecialCard}
     * if present in the shop.
     * @return the translated card if present in the shop.
     */
    Optional<SpecialCard> translateCard(SpecialCardInfo specialCard);
}
