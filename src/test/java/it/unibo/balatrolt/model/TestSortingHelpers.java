package it.unibo.balatrolt.model;

import static org.junit.Assert.*;

import java.util.*;

import it.unibo.balatrolt.model.api.PlayableCard;

public class TestSortingHelpers {
    
    private List<PlayableCard> hand;

    @org.junit.Before
	public void init() {
		this.hand = new LinkedList<>();
	}
}
