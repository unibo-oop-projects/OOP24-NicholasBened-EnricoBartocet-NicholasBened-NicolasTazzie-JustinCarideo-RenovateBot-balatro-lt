package it.unibo.balatrolt.model.impl.cards.modifier;

import static com.google.common.base.Preconditions.checkState;

import java.util.function.Predicate;

import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;
import it.unibo.balatrolt.model.api.combination.Combination.CombinationType;

/**
 * Implementation of ConditionalModifier checking if the the current combination satisfies the specified condition.
 */
public final class ModifierCombinationCondition extends ConditionalModifier<CombinationType> {
    /**
     * @param modifier base modifier
     * @param condition condition on holding cards to satisfy
     */
    public ModifierCombinationCondition(final CombinationModifier modifier, final Predicate<CombinationType> condition) {
        super(modifier, condition);
    }

    @Override
    protected boolean checkCondition() {
        final var combination = super.getStats().get().getCurrentCombinationType();
        checkState(combination.isPresent(), "Current CombinationType is required");
        return super.getCondition().test(combination.get());
    }
}
