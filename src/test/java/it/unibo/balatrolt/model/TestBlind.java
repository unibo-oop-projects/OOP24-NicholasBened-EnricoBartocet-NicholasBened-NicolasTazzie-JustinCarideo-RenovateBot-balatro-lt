package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.levels.Blind;
import it.unibo.balatrolt.model.api.levels.BlindModifier;
import it.unibo.balatrolt.model.impl.BuffedDeckImpl;
import it.unibo.balatrolt.model.impl.PlayerStatusImpl;
import it.unibo.balatrolt.model.impl.combination.PlayedHandImpl;
import it.unibo.balatrolt.model.impl.levels.BlindConfigurationImpl;
import it.unibo.balatrolt.model.impl.levels.BlindImpl;
import it.unibo.balatrolt.model.impl.levels.BlindModifierImpl;
import it.unibo.balatrolt.model.impl.levels.BlindStats;

class TestBlind {
    private static final int BLIND_ID = 1;
    private static final int BASE_CHIPS = 1000;
    private static final int REWARD = 2;
    private BlindModifier blindModifier;
    private Blind blind;

    @BeforeEach
    void init() {
        this.blindModifier = new BlindModifierImpl(n -> n - 1, n -> n + 1, n -> n / 2);
        this.blind = new BlindImpl(
            new BlindConfigurationImpl(BLIND_ID, BASE_CHIPS, REWARD),
            blindModifier
        );
    }

    @Test
    void testCreation() {
        assertNotNull(this.blind);
        assertEquals(BLIND_ID, this.blind.getBlindNumber());
        assertEquals(BASE_CHIPS, this.blind.getMinimumChips());
        assertEquals(REWARD, this.blind.getReward());
        assertEquals(0, this.blind.getCurrentChips());
        assertEquals(this.blindModifier.getNewHands(BlindStats.BASE_HANDS), this.blind.getRemainingHands());
        assertEquals(this.blindModifier.getNewDiscards(BlindStats.BASE_DISCARDS), this.blind.getRemainingDiscards());
    }

    @Test
    void testConfiguration() {
        assertThrows(IllegalArgumentException.class, () -> new BlindConfigurationImpl(BLIND_ID, -BASE_CHIPS, REWARD));
        assertThrows(IllegalArgumentException.class, () -> new BlindConfigurationImpl(BLIND_ID, BASE_CHIPS, -REWARD));
    }

    @Test
    void testDiscards() {
        // Finish the discards and assert that no chips are earned
        for (int i = 0; i < this.blindModifier.getNewDiscards(BlindStats.BASE_DISCARDS); i++) {
            final List<PlayableCard> toDiscard = this.blind.getHandCards().subList(0, 2);
            this.blind.discardPlayableCards(toDiscard);
            toDiscard.forEach(c -> assertFalse(this.blind.getHandCards().contains(c)));
        }
        assertEquals(0, this.blind.getRemainingDiscards());
        assertEquals(0, this.blind.getCurrentChips());
    }

    @Test
    void testGamePlay() {
        // Finish the hands and check the status and the amount of chips
        assertEquals(0, this.blind.getCurrentChips());
        int chips = 0;
        for (int i = 0; i < this.blindModifier.getNewHands(BlindStats.BASE_HANDS); i++) {
            assertEquals(Blind.Status.IN_GAME, this.blind.getStatus());
            final List<PlayableCard> toPlay = this.blind.getHandCards().subList(0, 2);
            this.blind.playHand(toPlay, new PlayerStatusImpl(new BuffedDeckImpl("Test", "Test", blindModifier), List.of(), 0));
            toPlay.forEach(c -> assertFalse(this.blind.getHandCards().contains(c)));
            chips += this.blindModifier.getNewChips(new PlayedHandImpl(toPlay).evaluateCombination().getChips());
        }
        assertEquals(0, this.blind.getRemainingHands());
        assertEquals(chips, this.blind.getCurrentChips());
        assertEquals(chips >= blind.getMinimumChips() ? Blind.Status.DEFEATED : Blind.Status.GAME_OVER, blind.getStatus());
    }
}
