package it.unibo.balatrolt.model.impl.levels;

import java.util.List;

import it.unibo.balatrolt.model.api.DeckModifier;
import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.combination.PlayedHand;
import it.unibo.balatrolt.model.api.levels.Blind;

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
     */
    public BlindImpl(final BlindConfiguration config, final DeckModifier deckModifier) {
        this.config = config;
        this.cardsManager = new BlindCards();
        this.statistics = new BlindStats(deckModifier);
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
    public void incrementChips(final int handChips) {
        this.statistics.incrementChips(handChips);
    }

    @Override
    public boolean isOver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isOver'");
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
    public void playHand(PlayedHand hand) {
        if (this.cardsInHand(hand.getCards())) {
            if (this.statistics.getRemainingHands() > 0) {
                this.cardsManager.discardCards(hand.getCards());
                this.statistics.decrementHands();
                this.statistics.incrementChips(hand.evaluateCombination().getChips());
            }
        }
    }

    @Override
    public void discardPlayableCards(List<PlayableCard> toDiscard) {
        if (this.cardsInHand(toDiscard)) {
            if (this.statistics.getRemainingDiscards() > 0) {
                this.cardsManager.discardCards(toDiscard);
                this.statistics.decrementDiscards();
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
