package com.deck;


import com.player.AI;
import com.player.Player;
import com.player.UserPlayer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test Class for deck
 */
public class testDeck {

    Deck deck;
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Player[] players;

    @Before
    public void setUp() {

        deck = new Deck();
        player1 = new UserPlayer();
        player2 = new AI();
        player3 = new AI();
        player4 = new AI();
        players = new Player[]{player1, player2, player3, player4};

    }


    @Test
    public void testDeal_player1has5cards() {
        deck.deal(players, 0);
        assertEquals(5 ,player1.getPlayerHand().size());
    }


    @Test
    public void testDeal_player2has5cards() {
        deck.deal(players, 0);
        assertEquals(5 ,player2.getPlayerHand().size());
    }


    @Test
    public void testDeal_player3has5cards() {
        deck.deal(players, 0);
        assertEquals(5 ,player3.getPlayerHand().size());
    }


    @Test
    public void testDeal_player4has5cards() {
        deck.deal(players, 0);
        assertEquals(5 ,player4.getPlayerHand().size());
    }



}
