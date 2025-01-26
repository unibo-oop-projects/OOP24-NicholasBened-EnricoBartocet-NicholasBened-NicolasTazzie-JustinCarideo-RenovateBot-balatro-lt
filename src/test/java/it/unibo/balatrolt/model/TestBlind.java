package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.balatrolt.model.api.levels.Blind;
import it.unibo.balatrolt.model.impl.levels.BlindConfiguration;
import it.unibo.balatrolt.model.impl.levels.BlindImpl;

class TestBlind {
    private static final int BLIND_ID = 1;
    private static final int BASE_CHIPS = 1000;
    private static final int REWARD = 2;
    private static final int CHIPS_EARNED = 222;
    private Blind blind;

    @BeforeEach
    void init() {
        this.blind = new BlindImpl(new BlindConfiguration(BLIND_ID, BASE_CHIPS, REWARD));
    }

    @Test
    void testConfiguration() {
        assertEquals(BLIND_ID, this.blind.getBlindNumber());
        assertEquals(BASE_CHIPS, this.blind.getMinimumChips());
        assertEquals(REWARD, this.blind.getReward());
    }

    @Test
    void testIncrementChips() {
        this.blind.incrementChips(CHIPS_EARNED);
        assertEquals(CHIPS_EARNED, this.blind.getCurrentChips());
        assertThrows(IllegalArgumentException.class, () -> this.blind.incrementChips(-CHIPS_EARNED));
    }

    @Test
    void testDefeat() {
        assertFalse(this.blind.isOver());
        for (int i = 0; i <= BASE_CHIPS / CHIPS_EARNED; i++) {
            this.blind.incrementChips(CHIPS_EARNED);
        }
        assertTrue(this.blind.isOver());
    }
}
