package it.unibo.balatrolt.model.impl.combination;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.combination.CombinationCalculator;
import it.unibo.balatrolt.model.api.combination.CombinationCalculatorFactory;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.SortingPlayableHelpers;

/**
 * @author Justin Carideo
 */
public final class CombinationCalculatorFactoryImpl implements CombinationCalculatorFactory {

    private final CombinationTables table = new CombinationTables();

    /**
     * This method gives a function that compute the value
     * with all cards played.
     * @return function computing all cards
     */
    private Function<List<PlayableCard>, Integer> computeAllCards() {
        return hand -> hand
            .stream()
            .map(p -> table.convertRank(p.getRank()))
            .reduce(0, (i, j) -> i + j);
    }

    /**
     * Compute n equal cards.
     * @param n
     * @return a function that count n equal cards
     */
    private Function<List<PlayableCard>, Integer> computeNCards(final int n) {
        return hand -> hand.stream()
            .collect(Collectors.groupingBy(PlayableCard::getRank))
            .entrySet()
            .stream()
            .filter(e -> e.getValue().size() == n)
            .map(e -> table.convertRank(e.getKey()) * n)
            .reduce(0, (i, j) -> i + j);
    }

    /**
     * General method for obtaining a calculator.
     * @param fun for getting the value that depends on the cards played
     * @return a new combination calculator based on the cards played
     */
    private CombinationCalculator general(final Function<List<PlayableCard>, Integer> fun) {
        return (type, hand) -> {
            final int value = fun.apply(hand);
            final Pair<Integer, Double> comb = table.convertCombination(type);
            return new CombinationImpl(value + comb.e1(), comb.e2(), type);
        };
    }

    @Override
    public CombinationCalculator errorCardCalculator() {
        return general(List::size);
    }

    @Override
    public CombinationCalculator highCardCalculator() {
        return general(hand -> table.convertRank(SortingPlayableHelpers.sortingByRank(hand)
            .getLast()
            .getRank()));
    }

    @Override
    public CombinationCalculator pairsCalculator() {
        return general(computeNCards(2));
    }

    @Override
    public CombinationCalculator threeOfAKindCalculator() {
        return general(computeNCards(3));
    }


    @Override
    public CombinationCalculator fourOfAKindCalculator() {
        return general(computeNCards(4));
    }

    @Override
    public CombinationCalculator fiveCardsCalculator() {
        return general(computeAllCards());
    }

}
