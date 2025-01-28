package it.unibo.balatrolt.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.balatrolt.model.api.Combination;
import it.unibo.balatrolt.model.api.CombinationCalculator;
import it.unibo.balatrolt.model.api.CombinationCalculatorFactory;
import it.unibo.balatrolt.model.api.CombinationRecognizer;
import it.unibo.balatrolt.model.api.CombinationRecognizerHelpers;
import it.unibo.balatrolt.model.api.Combination.CombinationType;
import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.PlayedHand;

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
 */
public class PlayedHandImpl implements PlayedHand {

    private final List<PlayableCard> hand;
    private final CombinationRecognizerHelpers helper = new CombinationRecognizerHelpersImpl();
    private final CombinationCalculatorFactory factory = new CombinationCalculatorFactoryImpl();
    private List<Pair<CombinationType, CombinationRecognizer>> combinationTable = new ArrayList<>();

    /**
     * The constructor wraps the hand played.
     * @param hand
     */
    public PlayedHandImpl(List<PlayableCard> hand) {
        this.hand = hand;
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
        for (var type : CombinationType.values()) {
            var recognizer = getRecognizer(type);
            combinationTable.add(new Pair<>(type, recognizer));
        }
    }

    /**
     * @return the best combination scored
     */
    private CombinationType evaluateBest() {
        return combinationTable.stream()
            .map(p -> new Pair<>(p.e1(), p.e2().recognize(hand)))
            .filter(p -> p.e2())
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
        switch (type) {
            case HIGHCARD:
                return this.helper.highCardRecognizer();
            case PAIR:
                return this.helper.pairRecognizer();
            case TWOPAIR:
                return this.helper.twoPairRecognizer();
            case THREEOFAKIND:
                return this.helper.threeOfAKindRecognizer();
            case STRAIGHT:
                return this.helper.straightRecognizer();
            case FLUSH:
                return this.helper.flushRecognizer();
            case FULLHOUSE:
                return this.helper.fullHouseRecognizer();
            case FOUROFAKIND:
                return this.helper.fourOfAKindRecognizer();
            case STRAIGHTFLUSH:
                return this.helper.straightFlushRecognizer();
            case ROYALFLUSH:
                return this.helper.royalFlushRecognizer();
            default:
                throw new IllegalArgumentException("Invalid argument");
        }
    }

    /**
     * @param type of combination
     * @return the right calculator for the given combination
     */
    private CombinationCalculator getCalculator(final CombinationType type) {
        switch (type) {
            case HIGHCARD:
                return this.factory.highCardCalculator();
            case PAIR:
                return this.factory.pairCalculator();
            case TWOPAIR:
                return this.factory.twoPairCalculator();
            case THREEOFAKIND:
                return this.factory.threeOfAKindCalculator();
            case STRAIGHT:
                return this.factory.straightCalculator();
            case FLUSH:
                return this.factory.flushCalculator();
            case FULLHOUSE:
                return this.factory.fullHouseCalculator();
            case FOUROFAKIND:
                return this.factory.fourOfAKindCalculator();
            case STRAIGHTFLUSH:
                return this.factory.straightFlushCalculator();
            case ROYALFLUSH:
                return this.factory.royalFlushCalculator();
            default:
                throw new IllegalArgumentException("Invalid argument");
        }
    }
}
