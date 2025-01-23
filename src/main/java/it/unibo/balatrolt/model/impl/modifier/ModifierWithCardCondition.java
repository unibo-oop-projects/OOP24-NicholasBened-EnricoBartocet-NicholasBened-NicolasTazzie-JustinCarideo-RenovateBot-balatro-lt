package it.unibo.balatrolt.model.impl.modifier;

import java.util.Set;
import java.util.function.Predicate;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.Card;
import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.ModifierStatsSupplier;

public class ModifierWithCardCondition extends ConditionalModifier<Set<Card>>{
    public ModifierWithCardCondition(Modifier base, Predicate<Set<Card>> condition) {
        super(condition, base);
    }

    @Override
    protected boolean canApply() {
        Optional<ModifierStatsSupplier> stats = super.getStats();
        if(!stats.isPresent()) {
            return false;
        }
        Optional<Set<Card>> playedCards = stats.get().getPlayedCards();
        if(!playedCards.isPresent()) {
            return false;
        }
        return super.getCondition().test(playedCards.get());
    }
}
