package it.unibo.balatrolt.view.impl;

import java.util.Set;

import com.google.common.base.Optional;

import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;
import it.unibo.balatrolt.view.api.ShopInnerLogic;

public class ShopInnerLogicImpl implements ShopInnerLogic {
    // private Set<SpecialCardInfo> toSell = Set.of();
    private Optional<SpecialCardInfo> selected = Optional.absent();

    @Override
    public void setCardsToSell(Set<SpecialCardInfo> toSell) {
        //this.toSell = toSell;
    }

    @Override
    public boolean isCardSelected() {
        return selected.isPresent();
    }

    @Override
    public void hitCard(SpecialCardInfo specialCardInfo) {
        if(this.selected.equals(Optional.of(specialCardInfo))) {
            this.selected = Optional.absent();
        } else {
            this.selected = Optional.of(specialCardInfo);
        }
    }

    @Override
    public Optional<SpecialCardInfo> getSelectedCard() {
        return this.selected;
    }
}
