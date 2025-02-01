package it.unibo.balatrolt.controller.impl;

import java.util.List;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.controller.api.PlayerController;
import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;
import it.unibo.balatrolt.model.api.BuffedDeck;
import it.unibo.balatrolt.model.api.Player;
import it.unibo.balatrolt.model.api.PlayerStatus;
import it.unibo.balatrolt.model.api.cards.specialcard.SpecialCard;
import it.unibo.balatrolt.model.impl.PlayerImpl;
import it.unibo.balatrolt.model.impl.PlayerStatusImpl;

public class PlayerControllerImpl implements PlayerController {

    private final Player player;

    public PlayerControllerImpl(final BuffedDeck deck) {
        Preconditions.checkNotNull(deck);
        this.player = new PlayerImpl(deck);
    }

    @Override
    public List<SpecialCardInfo> getSpecialCards() {
        return this.player.getSpecialCardSlot()
            .stream()
            .map(this::getSpecialCardInfo)
            .toList();
    }

    @Override
    public PlayerStatus getPlayerStatus() {
        return new PlayerStatusImpl(this.player.getDeck(), this.player.getSpecialCardSlot(), this.player.getCurrency());
    }

    @Override
    public void addCurrency(int reward) {
        this.player.addCurrency(reward);
    }

    private SpecialCardInfo getSpecialCardInfo(final SpecialCard card) {
        return new SpecialCardInfo(card.getName(), card.getDescription(), card.getToSellValue());
    }
}
