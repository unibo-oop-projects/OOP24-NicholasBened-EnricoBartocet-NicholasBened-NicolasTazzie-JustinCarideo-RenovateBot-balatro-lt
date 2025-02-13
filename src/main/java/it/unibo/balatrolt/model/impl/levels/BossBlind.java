package it.unibo.balatrolt.model.impl.levels;

import java.util.List;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.PlayerStatus;
import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;
import it.unibo.balatrolt.model.api.cards.specialcard.SpecialCard;
import it.unibo.balatrolt.model.api.combination.Combination;
import it.unibo.balatrolt.model.api.levels.BlindConfiguration;
import it.unibo.balatrolt.model.api.levels.BlindModifier;
import it.unibo.balatrolt.model.impl.combination.PlayedHandImpl;

/**
 * Models a boss Blind which gives the player a debuff.
 * The debuff can be on the chips needed to defeat the blind,
 * or in the cards (their rank or suit).
 * @author Benedetti Nicholas
 */
public final class BossBlind extends AbstractBlind {

    private final BossModifiersCatalog bossMod;
    private final CombinationModifier combMod;

    /**
     * Sets the blind and gets a random debuff from the catalog.
     * @param config the blind's configuration
     * @param modifier blind's modifiers
     */
    public BossBlind(final BlindConfiguration config, final BlindModifier modifier) {
        super(config, modifier);
        this.bossMod = new BossModifiersCatalog();
        this.combMod = this.bossMod.getRandom();
    }

    @Override
    protected int evaluateChips(final List<PlayableCard> toPlay, final PlayerStatus playerStatus) {
        final Combination combination = new PlayedHandImpl(toPlay).evaluateCombination();

        playerStatus.specialCards().stream()
            .map(SpecialCard::getModifier)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .forEach(m -> {
                m.setGameStatus(getGameStatus(combination, toPlay, playerStatus));
                combination.applyModifier(m);
            });
        this.combMod.setGameStatus(getGameStatus(combination, toPlay, playerStatus));
        combination.applyModifier(this.combMod);

        return combination.getChips();
    }

    @Override
    public String getDescription() {
        return this.bossMod.getDescription();
    }
}
