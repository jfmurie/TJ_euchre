package com.player;

import android.support.v4.util.Pair;

import com.card.Card;
import com.suits.Suit;

import java.util.ArrayList;

public interface Player {
    int getPlayerNum();

    boolean isLead();
    void setLead(boolean lead);

    ArrayList<Card> getPlayerHand();
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

    Card playCard(int cardIndex);
    Card playCard(ArrayList<Card> cardsPlayed, Suit trump);

    /**
     * When player is the dealer and another calls trump as the top card,
     * the player picks up the top card and discards another.
     * @param topCard the card flipped up.
     */
    void pickItUp(Card topCard, int indexOfOldCard);

    boolean isAI();
}
