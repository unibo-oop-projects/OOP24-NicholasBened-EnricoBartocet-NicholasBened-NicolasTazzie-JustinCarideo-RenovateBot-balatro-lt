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
 * //TODO: ll
 */
public class BaseBlind extends AbstractBlind {

    public BaseBlind(BlindConfiguration config, BlindModifier modifier) {
        super(config, modifier);
    }

    @Override
    protected int evaluateChips(List<PlayableCard> toPlay, PlayerStatus playerStatus) {
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
