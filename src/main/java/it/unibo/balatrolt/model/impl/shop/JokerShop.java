package it.unibo.balatrolt.model.impl.shop;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unibo.balatrolt.model.api.JokerSupplier;
import it.unibo.balatrolt.model.api.Shop;
import it.unibo.balatrolt.model.api.SpecialCard;
import it.unibo.balatrolt.model.impl.specialcard.JokerSupplierImpl;

/**
 * A shop that is only supplied with Jokers.
 */
public class JokerShop implements Shop {
    private Map<SpecialCard, Integer> cards;
    private final JokerSupplier supplier = new JokerSupplierImpl();
    private final int size;

    /**
     * Shop constructor.
     * @param size the maximum size of the shop.
     */
    public JokerShop(final int size) {
        supply();
        this.size = size;
    }

    @Override
    public Map<SpecialCard, Integer> getSellableSpecialCards() {
        return Map.copyOf(this.cards);
    }

    @Override
    public boolean buy(final SpecialCard toBuy, final int money) {
        if (this.cards.containsKey(toBuy)) {
            if (this.cards.get(toBuy) < money) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void supply() {
        this.cards = Stream.iterate(0, i -> i < this.size, i -> i + 1)
            .map(i -> this.supplier.getRandom())
            .collect(Collectors.toMap(j -> j, j -> j.getShopPrice()));
    }
}
