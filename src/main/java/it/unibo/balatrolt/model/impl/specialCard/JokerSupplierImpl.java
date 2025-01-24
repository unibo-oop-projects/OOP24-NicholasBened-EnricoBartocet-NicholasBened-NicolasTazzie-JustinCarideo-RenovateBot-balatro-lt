package it.unibo.balatrolt.model.impl.specialcard;

import java.util.Random;

import it.unibo.balatrolt.model.api.Joker;
import it.unibo.balatrolt.model.api.JokerFactory;
import it.unibo.balatrolt.model.api.JokerSupplier;
import it.unibo.balatrolt.model.impl.modifier.ModifierBuilderImpl;

/**
 * Joker supplier implementation.
 */
public final class JokerSupplierImpl implements JokerSupplier {
    private final JokerFactory factory = new JokerFactoryImpl();
    private final Random r = new Random();

    @Override
    public Joker getRandom() {
        return switch (r.nextInt(1)) {
            case 0 -> doubleMultiplier();
            default -> factory.standardJoker("The bored joker", "It does nothing");
        };
    }

    private Joker doubleMultiplier() {
        return factory.withModifierAndRandomPrice("The doubler",
                "It doubles the current value of multipler without checking any condition",
                new ModifierBuilderImpl()
                        .addMultiplierModifier(m -> m * 2)
                        .build());
    }
}
