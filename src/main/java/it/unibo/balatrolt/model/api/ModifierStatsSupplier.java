package it.unibo.balatrolt.model.api;

import java.util.Set;

import com.google.common.base.Optional;

/**
 * It models a supplier of statistics used to retrieve player, card and other statistics.
 * Although it's not required, it's preferable to pass immutable implementation of the provided classes.
 */
public interface ModifierStatsSupplier {
    Optional<Set<Card>> getHoldingCards();

    Optional<Set<Card>> getPlayedCards();

    Optional<Currency> getCurrentCurrency();

    /* [TODO: add combination getter] */
}
