package it.unibo.balatrolt.model.impl.levels;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Suit;
import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;
import it.unibo.balatrolt.model.api.cards.modifier.ModifierBuilder;
import it.unibo.balatrolt.model.impl.cards.modifier.ModifierBuilderImpl;

/**
 * Represents a catalog with all the debuffs a boss blind can give,
 * one of the debuffs will be chosen randomly.
 * @author Benedetti Nicholas
 */
public final class BossModifiersCatalog {

    private static final List<Suit> SUITS = Collections.unmodifiableList(Arrays.asList(Suit.values()));
    private static final List<Rank> RANKS = Collections.unmodifiableList(Arrays.asList(Rank.values()));
    private static final Random RANDOM = new Random();
    private static final int CATALOG_BUFF = 4;

    private final ModifierBuilder modBuilder;
    private String desc;

    /**
     * Creates the builder object for the modifiers.
     */
    public BossModifiersCatalog() {
        this.modBuilder = new ModifierBuilderImpl();
        this.desc = "If you play a ";
    }

    /**
     * Return the description of the randomly chosen debuff.
     * @return the descrption of the debuff
     */
    String getDescription() {
        return this.desc;
    }

    /**
     * Returns a random debuff from the catalog.
     * @return a random debuff from the catalog
     */
    CombinationModifier getRandom() {
        switch (RANDOM.nextInt(CATALOG_BUFF)) {
            case 0 -> playedSuitBP();
            case 1 -> playedRankBP();
            case 2 -> playedSuitMP();
            case 3 -> playedRankMP();
            default -> { }
        }
        return this.modBuilder.build();
    }

    private void playedSuitBP() {
        final Suit suit = SUITS.get(RANDOM.nextInt(SUITS.size()));

        this.modBuilder.addPlayedCardBound(set -> set.stream()
            .map(PlayableCard::getSuit)
            .anyMatch(su -> su.equals(suit))
        );
        this.modBuilder.addBasePointsModifier(p -> 0);
        this.desc = this.desc.concat(suit + " card, it gets 0 base points");
    }

    private void playedRankBP() {
        final Rank rank = RANKS.get(RANDOM.nextInt(RANKS.size()));

        this.modBuilder.addPlayedCardBound(set -> set.stream()
            .map(PlayableCard::getRank)
            .anyMatch(ra -> ra.equals(rank))
        );
        this.modBuilder.addBasePointsModifier(p -> 0);
        this.desc = this.desc.concat(rank + " card, it gets 0 base points");
    }

    private void playedRankMP() {
        final Rank rank = RANKS.get(RANDOM.nextInt(RANKS.size()));

        this.modBuilder.addPlayedCardBound(set -> set.stream()
            .map(PlayableCard::getRank)
            .anyMatch(ra -> ra.equals(rank))
        );
        this.modBuilder.addMultiplierModifier(p -> 1.0);
        this.desc = this.desc.concat(rank + " card, it gets 1x multiplier");
    }

    private void playedSuitMP() {
        final Suit suit = SUITS.get(RANDOM.nextInt(SUITS.size()));

        this.modBuilder.addPlayedCardBound(set -> set.stream()
            .map(PlayableCard::getSuit)
            .anyMatch(su -> su.equals(suit))
        );
        this.modBuilder.addMultiplierModifier(p -> 1.0);
        this.desc = this.desc.concat(suit + " card, it gets 1x multiplier");
    }
}
