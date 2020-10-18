package com.player;

import com.card.Card;
import com.cardvalues.CardValue;
import com.suits.Suit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;

import java.util.ArrayList;

public class testAI {

    @Test
    public void testGetCards1(){
        ArrayList<Card> testCards = new ArrayList<>();
        testCards.add(new Card(Suit.DIAMONDS, CardValue.JACK));
        testCards.add(new Card(Suit.DIAMONDS, CardValue.QUEEN));
        testCards.add(new Card(Suit.DIAMONDS, CardValue.KING));

        AI ai1 = new AI(1);
        ai1.getCards(testCards);

        for(Card c : ai1.getPlayerHand()){
            Assert.assertEquals(c, testCards.remove(0));
        }
        Assert.assertEquals(ai1.getPlayerHand().size(), 3);
    }
}
