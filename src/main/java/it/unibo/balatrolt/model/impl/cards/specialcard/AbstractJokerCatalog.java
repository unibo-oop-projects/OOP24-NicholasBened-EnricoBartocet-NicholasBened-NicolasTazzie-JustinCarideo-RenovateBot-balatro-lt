package it.unibo.balatrolt.model.impl.cards.specialcard;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.cards.specialcard.Joker;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerCatalog;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerFactory;

/**
 * An abstract class that provides {@link Joker}.
 * @author Nicolas Tazzieri
 */
public abstract class AbstractJokerCatalog implements JokerCatalog {
    private Map<String, Joker> jokers = Map.of();
    private final JokerFactory factory = new JokerFactoryImpl();

    @Override
    public final List<Joker> getJokerList() {
        this.addJokers();
        return this.jokers.values().stream().toList();
    }

    @Override
    public final Optional<Joker> getJoker(final String name) {
        this.addJokers();
        return Optional.fromNullable(this.jokers.get(name.toLowerCase(Locale.getDefault())));
    }

    private void addJokers() {
        this.jokers = this.getJokersMap();
    }

    /**
     * It returns a JokerFactory.
     * @return a JokerFactory.
     */
    protected final JokerFactory getFactory() {
        return this.factory;
    }

    /**
     * It returns the Map of jokers created by the extended classes.
     * @return a map that given the jokers's name returns the effective joker.
     */
    protected abstract Map<String, Joker> getJokersMap();
}
