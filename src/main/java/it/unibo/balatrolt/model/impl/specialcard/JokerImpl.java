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
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((modifier == null) ? 0 : modifier.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JokerImpl other = (JokerImpl) obj;
        if (modifier == null) {
            if (other.modifier != null) {
                return false;
            }
        } else if (!modifier.equals(other.modifier) || !super.equals(obj)) {
            return false;
        }
        return true;
    }
}
