package it.unibo.balatrolt.view.impl;

import com.google.common.base.Optional;

import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;
import it.unibo.balatrolt.view.api.ShopInnerLogic;

/**
 * Implementation of {@link ShopInnerLogic}.
 * @author Nicolas Tazzieri
 */
public final class ShopInnerLogicImpl implements ShopInnerLogic {
    private Optional<SpecialCardInfo> selected = Optional.absent();

    @Override
    public boolean isCardSelected() {
        return selected.isPresent();
    }

    @Override
    public void hitCard(final SpecialCardInfo specialCardInfo) {
        final Optional<SpecialCardInfo> toSelect = Optional.fromNullable(specialCardInfo);
        if (this.selected.equals(toSelect)) {
            this.selected = Optional.absent();
        } else {
            this.selected = toSelect;
        }
    }

    @Override
    public Optional<SpecialCardInfo> getSelectedCard() {
        return this.selected;
    }

    @Override
    public void reset() {
        this.selected = Optional.absent();
    }
}
