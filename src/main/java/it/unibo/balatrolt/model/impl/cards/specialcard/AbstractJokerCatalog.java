package it.unibo.balatrolt.model.impl.cards.specialcard;

import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.cards.specialcard.Joker;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerCatalog;

/**
 * An abstract class that provides {@link Joker}.
 */
public abstract class AbstractJokerCatalog implements JokerCatalog {
    private Map<String, Joker> jokers;

    protected AbstractJokerCatalog() {
        this.addJokers();
    }

    @Override
    public final List<Joker> getJokerList() {
        return this.jokers.values().stream().toList();
    }

    @Override
    public final Optional<Joker> getJoker(final String name) {
        return Optional.fromNullable(this.jokers.get(name.toLowerCase()));
    }

    private final void addJokers() {
        this.jokers = this.getJokersMap();
    }

    protected abstract Map<String, Joker> getJokersMap();
}
