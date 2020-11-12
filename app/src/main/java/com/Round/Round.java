package com.Round;

/* the original statement: android.util.Pair
 * changed because that import doesn't work with unit testing
 * https://stackoverflow.com/questions/35979397/android-immediately-created-pair-elements-are-null
 */
import android.support.v4.util.Pair;

import com.Trick.Trick;
import com.card.Card;
import com.player.Player;
import com.suits.Suit;

public class Round {

    private Suit trump; // do we want this in construtor

    // make team field

    private final Trick[] trick;

    private final int trickIndex;

    private int dealerIndex;

    private int sitOut;

    /**
     * Constructor for the Round class.
     */
    public Round() {
        this.trick = new Trick[5];
        this.trickIndex = 0;
        sitOut = -1;
    }

    public Suit getTrump() {
        return trump;
    }

    public int getTrickIndex() {
        return trickIndex;
    }

    public void setSitOut(int sitOut) {
        this.sitOut = sitOut;
    }

    /**
     * Loops through the players until one calls trump.  Implemented with screw the dealer
     * logic only.
     *
     * @param players List of players
     * @param turnedUp Suit of the card that was turned up
     */
    public void decideTrump(Player[] players, Card turnedUp) {
        Suit suit;
        Boolean goAlone;
        boolean turnedDown = false;
        int playerIndex = (dealerIndex + 1) % 4;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                Pair<Suit, Boolean> suitGoAlonePair = players[playerIndex].callTrump(turnedUp, turnedDown, false);
                suit = suitGoAlonePair.first;
                goAlone = suitGoAlonePair.second;

                if (suit != null) {
                    this.trump = suit;

                    if(!turnedDown) {
                        players[playerIndex].pickItUp(turnedUp);
                    }

                    if (goAlone != null && goAlone) {
                        this.sitOut = playerIndex + 2 % 4;
                    }
                    return;
                }

                playerIndex = (playerIndex + 1) % 4;
            }
            turnedDown = true;
        }
    }


    public void playRound(){
        //Todo: run through 5 tricks
    }


}
