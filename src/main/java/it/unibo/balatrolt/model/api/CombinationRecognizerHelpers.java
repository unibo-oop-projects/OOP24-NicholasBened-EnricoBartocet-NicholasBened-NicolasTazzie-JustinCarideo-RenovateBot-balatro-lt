package it.unibo.balatrolt.model.api;

/**
 * Interface modelling the concept of creating CombinationRecognizer classes.
 * Simply it's a factory that provides some classes for recognizing combination
 * given by input.
 */
public interface CombinationRecognizerHelpers {

    /**
     * @return a recognizer
     */
    CombinationRecognizer highCardRecognizer();

    /**
     * @return a recognizer
     */
    CombinationRecognizer pairRecognizer();

    /**
     * @return a recognizer
     */
    CombinationRecognizer twoPairRecognizer();

    /**
     * @return a recognizer
     */
    CombinationRecognizer threeOfAKindRecognizer();

    /**
     * @return a recognizer
     */
    CombinationRecognizer straightRecognizer();

    /**
     * @return a recognizer
     */
    CombinationRecognizer flushRecognizer();

    /**
     * @return a recognizer
     */
    CombinationRecognizer fullHouseRecognizer();

    /**
     * @return a recognizer
     */
    CombinationRecognizer fourOfAKindRecognizer();

    /**
     * @return a recognizer
     */
    CombinationRecognizer straightFlushRecognizer();

    /**
     * @return a recognizer
     */
    CombinationRecognizer royalFlushRecognizer();
}
