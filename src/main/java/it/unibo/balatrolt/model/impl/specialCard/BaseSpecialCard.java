package it.unibo.balatrolt.model.impl.specialCard;


import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.SpecialCard;

/**
 * It's a BasicSpecialCard, it has a name and a description, but doesn't have any modifier.
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
}
