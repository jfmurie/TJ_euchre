package com.player;

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

    void goAlone(Suit s);

    void callTrump(Suit s);

    Card playCard(int c);
}
