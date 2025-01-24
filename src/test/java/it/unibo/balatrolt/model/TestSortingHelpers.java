package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

import java.util.*;

import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.PlayableCard.Suit;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.PlayableCardImpl;
import it.unibo.balatrolt.model.impl.SortingPlayableHelpers;

public class TestSortingHelpers {
    
    private List<PlayableCard> hand;

	@BeforeEach
	public void init() {
		this.hand = fill();
	}

	@Test
	public void testSortEmptyHand() {
		List<PlayableCard> emptyHand = Collections.emptyList();
		assertEquals(emptyHand, SortingPlayableHelpers.sortingByRank(emptyHand));
	}

	public List<PlayableCard> fill() {
		return List.of(
			new PlayableCardImpl(new Pair<Rank,Suit>(Rank.SEVEN, Suit.CLUBS)),
			new PlayableCardImpl(new Pair<Rank,Suit>(Rank.FIVE, Suit.SPADES)),
			new PlayableCardImpl(new Pair<Rank,Suit>(Rank.KING, Suit.HEARTS)),
			new PlayableCardImpl(new Pair<Rank,Suit>(Rank.ACE, Suit.DIAMONDS)),
			new PlayableCardImpl(new Pair<Rank,Suit>(Rank.SIX, Suit.CLUBS))
		);
	}

	@Test
	public void testSortByRank() {
		assertEquals(List.of(
			new PlayableCardImpl(new Pair<Rank,Suit>(Rank.ACE, Suit.DIAMONDS)),
			new PlayableCardImpl(new Pair<Rank,Suit>(Rank.FIVE, Suit.SPADES)),
			new PlayableCardImpl(new Pair<Rank,Suit>(Rank.SIX, Suit.CLUBS)),
			new PlayableCardImpl(new Pair<Rank,Suit>(Rank.SEVEN, Suit.CLUBS)),
			new PlayableCardImpl(new Pair<Rank,Suit>(Rank.KING, Suit.HEARTS))
		), 
			SortingPlayableHelpers.sortingByRank(hand)	
		);
	}
}
