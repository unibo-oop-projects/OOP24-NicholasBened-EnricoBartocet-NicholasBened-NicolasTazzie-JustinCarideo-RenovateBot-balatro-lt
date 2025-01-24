package it.unibo.balatrolt.model.impl.specialCard;

import it.unibo.balatrolt.model.api.Joker;
import it.unibo.balatrolt.model.api.JokerFactory;
import it.unibo.balatrolt.model.api.Modifier;

/**
 * Implementation of {@link JokerFactory}
 */
public final class JokerFactoryImpl implements JokerFactory {

    @Override
    public Joker standardJoker(String name, String description) {
        return new JokerImpl(name, description, null);
    }

    @Override
    public Joker withModifier(String name, String description, Modifier modifier) {
        return new JokerImpl(name, description, modifier);
    }

    @Override
    public Joker merge(String newName, String newDescription, Joker j1, Joker j2) {
        // TODO
        throw new UnsupportedOperationException("Unimplemented method 'merge'");
    }
}
