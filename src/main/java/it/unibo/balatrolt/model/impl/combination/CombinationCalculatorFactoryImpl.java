package it.unibo.balatrolt.model.impl.combination;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.combination.CombinationCalculator;
import it.unibo.balatrolt.model.api.combination.CombinationCalculatorFactory;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.SortingPlayableHelpers;

/**
 * @author Justin Carideo
 */
public class CombinationCalculatorFactoryImpl implements CombinationCalculatorFactory {

    private final CombinationTables table = new CombinationTables();

    private Function<List<PlayableCard>,Integer> computeFiveCards() {
        return hand -> hand
            .stream()
            .map(p -> table.convertRank(p.getRank()))
            .reduce(0, (i, j) -> i + j);
    }

    private Function<List<PlayableCard>,Integer> computeBelowFiveCards(final int n) {
        return hand -> hand.stream()
            .collect(Collectors.groupingBy(p -> p.getRank()))
            .entrySet()
            .stream()
            .filter(e -> e.getValue().size() == n)
            .map(e -> table.convertRank(e.getKey()) * n)
            .reduce(0, (i, j) -> i + j);
    }

    private CombinationCalculator general(Function<List<PlayableCard>,Integer> fun) {
        return (type, hand) -> {
            final int value = fun.apply(hand);
            final Pair<Integer,Double> comb = table.convertCombination(type);
            return new CombinationImpl(value + comb.e1(), comb.e2(), type);
        };
    }

    @Override
    public CombinationCalculator highCardCalculator() {
        return general(hand -> table.convertRank(SortingPlayableHelpers.sortingByRank(hand)
            .getLast()
            .getRank()));
    }

    @Override
    public CombinationCalculator pairsCalculator() {
        return general(computeBelowFiveCards(2));
    }

    @Override
    public CombinationCalculator threeOfAKindCalculator() {
        return general(computeBelowFiveCards(3));
    }


    @Override
    public CombinationCalculator fourOfAKindCalculator() {
        return general(computeBelowFiveCards(4));
    }

    @Override
    public CombinationCalculator fiveCardsCalculator() {
        return general(computeFiveCards());
    }

}
