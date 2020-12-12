package com.player;

/* the original statement: android.util.Pair
 * changed because that import doesn't work with unit testing
 * https://stackoverflow.com/questions/35979397/android-immediately-created-pair-elements-are-null
 */
import android.support.v4.util.Pair;
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
     * Tests the AI will play its last card
     */
    @Test
    public void testPlayCard1(){
        AI ai1 = new AI(1);

        //Prepare Hand
        ai1.receiveCardFromDealer(new Card(Suit.DIAMONDS, CardValue.KING));

        //Prepare cardsPlayed
        ArrayList<Card> cardsPlayed = new ArrayList<>();
        cardsPlayed.add(new Card(Suit.DIAMONDS, CardValue.TEN));
        cardsPlayed.add(new Card(Suit.CLUBS, CardValue.TEN));

        ai1.setLead(false);
        ai1.setHighestRemainingTrumpCard(Suit.CLUBS, CardValue.NINE);

        Card play = ai1.determinePlay(cardsPlayed, Suit.CLUBS);

        Assert.assertEquals(Suit.DIAMONDS, play.getSuit());
        Assert.assertEquals(CardValue.KING, play.getValue());
    }

    /**
     * Tests the AI to lead highest non trump card when it does NOT have trump
     */
    @Test
    public void testPlayCard2(){
        AI ai1 = new AI(1);

        //Prepare Hand
        ai1.receiveCardFromDealer(new Card(Suit.SPADES, CardValue.JACK));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.QUEEN));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.TEN));
        ai1.receiveCardFromDealer(new Card(Suit.DIAMONDS, CardValue.ACE));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.KING));

        //Prepare cardsPlayed, its empty because ai is leading
        ArrayList<Card> cardsPlayed = new ArrayList<>();

        ai1.setLead(true);
        ai1.setHighestRemainingTrumpCard(Suit.HEARTS, CardValue.JACK);

        Card play = ai1.determinePlay(cardsPlayed, Suit.HEARTS);

        Assert.assertEquals(Suit.DIAMONDS, play.getSuit());
        Assert.assertEquals(CardValue.ACE, play.getValue());
    }

    /**
     * Tests the AI to lead Trump when it has the HighestRemainingTrump
     */
    @Test
    public void testPlayCard3(){
        Pair<Integer, Double> p = new Pair<>(1, 1.0);

        AI ai1 = new AI(1);

        //Prepare Hand
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.JACK));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.QUEEN));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.TEN));
        ai1.receiveCardFromDealer(new Card(Suit.DIAMONDS, CardValue.KING));
        ai1.receiveCardFromDealer(new Card(Suit.HEARTS, CardValue.KING));

        //Prepare cardsPlayed, it's empty here
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
    public void testPlayCard4(){
        AI ai1 = new AI(1);

        //Prepare Hand
        ai1.receiveCardFromDealer(new Card(Suit.SPADES, CardValue.JACK));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.QUEEN));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.TEN));
        ai1.receiveCardFromDealer(new Card(Suit.DIAMONDS, CardValue.KING));
        ai1.receiveCardFromDealer(new Card(Suit.HEARTS, CardValue.KING));


        //Prepare cardsPlayed, its empty because ai is leading
        ArrayList<Card> cardsPlayed = new ArrayList<>();

        ai1.setLead(true);
        ai1.setHighestRemainingTrumpCard(Suit.CLUBS, CardValue.JACK);

        Card play = ai1.determinePlay(cardsPlayed, Suit.CLUBS);

        Assert.assertEquals(Suit.DIAMONDS, play.getSuit());
        Assert.assertEquals(CardValue.KING, play.getValue());
    }

    /**
     * Tests the AI to follow suit
     */
    @Test
    public void testPlayCard5(){
        AI ai1 = new AI(1);

        //Prepare Hand
        ai1.receiveCardFromDealer(new Card(Suit.SPADES, CardValue.JACK));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.QUEEN));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.TEN));
        ai1.receiveCardFromDealer(new Card(Suit.DIAMONDS, CardValue.KING));
        ai1.receiveCardFromDealer(new Card(Suit.HEARTS, CardValue.KING));

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

    /**
     * Tests the AI to play off when teammate has trick & AI has the possibility to trump
     */
    @Test
    public void testPlayCard6(){
        AI ai1 = new AI(1);

        //Prepare Hand
        ai1.receiveCardFromDealer(new Card(Suit.SPADES, CardValue.JACK));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.QUEEN));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.TEN));
        ai1.receiveCardFromDealer(new Card(Suit.DIAMONDS, CardValue.KING));
        ai1.receiveCardFromDealer(new Card(Suit.HEARTS, CardValue.KING));

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
     * Tests the AI to trump when teammate does not have trick & AI has the possibility to trump
     */
    @Test
    public void testPlayCard7(){
        AI ai1 = new AI(1);

        //Prepare Hand
        ai1.receiveCardFromDealer(new Card(Suit.SPADES, CardValue.JACK));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.QUEEN));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.TEN));
        ai1.receiveCardFromDealer(new Card(Suit.DIAMONDS, CardValue.KING));

        //Prepare cardsPlayed
        ArrayList<Card> cardsPlayed = new ArrayList<>();
        cardsPlayed.add(new Card(Suit.HEARTS, CardValue.ACE));
        cardsPlayed.add(new Card(Suit.HEARTS, CardValue.KING));
        cardsPlayed.add(new Card(Suit.HEARTS, CardValue.NINE));

        ai1.setLead(false);
        ai1.setHighestRemainingTrumpCard(Suit.DIAMONDS, CardValue.JACK);

        Card play = ai1.determinePlay(cardsPlayed, Suit.DIAMONDS);

        Assert.assertEquals(Suit.DIAMONDS, play.getSuit());
        Assert.assertEquals(CardValue.KING, play.getValue());
    }

    /**
     * Tests the AI to play lowest trump when only having trump & teammate has trick
     */
    @Test
    public void testPlayCard8(){
        AI ai1 = new AI(1);

        //Prepare Hand
        ai1.receiveCardFromDealer(new Card(Suit.SPADES, CardValue.JACK));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.QUEEN));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.TEN));

        //Prepare cardsPlayed
        ArrayList<Card> cardsPlayed = new ArrayList<>();
        cardsPlayed.add(new Card(Suit.SPADES, CardValue.KING));
        cardsPlayed.add(new Card(Suit.SPADES, CardValue.ACE));
        cardsPlayed.add(new Card(Suit.SPADES, CardValue.NINE));

        ai1.setLead(false);
        ai1.setHighestRemainingTrumpCard(Suit.SPADES, CardValue.JACK);

        Card play = ai1.determinePlay(cardsPlayed, Suit.CLUBS);

        Assert.assertEquals(Suit.CLUBS, play.getSuit());
        Assert.assertEquals(CardValue.TEN, play.getValue());
    }

    /**
     * Tests the AI to play off/lowest nontrump when having no trump and trump is led
     */
    @Test
    public void testPlayCard9(){
        AI ai1 = new AI(1);

        //Prepare Hand
        ai1.receiveCardFromDealer(new Card(Suit.SPADES, CardValue.JACK));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.QUEEN));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.TEN));

        //Prepare cardsPlayed
        ArrayList<Card> cardsPlayed = new ArrayList<>();
        cardsPlayed.add(new Card(Suit.HEARTS, CardValue.KING));
        cardsPlayed.add(new Card(Suit.HEARTS, CardValue.ACE));
        cardsPlayed.add(new Card(Suit.HEARTS, CardValue.NINE));

        ai1.setLead(false);
        ai1.setHighestRemainingTrumpCard(Suit.HEARTS, CardValue.ACE);

        Card play = ai1.determinePlay(cardsPlayed, Suit.HEARTS);

        Assert.assertEquals(Suit.CLUBS, play.getSuit());
        Assert.assertEquals(CardValue.TEN, play.getValue());
    }

    /**
     * Tests the AI to plays highest trump when trump is led & can take trick
     */
    @Test
    public void testPlayCard10(){
        AI ai1 = new AI(1);

        //Prepare Hand
        ai1.receiveCardFromDealer(new Card(Suit.DIAMONDS, CardValue.JACK));
        ai1.receiveCardFromDealer(new Card(Suit.HEARTS, CardValue.QUEEN));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.TEN));

        //Prepare cardsPlayed
        ArrayList<Card> cardsPlayed = new ArrayList<>();
        cardsPlayed.add(new Card(Suit.HEARTS, CardValue.KING));
        cardsPlayed.add(new Card(Suit.HEARTS, CardValue.ACE));
        cardsPlayed.add(new Card(Suit.HEARTS, CardValue.NINE));

        ai1.setLead(false);
        ai1.setHighestRemainingTrumpCard(Suit.DIAMONDS, CardValue.JACK);

        Card play = ai1.determinePlay(cardsPlayed, Suit.HEARTS);

        Assert.assertEquals(Suit.DIAMONDS, play.getSuit());
        Assert.assertEquals(CardValue.JACK, play.getValue());
    }

    /**
     * Tests the AI to play right bower when left bower is played & trump led
     */
    @Test
    public void testPlayCard11(){
        AI ai1 = new AI(1);

        //Prepare Hand
        ai1.receiveCardFromDealer(new Card(Suit.HEARTS, CardValue.JACK));
        ai1.receiveCardFromDealer(new Card(Suit.HEARTS, CardValue.QUEEN));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.TEN));

        //Prepare cardsPlayed
        ArrayList<Card> cardsPlayed = new ArrayList<>();
        cardsPlayed.add(new Card(Suit.DIAMONDS, CardValue.JACK));
        cardsPlayed.add(new Card(Suit.HEARTS, CardValue.ACE));
        cardsPlayed.add(new Card(Suit.HEARTS, CardValue.NINE));

        ai1.setLead(false);
        ai1.setHighestRemainingTrumpCard(Suit.HEARTS, CardValue.JACK);

        Card play = ai1.determinePlay(cardsPlayed, Suit.HEARTS);

        Assert.assertEquals(Suit.HEARTS, play.getSuit());
        Assert.assertEquals(CardValue.JACK, play.getValue());
    }

    /**
     * Tests the AI to play low trump when left/right bower is played, trump led, and AI does not have a bower
     */
    @Test
    public void testPlayCard12(){
        AI ai1 = new AI(1);

        //Prepare Hand
        ai1.receiveCardFromDealer(new Card(Suit.HEARTS, CardValue.KING));
        ai1.receiveCardFromDealer(new Card(Suit.HEARTS, CardValue.TEN));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.TEN));

        //Prepare cardsPlayed
        ArrayList<Card> cardsPlayed = new ArrayList<>();
        cardsPlayed.add(new Card(Suit.DIAMONDS, CardValue.JACK));
        cardsPlayed.add(new Card(Suit.HEARTS, CardValue.ACE));
        cardsPlayed.add(new Card(Suit.HEARTS, CardValue.NINE));

        ai1.setLead(false);
        ai1.setHighestRemainingTrumpCard(Suit.HEARTS, CardValue.JACK);

        Card play = ai1.determinePlay(cardsPlayed, Suit.HEARTS);

        Assert.assertEquals(Suit.HEARTS, play.getSuit());
        Assert.assertEquals(CardValue.TEN, play.getValue());
    }

    /**
     * Tests the AI to play low trump when AI cannot take trick
     */
    @Test
    public void testPlayCard13(){
        AI ai1 = new AI(1);

        //Prepare Hand
        ai1.receiveCardFromDealer(new Card(Suit.HEARTS, CardValue.KING));
        ai1.receiveCardFromDealer(new Card(Suit.HEARTS, CardValue.TEN));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.TEN));

        //Prepare cardsPlayed
        ArrayList<Card> cardsPlayed = new ArrayList<>();
        cardsPlayed.add(new Card(Suit.HEARTS, CardValue.ACE));
        cardsPlayed.add(new Card(Suit.HEARTS, CardValue.NINE));

        ai1.setLead(false);
        ai1.setHighestRemainingTrumpCard(Suit.HEARTS, CardValue.JACK);

        Card play = ai1.determinePlay(cardsPlayed, Suit.HEARTS);

        Assert.assertEquals(Suit.HEARTS, play.getSuit());
        Assert.assertEquals(CardValue.TEN, play.getValue());
    }

    /**
     * Tests the AI to play higher of Suit when teammate does not have trick & the card can take trick
     */
    @Test
    public void testPlayCard14(){
        AI ai1 = new AI(1);

        //Prepare Hand
        ai1.receiveCardFromDealer(new Card(Suit.HEARTS, CardValue.KING));
        ai1.receiveCardFromDealer(new Card(Suit.HEARTS, CardValue.TEN));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.TEN));

        //Prepare cardsPlayed
        ArrayList<Card> cardsPlayed = new ArrayList<>();
        cardsPlayed.add(new Card(Suit.HEARTS, CardValue.QUEEN));
        cardsPlayed.add(new Card(Suit.HEARTS, CardValue.NINE));
        cardsPlayed.add(new Card(Suit.HEARTS, CardValue.JACK));

        ai1.setLead(false);
        ai1.setHighestRemainingTrumpCard(Suit.SPADES, CardValue.JACK);

        Card play = ai1.determinePlay(cardsPlayed, Suit.SPADES);

        Assert.assertEquals(Suit.HEARTS, play.getSuit());
        Assert.assertEquals(CardValue.KING, play.getValue());
    }

    /**
     * Test that ai calls trump when "screw the dealer" as dealer
     */
    @Test
    public void testCallTrump1(){
        AI ai1 = new AI(1);

        //Prepare Hand
        ai1.receiveCardFromDealer(new Card(Suit.SPADES, CardValue.TEN));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.QUEEN));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.TEN));
        ai1.receiveCardFromDealer(new Card(Suit.DIAMONDS, CardValue.KING));
        ai1.receiveCardFromDealer(new Card(Suit.HEARTS, CardValue.KING));

        Card cardUp = new Card(Suit.CLUBS, CardValue.NINE);
        Pair<Suit, Boolean> result = ai1.callTrump(cardUp, true, true);
        Assert.assertEquals(Suit.SPADES, result.first);
    }

    /**
     * Test that ai calls trump as dealer when has 2+ of the suit of cardUp, the cardUp is a Jack
     * and/or ai has the left or right bower.
     */
    @Test
    public void testCallTrump2(){
        AI ai1 = new AI(1);

        //Prepare Hand
        ai1.receiveCardFromDealer(new Card(Suit.SPADES, CardValue.JACK));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.QUEEN));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.TEN));
        ai1.receiveCardFromDealer(new Card(Suit.DIAMONDS, CardValue.KING));
        ai1.receiveCardFromDealer(new Card(Suit.HEARTS, CardValue.KING));

        Card cardUp = new Card(Suit.CLUBS, CardValue.NINE);
        Pair<Suit, Boolean> result = ai1.callTrump(cardUp, false, true);
        Assert.assertEquals(Suit.CLUBS, result.first);
    }

    /**
     * Test that ai turns down trump when it does not have the requirements & topCard has been
     * turned down already
     */
    @Test
    public void testCallTrump3(){
        AI ai1 = new AI(1);

        //Prepare Hand
        ai1.receiveCardFromDealer(new Card(Suit.DIAMONDS, CardValue.JACK));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.QUEEN));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.TEN));
        ai1.receiveCardFromDealer(new Card(Suit.HEARTS, CardValue.TEN));
        ai1.receiveCardFromDealer(new Card(Suit.HEARTS, CardValue.KING));

        Card cardUp = new Card(Suit.CLUBS, CardValue.NINE);
        Pair<Suit, Boolean> result = ai1.callTrump(cardUp, true, false);
        Assert.assertEquals(result.first, Suit.PASS);
    }

    /**
     * Test that ai calls trump when it does have the requirements & topCard has been
     * turned down already
     */
    @Test
    public void testCallTrump4(){
        AI ai1 = new AI(1);

        //Prepare Hand
        ai1.receiveCardFromDealer(new Card(Suit.SPADES, CardValue.JACK));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.QUEEN));
        ai1.receiveCardFromDealer(new Card(Suit.CLUBS, CardValue.TEN));
        ai1.receiveCardFromDealer(new Card(Suit.HEARTS, CardValue.TEN));
        ai1.receiveCardFromDealer(new Card(Suit.HEARTS, CardValue.KING));

        Card cardUp = new Card(Suit.SPADES, CardValue.NINE);
        Pair<Suit, Boolean> result = ai1.callTrump(cardUp, true, false);
        Assert.assertEquals(result.first, Suit.PASS);
    }

}
