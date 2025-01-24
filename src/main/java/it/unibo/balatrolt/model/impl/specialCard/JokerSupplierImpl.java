package it.unibo.balatrolt.model.impl.specialCard;

import java.util.Random;

import it.unibo.balatrolt.model.api.Joker;
import it.unibo.balatrolt.model.api.JokerFactory;
import it.unibo.balatrolt.model.api.JokerSupplier;
import it.unibo.balatrolt.model.impl.modifier.ModifierBuilderImpl;

public class JokerSupplierImpl implements JokerSupplier {
    private JokerFactory factory = new JokerFactoryImpl();
    private Random r = new Random();

    @Override
    public Joker getRandom() {
        return switch(r.nextInt(1)) {
            case 0 -> doubleMultiplier();
            default -> factory.standardJoker("The bored joker", "It does nothing");
        };
    }

    private Joker doubleMultiplier() {
        return factory.withModifier("The doubler",
                "It doubles the current value of multipler without checking any condition",
                new ModifierBuilderImpl()
                        .addMultiplierModifier(null)
                        .build());
    }
}
