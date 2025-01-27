package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.balatrolt.model.api.levels.Ante;
import it.unibo.balatrolt.model.api.levels.AnteFactory;
import it.unibo.balatrolt.model.impl.levels.AnteFactoryImpl;

class TestAnteFactory {
    private static final int ANTE_ID = 1;
    private static final int NUM_ANTE = 10;
    private static final int NUM_BLINDS = 5;
    private BinaryOperator<Integer> baseChipsCalculator;
    private UnaryOperator<Integer> rewardCalculator;
    private AnteFactory factory;

    @BeforeEach
    void init() {
        baseChipsCalculator = (a, b) -> a * 10 + (b + 2) * 4;
        rewardCalculator = UnaryOperator.identity();
        this.factory = new AnteFactoryImpl(NUM_BLINDS, baseChipsCalculator, rewardCalculator);
    }

    @Test
    void testCreation() {
        assertNotNull(this.factory);
    }

    @Test
    void testSingleAnte() {
        final Ante newAnte = this.factory.fromId(ANTE_ID);
        assertEquals(ANTE_ID, newAnte.getAnteNumber());
        assertEquals(NUM_BLINDS, newAnte.getBlinds().size());
        final int blindId = newAnte.getCurrentBlind().get().getBlindNumber();
        assertEquals(baseChipsCalculator.apply(ANTE_ID, blindId), newAnte.getCurrentBlind().get().getMinimumChips());
        assertEquals(rewardCalculator.apply(blindId), newAnte.getCurrentBlind().get().getReward());
    }

    @Test
    void testInvalidFactory() {
        assertThrows(IllegalArgumentException.class, () -> new AnteFactoryImpl(0, baseChipsCalculator, rewardCalculator));
        assertThrows(NullPointerException.class, () -> new AnteFactoryImpl(NUM_BLINDS, null, rewardCalculator));
        assertThrows(NullPointerException.class, () -> new AnteFactoryImpl(NUM_BLINDS, baseChipsCalculator, null));
    }

    @Test
    void testList() {
        final List<Ante> list = this.factory.generateList(NUM_ANTE);
        assertNotNull(list);
        assertEquals(NUM_ANTE, list.size());
    }

    @Test
    void testInvalidList() {
        assertThrows(IllegalArgumentException.class, () -> this.factory.generateList(-NUM_ANTE));
    }
}
