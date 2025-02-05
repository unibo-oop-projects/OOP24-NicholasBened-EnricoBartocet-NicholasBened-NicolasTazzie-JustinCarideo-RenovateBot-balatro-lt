package it.unibo.balatrolt.model.impl.cards.specialcard;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;
import it.unibo.balatrolt.model.api.cards.specialcard.Joker;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerTier;

/**
 * Implementation of {@link Joker}.
 * @author Nicolas Tazzieri
 */
public final class JokerImpl extends BaseSpecialCard implements Joker {
    private final Optional<CombinationModifier> modifier;
    private final JokerTier tier;

    /**
     * Joker constructor.
     *
     * @param name        joker name
     * @param description a description of what the joker does
     * @param price       card price
     * @param modifier    modifier
     * @param tier        joker tier
     * @throws NullPointerException if the modifier is null
     */
    public JokerImpl(final String name,
        final String description,
        final int price,
        final CombinationModifier modifier,
        final JokerTier tier) {
        super(name, description, price);
        this.modifier = Optional.fromNullable(modifier);
        this.tier = tier;
    }

    @Override
    protected Optional<CombinationModifier> getInnerModifier() {
        return this.modifier;
    }

    @Override
    public String toString() {
        return "JokerImpl [name=" + getName() + ", description=" + getDescription()
                + ", price=" + getShopPrice() + ", sellValue=" + getToSellValue() + "]";
    }

    @Override
    public JokerTier getTier() {
        return this.tier;
    }
}
