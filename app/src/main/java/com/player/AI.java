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
        return decideTrump(topCard.suit, topCardTurnedDown, dealer);
    }

    public Pair<Suit, Boolean> decideTrump(Suit cardUp, boolean topCardTurnedDown, boolean dealer) {
        if(dealer){
            if(topCardTurnedDown){
                //Todo: Screw the dealer
                // Call trump as suit that AI has the most cards for
                return new Pair<>(null, true);
            }
            else{
                //Todo: decide to turn down card or not
                // if count of suit in hand > 3 and
                //      aiHand.hasCard(Jack, Suit) or aiHand.hasCard(Jack, Other Suit of Same Color)
                //      pick up the card
                // else,
                //      turn it down
            }
        }
        else {
            if(topCardTurnedDown){
                // Todo:
                // if count of suit in hand > 3 and
                //      aiHand.hasCard(Jack, Suit) or aiHand.hasCard(Jack, Other Suit of Same Color)
                //      call pick up the card
            }
            else{
                // Todo:
                // if count of suit in hand > 3 and
                //      aiHand.hasCard(Jack, Suit) or aiHand.hasCard(Jack, Other Suit of Same Color)
                //      call pick up the card
            }
        }
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
     * Method to determine which card an AI should play
     * Based off the flowchart/diagram from initial planning
     *
     * @param cardsPlayed a list of the cards played before this ai's turn
     * @param trump the trump for the round
     * @return the card the logic determines to play
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
        }
        else {
            /*
             * if trump led
             *      if hasTrump
             *          if highest trump can take trick, play it
             *          else if has trump, play lowest
             *      else, throw off
             * else if can follow suit & suit led is not trump
             *      if highest of suit can take trick, play it
             *      else play lowest
             * else if hasTrump
             *      if teammate played highest card, play off
             *      else if highest trump > highest trump played, play highest trump
             *      else play off
             * else play off
             */

            if (cardsPlayed.get(0).getSuit() == trump) {
                if (!aiHand.hasTrump()) {
                    cardIndex = aiHand.getHand().indexOf(aiHand.getLowestNonTrump());
                }
                else {
                    Card highestAITrump = aiHand.getHighestTrump();
                    CardValue highestTrumpPlayed = CardValue.NINE;
                    for (Card c : cardsPlayed) {
                        if (c.getValue().getNumericalValue() > highestTrumpPlayed.getNumericalValue()) {
                            highestTrumpPlayed = c.getValue();
                        }
                    }
                    if (highestTrumpPlayed.getNumericalValue() < highestAITrump.getValue().getNumericalValue()) {
                        cardIndex = aiHand.getHand().indexOf(highestAITrump);
                    } else {
                        cardIndex = aiHand.getHand().indexOf(aiHand.getLowestTrump());
                    }
                }

            }
            //Todo: Need a hasSuit(Suit s) method in Hand class
            // else if(aiHand.hasSuit(cardsPlayed.get(0).getSuit()))
            else if(aiHand.hasTrump()){
                int tm8PlayIndex = getTeammatesPlayIndex(cardsPlayed.size());
                int winningCardIndex = getCurrentWinningCardIndex(cardsPlayed, trump);

                if(tm8PlayIndex == winningCardIndex){
                    cardIndex = aiHand.getHand().indexOf(aiHand.getLowestNonTrump());
                }
                else if(cardsPlayed.get(winningCardIndex).getSuit() == trump){
                    Card aiHighestTrump = aiHand.getHighestTrump();
                    if(cardsPlayed.get(winningCardIndex).getValue().getNumericalValue() < aiHighestTrump.getValue().getNumericalValue()){
                        cardIndex = aiHand.getHand().indexOf(aiHand.getHighestTrump());
                    }
                    else{
                        cardIndex = aiHand.getHand().indexOf(aiHand.getLowestNonTrump());
                    }
                }
            }
            else{
                cardIndex = aiHand.getHand().indexOf(aiHand.getLowestNonTrump());
            }

        }
        return aiHand.removeCard(cardIndex);
    }

    //helper method for AI Logic
    private int getTeammatesPlayIndex(int cardsPlayedNum){
        switch (cardsPlayedNum){
            //Teammate has played
            case 2:
                return 0;
            case 3:
                return 1;

            //Teammate has not played or error
            case 1:
            default:
                return -1;
        }
    }

    //helper method for AI Logic
    private int getCurrentWinningCardIndex(ArrayList<Card> cardsPlayed, Suit trump){
        int highestCardValue = 0;
        int index = -1;
        for(int i = 0; i < cardsPlayed.size(); i++){
            int temp = cardsPlayed.get(i).getValue().getNumericalValue();
            if(cardsPlayed.get(i).getSuit() == cardsPlayed.get(0).getSuit()){
                temp *= 2;
            }
            if(cardsPlayed.get(i).getSuit() == trump){
                temp *= 3;
            }
            if(highestCardValue < temp){
                highestCardValue = temp;
                index = i;
            }
        }
        return index;
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
