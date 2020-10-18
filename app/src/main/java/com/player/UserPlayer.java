package com.player;

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
    public void getCards(ArrayList<Card> dealtCards) {
        for(Card c: dealtCards){
            playerHand.addCard(c);
        }
    }

    @Override
    public void goAlone(Suit s) {
        callTrump(s);
        //Todo: Alert Round Class that only 3 people are playing
    }

    @Override
    public void callTrump(Suit s) {
        //Todo: this will alert Round class that the other player of this team will be sitting out
    }

    @Override
    public Card playCard(int c) {
        if(c < 0 || c > playerHand.getHand().size()){   //Todo: change to playerHand.handSize()
            throw new IndexOutOfBoundsException("This card does not exist.");
        }
        return playerHand.getHand().remove(c); //Todo: change to playerHand.removeCard(c)
    }
}
