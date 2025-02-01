package it.unibo.balatrolt.controller.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.controller.api.LevelsController;
import it.unibo.balatrolt.controller.api.communication.AnteInfo;
import it.unibo.balatrolt.controller.api.communication.BlindInfo;
import it.unibo.balatrolt.controller.api.communication.BlindStats;
import it.unibo.balatrolt.controller.api.communication.PlayableCardInfo;
import it.unibo.balatrolt.controller.api.communication.RoundStatus;
import it.unibo.balatrolt.model.api.BuffedDeck;
import it.unibo.balatrolt.model.api.PlayerStatus;
import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.levels.Ante;
import it.unibo.balatrolt.model.api.levels.Blind;
import it.unibo.balatrolt.model.impl.levels.AnteFactoryImpl;

public class LevelsControllerImpl implements LevelsController {
    private static final int NUM_ANTE = 8;
    private static final int NUM_BLINDS = 3;
    private static final BinaryOperator<Integer> BASE_CHIP_CALCULATOR = (a, b) -> a * 150 + b * 10;
    private static final UnaryOperator<Integer> REWARD_CALCULATOR = b -> b + 4;

    private final List<Ante> anteList;
    private final Map<PlayableCardInfo, PlayableCard> cardsTranslator = new HashMap<>();
    private int currAnte;

    /**
     * Constructor of the levels controller.
     */
    public LevelsControllerImpl(final BuffedDeck deck) {
        Preconditions.checkNotNull(deck);
        this.anteList = new AnteFactoryImpl(NUM_BLINDS, BASE_CHIP_CALCULATOR, REWARD_CALCULATOR, deck.getModifier())
            .generateList(NUM_ANTE);
        this.currAnte = 0;
        deck.getCards().forEach(c -> cardsTranslator.put(new PlayableCardInfo(c.getRank().name(), c.getSuit().name()), c));
    }

    @Override
    public AnteInfo getCurrentAnte() {
        return new AnteInfo(
            this.currentAnte().getAnteNumber(),
            this.currentAnte().getBlinds().stream().map(this::getBlindInfo).toList(),
            this.currentBlind().getBlindNumber()
        );
    }

    @Override
    public BlindInfo getCurrentBlindInfo() {
        return this.getBlindInfo(this.currentBlind());
    }

    @Override
    public BlindStats getCurrentBlindStats() {
        return new BlindStats(this.currentBlind().getCurrentChips(), this.currentBlind().getRemainingHands(), this.currentBlind().getRemainingDiscards());
    }

    @Override
    public List<PlayableCardInfo> getHand() {
        return this.currentBlind().getHandCards()
            .stream()
            .map(c -> new PlayableCardInfo(c.getRank().name(), c.getSuit().name()))
            .toList();
    }

    @Override
    public void playCards(final List<PlayableCardInfo> cards, final PlayerStatus player) {
        this.currentBlind().playHand(cards.stream().map(cardsTranslator::get).toList(), player);
    }

    @Override
    public void discardCards(List<PlayableCardInfo> cards) {
        this.currentBlind().discardPlayableCards(cards.stream().map(cardsTranslator::get).toList());
    }

    @Override
    public RoundStatus getRoundStatus() {
        return switch(this.currentBlind().getStatus()) {
            case DEFEATED -> RoundStatus.BLIND_DEFEATED;
            case GAME_OVER -> RoundStatus.BLIND_WON;
            case IN_GAME -> RoundStatus.IN_GAME;
        };
    }

    @Override
    public void updateAnte() {
        if (this.currentAnte().isOver()) {
            this.currAnte++;
        } else if (this.currentBlind().getStatus().equals(Blind.Status.DEFEATED)) {
            this.currentAnte().nextBlind();
        }
    }

    @Override
    public boolean isOver() {
        return this.anteList.size() > this.currAnte;
    }

    private Ante currentAnte() {
        return this.anteList.get(this.currAnte);
    }

    private Blind currentBlind() {
        return this.currentAnte().getCurrentBlind().get();
    }

    private BlindInfo getBlindInfo(final Blind blind) {
        return new BlindInfo(blind.getBlindNumber(), blind.getMinimumChips(), blind.getReward());
    }
}
