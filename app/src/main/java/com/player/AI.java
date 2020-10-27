package com.player;

import android.util.Pair;

import com.card.Card;
import com.cardvalues.CardValue;
import com.hand.Hand;
import com.suits.Suit;

import java.util.ArrayList;

public class AI implements Player {
    private int aiNum;
    private int teammateNum;
    private boolean isLead;
    private Hand aiHand;
    //This may end up in Round Class as we go forward
    private Pair<Suit, CardValue> HighestRemainingTrumpCard;

    public AI(){
        this.aiNum = 1;
        this.teammateNum = 3;
        this.isLead = false;
        this.aiHand = new Hand();
    }

    public AI(int num){
        this.aiNum = num;
        this.teammateNum = (this.aiNum + 2) % 4;
        this.isLead = false;
        this.aiHand = new Hand();
    }

    public AI(int num, boolean lead){
        this.aiNum = num;
        this.teammateNum = (this.aiNum + 2) % 4;
        this.isLead = lead;
        this.aiHand = new Hand();
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
    public void recieveCardFromDealer(Card dealtCard) {
        aiHand.addCard(dealtCard);
    }

    public void setHighestRemainingTrumpCard(Pair<Suit, CardValue> hrtc) {
        this.HighestRemainingTrumpCard = hrtc;
    }

    @Override
    public boolean goAlone(Card card) {
        return false;
    }

    @Override
    public Pair<Suit, Boolean> callTrump(Card topCard, boolean topCardTurnedDown, boolean dealer) {
        //Todo

        if(dealer){
            if(topCardTurnedDown){

            }
            else{

            }
        }
        else {
            if(topCardTurnedDown){

            }
            else{

            }
        }


        return decideTrump(topCard.suit, topCardTurnedDown);
    }

    public Pair<Suit, Boolean> decideTrump(Suit cardUp, boolean topCardTurnedDown) {
        //Todo
        return new Pair<>(null, false);
    }

    @Override
    public Card playCard(ArrayList<Card> cardsPlayed, Suit trump) {
        return determinePlay(cardsPlayed, trump);
    }

    @Override
    public Card playCard(){
        return null;
    }

    /**
     *
     * @param cardsPlayed
     * @param trump
     * @return
     */
    public Card determinePlay(ArrayList<Card> cardsPlayed, Suit trump){
        if(aiHand.handSize() == 1) {
            return aiHand.removeCard(0);
        }
        int cardIndex = -1;

        if(isLead){
            /* if player has trump then
             *      if HighestRemainingTrumpCard == ai's highest trump card,
             *          play it
             *      else
             *          play highest nontrump card
             * else,
             *      play highest nontrump card
             */
            if(aiHand.hasTrump()){
                Card highestTrump = aiHand.getHighestTrump();
                if(highestTrump.suit == HighestRemainingTrumpCard.first
                        && highestTrump.getValue() == HighestRemainingTrumpCard.second){

                    cardIndex = aiHand.getHand().indexOf(highestTrump);
                }
                else{
                    cardIndex = aiHand.getHand().indexOf(aiHand.getHighestNonTrump());
                }
            }
            else {
                cardIndex = aiHand.getHand().indexOf(aiHand.getLowestNonTrump());
            }
            return aiHand.removeCard(cardIndex);
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

    @Override
    public void pickItUp(Card topCard){
        int lowestCard = aiHand.getHand().indexOf(aiHand.getLowestNonTrump());
        aiHand.removeCard(lowestCard);
        recieveCardFromDealer(topCard);
    }

    @Override
    public boolean isAI(){
        return true;
    }
}
