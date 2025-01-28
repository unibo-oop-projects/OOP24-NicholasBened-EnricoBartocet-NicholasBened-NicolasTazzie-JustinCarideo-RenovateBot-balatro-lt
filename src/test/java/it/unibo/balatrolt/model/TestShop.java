package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.balatrolt.model.api.Joker;
import it.unibo.balatrolt.model.api.JokerSupplier;
import it.unibo.balatrolt.model.api.Shop;
import it.unibo.balatrolt.model.impl.shop.JokerShop;
import it.unibo.balatrolt.model.impl.specialcard.JokerSupplierImpl;

class TestShop {
    private static final int DELTA_PRICE = 3;
    private static final int SIZE = 5;
    private Shop shop;

    @BeforeEach
    void createShop() {
        this.shop = new JokerShop(SIZE);
        this.shop.supply();
    }

    @Test
    void testCreation() {
        assertNotNull(this.shop.getSellableSpecialCards());
        assertEquals(this.shop.getSellableSpecialCards().size(), SIZE);
        for (final var item : this.shop.getSellableSpecialCards().entrySet()) {
            assertNotNull(item.getKey());
            assertNotNull(item.getValue());
            assertTrue(item.getValue() > 0);
        }
    }

    @Test
    void testBuy() {
        Joker j = getRandomJokerInShop();
        int currentMoney = this.shop.getSellableSpecialCards().get(j) + DELTA_PRICE;
        // can buy
        assertTrue(this.shop.buy(j, currentMoney));
        assertFalse(this.shop.getSellableSpecialCards().containsKey(j));
        assertFalse(this.shop.buy(j, currentMoney));
        j = getRandomJokerInShop();
        currentMoney = this.shop.getSellableSpecialCards().get(j) - DELTA_PRICE;
        // cannot buy
        assertFalse(this.shop.buy(j, currentMoney));
        assertTrue(this.shop.getSellableSpecialCards().containsKey(j));
    }

    private Joker getRandomJokerInShop() {
        // for some reason sometimes it breaks
        final JokerSupplier js = new JokerSupplierImpl();
        Joker j = js.getRandom();
        while (!this.shop.getSellableSpecialCards().containsKey(j)) {
            j = js.getRandom();
        }
        return j;
    }
}
