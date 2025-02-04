package it.unibo.balatrolt.view.api;

import java.util.Set;

import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;

/**
 * It represents the shop view.
 */
public interface ShopView {
    /**
     * It updates the {@link SpecialCard} to sell.
     * @param toSell set of {@link SpecialCard} to sell
     * @throws NullPointerException if toSell is null
     */
    void updateCards(Set<SpecialCardInfo> toSell);

}
