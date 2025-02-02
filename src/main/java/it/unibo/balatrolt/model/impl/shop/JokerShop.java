package it.unibo.balatrolt.model.impl.shop;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.Supplier;

import it.unibo.balatrolt.model.api.Shop;
import it.unibo.balatrolt.model.api.cards.specialcard.Joker;
import it.unibo.balatrolt.model.api.cards.specialcard.SpecialCard;
import it.unibo.balatrolt.model.impl.cards.specialcard.JokerSupplierImpl;

/**
 * A shop that is only supplied with Jokers.
 */
public final class JokerShop implements Shop {
    private Map<SpecialCard, Integer> cards = Map.of();
    private final Supplier<Joker> supplier = new JokerSupplierImpl();
    private final int size;

    /**
     * Shop constructor.
     * @param size the maximum size of the shop.
     * @throws NullPointerException if size is null
     */
    public JokerShop(final int size) {
        supply();
        this.size = checkNotNull(size, "Size can't be null");
    }

    @Override
    public Map<SpecialCard, Integer> getSellableSpecialCards() {
        return Map.copyOf(this.cards);
    }

    @Override
    public boolean buy(final SpecialCard toBuy, final int money) {
        if (this.cards.containsKey(toBuy) && this.cards.get(toBuy) < money) {
            this.cards.remove(toBuy);
            return true;
        }
        return false;
    }

    @Override
    public void supply() {
        while (this.cards.size() < this.size) {
            this.cards = Stream.iterate(0, i -> i < this.size, i -> i + 1)
                .map(i -> this.supplier.get())
                .distinct()
                .collect(Collectors.toMap(j -> j, Joker::getShopPrice));
        }
    }

    @Override
    public void reset() {
        this.cards.clear();
    }
}
