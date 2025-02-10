package it.unibo.balatrolt.model.impl.combination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.combination.Combination;
import it.unibo.balatrolt.model.api.combination.CombinationCalculator;
import it.unibo.balatrolt.model.api.combination.CombinationCalculatorFactory;
import it.unibo.balatrolt.model.api.combination.CombinationRecognizer;
import it.unibo.balatrolt.model.api.combination.CombinationRecognizerHelpers;
import it.unibo.balatrolt.model.api.combination.PlayedHand;
import it.unibo.balatrolt.model.api.combination.Combination.CombinationType;

/**
 * Implementation the played hand.
 * Simply it wraps a list of PlayableCard and it recognize
 * what type of combination has the player done.
 * For evaluating the combination it goes in these steps:
 * - it creates a list for all combination types
 * - then maps only avaiable combinations
 * - once obtained the mapped list, it takes the last one, in other words the best
 * - In the end it creates the right combination with a calculator
 * For more details check Combination class that simply acts as a
 * container of the result.
 * @author Justin Carideo
 */
public final class PlayedHandImpl implements PlayedHand {

    private static final int FULL_HAND = 5;
    private final List<PlayableCard> hand;
    private final CombinationRecognizerHelpers helper = new CombinationRecognizerHelpersImpl();
    private final CombinationCalculatorFactory factory = new CombinationCalculatorFactoryImpl();
    private final List<Pair<CombinationType, CombinationRecognizer>> combinationTable = new ArrayList<>();

    /**
     * The constructor wraps the hand played.
     * @param hand
     */
    public PlayedHandImpl(final List<PlayableCard> hand) {
        Preconditions.checkArgument(hand.size() <= FULL_HAND,
        "Hand played must be within 0 or 5 cards");
        this.hand = Collections.unmodifiableList(hand);
    }

    @Override
    public List<PlayableCard> getCards() {
        return Collections.unmodifiableList(this.hand);
    }

    @Override
    public Combination evaluateCombination() {
        resetCombinations();
        final CombinationType bestCombination = evaluateBest();
        return getCalculator(bestCombination).compute(bestCombination, hand);
    }

    /**
     * This method restarts the combination list,
     * where each combination type is going to checked
     * whether is an avaiable combination for the hand played.
     */
    private void resetCombinations() {
        this.combinationTable.clear();
        for (final var type : CombinationType.values()) {
            final var recognizer = getRecognizer(type);
            combinationTable.add(new Pair<>(type, recognizer));
        }
    }

    /**
     * @return the best combination scored
     */
    private CombinationType evaluateBest() {
        return combinationTable.stream()
            .map(p -> new Pair<>(p.e1(), p.e2().recognize(hand)))
            .filter(Pair::e2)
            .toList().getLast().e1();
    }

    /**
     * This is a support method for recognizing the right combination.
     * Given the type of the combination, it returns the right recognizer,
     * produced by the factory CombinationFactoryHelpers implementation.
     * @param type of the combination
     * @return the right recognizer
     */
    private CombinationRecognizer getRecognizer(final CombinationType type) {
        return switch (type) {
            case HIGH_CARD -> this.helper.highCardRecognizer();
            case PAIR -> this.helper.pairRecognizer();
            case TWO_PAIR -> this.helper.twoPairRecognizer();
            case THREE_OF_A_KIND -> this.helper.threeOfAKindRecognizer();
            case STRAIGHT -> this.helper.straightRecognizer();
            case FLUSH -> this.helper.flushRecognizer();
            case FULL_HOUSE -> this.helper.fullHouseRecognizer();
            case FOUR_OF_A_KIND -> this.helper.fourOfAKindRecognizer();
            case STRAIGHT_FLUSH -> this.helper.straightFlushRecognizer();
            case ROYAL_FLUSH -> this.helper.royalFlushRecognizer();
            default -> this.helper.emptyCardRecognizer();
        };
    }

    /**
     * @param type of combination
     * @return the right calculator for the given combination
     */
    private CombinationCalculator getCalculator(final CombinationType type) {
        return switch (type) {
            case EMPTY_CARD -> this.factory.emptyCardCalculator();
            case HIGH_CARD -> this.factory.highCardCalculator();
            case PAIR, TWO_PAIR -> this.factory.pairsCalculator();
            case THREE_OF_A_KIND -> this.factory.threeOfAKindCalculator();
            case FOUR_OF_A_KIND -> this.factory.fourOfAKindCalculator();
            case ROYAL_FLUSH, STRAIGHT, FLUSH, FULL_HOUSE, STRAIGHT_FLUSH -> this.factory.fiveCardsCalculator();
            default -> throw new IllegalArgumentException("Invalid argument");
        };
    }
}
