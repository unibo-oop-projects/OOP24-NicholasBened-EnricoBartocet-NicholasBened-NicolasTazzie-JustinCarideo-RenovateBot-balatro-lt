package it.unibo.balatrolt.model.impl.modifier;

import static com.google.common.base.Preconditions.checkState;

import java.util.function.Predicate;

import it.unibo.balatrolt.model.api.Combination.CombinationType;
import it.unibo.balatrolt.model.api.Modifier;

/**
 * Implementation of ConditionalModifier checking if the the current combination satisfies the specified condition.
 */
public class ModifierCombinationCondition extends ConditionalModifier<CombinationType> {
    /**
     * @param base base modifier
     * @param condition condition on holding cards to satisfy
     */
    public ModifierCombinationCondition(Predicate<CombinationType> condition, Modifier modifier) {
        super(condition, modifier);
    }

    @Override
    protected boolean checkCondition() {
        final var combination = super.getStats().get().getCurrentCombinationType();
        checkState(combination.isPresent(), "Current CombinationType is required");
        return super.getCondition().test(combination.get());
    }
}
