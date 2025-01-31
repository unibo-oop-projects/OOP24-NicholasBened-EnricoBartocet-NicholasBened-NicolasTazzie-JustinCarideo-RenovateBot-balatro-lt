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
import it.unibo.balatrolt.model.api.levels.BlindModifier;
import it.unibo.balatrolt.model.impl.levels.AnteFactoryImpl;
import it.unibo.balatrolt.model.impl.levels.BlindModifierImpl;

class TestAnteFactory {
    private static final int ANTE_ID = 1;
    private static final int NUM_ANTE = 10;
    private static final int NUM_BLINDS = 5;
    private BinaryOperator<Integer> baseChipsCalculator;
    private UnaryOperator<Integer> rewardCalculator;
    private AnteFactory factory;
    private BlindModifier modifier;

    @BeforeEach
    void init() {
        this.modifier = new BlindModifierImpl(n -> n - 1, n -> n + 1, n -> n / 2);
        baseChipsCalculator = (a, b) -> a * 10 + (b + 2) * 4;
        rewardCalculator = UnaryOperator.identity();
        this.factory = new AnteFactoryImpl(NUM_BLINDS, baseChipsCalculator, rewardCalculator, this.modifier);
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
        // Used to avoid line length > 130 characters
        final var npException = NullPointerException.class;
        final var argException = IllegalArgumentException.class;
        assertThrows(argException, () -> new AnteFactoryImpl(0, baseChipsCalculator, rewardCalculator, modifier));
        assertThrows(npException, () -> new AnteFactoryImpl(NUM_BLINDS, null, rewardCalculator, modifier));
        assertThrows(npException, () -> new AnteFactoryImpl(NUM_BLINDS, baseChipsCalculator, null, modifier));
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
