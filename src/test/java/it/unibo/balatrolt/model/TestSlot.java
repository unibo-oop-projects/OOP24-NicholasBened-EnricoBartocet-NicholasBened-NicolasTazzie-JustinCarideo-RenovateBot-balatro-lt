package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.Slot;
import it.unibo.balatrolt.model.api.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.PlayableCard.Suit;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.PlayableCardImpl;
import it.unibo.balatrolt.model.impl.SlotImpl;

/**
 * Tester for Slot and it's methods.
 */
final class TestSlot {
    private Slot slot;
    private PlayableCard card;
    private PlayableCard otheCard;
    private List<PlayableCard> list;
    private static final Rank RANK = Rank.ACE;
    private static final Suit SUIT = Suit.SPADES;
    private static final int SIZE = 3;

    @BeforeEach
    void init() {
        this.slot = new SlotImpl(SIZE);
        card = new PlayableCardImpl(new Pair<>(RANK, SUIT));
        otheCard = new PlayableCardImpl(new Pair<>(Rank.TWO, Suit.CLUBS));
        list = new ArrayList<>(Stream.iterate(otheCard, e -> e)
            .limit(SIZE - 1)
            .toList());
    }

    @Test
    void testConfiguration() {
        assertEquals(0, this.slot.getSize());
        assertEquals(List.of(), this.slot.getCards());
    }

    @Test
    void testAdd() {
        this.slot.addCard(card);
        assertEquals(List.of(card), this.slot.getCards());

        this.slot.addAll(list);
        assertEquals(List.of(card, otheCard, otheCard), this.slot.getCards());

        assertThrows(IllegalArgumentException.class, () -> this.slot.addCard(card));
        assertEquals(List.of(card, otheCard, otheCard), this.slot.getCards());
    }

    @Test
    void testRemove() {
        this.slot.addAll(list);
        this.slot.addCard(card);
        assertEquals(List.of(otheCard, otheCard, card), this.slot.getCards());

        this.slot.remove(otheCard);
        assertEquals(List.of(otheCard, card), this.slot.getCards());

        this.slot.remove(card);
        assertEquals(List.of(otheCard), this.slot.getCards());

        this.slot.remove(0);
        assertEquals(List.of(), this.slot.getCards());

        this.slot.addAll(list);
        assertEquals(List.of(otheCard, otheCard), this.slot.getCards());

        this.slot.clear();
        assertEquals(List.of(), this.slot.getCards());
    }
}
