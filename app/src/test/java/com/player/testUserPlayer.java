package com.player;

import com.card.Card;
import com.cardvalues.CardValue;
import com.suits.Suit;

import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;

public class testUserPlayer {

    @Test
    public void testGetCards1(){
        ArrayList<Card> testCards = new ArrayList<>();
        testCards.add(new Card(Suit.DIAMONDS, CardValue.JACK));
        testCards.add(new Card(Suit.DIAMONDS, CardValue.QUEEN));
        testCards.add(new Card(Suit.DIAMONDS, CardValue.KING));

        UserPlayer user = new UserPlayer();

        for(Card c : user.getPlayerHand()){
            Assert.assertEquals(c,testCards.remove(0));
        }
        Assert.assertEquals(user.getPlayerHand().size(), 3);
    }

}
