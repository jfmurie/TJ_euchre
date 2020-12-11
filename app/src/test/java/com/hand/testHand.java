package com.hand;

import com.card.Card;
import com.cardvalues.CardValue;
import com.suits.Suit;
import org.junit.Test;
import org.junit.Assert;

//tests for hand class
public class testHand {

    @Test
    public void testHandTrump() {
        Hand testHand = new Hand();

        testHand.addCard(new Card(Suit.CLUBS, CardValue.NINE));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.NINE));

        Assert.assertEquals(testHand.hasTrump(Suit.DIAMONDS), true);
        //System.out.println("Diamonds In");
        Assert.assertEquals(testHand.hasTrump(Suit.CLUBS), true);
        //System.out.println("Clubs In");
        Assert.assertEquals(testHand.hasTrump(Suit.HEARTS), false);
        //System.out.println("Hearts Out");
        Assert.assertEquals(testHand.hasTrump(Suit.SPADES), false);
        //System.out.println("Spades Out");
    }

    @Test
    public void testHandSuit(){
        Hand testHand = new Hand();

        testHand.addCard(new Card(Suit.CLUBS, CardValue.NINE));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.NINE));

        Assert.assertEquals(testHand.hasSuit(Suit.DIAMONDS, Suit.HEARTS), true);
        Assert.assertEquals(testHand.hasSuit(Suit.CLUBS, Suit.HEARTS), true);
        Assert.assertEquals(testHand.hasSuit(Suit.HEARTS, Suit.HEARTS), false);
        Assert.assertEquals(testHand.hasSuit(Suit.SPADES, Suit.HEARTS), false);
    }

    @Test
    public void testHandHighestTrump(){
        Hand testHand = new Hand();
        //Hand is empty test
        Assert.assertEquals(testHand.getHighestTrump(Suit.DIAMONDS), null);
        System.out.println("empty hand pass");

        testHand.addCard(new Card(Suit.CLUBS, CardValue.NINE));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.NINE));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.KING));
        testHand.addCard(new Card(Suit.CLUBS, CardValue.QUEEN));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.TEN));
        Card testCard = new Card(Suit.DIAMONDS, CardValue.KING);

        //No trump cards test
        Assert.assertEquals(testHand.getHighestTrump(Suit.HEARTS), null);
        System.out.println("no trump pass");
        Assert.assertEquals(testHand.getHighestTrump(Suit.DIAMONDS).getValue().getNumericalValue(), testCard.getValue().getNumericalValue());
        System.out.println("returned value " + testHand.getHighestTrump(Suit.DIAMONDS).getValue().getNumericalValue());
        System.out.println("expected value " + testCard.getValue().getNumericalValue());
    }

    @Test
    public void testHandLowestTrump(){
        Hand testHand = new Hand();
        //Hand is empty test
        Assert.assertEquals(testHand.getHighestTrump(Suit.DIAMONDS), null);
        System.out.println("empty hand pass");

        testHand.addCard(new Card(Suit.CLUBS, CardValue.NINE));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.NINE));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.KING));
        testHand.addCard(new Card(Suit.CLUBS, CardValue.QUEEN));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.TEN));
        Card testCard = new Card(Suit.DIAMONDS, CardValue.NINE);

        //No trump cards test
        Assert.assertEquals(testHand.getHighestTrump(Suit.HEARTS), null);
        System.out.println("no trump pass");
        Assert.assertEquals(testHand.getLowestTrump(Suit.DIAMONDS).getValue().getNumericalValue(), testCard.getValue().getNumericalValue());
        System.out.println("returned value " + testHand.getLowestTrump(Suit.DIAMONDS).getValue().getNumericalValue());
        System.out.println("expected value " + testCard.getValue().getNumericalValue());
    }

    @Test
    public void testHandHighestNonTrump(){
        Hand testHand = new Hand();
        //Hand is empty test
        Assert.assertEquals(testHand.getHighestNonTrump(Suit.DIAMONDS), null);
        System.out.println("empty hand pass");

        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.NINE));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.KING));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.TEN));

        //No trump cards test
        Assert.assertEquals(testHand.getHighestNonTrump(Suit.DIAMONDS), null);
        System.out.println("no nontrump pass");

        testHand.addCard(new Card(Suit.CLUBS, CardValue.NINE));
        testHand.addCard(new Card(Suit.CLUBS, CardValue.QUEEN));
        Card testCard = new Card(Suit.CLUBS, CardValue.QUEEN);

        Assert.assertEquals(testHand.getHighestNonTrump(Suit.DIAMONDS).getValue().getNumericalValue(), testCard.getValue().getNumericalValue());
        System.out.println("returned value " + testHand.getHighestNonTrump(Suit.DIAMONDS).getValue().getNumericalValue());
        System.out.println("expected value " + testCard.getValue().getNumericalValue());
    }

    @Test
    public void testHandLowestNonTrump(){
        Hand testHand = new Hand();
        //Hand is empty test
        Assert.assertEquals(testHand.getLowestNonTrump(Suit.DIAMONDS), null);
        System.out.println("empty hand pass");

        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.NINE));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.KING));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.TEN));

        //No trump cards test
        Assert.assertEquals(testHand.getLowestNonTrump(Suit.DIAMONDS), null);
        System.out.println("no nontrump pass");

        testHand.addCard(new Card(Suit.CLUBS, CardValue.NINE));
        testHand.addCard(new Card(Suit.CLUBS, CardValue.QUEEN));
        Card testCard = new Card(Suit.CLUBS, CardValue.NINE);

        Assert.assertEquals(testHand.getLowestNonTrump(Suit.DIAMONDS).getValue().getNumericalValue(), testCard.getValue().getNumericalValue());
        System.out.println("returned value " + testHand.getLowestNonTrump(Suit.DIAMONDS).getValue().getNumericalValue());
        System.out.println("expected value " + testCard.getValue().getNumericalValue());
    }

    @Test
    public void testHandCountofSuits(){
        Hand testHand = new Hand();
        //Hand is empty test
        Assert.assertEquals(testHand.getCountOfSuit(Suit.DIAMONDS, true), -1);
        Assert.assertEquals(testHand.getCountOfSuit(Suit.DIAMONDS, false), -1);
        System.out.println("empty hand pass");

        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.NINE));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.KING));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.TEN));
        testHand.addCard(new Card(Suit.CLUBS, CardValue.NINE));
        testHand.addCard(new Card(Suit.CLUBS, CardValue.QUEEN));

        //Tests for just plain cards of that suit
        Assert.assertEquals(testHand.getCountOfSuit(Suit.DIAMONDS, false), 3);
        System.out.println("returned value " + testHand.getCountOfSuit(Suit.DIAMONDS, true));
        System.out.println("expected value 3");

        testHand.removeCard(0);
        testHand.removeCard(0);
        testHand.removeCard(0);
        testHand.removeCard(0);
        testHand.removeCard(0);

        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.NINE));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.KING));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.TEN));
        testHand.addCard(new Card(Suit.HEARTS, CardValue.JACK));
        testHand.addCard(new Card(Suit.HEARTS, CardValue.QUEEN));

        //Tests to see if the bower is added when checking trump
        Assert.assertEquals(testHand.getCountOfSuit(Suit.DIAMONDS, true), 4);
        System.out.println("returned value " + testHand.getCountOfSuit(Suit.DIAMONDS, true));
        System.out.println("expected value 4");
    }

    @Test
    public void testHandHasCard(){
        Hand testHand = new Hand();
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.KING));
        Assert.assertEquals(testHand.hasCard(Suit.DIAMONDS, CardValue.KING), true);
        Assert.assertEquals(testHand.hasCard(Suit.DIAMONDS, CardValue.QUEEN), false);
    }

    @Test
    public void testHandHighestofSuit(){
        Hand testHand = new Hand();
        //no cards in hand
        Assert.assertEquals(testHand.getHighestofSuit(Suit.DIAMONDS), null);

        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.NINE));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.KING));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.TEN));
        testHand.addCard(new Card(Suit.HEARTS, CardValue.JACK));
        testHand.addCard(new Card(Suit.HEARTS, CardValue.QUEEN));
        Card DTestCard = new Card(Suit.DIAMONDS, CardValue.KING);
        Card HTestCard = new Card(Suit.HEARTS, CardValue.QUEEN);

        Assert.assertEquals(testHand.getHighestofSuit(Suit.DIAMONDS).getValue().getNumericalValue(), DTestCard.getValue().getNumericalValue());
        System.out.println("returned value " + testHand.getHighestofSuit(Suit.DIAMONDS).getValue().getNumericalValue());
        System.out.println("expected value " + DTestCard.getValue().getNumericalValue());

        Assert.assertEquals(testHand.getHighestofSuit(Suit.HEARTS).getValue().getNumericalValue(), HTestCard.getValue().getNumericalValue());
        System.out.println("returned value " + testHand.getHighestofSuit(Suit.HEARTS).getValue().getNumericalValue());
        System.out.println("expected value " + HTestCard.getValue().getNumericalValue());
    }

    @Test
    public void testHandLowestofSuit(){
        Hand testHand = new Hand();
        //no cards in hand
        Assert.assertEquals(testHand.getLowestofSuit(Suit.DIAMONDS), null);

        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.NINE));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.KING));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.TEN));
        testHand.addCard(new Card(Suit.HEARTS, CardValue.JACK));
        testHand.addCard(new Card(Suit.HEARTS, CardValue.QUEEN));
        Card DTestCard = new Card(Suit.DIAMONDS, CardValue.NINE);
        Card HTestCard = new Card(Suit.HEARTS, CardValue.JACK);

        Assert.assertEquals(testHand.getLowestofSuit(Suit.DIAMONDS).getValue().getNumericalValue(), DTestCard.getValue().getNumericalValue());
        System.out.println("returned value " + testHand.getLowestofSuit(Suit.DIAMONDS).getValue().getNumericalValue());
        System.out.println("expected value " + DTestCard.getValue().getNumericalValue());

        Assert.assertEquals(testHand.getLowestofSuit(Suit.HEARTS).getValue().getNumericalValue(), HTestCard.getValue().getNumericalValue());
        System.out.println("returned value " + testHand.getLowestofSuit(Suit.HEARTS).getValue().getNumericalValue());
        System.out.println("expected value " + HTestCard.getValue().getNumericalValue());
    }

    @Test
    public void testHandhasSuit(){
        Hand testHand = new Hand();
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.NINE));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.KING));
        testHand.addCard(new Card(Suit.DIAMONDS, CardValue.TEN));
        testHand.addCard(new Card(Suit.HEARTS, CardValue.JACK));

        //Calls for the hasTrump
        Assert.assertEquals(testHand.hasSuit(Suit.DIAMONDS, Suit.DIAMONDS), true);
        Assert.assertEquals(testHand.hasSuit(Suit.SPADES, Suit.SPADES), false);
        //has the suit
        Assert.assertEquals(testHand.hasSuit(Suit.DIAMONDS, Suit.HEARTS), true);
        //doesnt have the suit
        Assert.assertEquals(testHand.hasSuit(Suit.SPADES, Suit.DIAMONDS), false);
        //has the suit but its a bower so actually part of trump and other suit
        Assert.assertEquals(testHand.hasSuit(Suit.HEARTS, Suit.DIAMONDS), false);
    }
}
