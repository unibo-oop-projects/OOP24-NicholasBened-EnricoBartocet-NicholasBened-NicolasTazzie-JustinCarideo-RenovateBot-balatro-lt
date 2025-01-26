package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.levels.Ante;
import it.unibo.balatrolt.model.impl.levels.AnteConfiguration;
import it.unibo.balatrolt.model.impl.levels.AnteImpl;

class TestAnte {
    private static final int ANTE_ID = 2;
    private static final int NUM_BLINDS = 3;
    private Ante ante;

    @BeforeEach
    void init() {
        this.ante = new AnteImpl(new AnteConfiguration(ANTE_ID, NUM_BLINDS, (a, b) -> a * b, Function.identity()));
    }

    @Test
    void testCreation() {
        assertNotNull(this.ante);
        assertEquals(ANTE_ID, this.ante.getAnteNumber());
        assertNotNull(this.ante.getBlinds());
        assertEquals(NUM_BLINDS, this.ante.getBlinds().size());
    }

    @Test
    void testConfiguration() {
        assertThrows(IllegalArgumentException.class, () -> new AnteConfiguration(ANTE_ID, NUM_BLINDS, null, Function.identity()));
        assertThrows(IllegalArgumentException.class, () -> new AnteConfiguration(ANTE_ID, NUM_BLINDS, (a, b) -> a * b, null));
        assertThrows(IllegalArgumentException.class, () -> new AnteConfiguration(ANTE_ID, 0, (a, b) -> a, Function.identity()));
    }

    @Test
    void testBlindAdvancement() {
        for (int i = 0; i < NUM_BLINDS; i++) {
            assertFalse(this.ante.isOver());
            assertNotEquals(Optional.absent(), this.ante.getCurrentBlind());
            this.ante.nextBlind();
        }
        assertEquals(Optional.absent(), this.ante.getCurrentBlind());
        assertTrue(this.ante.isOver());
        this.ante.nextBlind();
        assertEquals(Optional.absent(), this.ante.getCurrentBlind());
        assertTrue(this.ante.isOver());
    }
}
