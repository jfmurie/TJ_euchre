package com.player;

import com.Round.Round;
import com.card.Card;
import com.cardvalues.CardValue;
import com.hand.Hand;
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

    @Test
    public void testPlayCard1(){
        AI ai1 = new AI(1);
        prepareAIHand(ai1);
        ai1.setHrtc(new Card(Suit.DIAMONDS, CardValue.ACE));
        Card play = ai1.determinePlay(prepareCardsPlayed(), Suit.DIAMONDS);

        Assert.assertEquals(play.suit, Suit.DIAMONDS);
        Assert.assertEquals(play.value, CardValue.ACE);
    }

    private ArrayList<Card> prepareCardsPlayed(){
        ArrayList<Card> cardsPlayed = new ArrayList<>();
        cardsPlayed.add(new Card(Suit.DIAMONDS, CardValue.NINE));
        cardsPlayed.add(new Card(Suit.DIAMONDS, CardValue.QUEEN));
        cardsPlayed.add(new Card(Suit.DIAMONDS, CardValue.KING));
        return cardsPlayed;
    }

    private void prepareAIHand(AI ai){

        ArrayList<Card> testHand = new ArrayList<>();
        testHand.add(new Card(Suit.DIAMONDS, CardValue.ACE));
        testHand.add(new Card(Suit.HEARTS, CardValue.QUEEN));
        testHand.add(new Card(Suit.SPADES, CardValue.NINE));


        ai.getCards(testHand);
    }


}
