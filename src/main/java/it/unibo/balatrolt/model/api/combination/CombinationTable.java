package it.unibo.balatrolt.model.api.combination;

import java.util.Map;

import it.unibo.balatrolt.model.api.cards.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.combination.Combination.CombinationType;
import it.unibo.balatrolt.model.impl.Pair;

/**
 * This class represents how score is assigned. For each
 * type of combination there is a pair of points and multiplier and
 * for each rank there is an integer.
 */
public interface CombinationTable {

    /**
     * @return the combination table
     */
    Map<CombinationType, Pair<Integer, Double>> getCombinationTable();

    /**
     * Given the type of combination, it returns
     * the pair points-multiplier assigned in the table.
     * @param type combination
     * @return the couple points-multiplier
     */
    Pair<Integer, Double> convertCombination(CombinationType type);

    /**
     * Given the rank, it returns
     * amount of points assigned in the table.
     * @param rank
     * @return the couple points-multiplier
     */
    Integer convertRank(Rank rank);
}
