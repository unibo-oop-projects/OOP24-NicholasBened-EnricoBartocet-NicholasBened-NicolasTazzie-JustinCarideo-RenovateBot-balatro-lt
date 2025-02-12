package it.unibo.balatrolt.model.impl.combination;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

import it.unibo.balatrolt.model.api.cards.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.combination.Combination;
import it.unibo.balatrolt.model.api.combination.CombinationTable;
import it.unibo.balatrolt.model.api.combination.Combination.CombinationType;
import it.unibo.balatrolt.model.impl.Pair;

/**
 * This is an immutable class that implements
 * {@link CombinationTable} for evaluating {@link Combination}.
 * @author Justin Carideo
 */
public final class CombinationTableImpl implements CombinationTable {

    private final Function<Rank, Integer> rankMapper;
    private final Map<CombinationType, Pair<Integer, Double>> combinationTable = new EnumMap<>(CombinationType.class);

    /**
     * Constructor for initializing the table of
     * combinations and mapper for ranks.
     */
    public CombinationTableImpl() {
        final int rankForAce = 11;
        final int shift = 2;
        final int tenValue = 10;
        this.rankMapper = r -> r.equals(Rank.ACE) ? rankForAce : r.ordinal() + shift < tenValue ? r.ordinal() + shift : tenValue;
        for (final var type : Combination.CombinationType.values()) {
            final int innerMultiplier = 5;
            this.combinationTable.put(type, new Pair<>(type.ordinal() * innerMultiplier, (double) type.ordinal()));
        }
    }

    @Override
    public Map<CombinationType, Pair<Integer, Double>> getCombinationTable() {
        return Collections.unmodifiableMap(this.combinationTable);
    }


    @Override
    public Pair<Integer, Double> convertCombination(final CombinationType type) {
        return this.combinationTable.get(type);
    }

    @Override
    public Integer convertRank(final Rank rank) {
        return rankMapper.apply(rank);
    }
}
