package it.unibo.balatrolt.view.api;

import java.util.Set;

import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;

public interface ShopView {

    void updateCards(Set<SpecialCardInfo> toSell);

}