package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.balatrolt.model.api.levels.Blind;
import it.unibo.balatrolt.model.api.levels.BlindFactory;
import it.unibo.balatrolt.model.impl.levels.BlindFactoryImpl;
import it.unibo.balatrolt.model.impl.levels.BlindModifierImpl;

class TestBlindFactory {
    private static final int NUM_BLINDS = 5;
    private static final int ANTE_ID = 3;
    private static final int BLIND_ID = 2;

    private BlindFactory factory;
    private BinaryOperator<Integer> baseChipsCalculator;
    private UnaryOperator<Integer> rewardCalculator;

    @BeforeEach
    void init() {
        baseChipsCalculator = (a, b) -> a * 10 + (b + 2) * 4;
        rewardCalculator = UnaryOperator.identity();
        this.factory = new BlindFactoryImpl(
            baseChipsCalculator,
            rewardCalculator,
            new BlindModifierImpl(n -> n - 1, n -> n + 1, n -> n / 2)
        );
    }

    @Test
    void testCreation() {
        assertNotNull(this.factory);
    }

    @Test
    void testSingleBlind() {
        final Blind newBlind = this.factory.fromIds(ANTE_ID, BLIND_ID);
        assertEquals(2, newBlind.getBlindNumber());
        assertEquals(baseChipsCalculator.apply(ANTE_ID, BLIND_ID), newBlind.getMinimumChips());
        assertEquals(rewardCalculator.apply(BLIND_ID), newBlind.getReward());
        assertEquals(0, newBlind.getCurrentChips());
        assertEquals(Blind.Status.IN_GAME, newBlind.getStatus());
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
