package it.unibo.balatrolt.model.impl.cards.modifier;

import static com.google.common.base.Preconditions.checkState;

import java.util.Set;
import java.util.function.Predicate;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;

/**
 * Implementation of {@link ConditionalModifier} checking if the played cards satisfies the specified condition.
 * @author Nicolas Tazzieri
 */
public final class ModifierPlayedCardCondition extends ConditionalModifier<Set<PlayableCard>> {
    /**
     * @param base base modifier
     * @param condition condition on played cards to satisfy
     */
    public ModifierPlayedCardCondition(final CombinationModifier base, final Predicate<Set<PlayableCard>> condition) {
        super(base, condition);
    }

    @Override
    protected boolean checkCondition() {
        final var playedCards = super.getStats().get().getPlayedCards();
        checkState(playedCards.isPresent(), "Current played cards are required");
        return super.getCondition().test(playedCards.get());
    }
}
