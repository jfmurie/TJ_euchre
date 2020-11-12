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
     * Determines if this Card is trump
     *
     * @param trump the suit declared trump
     * @return true if Card has same suit or is left bower, false otherwise
     */
    public boolean isTrump(Suit trump) {
        return this.suit == trump
                || (CardValue.JACK == this.value && getColor(this.suit) == getColor(trump));
    }

    /**
     * Tells if this card has the given suit and CardValue
     *
     * @param s the suit to compare to
     * @param v the CardValue to compare to
     * @return true if this card has the save suit an CardValue
     */
    public boolean isCard(Suit s, CardValue v){
        return this.suit == s && this.value == v;
    }

    /**
     * tells if card is the right bower for a given trump
     *
     * @param trump the given trump
     * @return true if card is Jack of trump, false otherwise
     */
    public boolean isRightBower(Suit trump){
        return isCard(trump, CardValue.JACK);
    }

    /**
     * tells if card is the left bower for a given trump
     *
     * @param trump the given trump
     * @return true if card is Jack of same color suit of trump, false otherwise
     */
    public boolean isLeftBower(Suit trump){
        Suit leftBowerSuit = null;
        switch (trump){
            case CLUBS:
                leftBowerSuit = Suit.SPADES;
                break;
            case DIAMONDS:
                leftBowerSuit = Suit.HEARTS;
                break;
            case HEARTS:
                leftBowerSuit = Suit.DIAMONDS;
                break;
            case SPADES:
                leftBowerSuit = Suit.CLUBS;
                break;
        }
        return isCard(leftBowerSuit, CardValue.JACK);
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