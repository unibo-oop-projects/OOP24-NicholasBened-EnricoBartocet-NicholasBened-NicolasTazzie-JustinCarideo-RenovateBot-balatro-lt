package it.unibo.balatrolt.model.impl.modifier;

import static com.google.common.base.Preconditions.checkState;

import java.util.Set;
import java.util.function.Predicate;

import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.PlayableCard;

/**
 * Implementation of ConditionalModifier checking if the holding cards satisfies the specified condition.
 */
public final class ModifierHoldingCardCondition extends ConditionalModifier<Set<PlayableCard>> {
    /**
     * @param base base modifier
     * @param condition condition on holding cards to satisfy
     */
    public ModifierHoldingCardCondition(final Modifier base, final Predicate<Set<PlayableCard>> condition) {
        super(base, condition);
    }

    @Override
    protected boolean checkCondition() {
        final var holdingCards = super.getStats().get().getHoldingCards();
        checkState(holdingCards.isPresent(), "Current holding cards are required");
        return super.getCondition().test(holdingCards.get());
    }
}
