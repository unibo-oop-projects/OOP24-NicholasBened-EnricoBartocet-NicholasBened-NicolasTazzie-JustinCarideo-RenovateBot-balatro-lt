package it.unibo.balatrolt.controller.impl;

import java.util.Map;

import it.unibo.balatrolt.controller.api.ShopController;
import it.unibo.balatrolt.model.api.Shop;
import it.unibo.balatrolt.model.api.cards.specialcard.SpecialCard;
import it.unibo.balatrolt.model.impl.shop.JokerShop;

public class ShopControllerImpl implements ShopController {
    private final Shop shop;

    public ShopControllerImpl(int shopSize) {
        this.shop = new JokerShop(shopSize);
    }

    @Override
    public boolean buyCard(SpecialCard card, int currentMoney) {
        return this.shop.buy(card, currentMoney);
    }

    public void openShop() {
        this.shop.supply();
    }

    @Override
    public Map<SpecialCard, Integer> getCards() {
        return this.shop.getSellableSpecialCards();
    }
}
