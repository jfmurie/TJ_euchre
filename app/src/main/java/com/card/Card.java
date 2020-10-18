package com.card;

import com.cardvalues.CardValue;
import com.color.Color;
import com.suits.Suit;

/**
 * The class for card objects.  Cards have a suit and a value.  They may or may not be trump.
 *
 * TODO: playedBy field after Player class is written
 */
public class Card {

    /**
     * Suit is the suit of the card (Spades, Clubs, Hearts or Diamonds)
     */
    public final Suit suit;

    /**
     * Value is the value of the card (9-14)
     */
    public final CardValue value; // might want to consider making this an enum

    /**
     * If the card is Trump or not
     */
    public boolean isTrump;  // might want to consider a different approach to this


    /**
     * Constructor for Card object, sets the suit and value.
     *
     * @param suit the passed suit
     * @param value the passed value of the card
     */
    public Card(Suit suit, CardValue value) {
        this.suit = suit;
        this.value = value;
    }

    /**
     * Getter for isTrump variable.
     *
     * @return True if the card is trump.
     */
    public boolean isTrump() {
        return this.isTrump;
    }


    /**
     * Returns the color of the card based on the suit.
     * @param suit the suit
     * @return Color.BLACK if the suit is clubs or spades and Color.RED otherwise
     */
    public Color getColor(Suit suit) {
        if (suit == Suit.DIAMONDS || suit == Suit.HEARTS)
            return Color.RED;

        return Color.BLACK;
    }

    /**
     * Sets if the card is trump or not.
     *
     * @param trump The suit that is trump during a given round.
     */
    public void setIsTrump(Suit trump) {
        this.isTrump = this.suit == trump
            || (CardValue.JACK == this.getValue() && getColor(this.getSuit()) == getColor(trump));
    }

    /**
     * Getter for the suit variable.
     *
     * @return The suit of the card
     */
    public Suit getSuit() {
        return this.suit;
    }

    /**
     * Getter for the value variable.
     *
     * @return The value of the card (9-14)
     */
    public CardValue getValue() {
        return this.value;
    }
}