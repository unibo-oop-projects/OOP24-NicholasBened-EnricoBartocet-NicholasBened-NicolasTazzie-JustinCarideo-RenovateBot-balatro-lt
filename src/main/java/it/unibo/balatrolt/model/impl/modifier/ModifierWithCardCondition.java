package it.unibo.balatrolt.model.impl.modifier;

import java.util.List;
import java.util.function.Predicate;

import it.unibo.balatrolt.model.api.Card;
import it.unibo.balatrolt.model.api.Modifier;

public class ModifierWithCardCondition extends ConditionalModifier<List<Card>>{
    public ModifierWithCardCondition(Modifier base, Predicate<List<Card>> condition) {
        super(condition, base);
    }
}
