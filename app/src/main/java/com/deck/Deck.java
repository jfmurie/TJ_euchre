package com.deck;

import com.card.Card;
import com.cardvalues.CardValue;
import com.suits.Suit;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This is the class for the deck object.
 * It is implemented with an array list of cards.
 *
 */
public class Deck {

    public final ArrayList<Card> deck;


    /**
     * Constructor for the deck object.  This constructor
     * initializes the card ArrayList to have one card of each suit and value.
     */
    public Deck() {
        this.deck = new ArrayList<>();
        Suit[] suitArray = {Suit.SPADES, Suit.CLUBS, Suit.HEARTS, Suit.DIAMONDS};
        CardValue[] valuesArray = {CardValue.NINE,
                CardValue.TEN,
                CardValue. JACK,
                CardValue.QUEEN,
                CardValue.KING,
                CardValue.ACE};

        for (Suit suit : suitArray) {
            for (CardValue value : valuesArray) {
                deck.add(new Card(suit, value));
            }
        }

    }

    /**
     * TODO: decide if this should return void or the deck
     * Shuffles the deck using Collections.shuffle(list) method.
     *
     * https://docs.oracle.com/javase/6/docs/api/java/util/Collections.html#shuffle(java.util.List)
     *
     */
    public void shuffleDeck() {
        Collections.shuffle(this.deck);
    }
}
