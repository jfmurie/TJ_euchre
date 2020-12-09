package com.player;

/* the original statement: android.util.Pair
 * changed because that import doesn't work with unit testing
 * https://stackoverflow.com/questions/35979397/android-immediately-created-pair-elements-are-null
 */
import android.support.v4.util.Pair;

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
    private Pair<Suit, CardValue> HighestRemainingTrumpCard;

    public AI(){
        this.aiNum = 1;
        this.teammateNum = 3;
        this.isLead = false;
        this.aiHand = new Hand();
        this.HighestRemainingTrumpCard = null;
    }

    public AI(int num){
        this.aiNum = num;
        this.teammateNum = (this.aiNum + 2) % 4;
        this.isLead = false;
        this.aiHand = new Hand();
        this.HighestRemainingTrumpCard = null;
    }

    public AI(int num, boolean lead){
        this.aiNum = num;
        this.teammateNum = (this.aiNum + 2) % 4;
        this.isLead = lead;
        this.aiHand = new Hand();
        this.HighestRemainingTrumpCard = null;
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
    public void receiveCardFromDealer(Card dealtCard) {
        aiHand.addCard(dealtCard);
    }

    public void setHighestRemainingTrumpCard(Suit s, CardValue v) {
        this.HighestRemainingTrumpCard = new Pair<>(s, v);
    }

    /**
     * Ai cannot go Alone
     *
     * @param card the card that is turned up
     * @return whether or not the AI is going alone
     */
    @Override
    public boolean goAlone(Card card) {
        return false;
    }

    @Override
    public Pair<Suit, Boolean> callTrump(Card topCard, boolean topCardTurnedDown, boolean dealer) {
        return decideTrump(topCard, topCardTurnedDown, dealer);
    }

    public Pair<Suit, Boolean> decideTrump(Card cardUp, boolean topCardTurnedDown, boolean dealer) {
        Pair<Suit, Integer> mostSuitAndCount = aiHand.mostCardsOfThisSuit();

        /* Screw the dealer - Call trump as the suit that AI has the most cards of */
        if(dealer && topCardTurnedDown) {
            if(mostSuitAndCount.first == cardUp.getSuit()) {
                Suit secondBest = Suit.PASS;
                int secondBestCount = 0;
                for (Suit s : Suit.values()) {
                    if (s == cardUp.getSuit()) {
                        continue;
                    }
                    int curr = aiHand.getCountOfSuit(s, true);
                    if (curr > secondBestCount) {
                        secondBestCount = curr;
                        secondBest = s;
                    }
                }

                if (secondBest == Suit.PASS) {
                    System.out.println("Error, can't obtain a second best choice for callTrump.");
                    return null;
                }
                return new Pair<>(secondBest, false);
            }
            return new Pair<>(mostSuitAndCount.first, false);
        }
        else if(dealer){
            boolean hasABower = aiHand.hasRightBower(cardUp.getSuit())
                                || aiHand.hasLeftBower(cardUp.getSuit())
                                || cardUp.isLeftBower(cardUp.getSuit())
                                || cardUp.isRightBower(cardUp.getSuit());

            if((aiHand.getCountOfSuit(cardUp.getSuit(), true)) >= 2 && hasABower){
                return new Pair<>(cardUp.getSuit(), false);
            }
        }
        else if(!topCardTurnedDown){
            if(aiHand.getCountOfSuit(cardUp.getSuit(), true) > 2
                    && (aiHand.hasRightBower(cardUp.getSuit())
                    || aiHand.hasLeftBower(cardUp.getSuit()))){
                return new Pair<>(cardUp.getSuit(), false);
            }
        }
        else{
            /* Decide to turn down the card or not
             *
             * if count of suit in hand >= 3
             *      and aiHand.hasRightBower()
             *      or  aiHand.hasLeftBower()
             *      pick up the card (aka set trump)
             * else
             *      turn down the card (aka return null for trump)
             */
            if(mostSuitAndCount.first == null || mostSuitAndCount.second == null){
                return null;
            }
            if(mostSuitAndCount.first == cardUp.getSuit()){
                return new Pair<>(Suit.PASS, false);
            }
            if(mostSuitAndCount.second > 2
                    && (aiHand.hasRightBower(mostSuitAndCount.first)
                    || aiHand.hasLeftBower(mostSuitAndCount.first))){
                return new Pair<>(mostSuitAndCount.first, false);
            }
        }
        return new Pair<>(Suit.PASS, false);
    }

    @Override
    public Card playCard(ArrayList<Card> cardsPlayed, Suit trump) {
        return determinePlay(cardsPlayed, trump);
    }

    @Override
    public Card playCard(int cardIndex){
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
        int cardIndex;

        if(isLead){
            /* if player has trump then
             *      if HighestRemainingTrumpCard == ai's highest trump card,
             *          play it
             *      else
             *          play highest nontrump card
             * else,
             *      play highest nontrump card
             */
            if(aiHand.hasTrump(trump)){
                Card highestTrump = aiHand.getHighestTrump(trump);
                if(highestTrump.getSuit() == HighestRemainingTrumpCard.first
                        && highestTrump.getValue() == HighestRemainingTrumpCard.second){
                    cardIndex = aiHand.getHand().indexOf(highestTrump);
                }
                else{
                    cardIndex = aiHand.getHand().indexOf(aiHand.getHighestNonTrump(trump));
                }
            }
            else {
                cardIndex = aiHand.getHand().indexOf(aiHand.getHighestNonTrump(trump));
            }
        }
        else {
            /*
             * if trump led
             *      if hasTrump
             *          if highest trump can take trick, play it
             *          else if has trump, play lowest
             *      else, throw off
             */
            /*
             * else if can follow suit & suit led is not trump
             *      if highest of suit can take trick, play it
             *      else play lowest
             */
            /* NEEDS TO BE UPDATED:
             * else if hasTrump
             *      if teammate played highest card, play off
             *      else if highest trump > highest trump played, play highest trump
             *      else play off
             */
            /* else play off */
            Suit suitLead = cardsPlayed.get(0).getSuit();

            if (suitLead == trump) {
                if (!aiHand.hasTrump(trump)) {
                    cardIndex = aiHand.getHand().indexOf(aiHand.getLowestNonTrump(trump));
                }
                else {
                    Card highestAITrump = aiHand.getHighestTrump(trump);
                    CardValue highestTrumpPlayed = CardValue.NINE;
                    for (Card c : cardsPlayed) {
                        if (c.getValue().getNumericalValue() > highestTrumpPlayed.getNumericalValue()) {
                            highestTrumpPlayed = c.getValue();
                        }
                    }
                    if (highestTrumpPlayed.getNumericalValue() < highestAITrump.getValue().getNumericalValue()) {
                        cardIndex = aiHand.getHand().indexOf(highestAITrump);
                    } else {
                        cardIndex = aiHand.getHand().indexOf(aiHand.getLowestTrump(trump));
                    }
                }
            }
            else if(aiHand.hasSuit(suitLead, trump)){
                if(aiHand.getHighestofSuit(suitLead).getValue().getNumericalValue() <
                        cardsPlayed.get(getCurrentWinningCardIndex(cardsPlayed,trump)).getValue().getNumericalValue()){
                    cardIndex = aiHand.getHand().indexOf(aiHand.getLowestofSuit(suitLead));
                }
                else {
                    cardIndex = aiHand.getHand().indexOf(aiHand.getHighestofSuit(suitLead));
                }
            }
            else if(aiHand.hasTrump(trump)){
                int tm8PlayIndex = getTeammatesPlayIndex(cardsPlayed.size());
                int winningCardIndex = getCurrentWinningCardIndex(cardsPlayed, trump);

                if(tm8PlayIndex == winningCardIndex){
                    if(aiHand.getCountOfSuit(trump, true) == aiHand.handSize()){
                        cardIndex = aiHand.getHand().indexOf(aiHand.getLowestTrump(trump));
                    }
                    else{
                        cardIndex = aiHand.getHand().indexOf(aiHand.getLowestNonTrump(trump));
                    }
                }
                else if(cardsPlayed.get(winningCardIndex).getSuit() == trump){
                    Card aiHighestTrump = aiHand.getHighestTrump(trump);
                    System.out.println("Made it here to the right else if.");
                    if(cardsPlayed.get(winningCardIndex).getValue().getNumericalValue() < aiHighestTrump.getValue().getNumericalValue()) {
                        cardIndex = aiHand.getHand().indexOf(aiHighestTrump);
                    }
                    else if(aiHand.getCountOfSuit(trump, true) == aiHand.handSize()){
                        cardIndex = aiHand.getHand().indexOf(aiHand.getLowestTrump(trump));
                    }
                    else{
                        cardIndex = aiHand.getHand().indexOf(aiHand.getLowestNonTrump(trump));
                    }
                } else {
                    cardIndex = aiHand.getHand().indexOf(aiHand.getLowestTrump(trump));
                }
            }
            else{
                cardIndex = aiHand.getHand().indexOf(aiHand.getLowestNonTrump(trump));
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
    public void pickItUp(Card topCard, int indexOfOldCard){
        //indexOfOldCard, this value is only used in UserPlayer
        int lowestCard = aiHand.getHand().indexOf(aiHand.getLowestNonTrump(topCard.getSuit()));
        aiHand.removeCard(lowestCard);
        receiveCardFromDealer(topCard);
    }

    @Override
    public boolean isAI(){
        return true;
    }


}
