package com.card;

import com.cardvalues.CardValue;
import com.suits.Suit;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class testCard {

    Card card;

    @Test
    public void testIsTrump_nineOfHearts_trumpIsHearts_returnsTrue() {
        card = new Card(Suit.HEARTS, CardValue.NINE);
        card.setIsTrump(Suit.HEARTS);
        assertTrue(card.isTrump());
    }



}
