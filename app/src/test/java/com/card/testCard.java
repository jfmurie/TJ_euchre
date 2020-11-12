package com.card;

import com.cardvalues.CardValue;
import com.suits.Suit;

import org.junit.Assert;
import org.junit.Test;

public class testCard {

    Card card;

    @Test
    public void testIsTrump_nineOfHearts_trumpIsHearts_returnsTrue() {
        card = new Card(Suit.HEARTS, CardValue.NINE);
        Assert.assertTrue(card.isTrump(Suit.HEARTS));
    }



}
