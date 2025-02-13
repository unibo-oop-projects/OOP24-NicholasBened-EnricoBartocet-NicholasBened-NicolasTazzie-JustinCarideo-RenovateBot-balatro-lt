package it.unibo.balatrolt.model.impl.levels;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.model.api.PlayerStatus;
import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.modifier.ModifierStatsSupplier;
import it.unibo.balatrolt.model.api.combination.Combination;
import it.unibo.balatrolt.model.api.levels.Blind;
import it.unibo.balatrolt.model.api.levels.BlindConfiguration;
import it.unibo.balatrolt.model.api.levels.BlindModifier;
import it.unibo.balatrolt.model.impl.cards.modifier.ModifierStatsSupplierBuilderImpl;

/**
 * An abstract implementation for the {@link Blind} interface.
 * @author Enrico Bartocetti
 */
public abstract class AbstractBlind implements Blind {
    private final BlindConfiguration config;
    private final BlindStats statistics;
    private final BlindCards cardsManager;

    /**
     * Instance a new BlindImpl starting from the configuration.
     * @param config the configuration for the Blind
     * @param modifier the modifier that tells how to change the statistics of the Blind
     */
    public AbstractBlind(final BlindConfiguration config, final BlindModifier modifier) {
        this.config = Preconditions.checkNotNull(config);
        this.cardsManager = new BlindCards();
        this.statistics = new BlindStats(Preconditions.checkNotNull(modifier));
    }

    @Override
    public final int getBlindNumber() {
        return this.config.id();
    }

    @Override
    public final int getMinimumChips() {
        return this.config.baseChip();
    }

    @Override
    public final int getCurrentChips() {
        return this.statistics.getCurrentChips();
    }

    @Override
    public final Status getStatus() {
        if (this.getRemainingHands() > 0 && this.getCurrentChips() < this.getMinimumChips()) {
            return Status.IN_GAME;
        }
        if (this.getRemainingHands() >= 0 && this.getCurrentChips() >= this.getMinimumChips()) {
            return Status.DEFEATED;
        }
        return Status.GAME_OVER;
    }

    @Override
    public final int getReward() {
        return this.config.reward();
    }

    @Override
    public final List<PlayableCard> getRemainingDeckCards() {
        return this.cardsManager.getRemainingDeckCards();
    }

    @Override
    public final List<PlayableCard> getHandCards() {
        return this.cardsManager.getHandCards();
    }

    @Override
    public final void playHand(final List<PlayableCard> toPlay, final PlayerStatus playerStatus) {
        Preconditions.checkNotNull(toPlay);
        Preconditions.checkArgument(!toPlay.isEmpty(), "You need to play at least 1 card");
        Preconditions.checkState(this.cardsInHand(toPlay), "The Cards played need to be in your hand");
        Preconditions.checkState(this.statistics.getRemainingHands() > 0, "There aren't hands left");
        this.cardsManager.discardCards(toPlay);
        this.statistics.decrementHands();
        this.statistics.incrementChips(evaluateChips(toPlay, playerStatus));
    }

    /**
     * Evaluates the chips earned from the cards played by applying
     * the needed modifiers.
     * @param toPlay cards to play.
     * @param playerStatus
     * @return the chips earned from the played hand.
     */
    protected abstract int evaluateChips(List<PlayableCard> toPlay, PlayerStatus playerStatus);

    @Override
    public abstract String getDescription();

    @Override
    public final void discardPlayableCards(final List<PlayableCard> toDiscard) {
        Preconditions.checkState(this.cardsInHand(toDiscard), "The cards must be in your hand");
        Preconditions.checkState(this.statistics.getRemainingDiscards() > 0, "There aren't discards left");
        this.cardsManager.discardCards(toDiscard);
        this.statistics.decrementDiscards();
    }

    @Override
    public final int getRemainingHands() {
        return this.statistics.getRemainingHands();
    }

    @Override
    public final int getRemainingDiscards() {
        return this.statistics.getRemainingDiscards();
    }

    private boolean cardsInHand(final List<PlayableCard> cards) {
        return cards.stream().allMatch(c -> this.cardsManager.getHandCards().contains(c));
    }

    /**
     * Builds a ModifierStatsSupplier with the given params.
     * @param comb combination of the played hand.
     * @param toPlay cards played
     * @param playerStatus
     * @return ModifierStatsSupplier with the given params.
     */
    protected final ModifierStatsSupplier getGameStatus(
        final Combination comb,
        final List<PlayableCard> toPlay,
        final PlayerStatus playerStatus
    ) {
        return new ModifierStatsSupplierBuilderImpl()
            .addCurrentCombination(comb.getCombinationType())
            .addHoldingCards(listToSet(this.getHandCards()))
            .addPlayedCards(listToSet(toPlay))
            .addCurrentCurrency(playerStatus.currency())
            .build();
    }

    private <T> Set<T> listToSet(final List<T> list) {
        return list.stream().collect(Collectors.toSet());
    }
}
