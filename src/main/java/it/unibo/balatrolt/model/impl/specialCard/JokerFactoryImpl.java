package it.unibo.balatrolt.model.impl.specialCard;

import java.util.Random;

import it.unibo.balatrolt.model.api.Joker;
import it.unibo.balatrolt.model.api.JokerFactory;
import it.unibo.balatrolt.model.api.Modifier;

/**
 * Implementation of {@link JokerFactory}
 */
public final class JokerFactoryImpl implements JokerFactory {
    private static final int MAX_PRICE = 10;
    private static final int MIN_PRICE = 3;
    private Random priceSupplier = new Random();

    @Override
    public Joker standardJoker(String name, String description) {
        return new JokerImpl(name, description, getRandomPrice(), null);
    }

    @Override
    public Joker withModifier(String name, String description, int basePrice, Modifier modifier) {
        return new JokerImpl(name, description, basePrice, modifier);
    }

    @Override
    public Joker withModifierAndRandomPrice(String name, String description, Modifier modifier) {
        return new JokerImpl(name, description, getRandomPrice(), modifier);
    }

    @Override
    public Joker merge(String newName, String newDescription, Joker j1, Joker j2) {
        // TODO merge elems
        throw new UnsupportedOperationException("Unimplemented method 'merge'");
    }

    private int getRandomPrice() {
        return priceSupplier.nextInt(MIN_PRICE, MAX_PRICE);
    }
}
