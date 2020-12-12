package com.player;

import android.support.v4.util.Pair;

import com.card.Card;
import com.suits.Suit;

import java.util.ArrayList;

public interface Player {
    /**
     * Player Number is the index of the player in the array of players (GameScreen.java).
     *
     * @return player number as specified with the constructor (Valid values: 0-3)
     */
    int getPlayerNum();

    /**
     * Lead is a boolean to verify the player is leaing the trick
     *
     * @return true if player is leading trick, false otherwise.
     */
    boolean isLead();

    /**
     * changes if player is leading the trick or not
     *
     * @param lead boolean value, true if player is supposed to lead the next trick
     */
    void setLead(boolean lead);

    /**
     * Get the cards in the player's hand
     *
     * @return card arraylist of the card in the player's hand
     */
    ArrayList<Card> getPlayerHand();

    /**
     * Adds a single card to the player's hand
     *
     * @param dealtCard the card to add
     */
    void receiveCardFromDealer(Card dealtCard);

    /**
     * Calls trump and whether or not the player will be going alone.
     * If the player is a dealer and the topCard has been turned down,
     * the player will not be able to "pass" (return false for suit)
     *
     * @param topCard The top card of the kiddy
     * @param topCardTurnedDown Whether or not the top card has been turned down
     * @param dealer If the player is a dealer or not
     * @return The called suit (if one is called) or null (if the player passes
     * along with a boolean that says if the player is going alone or not.
     */
    Pair<Suit, Boolean> callTrump(Card topCard, boolean topCardTurnedDown, boolean dealer);

    /**
     * Plays a card (removing it from hand)
     * This method is to be used by UserPlayer.java only.
     *
     * @param cardIndex the index of the card to play
     * @return the card being played
     */
    Card playCard(int cardIndex);

    /**
     * Plays a card (removing it from hand)
     * This method is to be used by AI.java only.
     *
     * @param cardsPlayed the cards alreay
     * @param trump the suit trump was called as for the round
     * @return the card being played
     */
    Card playCard(ArrayList<Card> cardsPlayed, Suit trump);

    /**
     * When player is the dealer and another calls trump as the top card,
     * the player picks up the top card and discards another.
     *
     * @param topCard the card turned up for the dealer to pick up
     * @param indexOfOldCard The index of the card to be replaced (used for AI.java)
     */
    void pickItUp(Card topCard, int indexOfOldCard);

    /**
     * To determine if a cast to UserPlayer or AI is safe
     *
     * @return true if Player is an instance of AI, false otherwise
     */
    boolean isAI();
}
