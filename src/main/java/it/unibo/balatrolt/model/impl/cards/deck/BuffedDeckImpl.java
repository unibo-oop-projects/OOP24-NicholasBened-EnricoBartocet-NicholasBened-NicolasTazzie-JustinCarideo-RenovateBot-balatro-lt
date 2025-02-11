package it.unibo.balatrolt.model.impl.cards.deck;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.model.api.cards.BuffedDeck;
import it.unibo.balatrolt.model.api.levels.BlindModifier;

/**
 * A simple implementation of a {@link BuffedDeck}.
 * @author Enrico Bartocetti
 */
public final class BuffedDeckImpl extends DeckImpl implements BuffedDeck {

    private final String name;
    private final String description;
    private final BlindModifier modifier;

    /**
     * @param name the name of the Deck
     * @param desc the description of its characteristics
     * @param modifier the changes it makes to the blind
     */
    public BuffedDeckImpl(final String name, final String desc, final BlindModifier modifier) {
        Preconditions.checkArgument(name != null && !name.isBlank());
        Preconditions.checkArgument(desc != null && !desc.isBlank());
        this.name = name;
        this.description = desc;
        this.modifier = Preconditions.checkNotNull(modifier);
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
    public BlindModifier getModifier() {
        return this.modifier;
    }

}
