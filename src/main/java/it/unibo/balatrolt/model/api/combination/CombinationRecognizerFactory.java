package it.unibo.balatrolt.model.api.combination;

/**
 * Interface modelling the concept of creating {@link CombinationRecognizer} classes.
 * Simply it's a factory that provides some classes for recognizing combination
 * given by input.
 */
public interface CombinationRecognizerFactory {

    /**
     * @return an empty card recognizer
     */
    CombinationRecognizer emptyCardRecognizer();

    /**
     * @return an high card recognizer
     */
    CombinationRecognizer highCardRecognizer();

    /**
     * @return a pair recognizer
     */
    CombinationRecognizer pairRecognizer();

    /**
     * @return a two pair recognizer
     */
    CombinationRecognizer twoPairRecognizer();

    /**
     * @return a three of a kind recognizer
     */
    CombinationRecognizer threeOfAKindRecognizer();

    /**
     * @return a straight recognizer
     */
    CombinationRecognizer straightRecognizer();

    /**
     * @return a flush recognizer
     */
    CombinationRecognizer flushRecognizer();

    /**
     * @return a full house recognizer
     */
    CombinationRecognizer fullHouseRecognizer();

    /**
     * @return a four of a kind recognizer
     */
    CombinationRecognizer fourOfAKindRecognizer();

    /**
     * @return a straight recognizer
     */
    CombinationRecognizer straightFlushRecognizer();

    /**
     * @return a royal flush recognizer
     */
    CombinationRecognizer royalFlushRecognizer();
}
