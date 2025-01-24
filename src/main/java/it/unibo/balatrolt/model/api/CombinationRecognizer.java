package it.unibo.balatrolt.model.api;

import java.util.List;

/**
 * Interface that model a recognizer for cards.
 * It essentially has a method that recognize whether the combination
 * is correct or not.
 */
@FunctionalInterface
public interface CombinationRecognizer {
    
    /**
     * Recognize what type of combination the player done.
     * @param hand played
     * @return whether it's the correct combination or not
     */
    boolean recognize(List<PlayableCard> hand);
}
