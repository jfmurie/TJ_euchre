package com.trick;

import com.Trick.Trick;
import com.card.Card;
import com.cardvalues.CardValue;
import com.player.AI;
import com.player.Player;
import com.player.UserPlayer;
import com.suits.Suit;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;

public class testTrick {

    @Test
    public void testTrickGWCI(){
        Trick testTrick = new Trick();
        Player[] players;
        Player userPlayer = new UserPlayer();
        Player ai1 = new AI(1);
        Player ai2 = new AI(2);
        Player ai3 = new AI(3);
        userPlayer.setLead(true);
        players = new Player[]{userPlayer, ai1, ai2, ai3};
        //create and add 4 cards
        Card C1 = new Card(Suit.DIAMONDS, CardValue.NINE);
        Card C2 = new Card(Suit.HEARTS, CardValue.NINE);
        Card C3 = new Card(Suit.SPADES, CardValue.NINE);
        Card C4 = new Card(Suit.CLUBS, CardValue.NINE);
        testTrick.playerPlaysCard(C1);
        testTrick.playerPlaysCard(C2);
        testTrick.playerPlaysCard(C3);
        testTrick.playerPlaysCard(C4);
        //testing that it returns right card
        Assert.assertEquals(testTrick.getCurrentWinningCardIndex(Suit.HEARTS), 1);
    }

    @Test
    public void testTrickGWCIRB(){
        Trick testTrick = new Trick();
        Player[] players;
        Player userPlayer = new UserPlayer();
        Player ai1 = new AI(1);
        Player ai2 = new AI(2);
        Player ai3 = new AI(3);
        userPlayer.setLead(true);
        players = new Player[]{userPlayer, ai1, ai2, ai3};
        //create and add 4 cards
        Card C1 = new Card(Suit.DIAMONDS, CardValue.NINE);
        Card C2 = new Card(Suit.HEARTS, CardValue.JACK);
        Card C3 = new Card(Suit.SPADES, CardValue.NINE);
        Card C4 = new Card(Suit.CLUBS, CardValue.NINE);
        testTrick.playerPlaysCard(C1);
        testTrick.playerPlaysCard(C2);
        testTrick.playerPlaysCard(C3);
        testTrick.playerPlaysCard(C4);
        //testing that it returns right card RIGHT BOWER
        Assert.assertEquals(testTrick.getCurrentWinningCardIndex(Suit.HEARTS), 1);
    }

    @Test
    public void testTrickGWCILB(){
        Trick testTrick = new Trick();
        Player[] players;
        Player userPlayer = new UserPlayer();
        Player ai1 = new AI(1);
        Player ai2 = new AI(2);
        Player ai3 = new AI(3);
        userPlayer.setLead(true);
        players = new Player[]{userPlayer, ai1, ai2, ai3};
        //create and add 4 cards
        Card C1 = new Card(Suit.DIAMONDS, CardValue.JACK);
        Card C2 = new Card(Suit.HEARTS, CardValue.NINE);
        Card C3 = new Card(Suit.SPADES, CardValue.NINE);
        Card C4 = new Card(Suit.CLUBS, CardValue.NINE);
        testTrick.playerPlaysCard(C1);
        testTrick.playerPlaysCard(C2);
        testTrick.playerPlaysCard(C3);
        testTrick.playerPlaysCard(C4);
        //testing that it returns right card LEFT BOWER
        Assert.assertEquals(testTrick.getCurrentWinningCardIndex(Suit.HEARTS), 0);
    }

    @Test
    public void testTrickGWPI(){
        Trick testTrick = new Trick();
        Player[] players;
        Player userPlayer = new UserPlayer();
        Player ai1 = new AI(1);
        Player ai2 = new AI(2);
        Player ai3 = new AI(3);
        userPlayer.setLead(true);
        players = new Player[]{userPlayer, ai1, ai2, ai3};
        //create and add 4 cards
        Card C1 = new Card(Suit.DIAMONDS, CardValue.NINE);
        Card C2 = new Card(Suit.HEARTS, CardValue.NINE);
        Card C3 = new Card(Suit.SPADES, CardValue.NINE);
        Card C4 = new Card(Suit.CLUBS, CardValue.NINE);
        testTrick.playerPlaysCard(C1);
        testTrick.playerPlaysCard(C2);
        testTrick.playerPlaysCard(C3);
        testTrick.playerPlaysCard(C4);
//        int output = testTrick.getWinningPlayerIndex(players, Suit.DIAMONDS);
//        System.out.println("Winning Player Index is: " + output);
        assertEquals(testTrick.getWinningPlayerIndex(players, Suit.DIAMONDS), 0);
        Assert.assertEquals(testTrick.getWinningPlayerIndex(players, Suit.DIAMONDS), 0);
    }

}
