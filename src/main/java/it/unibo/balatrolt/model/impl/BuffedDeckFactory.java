package it.unibo.balatrolt.model.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.function.UnaryOperator;

import it.unibo.balatrolt.model.api.BuffedDeck;
import it.unibo.balatrolt.model.impl.levels.BlindModifierImpl;

/**
 * A Static Factory to generate some types of {@link BuffedDeck}.
 * @author Bartocetti Enrico
 */
public final class BuffedDeckFactory {

    private BuffedDeckFactory() { }

    /**
     * Returns a White Deck.
     * It is a classical deck, so it does not make any changes.
     * @return a white deck
     */
    public static BuffedDeck createWhite() {
        return new BuffedDeckImpl(
            "White",
            "No actions are made",
            new BlindModifierImpl(UnaryOperator.identity(), UnaryOperator.identity(), UnaryOperator.identity())
        );
    }

    /**
     * Returns a Red Deck.
     * This deck gives one more discard for every blind.
     * @return a red deck
     */
    public static BuffedDeck createRed() {
        return new BuffedDeckImpl(
            "Red",
            "+1 discard every round",
            new BlindModifierImpl(UnaryOperator.identity(), n -> n + 1, UnaryOperator.identity())
        );
    }

    /**
     * Returns a Blue Deck.
     * This deck give one more hand for evry blind.
     * @return a blue deck
     */
    public static BuffedDeck createBlue() {
        return new BuffedDeckImpl(
            "Blue",
            "+1 hand every round",
            new BlindModifierImpl(n -> n + 1, UnaryOperator.identity(), UnaryOperator.identity())
        );
    }

    /**
     * Returns a Gold Deck.
     * This deck doubles the chips earned, but removes one hand and discard every round.
     * @return a gold deck
     */
    public static BuffedDeck createGold() {
        return new BuffedDeckImpl(
            "Gold",
            "Doubles the chips earned, but -1 hand and discard every round",
            new BlindModifierImpl(n -> n - 1, n -> n - 1, n -> n * 2)
        );
    }

    /**
     * Returns a Purple Deck.
     * This deck doubles the discards, but removes two hands every blind.
     * @return a purple deck
     */
    public static BuffedDeck createPurple() {
        return new BuffedDeckImpl(
            "Purple",
            "Double the discards, but -2 hands every round",
            new BlindModifierImpl(n -> n - 2, n -> n * 2, UnaryOperator.identity())
        );
    }

    /**
     * Returns a list of all the decks that can be created by this class using reflection.
     * @return a list containing the decks provided by this factory
     */
    public static Set<BuffedDeck> getList() {
        final Set<BuffedDeck> toReturn = new HashSet<>();
        final Method[] methods = BuffedDeckFactory.class.getMethods();
        for (final Method method: methods) {
            if (method.getName().startsWith("create") && method.getParameterCount() == 0) {
                try {
                    toReturn.add((BuffedDeck) method.invoke(null));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    // TODO: Handle Exception
                }
            }
        }
        return toReturn;
    }
}
