package it.unibo.balatrolt.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.balatrolt.model.api.Combination;
import it.unibo.balatrolt.model.api.CombinationRecognizer;
import it.unibo.balatrolt.model.api.CombinationRecognizerHelpers;
import it.unibo.balatrolt.model.api.Combination.CombinationType;
import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.PlayedHand;

public class PlayedHandImpl implements PlayedHand {

    private final List<PlayableCard> hand;
    private final CombinationRecognizerHelpers helper = new CombinationRecognizerHelpersImpl();
    private List<Pair<CombinationType, CombinationRecognizer>> combinationTable = new ArrayList<>();

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
        for (var type : CombinationType.values()) {
            var recognizer = giveRecognizer(type);
            combinationTable.add(new Pair<>(type, recognizer));
        }
        CombinationType bestCombination = combinationTable.stream()
            .map(p -> new Pair<>(p.get1(), p.get2().recognize(hand)))
            .filter(p -> p.get2())
            .toList().getLast().get1();
        //TODO
        return new CombinationImpl(0, 0, bestCombination);
    }

    private void resetCombinations() {
        this.combinationTable.clear();
    }

    private CombinationRecognizer giveRecognizer(CombinationType type) {
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
}
