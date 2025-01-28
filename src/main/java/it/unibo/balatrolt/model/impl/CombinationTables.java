package it.unibo.balatrolt.model.impl;

import java.util.Collections;
import java.util.Map;

import it.unibo.balatrolt.model.api.Combination.CombinationType;
import it.unibo.balatrolt.model.api.PlayableCard.Rank;

/**
 * This is an utility class that contains
 * conversition tables for evaluating combinations.
 * @author Justin Carideo
 */
public final class CombinationTables {

    private final Map<Rank,Integer> RANK_TABLE;
    private final Map<CombinationType,Pair<Integer,Double>> COMBINATION_TABLE;

    /**
     * Private constructor for preventing the creation of a new instance
     */
    private CombinationTables() {
        this.RANK_TABLE = Map.ofEntries(
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
        this.COMBINATION_TABLE = Map.ofEntries(
            Map.entry(CombinationType.HIGHCARD, new Pair<>(5, 1.0)),
            Map.entry(CombinationType.PAIR, new Pair<>(10, 2.0)),
            Map.entry(CombinationType.TWOPAIR, new Pair<>(20, 2.0)),
            Map.entry(CombinationType.THREEOFAKIND, new Pair<>(30, 3.0)),
            Map.entry(CombinationType.STRAIGHT, new Pair<>(30, 4.0)),
            Map.entry(CombinationType.FLUSH, new Pair<>(35, 4.0)),
            Map.entry(CombinationType.FULLHOUSE, new Pair<>(40, 4.0)),
            Map.entry(CombinationType.FOUROFAKIND, new Pair<>(60, 7.0)),
            Map.entry(CombinationType.STRAIGHTFLUSH, new Pair<>(100, 8.0)),
            Map.entry(CombinationType.ROYALFLUSH, new Pair<>(150, 8.0))
        );
    }

    /**
     * @return the combination table
     */
    public Map<CombinationType,Pair<Integer,Double>> getCombinationTable() {
        return Collections.unmodifiableMap(this.COMBINATION_TABLE);
    }

    /**
     * Given the type of combination, it returns
     * the pair points-multiplier assigned in the table.
     * @param type combination
     * @return the couple points-multiplier
     */
    public Pair<Integer,Double> convertCombination(CombinationType type) {
        return this.COMBINATION_TABLE.get(type);
    }

    /**
     * Given the rank, it returns
     * amount of points assigned in the table.
     * @param rank
     * @return the couple points-multiplier
     */
    public Integer convertRank(Rank rank) {
        return this.RANK_TABLE.get(rank);
    }
}
