package it.unibo.balatrolt.model.impl.modifier;

import java.util.Set;
import java.util.function.Predicate;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.ModifierStatsSupplier;

/**
 * Implementation of ConditionalModifier checking if the played cards satisfies the specified condition.
 */
public final class ModifierWithCardCondition extends ConditionalModifier<Set<PlayableCard>> {
    /**
     * @param base base modifier
     * @param condition condition on cards to satisfy
     */
    public ModifierWithCardCondition(final Modifier base, final Predicate<Set<PlayableCard>> condition) {
        super(condition, base);
    }

    @Override
    protected boolean canApply() {
        final Optional<ModifierStatsSupplier> stats = super.getStats();
        if (!stats.isPresent()) {
            return true;
        }
        final Optional<Set<PlayableCard>> playedCards = stats.get().getPlayedCards();
        if (!playedCards.isPresent()) {
            throw new IllegalStateException("Current played cards are required");
        }
        return super.getCondition().test(playedCards.get());
    }
}
