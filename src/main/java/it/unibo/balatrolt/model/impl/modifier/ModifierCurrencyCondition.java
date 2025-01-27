package it.unibo.balatrolt.model.impl.modifier;

import static com.google.common.base.Preconditions.checkState;

import java.util.function.Predicate;

import it.unibo.balatrolt.model.api.Modifier;

/**
 * Implementation of ConditionalModifier checking if the currency held by the player satisfies the specified condition.
 */
public class ModifierCurrencyCondition extends ConditionalModifier<Integer> {

    protected ModifierCurrencyCondition(Modifier modifier, Predicate<Integer> condition) {
        super(modifier, condition);
    }

    @Override
    protected boolean checkCondition() {
        final var getCurrentCurrency = super.getStats().get().getCurrentCurrency();
        checkState(getCurrentCurrency.isPresent(), "Current currency is required");
        return super.getCondition().test(getCurrentCurrency.get());
    }
}
