package com.player;

/* the original statement: android.util.Pair
 * changed because that import doesn't work with unit testing
 * https://stackoverflow.com/questions/35979397/android-immediately-created-pair-elements-are-null
 */

import android.support.v4.util.Pair;

import com.card.Card;
import com.hand.Hand;
import com.suits.Suit;

import java.util.ArrayList;

public class UserPlayer implements Player {
    private int playerNum;
    private boolean isLead;
    private Hand playerHand;
    private Suit suitUserChose;

    /**
     * default constuctor
     * playerNum is always 0 because user index will always be zero
     */
    public UserPlayer(){
        this.playerNum = 0;
        this.isLead = false;
        this.playerHand = new Hand();
        this.suitUserChose = null;
    }

    @Override
    public int getPlayerNum() {
        return playerNum;
    }

    @Override
    public boolean isLead() {
        return isLead;
    }

    @Override
    public void setLead(boolean lead) {
        isLead = lead;
    }

    @Override
    public ArrayList<Card> getPlayerHand() {
        return playerHand.getHand();
    }

    @Override
    public void receiveCardFromDealer(Card dealtCard) {
        playerHand.addCard(dealtCard);
    }

    @Override
    public Pair<Suit, Boolean> callTrump(Card topCard, boolean topCardTurnedDown, boolean dealer) {
        return new Pair<>(this.suitUserChose, false);
    }

    @Override
    public Card playCard(int cardIndex) {
        return playerHand.removeCard(cardIndex);
    }

    @Override
    public Card playCard(ArrayList<Card> cardsPlayed, Suit trump){
        return null;
    }

    @Override
    public void pickItUp(Card topCard, int indexOfOldCard){
        this.playerHand.replaceCard(topCard, indexOfOldCard);
    }

    @Override
    public boolean isAI(){
        return false;
    }
}
