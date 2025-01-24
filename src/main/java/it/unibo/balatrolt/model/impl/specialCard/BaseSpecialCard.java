package it.unibo.balatrolt.model.impl.specialCard;


import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.SpecialCard;

/**
 * It's a Basic SpecialCard, it has a name and a description, but doesn't have any modifier.
 */
public class BaseSpecialCard implements SpecialCard {
    private final String name;
    private final String description;

    /**
     * Constructor
     * @param name card name
     * @param description description of what the card does
     */
    public BaseSpecialCard(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Optional<Modifier> getModifier() {
        return Optional.absent();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaseSpecialCard other = (BaseSpecialCard) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        return true;
    }
}
