package com.player;

import android.util.Pair;

import com.card.Card;
import com.hand.Hand;
import com.suits.Suit;

import java.util.ArrayList;

public class AI implements Player {
    private int aiNum;
    private boolean isLead;
    private Hand aiHand;

    //This will probably end up in Round Class as we go forward
    private Card hrtc; //Highest Remaining Trump Card

    public AI(){
        aiNum = 1;
        isLead = false;
        aiHand = new Hand();
    }

    public AI(int num){
        aiNum = num;
        isLead = false;
        aiHand = new Hand();
    }

    public AI(int num, boolean lead){
        aiNum = num;
        isLead = lead;
        aiHand = new Hand();
    }

    @Override
    public int getPlayerNum() {
        return aiNum;
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
    public ArrayList<Card> getPlayerHand() { return aiHand.getHand(); }

    @Override
    public void getCards(ArrayList<Card> dealtCards) {
        for(Card c: dealtCards){
            aiHand.addCard(c);
        }
    }

    public void setHrtc(Card hrtc) {
        this.hrtc = hrtc;
    }

    @Override
    public boolean goAlone(Card card) {
        return false;
    }

    @Override
    public Pair<Suit, Boolean> callTrump(Card topCard, boolean topCardTurnedDown, boolean dealer) {
        //Todo: pass this suit to setTrump() in Round class, when it is made
        return new Pair<>(null, false);
    }

    public void decideTrump(Suit cardUp) {
        //Todo:
    }

    public void decideTrump() {
        //Todo:
    }

    @Override
    public Card playCard(int c) {
        if(c < 0 || c > aiHand.getHand().size()){
            throw new IndexOutOfBoundsException("This card does not exist.");
        }
        return aiHand.getHand().remove(c);  //Todo: change to aiHand.removeCard(c);
    }

    public Card determinePlay(ArrayList<Card> cardsPlayed, Suit trump){
        if(aiHand.getHand().size() == 1){ //Todo: aiHand.handSize();
            return playCard(0);
        }
        int cardIndex = -1;

        if(isLead){
            //Todo: if has trump,
            //          check the trump you have
            //          if hrtc == ai's highest trump card, play it
            //          else play highest nontrump card
            //      else,
            //          play highest nontrump card

            if(aiHand.hasTrump()){
                if(aiHand.getHighestTrump().equals(hrtc)){
                    cardIndex = aiHand.getHand().indexOf(aiHand.getHighestTrump());
                }
                else{
                    cardIndex = aiHand.getHand().indexOf(aiHand.getHighestNonTrump());
                }
            }

            return playCard(cardIndex);
        }
        else{
            //Todo:
            // if trump led
            //      if highest trump can take trick, play it
            //      else play lowest
            // else if can follow suit & suit led is not trump
            //      if highest of suit can take trick, play it
            //      else play lowest
            // else if hasTrump
            //      if teammate played highest card, play off
            //      else if highest trump > highest trump played, play highest trump
            //      else play off
            // else play off




        }


        return null;
    }


}
