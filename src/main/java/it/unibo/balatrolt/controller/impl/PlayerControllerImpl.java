package it.unibo.balatrolt.controller.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.controller.api.PlayerController;
import it.unibo.balatrolt.controller.api.communication.DeckInfo;
import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;
import it.unibo.balatrolt.model.api.BuffedDeck;
import it.unibo.balatrolt.model.api.Player;
import it.unibo.balatrolt.model.api.PlayerStatus;
import it.unibo.balatrolt.model.api.cards.specialcard.SpecialCard;
import it.unibo.balatrolt.model.impl.PlayerImpl;
import it.unibo.balatrolt.model.impl.PlayerStatusImpl;

/**
 * An implementation of the {@link PlayerController}.
 */
public final class PlayerControllerImpl implements PlayerController {

    private final Player player;
    private final Map<SpecialCardInfo, SpecialCard> specialCardTranslator = new HashMap<>();

    /**
     * Create a new PlayerController.
     * @param deck the deck choosen by the player
     */
    public PlayerControllerImpl(final BuffedDeck deck) {
        Preconditions.checkNotNull(deck);
        this.player = new PlayerImpl(deck);
    }

    @Override
    public void addCurrency(final int reward) {
        this.player.addCurrency(reward);
    }

    @Override
    public void spendCurrency(final int money) {
        this.player.spendCurrency(money);
    }

    @Override
    public void addSpecialCard(final SpecialCard card) {
        this.player.addSpecialCard(card);
        this.specialCardTranslator.put(getSpecialCardInfo(card), card);
    }

    @Override
    public void sellSpecialCard(final SpecialCardInfo card) {
        this.player.sellSpecialCard(specialCardTranslator.get(card));
    }

    @Override
    public List<SpecialCardInfo> getSpecialCards() {
        return this.player.getSpecialCardSlot()
            .stream()
            .map(this::getSpecialCardInfo)
            .toList();
    }

    @Override
    public int getCurrency() {
        return this.player.getCurrency();
    }

    @Override
    public PlayerStatus getPlayerStatus() {
        return new PlayerStatusImpl(this.player.getDeck(), this.player.getSpecialCardSlot(), this.player.getCurrency());
    }

    @Override
    public int getMaxSpecialCards() {
        return this.player.getMaxSpecialCards();
    }

    @Override
    public DeckInfo getDeck() {
        return new DeckInfo(this.player.getDeck().getName(), this.player.getDeck().getDescription());
    }

    private SpecialCardInfo getSpecialCardInfo(final SpecialCard card) {
        return new SpecialCardInfo(card.getName(), card.getDescription(), card.getToSellValue());
    }
}
