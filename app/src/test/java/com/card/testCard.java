package com.card;

import com.cardvalues.CardValue;
import com.suits.Suit;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class testCard {

    Card card;

    @Test
    public void testIsTrump_nineOfHearts_trumpIsHearts_returnsTrue() {
        card = new Card(Suit.HEARTS, CardValue.NINE);
        card.setIsTrump(Suit.HEARTS);
        assertTrue(card.isTrump());
    }


    @Test
    public void testIsTrump_nineOfHearts_trumpIsDiamonds_returnsFalse() {
        card = new Card(Suit.HEARTS, CardValue.NINE);
        card.setIsTrump(Suit.DIAMONDS);
        assertFalse(card.isTrump());
    }

    @Test
    public void testIsTrump_nineOfHearts_trumpIsClubs_returnsFalse() {
        card = new Card(Suit.HEARTS, CardValue.NINE);
        card.setIsTrump(Suit.CLUBS);
        assertFalse(card.isTrump());
    }


    @Test
    public void testIsTrump_nineOfHearts_trumpIsSpades_returnsFalse() {
        card = new Card(Suit.HEARTS, CardValue.NINE);
        card.setIsTrump(Suit.SPADES);
        assertFalse(card.isTrump());
    }


    @Test
    public void testIsTrump_jackOfHearts_trumpIsDiamonds_returnsTrue() {
        card = new Card(Suit.HEARTS, CardValue.JACK);
        card.setIsTrump(Suit.DIAMONDS);
        assertTrue(card.isTrump());
    }


    @Test
    public void testIsTrump_jackOfDiamonds_trumpIsHearts_returnsTrue() {
        card = new Card(Suit.DIAMONDS, CardValue.JACK);
        card.setIsTrump(Suit.HEARTS);
        assertTrue(card.isTrump());
    }


    @Test
    public void testIsTrump_jackOfSpades_trumpIsClubs_returnsTrue() {
        card = new Card(Suit.SPADES, CardValue.JACK);
        card.setIsTrump(Suit.CLUBS);
        assertTrue(card.isTrump());
    }


    @Test
    public void testIsTrump_jackOfClubs_trumpIsSpades_returnsTrue() {
        card = new Card(Suit.CLUBS, CardValue.JACK);
        card.setIsTrump(Suit.SPADES);
        assertTrue(card.isTrump());
    }


    @Test
    public void testIsTrump_jackOfSpades_trumpIsHearts_returnsFalse() {
        card = new Card(Suit.SPADES, CardValue.JACK);
        card.setIsTrump(Suit.HEARTS);
        assertFalse(card.isTrump());
    }


    @Test
    public void testIsTrump_jackOfDiamonds_trumpIsClubs_returnsFalse() {
        card = new Card(Suit.DIAMONDS, CardValue.JACK);
        card.setIsTrump(Suit.CLUBS);
        assertFalse(card.isTrump());
    }
}
