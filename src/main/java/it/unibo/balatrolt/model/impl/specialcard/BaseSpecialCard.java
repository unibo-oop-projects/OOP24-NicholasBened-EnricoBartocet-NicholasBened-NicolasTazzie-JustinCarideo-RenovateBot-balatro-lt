package it.unibo.balatrolt.model.impl.specialcard;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.SpecialCard;

/**
 * It's a Basic SpecialCard, it has a name and a description, but doesn't have
 * any modifier.
 */
public abstract class BaseSpecialCard implements SpecialCard {
    private final String name;
    private final String description;
    private final int price;

    /**
     * Constructor.
     *
     * @param name        card name
     * @param description description of what the card does
     * @param price       card selling price
     */
    public BaseSpecialCard(final String name, final String description, final int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final String getDescription() {
        return this.description;
    }

    @Override
    public final Optional<Modifier> getModifier() {
        return getInnerModifier();
    }

    /**
     * It should return an inner modifier relative to the specific implementation.
     * @return inner modifier
     */
    protected abstract Optional<Modifier> getInnerModifier();

    @Override
    public final int getShopPrice() {
        return this.price;
    }

    @Override
    public final int getToSellValue() {
        return this.price / 2;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseSpecialCard other = (BaseSpecialCard) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        return true;
    }

}
