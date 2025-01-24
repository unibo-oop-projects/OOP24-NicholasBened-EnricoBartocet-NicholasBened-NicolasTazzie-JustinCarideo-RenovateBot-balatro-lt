package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.balatrolt.model.api.Blind;
import it.unibo.balatrolt.model.api.BlindFactory;
import it.unibo.balatrolt.model.impl.BlindFactoryImpl;

class TestBlindFactory {
    private static final int NUM_BLINDS = 5;
    private static final int ANTE_ID = 3;
    private static final int BLIND_ID = 2;

    private BlindFactory factory;
    private BiFunction<Integer, Integer, Integer> baseChipsCalculator;
    private Function<Integer, Integer> rewardCalculator;

    @BeforeEach
    void init() {
        baseChipsCalculator = (a, b) -> a * 10 + (b + 2) * 4;
        rewardCalculator = Function.identity();
        this.factory = new BlindFactoryImpl(baseChipsCalculator, rewardCalculator);
    }

    @Test
    void testSingleBlind() {
        final Blind newBlind = this.factory.fromIds(ANTE_ID, BLIND_ID);
        assertEquals(2, newBlind.getBlindNumber());
        assertEquals(baseChipsCalculator.apply(ANTE_ID, BLIND_ID), newBlind.getMinimumChips());
        assertEquals(rewardCalculator.apply(BLIND_ID), newBlind.getReward());
        assertEquals(0, newBlind.getCurrentChips());
        assertFalse(newBlind.isOver());
    }

    @Test
    void testList() {
        final List<Blind> blinds = this.factory.createList(NUM_BLINDS, ANTE_ID);
        assertNotNull(blinds);
        assertEquals(NUM_BLINDS, blinds.size());
    }

    @Test
    void testInvalidList() {
        assertThrows(IllegalArgumentException.class, () -> this.factory.createList(-NUM_BLINDS, ANTE_ID));
    }
}
