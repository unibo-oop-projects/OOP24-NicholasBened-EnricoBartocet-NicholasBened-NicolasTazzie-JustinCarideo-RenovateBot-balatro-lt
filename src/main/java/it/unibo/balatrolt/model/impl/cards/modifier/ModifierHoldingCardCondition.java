package it.unibo.balatrolt.model.impl.cards.modifier;

import static com.google.common.base.Preconditions.checkState;

import java.util.Set;
import java.util.function.Predicate;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;

/**
 * Implementation of {@link ConditionalModifier} checking if the holding cards satisfies the specified condition.
 * @author Nicolas Tazzieri
 */
public final class ModifierHoldingCardCondition extends ConditionalModifier<Set<PlayableCard>> {
    /**
     * @param base base modifier
     * @param condition condition on holding cards to satisfy
     */
    public ModifierHoldingCardCondition(final CombinationModifier base, final Predicate<Set<PlayableCard>> condition) {
        super(base, condition);
    }

    @Override
    protected boolean checkCondition() {
        final var holdingCards = super.getStats().get().getHoldingCards();
        checkState(holdingCards.isPresent(), "Current holding cards are required");
        return super.getCondition().test(holdingCards.get());
    }
}
