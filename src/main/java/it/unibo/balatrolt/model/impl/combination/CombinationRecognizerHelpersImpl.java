package it.unibo.balatrolt.model.impl.combination;

import java.util.List;
import java.util.stream.Collectors;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.combination.CombinationRecognizer;
import it.unibo.balatrolt.model.api.combination.CombinationRecognizerHelpers;
import it.unibo.balatrolt.model.impl.cards.SortingPlayableHelpers;

/**
 * Factory that creats {@link CombinationRecognizer}.
 * @author Justin Carideo
 */
public final class CombinationRecognizerHelpersImpl implements CombinationRecognizerHelpers {

    private static final int FULL_HAND = 5;
    private static final int THREE_SIZE = 3;
    private static final int FOUR_SIZE = 4;
    private static final int PAIR_SIZE = 2;
    private final List<Rank> ranks = List.of(Rank.values());

    @Override
    public CombinationRecognizer emptyCardRecognizer() {
        return List::isEmpty;
    }

    @Override
    public CombinationRecognizer highCardRecognizer() {
        return hand -> !hand.isEmpty();
    }

    /**
     * This method is for recognizers that has to
     * recognize whether there are n equal cards
     * depending on the rank.
     * @param n
     * @return a general recognizer for n equal cards
     */
    private CombinationRecognizer sameNRecognizer(final int n) {
        return hand -> hand.size() >= PAIR_SIZE
            && hand.stream()
                .collect(Collectors.groupingBy(PlayableCard::getRank, Collectors.counting()))
                .entrySet()
                .stream()
                .anyMatch(e -> e.getValue() == n);
    }

    @Override
    public CombinationRecognizer pairRecognizer() {
        return sameNRecognizer(PAIR_SIZE);
    }

    @Override
    public CombinationRecognizer twoPairRecognizer() {
        return hand -> hand.size() >= FOUR_SIZE
            && hand.stream()
                .collect(Collectors.groupingBy(PlayableCard::getRank, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == PAIR_SIZE)
                .toList().size() == PAIR_SIZE;
    }

    @Override
    public CombinationRecognizer threeOfAKindRecognizer() {
        return sameNRecognizer(THREE_SIZE);
    }

    @Override
    public CombinationRecognizer straightRecognizer() {
        return hand -> {
            if (hand.size() != FULL_HAND) {
                return false;
            }
            final List<Rank> sorted = hand.stream()
                .map(PlayableCard::getRank)
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
        return hand -> hand.size() == FULL_HAND
            && hand.stream()
                .collect(Collectors.groupingBy(PlayableCard::getSuit, Collectors.counting()))
                .entrySet()
                .stream()
                .anyMatch(e -> e.getValue() == FULL_HAND);
    }

    @Override
    public CombinationRecognizer fullHouseRecognizer() {
        return hand -> hand.size() == FULL_HAND
            && pairRecognizer().recognize(hand)
            && threeOfAKindRecognizer().recognize(hand);
    }

    @Override
    public CombinationRecognizer fourOfAKindRecognizer() {
        return sameNRecognizer(FOUR_SIZE);
    }

    @Override
    public CombinationRecognizer straightFlushRecognizer() {
        return hand -> straightRecognizer().recognize(hand)
            && flushRecognizer().recognize(hand);
    }

    @Override
    public CombinationRecognizer royalFlushRecognizer() {
        return hand -> {
            final List<Rank> l = SortingPlayableHelpers.sortingByRank(hand)
                .stream()
                .map(PlayableCard::getRank)
                .toList();
            return l.equals(List.of(
                Rank.TEN,
                Rank.JACK,
                Rank.QUEEN,
                Rank.KING,
                Rank.ACE
            )) && flushRecognizer().recognize(hand);
        };
    }
}
