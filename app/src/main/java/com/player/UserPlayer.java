package com.player;

/* the original statement: android.util.Pair
 * changed because that import doesn't work with unit testing
 * https://stackoverflow.com/questions/35979397/android-immediately-created-pair-elements-are-null
 */
import android.support.v4.util.Pair;

import com.card.Card;
import com.suits.Suit;
import com.hand.Hand;

import java.util.ArrayList;

public class UserPlayer implements Player {
    private int playerNum;
    private boolean isLead;
    private Hand playerHand;

    public UserPlayer(){
        playerNum = 0;
        isLead = false;
        playerHand = new Hand();
    }

    public UserPlayer(boolean isLead){
        playerNum = 0;
        this.isLead = isLead;
        playerHand = new Hand();
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
    public boolean goAlone(Card card) {
        //Todo: Alert Round Class that only 3 people are playing
        // AGAIN GET USER INPUT SOMEHOW
        return false;
    }

    @Override
    public Pair<Suit, Boolean> callTrump(Card topCard, boolean topCardTurnedDown, boolean dealer) {
        //Todo: this will alert Round class that the other player of this team will be sitting out
        // SOMEHOW SOMEWAY GET USER INPUT :(
        return new Pair<>(null, goAlone(topCard));
    }

    @Override
    public Card playCard() {
        int c = -1;
        //Todo: get user input... idk how
        return playerHand.removeCard(c);
    }

    @Override
    public Card playCard(ArrayList<Card> cardsPlayed, Suit trump){
        return null;
    }

    @Override
    public void pickItUp(Card topCard){
        //Todo: get user input on which card to remove
        receiveCardFromDealer(topCard);
    }

    @Override
    public boolean isAI(){
        return false;
    }
}
