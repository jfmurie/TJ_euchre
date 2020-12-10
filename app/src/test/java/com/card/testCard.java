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
        assertTrue(card.isTrump(Suit.HEARTS));
    }


    @Test
    public void testIsTrump_nineOfHearts_trumpIsDiamonds_returnsFalse() {
        card = new Card(Suit.HEARTS, CardValue.NINE);
        assertFalse(card.isTrump(Suit.DIAMONDS));
    }

    @Test
    public void testIsTrump_nineOfHearts_trumpIsClubs_returnsFalse() {
        card = new Card(Suit.HEARTS, CardValue.NINE);
        assertFalse(card.isTrump(Suit.CLUBS));
    }


    @Test
    public void testIsTrump_nineOfHearts_trumpIsSpades_returnsFalse() {
        card = new Card(Suit.HEARTS, CardValue.NINE);
        assertFalse(card.isTrump(Suit.SPADES));
    }


    @Test
    public void testIsTrump_jackOfHearts_trumpIsDiamonds_returnsTrue() {
        card = new Card(Suit.HEARTS, CardValue.JACK);
        assertTrue(card.isTrump(Suit.DIAMONDS));
    }


    @Test
    public void testIsTrump_jackOfDiamonds_trumpIsHearts_returnsTrue() {
        card = new Card(Suit.DIAMONDS, CardValue.JACK);
        assertTrue(card.isTrump(Suit.HEARTS));
    }


    @Test
    public void testIsTrump_jackOfSpades_trumpIsClubs_returnsTrue() {
        card = new Card(Suit.SPADES, CardValue.JACK);
        assertTrue(card.isTrump(Suit.CLUBS));
    }


    @Test
    public void testIsTrump_jackOfClubs_trumpIsSpades_returnsTrue() {
        card = new Card(Suit.CLUBS, CardValue.JACK);
        assertTrue(card.isTrump(Suit.SPADES));
    }


    @Test
    public void testIsTrump_jackOfSpades_trumpIsHearts_returnsFalse() {
        card = new Card(Suit.SPADES, CardValue.JACK);
        assertFalse(card.isTrump(Suit.HEARTS));
    }


    @Test
    public void testIsTrump_jackOfDiamonds_trumpIsClubs_returnsFalse() {
        card = new Card(Suit.DIAMONDS, CardValue.JACK);
        assertFalse(card.isTrump(Suit.CLUBS));
    }

    @Test
    public void testIsLeftBower_jackOfDiamonds_heartsIsTrump_returnsTrue() {
        card = new Card(Suit.DIAMONDS, CardValue.JACK);
        assertTrue(card.isLeftBower(Suit.HEARTS));
    }

    @Test
    public void testIsLeftBower_jackOfHearts_trumpIsDiamonds_returnsTrue() {
        card = new Card(Suit.HEARTS, CardValue.JACK);
        assertTrue(card.isLeftBower(Suit.DIAMONDS));
    }


    @Test
    public void testIsLeftBower_jackOfSpades_trumpIsClubs_returnsTrue() {
        card = new Card(Suit.SPADES, CardValue.JACK);
        assertTrue(card.isLeftBower(Suit.CLUBS));
    }


    @Test
    public void testIsLeftBower_jackOfClubs_trumpIsSpades_returnsTrue() {
        card = new Card(Suit.CLUBS, CardValue.JACK);
        assertTrue(card.isLeftBower(Suit.SPADES));
    }
}
