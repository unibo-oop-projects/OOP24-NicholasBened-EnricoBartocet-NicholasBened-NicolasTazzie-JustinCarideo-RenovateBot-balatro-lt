package it.unibo.balatrolt.model.impl.specialCard;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.Joker;
import it.unibo.balatrolt.model.api.Modifier;

public final class JokerImpl extends BaseSpecialCard implements Joker {
    private final Optional<Modifier> modifier;

    /**
     * Joker constructor.
     *
     * @param name        joker name
     * @param description a description of what the joker does
     * @param modifier    modifier
     * @throws NullPointerException if the modifier is null
     */
    public JokerImpl(String name, String description, Modifier modifier) {
        super(name, description);
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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        JokerImpl other = (JokerImpl) obj;
        if (modifier == null) {
            if (other.modifier != null)
                return false;
        } else if (!modifier.equals(other.modifier) || !super.equals(obj))
            return false;
        return true;
    }
}
