package it.unibo.balatrolt.model.api.combination;

import java.util.Map;

import it.unibo.balatrolt.model.api.cards.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.combination.Combination.CombinationType;
import it.unibo.balatrolt.model.impl.Pair;

public interface CombinationTable {

    Map<CombinationType, Pair<Integer, Double>> getCombinationTable();

    public Pair<Integer, Double> convertCombination(final CombinationType type);

    public Integer convertRank(final Rank rank);
}
