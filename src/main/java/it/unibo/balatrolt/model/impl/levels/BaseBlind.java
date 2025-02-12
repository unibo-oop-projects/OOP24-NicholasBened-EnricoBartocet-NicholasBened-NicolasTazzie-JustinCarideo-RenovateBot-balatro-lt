package it.unibo.balatrolt.model.impl.levels;

import java.util.List;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.PlayerStatus;
import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.specialcard.SpecialCard;
import it.unibo.balatrolt.model.api.combination.Combination;
import it.unibo.balatrolt.model.api.levels.BlindConfiguration;
import it.unibo.balatrolt.model.api.levels.BlindModifier;
import it.unibo.balatrolt.model.impl.combination.PlayedHandImpl;

/**
 * Implementation of an AbstractBlind which doesn't have any debuff.
 */
public final class BaseBlind extends AbstractBlind {

    /**
     * Create a new base blind.
     * @param config the blind configuration
     * @param modifier the blind modifier
     */
    public BaseBlind(final BlindConfiguration config, final BlindModifier modifier) {
        super(config, modifier);
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
        return combination.getChips();
    }

    @Override
    public String getDescription() {
        return "Just a normal blind";
    }
}
