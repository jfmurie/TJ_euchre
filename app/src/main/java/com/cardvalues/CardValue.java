package com.cardvalues;

/**
 * Card value enum.  This is used to determine the face value of a card
 * and the corresponding numerical value.
 */
public enum CardValue {
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13),
    ACE(14);


    private final int numericalValue;

    /**
     * Constructor for CardValue class.
     *
     * @param numericalValue The numerical value of the card based on their pecking order
     *                       during game play (ignoring suit)
     */
    CardValue(int numericalValue) {
        this.numericalValue = numericalValue;
    }

    public int getNumericalValue() {
        return this.numericalValue;
    }
}
