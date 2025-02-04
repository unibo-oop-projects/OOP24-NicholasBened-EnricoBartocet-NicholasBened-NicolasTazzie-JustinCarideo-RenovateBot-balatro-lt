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

/**
 * An implementation of the {@link LevelsController}.
 */
public final class LevelsControllerImpl implements LevelsController {
    private static final int NUM_ANTE = 7;
    private static final int NUM_BLINDS = 3;
    private static final int ANTE_MULTIPLIER = 150;
    private static final int ANTE_EXP = 3;
    private static final int BLIND_EXP = 2;
    private static final int BLIND_MULTIPLIER = 10;
    private static final UnaryOperator<Integer> REWARD_CALCULATOR = b -> b + 4;
    private static final BinaryOperator<Integer> BASE_CHIP_CALCULATOR = (a, b) -> {
        return (int) Math.pow(a + 1, ANTE_EXP) * ANTE_MULTIPLIER + (int) Math.pow(b + 1, BLIND_EXP) * BLIND_MULTIPLIER;
    };

    private final List<Ante> anteList;
    private final Map<PlayableCardInfo, PlayableCard> cardsTranslator = new HashMap<>();
    private int currentAnteId;

    /**
     * Constructor of the levels controller.
     * @param deck the Deck choosen by the player.
     */
    public LevelsControllerImpl(final BuffedDeck deck) {
        Preconditions.checkNotNull(deck);
        final var anteFacroty = new AnteFactoryImpl(NUM_BLINDS, BASE_CHIP_CALCULATOR, REWARD_CALCULATOR, deck.getModifier());
        this.anteList = anteFacroty.generateList(NUM_ANTE);
        this.currentAnteId = 0;
        deck.getCards().forEach(c -> cardsTranslator.put(new PlayableCardInfo(c.getRank().name(), c.getSuit().name()), c));
    }

    @Override
    public AnteInfo getCurrentAnte() {
        return new AnteInfo(
            this.currentAnte().getAnteNumber() + 1,
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
        return new BlindStats(
            this.currentBlind().getCurrentChips(),
            this.currentBlind().getRemainingHands(),
            this.currentBlind().getRemainingDiscards()
        );
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
        this.currentBlind().playHand(this.translatePlayableCard(cards), player);
    }

    @Override
    public void discardCards(final List<PlayableCardInfo> cards) {
        this.currentBlind().discardPlayableCards(this.translatePlayableCard(cards));
    }

    @Override
    public RoundStatus getRoundStatus() {
        return switch (this.currentBlind().getStatus()) {
            case DEFEATED -> RoundStatus.BLIND_DEFEATED;
            case GAME_OVER -> RoundStatus.BLIND_WON;
            case IN_GAME -> RoundStatus.IN_GAME;
        };
    }

    @Override
    public void updateAnte() {
        if (this.currentAnte().isOver()) {
            this.currentAnteId++;
        } else if (this.currentBlind().getStatus().equals(Blind.Status.DEFEATED)) {
            this.currentAnte().nextBlind();
        }
    }

    @Override
    public int getNumAnte() {
        return NUM_ANTE;
    }

    @Override
    public List<PlayableCard> translatePlayableCard(final List<PlayableCardInfo> cards) {
        return cards.stream()
            .map(cardsTranslator::get)
            .toList();
    }

    @Override
    public boolean isOver() {
        return this.currentAnte().isOver() && (this.currentAnteId + 1 >= NUM_ANTE);
    }

    private Ante currentAnte() {
        return this.anteList.get(this.currentAnteId);
    }

    private Blind currentBlind() {
        return this.currentAnte().getCurrentBlind();
    }

    private BlindInfo getBlindInfo(final Blind blind) {
        return new BlindInfo(blind.getBlindNumber() + 1, blind.getMinimumChips(), blind.getReward());
    }

}
