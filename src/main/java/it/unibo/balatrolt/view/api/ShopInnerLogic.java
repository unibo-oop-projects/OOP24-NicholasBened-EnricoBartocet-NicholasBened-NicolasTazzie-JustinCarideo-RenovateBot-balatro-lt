package it.unibo.balatrolt.view.api;

import java.util.Set;

import com.google.common.base.Optional;

import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;

public interface ShopInnerLogic {

    void setCardsToSell(Set<SpecialCardInfo> toSell);

    boolean isCardSelected();

    void hitCard(SpecialCardInfo specialCardInfo);

    Optional<SpecialCardInfo> getSelectedCard();
}
