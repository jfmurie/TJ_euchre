package com.player;

/* the original statement: android.util.Pair
 * changed because that import doesn't work with unit testing
 * https://stackoverflow.com/questions/35979397/android-immediately-created-pair-elements-are-null
 */
import android.support.v4.util.Pair; //

import com.card.Card;
import com.cardvalues.CardValue;
import com.suits.Suit;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class testAI {

    /**
     * Tests the AI can properly get the right cards
     */
    @Test
    public void test_receiveCardFromDealer(){

        ArrayList<Card> testCards = new ArrayList<>();
        testCards.add(new Card(Suit.DIAMONDS, CardValue.JACK));
        testCards.add(new Card(Suit.DIAMONDS, CardValue.QUEEN));
        testCards.add(new Card(Suit.DIAMONDS, CardValue.KING));

        AI ai1 = new AI(1);
        for(Card c: testCards){
            ai1.receiveCardFromDealer(c);
        }

        for(Card c : ai1.getPlayerHand()){
            Assert.assertEquals(c, testCards.remove(0));
        }
        Assert.assertEquals(ai1.getPlayerHand().size(), 3);
    }

    /**
     * Tests the AI to lead Trump when it has the HighestRemainingTrump
     */
    @Test
    public void testPlayCard1(){
        Pair<Integer, Double> p = new Pair<>(1, 1.0);

        AI ai1 = new AI(1);

        //Prepare Hand
        ArrayList<Card> testHand = new ArrayList<>();
        testHand.add(new Card(Suit.CLUBS, CardValue.JACK));
        testHand.add(new Card(Suit.CLUBS, CardValue.QUEEN));
        testHand.add(new Card(Suit.CLUBS, CardValue.TEN));
        testHand.add(new Card(Suit.DIAMONDS, CardValue.KING));
        testHand.add(new Card(Suit.HEARTS, CardValue.KING));
        for(Card c: testHand){
            ai1.receiveCardFromDealer(c);
        }

        //Prepare cardsPlayed
        ArrayList<Card> cardsPlayed = new ArrayList<>();

        ai1.setLead(true);
        ai1.setHighestRemainingTrumpCard(Suit.CLUBS, CardValue.JACK);

        Card play = ai1.determinePlay(cardsPlayed, Suit.CLUBS);

        Assert.assertEquals(Suit.CLUBS, play.getSuit());
        Assert.assertEquals(CardValue.JACK, play.getValue());
    }

    /**
     * Tests the AI to lead highest non trump card when it does NOT have the HighestRemainingTrump
     */
    @Test
    public void testPlayCard2(){
        AI ai1 = new AI(1);

        //Prepare Hand
        ArrayList<Card> testHand = new ArrayList<>();
        testHand.add(new Card(Suit.SPADES, CardValue.JACK));
        testHand.add(new Card(Suit.CLUBS, CardValue.QUEEN));
        testHand.add(new Card(Suit.CLUBS, CardValue.TEN));
        testHand.add(new Card(Suit.DIAMONDS, CardValue.KING));
        testHand.add(new Card(Suit.HEARTS, CardValue.KING));
        for(Card c: testHand){
            ai1.receiveCardFromDealer(c);
        }

        //Prepare cardsPlayed
        ArrayList<Card> cardsPlayed = new ArrayList<>();

        ai1.setLead(true);
        ai1.setHighestRemainingTrumpCard(Suit.CLUBS, CardValue.JACK);

        Card play = ai1.determinePlay(cardsPlayed, Suit.CLUBS);

        Assert.assertEquals(Suit.DIAMONDS, play.getSuit());
        Assert.assertEquals(CardValue.KING, play.getValue());
    }



    /**
     * Tests the AI to play off when teammate has trick & AI has the possibility to trump
     */
    @Test
    public void testPlayCard3(){
        AI ai1 = new AI(1);

        //Prepare Hand
        ArrayList<Card> testHand = new ArrayList<>();
        testHand.add(new Card(Suit.SPADES, CardValue.JACK));
        testHand.add(new Card(Suit.CLUBS, CardValue.QUEEN));
        testHand.add(new Card(Suit.CLUBS, CardValue.TEN));
        testHand.add(new Card(Suit.DIAMONDS, CardValue.KING));
        testHand.add(new Card(Suit.HEARTS, CardValue.KING));
        for(Card c: testHand){
            ai1.receiveCardFromDealer(c);
        }

        //Prepare cardsPlayed
        ArrayList<Card> cardsPlayed = new ArrayList<>();
        cardsPlayed.add(new Card(Suit.SPADES, CardValue.ACE));
        cardsPlayed.add(new Card(Suit.CLUBS, CardValue.KING));
        cardsPlayed.add(new Card(Suit.SPADES, CardValue.QUEEN));

        ai1.setLead(false);
        ai1.setHighestRemainingTrumpCard(Suit.CLUBS, CardValue.JACK);

        Card play = ai1.determinePlay(cardsPlayed, Suit.CLUBS);

        Assert.assertEquals(Suit.DIAMONDS, play.getSuit());
        Assert.assertEquals(CardValue.KING, play.getValue());
    }

    /**
     * Tests the AI to follow suit
     */
    @Test
    public void testPlayCard4(){
        AI ai1 = new AI(1);

        //Prepare Hand
        ArrayList<Card> testHand = new ArrayList<>();
        testHand.add(new Card(Suit.SPADES, CardValue.JACK));
        testHand.add(new Card(Suit.CLUBS, CardValue.QUEEN));
        testHand.add(new Card(Suit.CLUBS, CardValue.TEN));
        testHand.add(new Card(Suit.DIAMONDS, CardValue.KING));
        testHand.add(new Card(Suit.HEARTS, CardValue.KING));
        for(Card c: testHand){
            ai1.receiveCardFromDealer(c);
        }

        //Prepare cardsPlayed
        ArrayList<Card> cardsPlayed = new ArrayList<>();
        cardsPlayed.add(new Card(Suit.HEARTS, CardValue.ACE));
        cardsPlayed.add(new Card(Suit.HEARTS, CardValue.QUEEN));
        cardsPlayed.add(new Card(Suit.HEARTS, CardValue.TEN));

        ai1.setLead(false);
        ai1.setHighestRemainingTrumpCard(Suit.CLUBS, CardValue.JACK);

        Card play = ai1.determinePlay(cardsPlayed, Suit.CLUBS);

        Assert.assertEquals(Suit.HEARTS, play.getSuit());
        Assert.assertEquals(CardValue.KING, play.getValue());
    }









    /* POSSIBLE SCENARIOS */
    /* decidePlay
     *      Hand size: 1
     *          Plays the last card correctly
     *      Hand size: 2-5
     *          +Lead, +hasTrump, +hasHRTC
     *          +Lead, +hasTrump, -hasHRTC
     *          +Lead, -hasTrump
     *
     *          -Lead, +trumpLed, +hasTrump (2+), +canTakeTrick, +tm8Played, +tm8HasTrick
     *          -Lead, +trumpLed, +hasTrump (2+), +canTakeTrick, +tm8Played, -tm8HasTrick
     *          -Lead, +trumpLed, +hasTrump (2+), +canTakeTrick, -tm8Played
     *          -Lead, +trumpLed, +hasTrump (2+), -canTakeTrick
     *          -Lead, +trumpLed, +hasTrump (1)
     *          -Lead, +trumpLed, -hasTrump
     *
     *          -Lead, -trumpLed, +canFollowSuit, +canTakeTrick, +tm8Played, +tm8HasTrick
     *          -Lead, -trumpLed, +canFollowSuit, +canTakeTrick, +tm8Played, -tm8HasTrick
     *          -Lead, -trumpLed, +canFollowSuit, +canTakeTrick, -tm8Played
     *          -Lead, -trumpLed, +canFollowSuit, -canTakeTrick
     *
     *          -Lead, -trumpLed, -canFollowSuit, +canTakeTrick (trump), +tm8Played, +tm8HasTrick
     *          -Lead, -trumpLed, -canFollowSuit, +canTakeTrick (trump), +tm8Played, -tm8HasTrick
     *          -Lead, -trumpLed, -canFollowSuit, +canTakeTrick (trump), -tm8Played
     *          -Lead, -trumpLed, -canFollowSuit, -canTakeTrick
     */
    /* decideTrump
     *      4 suited
     *
     *      3 suited
     *
     *      2 suited
     *
     *      1 suited
     */


}
