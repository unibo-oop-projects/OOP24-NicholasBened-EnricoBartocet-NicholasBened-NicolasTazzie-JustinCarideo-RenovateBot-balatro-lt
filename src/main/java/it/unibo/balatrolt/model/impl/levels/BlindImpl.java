package it.unibo.balatrolt.model.impl.levels;

import java.util.List;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.combination.PlayedHand;
import it.unibo.balatrolt.model.api.levels.Blind;
import it.unibo.balatrolt.model.api.levels.BlindModifier;
import it.unibo.balatrolt.model.impl.combination.PlayedHandImpl;

/**
 * An implementation for the {@link Blind} interface.
 * @author Enrico Bartocetti
 */
public final class BlindImpl implements Blind {
    private final BlindConfiguration config;
    private final BlindStats statistics;
    private final BlindCards cardsManager;

    /**
     * Instance a new BlindImpl starting from the configuration.
     * @param config the configuration for the Blind
     * @param blindModifier the modifier that tells how to change the statistics of the Blind
     */
    public BlindImpl(final BlindConfiguration config, final BlindModifier blindModifier) {
        this.config = Preconditions.checkNotNull(config);
        this.cardsManager = new BlindCards();
        this.statistics = new BlindStats(Preconditions.checkNotNull(blindModifier));
    }

    @Override
    public int getBlindNumber() {
        return this.config.id();
    }

    @Override
    public int getMinimumChips() {
        return this.config.baseChip();
    }

    @Override
    public int getCurrentChips() {
        return this.statistics.getCurrentChips();
    }

    @Override
    public Status getStatus() {
        if (this.getRemainingHands() > 0) {
            return Status.IN_GAME;
        }
        if (this.getCurrentChips() >= this.getMinimumChips()) {
            return Status.DEFEATED;
        }
        return Status.GAME_OVER;
    }

    @Override
    public int getReward() {
        return this.config.reward();
    }

    @Override
    public List<PlayableCard> getRemainingDeckCards() {
        return this.cardsManager.getRemainingDeckCards();
    }

    @Override
    public List<PlayableCard> getHandCards() {
        return this.cardsManager.getHandCards();
    }

    @Override
    public void playHand(final List<PlayableCard> toPlay) {
        final PlayedHand hand = new PlayedHandImpl(toPlay);
        if (this.cardsInHand(hand.getCards())) {
            if (this.statistics.getRemainingHands() > 0) {
                this.cardsManager.discardCards(hand.getCards());
                this.statistics.decrementHands();
                this.statistics.incrementChips(hand.evaluateCombination().getChips());
            } else {
                throw new IllegalStateException("There aren't hands left");
            }
        }
    }

    @Override
    public void discardPlayableCards(final List<PlayableCard> toDiscard) {
        if (this.cardsInHand(toDiscard)) {
            if (this.statistics.getRemainingDiscards() > 0) {
                this.cardsManager.discardCards(toDiscard);
                this.statistics.decrementDiscards();
            } else {
                throw new IllegalStateException("There aren't discards left");
            }
        }
    }

    @Override
    public int getRemainingHands() {
        return this.statistics.getRemainingHands();
    }

    @Override
    public int getRemainingDiscards() {
        return this.statistics.getRemainingDiscards();
    }

    private boolean cardsInHand(final List<PlayableCard> cards) {
        return cards.stream().allMatch(c -> this.cardsManager.getHandCards().contains(c));
    }
}
