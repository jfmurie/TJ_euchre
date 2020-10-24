package com.player;

import android.util.Pair;

import com.card.Card;
import com.hand.Hand;
import com.suits.Suit;

import java.util.ArrayList;

public interface Player {
    int getPlayerNum();

    boolean isLead();
    void setLead(boolean lead);

    ArrayList<Card> getPlayerHand();
    void getCards(ArrayList<Card> dealtCards);

    boolean goAlone(Card card);

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

    Card playCard(int c);
}
