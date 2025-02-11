package it.unibo.balatrolt.model.impl.combination;

import java.util.Collections;
import java.util.Map;

import it.unibo.balatrolt.model.api.cards.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.combination.CombinationTable;
import it.unibo.balatrolt.model.api.combination.Combination.CombinationType;
import it.unibo.balatrolt.model.impl.Pair;

/**
 * This is an immutable class that implements
 * {@link CombinationTable} for evaluating {@link Combination}.
 * @author Justin Carideo
 */
public final class CombinationTableImpl implements CombinationTable {

    private final Map<Rank, Integer> rankTable = Map.ofEntries(
        Map.entry(Rank.ACE, 11),
        Map.entry(Rank.TWO, 2),
        Map.entry(Rank.THREE, 3),
        Map.entry(Rank.FOUR, 4),
        Map.entry(Rank.FIVE, 5),
        Map.entry(Rank.SIX, 6),
        Map.entry(Rank.SEVEN, 7),
        Map.entry(Rank.EIGHT, 8),
        Map.entry(Rank.NINE, 9),
        Map.entry(Rank.TEN, 10),
        Map.entry(Rank.JACK, 10),
        Map.entry(Rank.QUEEN, 10),
        Map.entry(Rank.KING, 10)
    );
    private final Map<CombinationType, Pair<Integer, Double>> combinationTable = Map.ofEntries(
        Map.entry(CombinationType.EMPTY_CARD, new Pair<>(0, 0.0)),
        Map.entry(CombinationType.HIGH_CARD, new Pair<>(5, 1.0)),
        Map.entry(CombinationType.PAIR, new Pair<>(10, 2.0)),
        Map.entry(CombinationType.TWO_PAIR, new Pair<>(20, 2.5)),
        Map.entry(CombinationType.THREE_OF_A_KIND, new Pair<>(30, 3.0)),
        Map.entry(CombinationType.STRAIGHT, new Pair<>(35, 4.0)),
        Map.entry(CombinationType.FLUSH, new Pair<>(40, 5.0)),
        Map.entry(CombinationType.FULL_HOUSE, new Pair<>(50, 5.5)),
        Map.entry(CombinationType.FOUR_OF_A_KIND, new Pair<>(60, 6.5)),
        Map.entry(CombinationType.STRAIGHT_FLUSH, new Pair<>(100, 7.0)),
        Map.entry(CombinationType.ROYAL_FLUSH, new Pair<>(150, 8.0))
    );

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
        return this.rankTable.get(rank);
    }
}
