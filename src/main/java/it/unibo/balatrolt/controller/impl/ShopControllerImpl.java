package it.unibo.balatrolt.controller.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.google.common.base.Optional;

import it.unibo.balatrolt.controller.api.ShopController;
import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;
import it.unibo.balatrolt.model.api.Shop;
import it.unibo.balatrolt.model.api.cards.specialcard.SpecialCard;
import it.unibo.balatrolt.model.impl.shop.JokerShop;

/**
 * Implementation of {@link ShopController}.
 */
public final class ShopControllerImpl implements ShopController {
    private final Shop shop;
    private Map<SpecialCardInfo, SpecialCard> cardTranslator = Map.of();

    /**
     * Constructor.
     * @param shopSize shop size
     * @throws NullPointerException if shopSize is null
     */
    public ShopControllerImpl(final int shopSize) {
        this.shop = new JokerShop(checkNotNull(shopSize));
    }

    @Override
    public boolean buyCard(final SpecialCardInfo card, final int currentMoney) {
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
            Entry::getKey));
    }

    @Override
    public Optional<SpecialCard> translateCard(final SpecialCardInfo specialCard) {
        checkNotNull(specialCard);
        if (!this.cardTranslator.containsKey(specialCard)) {
            return Optional.absent();
        }
        return Optional.of(this.cardTranslator.get(specialCard));
    }
}
