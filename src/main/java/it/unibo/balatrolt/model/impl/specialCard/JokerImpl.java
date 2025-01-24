package it.unibo.balatrolt.model.impl.specialCard;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.Joker;
import it.unibo.balatrolt.model.api.Modifier;

public final class JokerImpl extends BaseSpecialCard implements Joker {
    private final Optional<Modifier> modifier;

    /**
     * Joker constructor
     * @param name joker name
     * @param description a description of what the joker does
     * @param modifier modifier
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
}
