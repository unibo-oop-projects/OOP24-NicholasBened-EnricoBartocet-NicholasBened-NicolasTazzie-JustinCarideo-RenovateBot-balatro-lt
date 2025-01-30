package it.unibo.balatrolt.model.impl.combination;

import java.util.List;
import java.util.stream.Collectors;

import it.unibo.balatrolt.model.api.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.combination.CombinationRecognizer;
import it.unibo.balatrolt.model.api.combination.CombinationRecognizerHelpers;
import it.unibo.balatrolt.model.impl.SortingPlayableHelpers;

/**
 * Factory that creats recognizers.
 * @author Justin Carideo
 */
public class CombinationRecognizerHelpersImpl implements CombinationRecognizerHelpers {

    private static final int FULL_HAND = 5;
    private static final int THREE_SIZE = 3;
    private static final int FOUR_SIZE = 4;
    private static final int PAIR_SIZE = 2;
    private final List<Rank> ranks = List.of(Rank.values());

    @Override
    public CombinationRecognizer highCardRecognizer() {
        return hand -> !hand.isEmpty();
    }

    @Override
    public CombinationRecognizer pairRecognizer() {
        return hand -> hand.size() >= PAIR_SIZE &&
            hand.stream()
                .collect(Collectors.groupingBy(p -> p.getRank(), Collectors.counting()))
                .entrySet()
                .stream()
                .anyMatch(e -> e.getValue() == PAIR_SIZE);
    }

    @Override
    public CombinationRecognizer twoPairRecognizer() {
        return hand -> hand.size() >= FOUR_SIZE &&
            hand.stream()
                .collect(Collectors.groupingBy(p -> p.getRank(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == PAIR_SIZE)
                .toList().size() == PAIR_SIZE;
    }

    @Override
    public CombinationRecognizer threeOfAKindRecognizer() {
        return hand -> hand.size() >= THREE_SIZE  &&
            hand.stream()
                .collect(Collectors.groupingBy(p -> p.getRank(), Collectors.counting()))
                .entrySet()
                .stream()
                .anyMatch(e -> e.getValue() == THREE_SIZE);
    }

    @Override
    public CombinationRecognizer straightRecognizer() {
        return hand -> {
            if (hand.size() != FULL_HAND) {
                return false;
            }
            List<Rank> sorted = SortingPlayableHelpers.sortingByRank(hand)
                .stream()
                .map(p -> p.getRank())
                .sorted()
                .toList();
            if (sorted.equals(List.of(Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.ACE))) {
                return true;
            }
            boolean isStraight = true;
            for (int i = 1; i < sorted.size(); i++) {
                if (ranks.indexOf(sorted.get(i)) != ranks.indexOf(sorted.get(i - 1)) + 1) {
                    isStraight = false;
                }
            }
            return isStraight;
        };
    }

    @Override
    public CombinationRecognizer flushRecognizer() {
        return hand ->  hand.stream()
            .collect(Collectors.groupingBy(p -> p.getSuit(), Collectors.counting()))
            .entrySet()
            .stream()
            .anyMatch(e -> e.getValue() == FULL_HAND);
    }

    @Override
    public CombinationRecognizer fullHouseRecognizer() {
        return hand -> hand.size() == FULL_HAND &&
            pairRecognizer().recognize(hand) &&
            threeOfAKindRecognizer().recognize(hand);
    }

    @Override
    public CombinationRecognizer fourOfAKindRecognizer() {
        return hand -> !hand.isEmpty() &&
            hand.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()))
                .entrySet()
                .stream()
                .anyMatch(e -> e.getValue() == FOUR_SIZE);
    }

    @Override
    public CombinationRecognizer straightFlushRecognizer() {
        return hand -> straightRecognizer().recognize(hand) &&
            flushRecognizer().recognize(hand);
    }

    @Override
    public CombinationRecognizer royalFlushRecognizer() {
        return hand -> {
            List<Rank> l = SortingPlayableHelpers.sortingByRank(hand)
                .stream()
                .map(p -> p.getRank())
                .toList();
            if (l.equals(List.of(
                Rank.TEN,
                Rank.JACK,
                Rank.QUEEN,
                Rank.KING,
                Rank.ACE
            ))) {
                return flushRecognizer().recognize(hand);
            }
            return false;
        };
    }
}
