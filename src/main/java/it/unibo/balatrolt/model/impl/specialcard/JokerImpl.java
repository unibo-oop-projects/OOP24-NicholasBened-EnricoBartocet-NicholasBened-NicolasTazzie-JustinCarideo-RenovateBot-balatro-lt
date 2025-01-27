package it.unibo.balatrolt.model.impl.specialcard;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.Joker;
import it.unibo.balatrolt.model.api.Modifier;

/**
 * Implementation of {@link Joker}.
 */
public final class JokerImpl extends BaseSpecialCard implements Joker {
    private final Optional<Modifier> modifier;

    /**
     * Joker constructor.
     *
     * @param name        joker name
     * @param description a description of what the joker does
     * @param price       card price
     * @param modifier    modifier
     * @throws NullPointerException if the modifier is null
     */
    public JokerImpl(final String name, final String description, final int price, final Modifier modifier) {
        super(name, description, price);
        this.modifier = Optional.fromNullable(modifier);
    }

    @Override
    public Optional<Modifier> getModifier() {
        return this.modifier;
    }

    @Override
    public String toString() {
        return "JokerImpl [name=" + getName() + ", description=" + getDescription()
                + ", price=" + getShopPrice() + ", sellValue=" + getToSellValue() + "]";
    }
}
