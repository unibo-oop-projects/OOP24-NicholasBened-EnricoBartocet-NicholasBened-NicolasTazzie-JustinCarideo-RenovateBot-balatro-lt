package it.unibo.balatrolt.controller.impl;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Optional;

import it.unibo.balatrolt.controller.api.ShopController;
import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;
import it.unibo.balatrolt.model.api.Shop;
import it.unibo.balatrolt.model.api.cards.specialcard.SpecialCard;
import it.unibo.balatrolt.model.impl.shop.JokerShop;

public class ShopControllerImpl implements ShopController {
    private final Shop shop;
    private Map<SpecialCardInfo, SpecialCard> cardTranslator = Map.of();

    public ShopControllerImpl(int shopSize) {
        this.shop = new JokerShop(shopSize);
    }

    @Override
    public boolean buyCard(SpecialCardInfo card, int currentMoney) {
        return this.shop.buy(this.cardTranslator.get(card), currentMoney);
    }

    @Override
    public void openShop() {
        this.shop.reset();
        this.shop.supply();
        this.buildTransaltor();
    }

    @Override
    public Set<SpecialCardInfo> getCards() {
        this.buildTransaltor();
        return this.cardTranslator.keySet();
    }

    private void buildTransaltor() {
        this.cardTranslator = this.shop.getSellableSpecialCards()
        .entrySet()
        .stream()
        .collect(Collectors.toMap(
            e -> new SpecialCardInfo(
            e.getKey().getName(),
            e.getKey().getDescription(),
             e.getValue()),
            e -> e.getKey()));
    }

    @Override
    public Optional<SpecialCard> translateCard(SpecialCardInfo specialCard) {
        if(!this.cardTranslator.containsKey(specialCard)) {
            return Optional.absent();
        }
        return Optional.of(this.cardTranslator.get(specialCard));
    }
}
