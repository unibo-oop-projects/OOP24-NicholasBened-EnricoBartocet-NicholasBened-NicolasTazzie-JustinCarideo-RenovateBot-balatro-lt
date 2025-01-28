package it.unibo.balatrolt.model.impl;

import java.util.List;
import java.util.function.Function;

import org.checkerframework.checker.units.qual.s;

import it.unibo.balatrolt.model.api.CombinationCalculator;
import it.unibo.balatrolt.model.api.CombinationCalculatorFactory;
import it.unibo.balatrolt.model.api.PlayableCard;

/**
 * @author Justin Carideo
 */
public class CombinationCalculatorFactoryImpl implements CombinationCalculatorFactory {

    private final CombinationTables table = new CombinationTables();

    private Function<List<PlayableCard>,Integer> computeFiveCards() {
        return hand -> SortingPlayableHelpers.sortingByRank(hand)
            .stream()
            .map(p -> table.convertRank(p.getRank()))
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
        return general(hand -> table.convertRank(
            SortingPlayableHelpers.sortingByRank(hand)
            .getLast()
            .getRank()));
    }

    @Override
    public CombinationCalculator pairCalculator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pairCalculator'");
    }

    @Override
    public CombinationCalculator twoPairCalculator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'twoPairCalculator'");
    }

    @Override
    public CombinationCalculator threeOfAKindCalculator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'threeOfAKindCalculator'");
    }


    @Override
    public CombinationCalculator fourOfAKindCalculator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fourOfAKindCalculator'");
    }

    @Override
    public CombinationCalculator fiveCardsCalculator() {
        return general(computeFiveCards());
    }

}
