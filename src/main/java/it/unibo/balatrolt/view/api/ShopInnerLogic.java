package it.unibo.balatrolt.view.api;

import com.google.common.base.Optional;

import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;

/**
 * Represents the inner logic of the shop view.
 */
public interface ShopInnerLogic {
    /**
     * It returns if a card is selected.
     * @return true if a card is selected
     */
    boolean isCardSelected();

    /**
     * It handles the click on a card.
     * @param specialCardInfo clicked card
     */
    void hitCard(SpecialCardInfo specialCardInfo);

    /**
     * It returns the current selected card.
     * @return selected card. Optiona.absent() if not present
     */
    Optional<SpecialCardInfo> getSelectedCard();

    /**
     * Resets the shop logic.
     */
    void reset();
}
