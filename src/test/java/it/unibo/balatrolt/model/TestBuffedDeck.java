package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.function.UnaryOperator;

import org.junit.jupiter.api.Test;

import static it.unibo.balatrolt.model.impl.BuffedDeckFactory.createWhite;
import static it.unibo.balatrolt.model.impl.BuffedDeckFactory.createRed;
import static it.unibo.balatrolt.model.impl.BuffedDeckFactory.createBlue;
import static it.unibo.balatrolt.model.impl.BuffedDeckFactory.createGold;
import static it.unibo.balatrolt.model.impl.BuffedDeckFactory.createPurple;
import static it.unibo.balatrolt.model.impl.BuffedDeckFactory.getList;

import it.unibo.balatrolt.model.api.BuffedDeck;
import it.unibo.balatrolt.model.impl.combination.PlayedHandImpl;
import it.unibo.balatrolt.model.impl.levels.BlindConfiguration;
import it.unibo.balatrolt.model.impl.levels.BlindImpl;
import it.unibo.balatrolt.model.impl.levels.BlindStats;

class TestBuffedDeck {

    private static final int NUM_DECKS = 5;

    @Test
    void testWhite() {
        testGeneric(createWhite(), UnaryOperator.identity(), UnaryOperator.identity(), UnaryOperator.identity());
    }

    @Test
    void testRed() {
        testGeneric(createRed(), UnaryOperator.identity(), n -> n + 1, UnaryOperator.identity());
    }

    @Test
    void testBlue() {
        testGeneric(createBlue(), n -> n + 1, UnaryOperator.identity(), UnaryOperator.identity());
    }

    @Test
    void testGold() {
        testGeneric(createGold(), n -> n - 1, n -> n - 1, n -> n * 2);
    }

    @Test
    void testPurple() {
        testGeneric(createPurple(), n -> n - 2, n -> n * 2, UnaryOperator.identity());
    }

    @Test
    void testList() {
        final var deckList = getList();
        assertNotNull(deckList);
        assertEquals(NUM_DECKS, deckList.size());
        /*
        deckList.forEach(d -> {
            System.out.println("Name: " + d.getName());
            System.out.println("\tDescription: " + d.getDescription());
        });
        */
    }

    private void testGeneric(
        final BuffedDeck deck,
        final UnaryOperator<Integer> hands,
        final UnaryOperator<Integer> discards,
        final UnaryOperator<Integer> chips
    ) {
        final var blind = new BlindImpl(new BlindConfiguration(0, 0, 0), deck.getModifier());
        assertEquals(hands.apply(BlindStats.BASE_HANDS), blind.getRemainingHands());
        assertEquals(discards.apply(BlindStats.BASE_DISCARDS), blind.getRemainingDiscards());
        final var toPlay = blind.getHandCards().subList(0, 3);
        blind.playHand(toPlay);
        final var combinationChips = new PlayedHandImpl(toPlay).evaluateCombination().getChips();
        assertEquals(chips.apply(combinationChips), blind.getCurrentChips());
    }
}
