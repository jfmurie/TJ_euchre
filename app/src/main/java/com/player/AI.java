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
    private boolean isLead;
    private Hand aiHand;
    private Pair<Suit, CardValue> HighestRemainingTrumpCard;

    /**
     * default constructor
     * aiNum defaults to 1
     */
    public AI(){
        this.aiNum = 1;
        this.isLead = false;
        this.aiHand = new Hand();
        this.HighestRemainingTrumpCard = null;
    }

    /**
     * constructor to set aiNum (playerNum)
     *
     * @param num the index assigned to this AI
     */
    public AI(int num){
        this.aiNum = num;
        this.isLead = false;
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

    /**
     * HighestRemainingTrumpCard is the highest trump card that COULD be played in the eyes of the AI
     * because every CardValue of trump about this card has been played.
     * This Pair variable is reset to the right bower of trump every time trump is called.
     *
     * @param s trump
     * @param v CardValue
     */
    public void setHighestRemainingTrumpCard(Suit s, CardValue v) {
        this.HighestRemainingTrumpCard = new Pair<>(s, v);
    }

    @Override
    public Pair<Suit, Boolean> callTrump(Card topCard, boolean topCardTurnedDown, boolean dealer) {
        return decideTrump(topCard, topCardTurnedDown, dealer);
    }

    /**
     * Method to determine suit AI should call for trump (or pass)
     * If AI is dealer, the AI will call trump as the suit they have the most of,
     * given that suit is not the suit of the card that was turned down.
     * If AI is not dealer, the AI will call trump if they have 3 trump (one is bower), or 4+ trump.
     *
     * Note: goAlone functionality was discarded.
     *
     * @param cardUp the card that can be called to be picked up by dealer or turned down
     * @param topCardTurnedDown whether or not that cardUp has been turned down
     * @param dealer whther or not AI is dealer
     * @return the suit/pass for trump and a boolean to tell if AI is going alone (this is always false)
     */
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
                    if(aiHand.getCountOfSuit(trump, true) == aiHand.handSize()){
                        cardIndex = aiHand.getHand().indexOf(aiHand.getHighestTrump(trump));
                    }
                    else {
                        cardIndex = aiHand.getHand().indexOf(aiHand.getHighestNonTrump(trump));
                    }
                }
            }
            else {
                if(aiHand.getCountOfSuit(trump, true) == aiHand.handSize()){
                    cardIndex = aiHand.getHand().indexOf(aiHand.getHighestTrump(trump));
                }
                else {
                    cardIndex = aiHand.getHand().indexOf(aiHand.getHighestNonTrump(trump));
                }
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
            //If trump led
            if (cardsPlayed.get(0).isTrump(trump)) {
                if (!aiHand.hasTrump(trump)) {
                    cardIndex = aiHand.getHand().indexOf(aiHand.getLowestNonTrump(trump));
                }
                else {
                    Card highestAITrump = aiHand.getHighestTrump(trump);
                    CardValue highestTrumpPlayed = CardValue.NINE;
                    //get highest trump played
                    for (Card c : cardsPlayed) {
                        if(c.isTrump(trump)){
                            if(c.isRightBower(trump) || c.isLeftBower(trump)){
                                highestTrumpPlayed = CardValue.JACK;
                                break;
                            }
                            if (c.getValue().getNumericalValue() > highestTrumpPlayed.getNumericalValue()) {
                                highestTrumpPlayed = c.getValue();
                            }
                        }
                    }

                    if(highestAITrump.isRightBower(trump)
                            || (highestAITrump.isLeftBower(trump) && highestTrumpPlayed != CardValue.JACK)){
                        cardIndex = aiHand.getHand().indexOf(highestAITrump);
                    }
                    else if(highestTrumpPlayed == CardValue.JACK){
                        cardIndex = aiHand.getHand().indexOf(aiHand.getLowestTrump(trump));
                    }
                    else if (highestTrumpPlayed.getNumericalValue() < highestAITrump.getValue().getNumericalValue()) {
                        cardIndex = aiHand.getHand().indexOf(highestAITrump);
                    } else {
                        cardIndex = aiHand.getHand().indexOf(aiHand.getLowestTrump(trump));
                    }
                }
            }
            //If AI can follow suit
            else if(aiHand.hasSuit(suitLead, trump)){
                Card highestOfSuit = aiHand.getHighestofSuit(suitLead);
                int tm8PlayIndex = getTeammatesPlayIndex(cardsPlayed.size());
                int winningCardIndex = getCurrentWinningCardIndex(cardsPlayed, trump);

                //If teammate has trick so far or AI can't win trick
                if(tm8PlayIndex == winningCardIndex
                        || (highestOfSuit.getValue().getNumericalValue() < cardsPlayed.get(winningCardIndex).getValue().getNumericalValue()
                            && !cardsPlayed.get(winningCardIndex).isTrump(trump))){
                    cardIndex = aiHand.getHand().indexOf(aiHand.getLowestofSuit(suitLead));
                }
                else {
                    cardIndex = aiHand.getHand().indexOf(highestOfSuit);
                }
            }
            //If AI can trump
            else if(aiHand.hasTrump(trump)){
                int tm8PlayIndex = getTeammatesPlayIndex(cardsPlayed.size());
                int winningCardIndex = getCurrentWinningCardIndex(cardsPlayed, trump);

                //if teammate has trick
                if(tm8PlayIndex == winningCardIndex){
                    //if AI can only play trump
                    if(aiHand.getCountOfSuit(trump, true) == aiHand.handSize()){
                        cardIndex = aiHand.getHand().indexOf(aiHand.getLowestTrump(trump));
                    }
                    else{
                        cardIndex = aiHand.getHand().indexOf(aiHand.getLowestNonTrump(trump));
                    }
                }
                else if(cardsPlayed.get(winningCardIndex).isTrump(trump)){
                    Card aiHighestTrump = aiHand.getHighestTrump(trump);
                    Card winningCard = cardsPlayed.get(winningCardIndex);

                    //if no bowers played
                    if( !(winningCard.isRightBower(trump) || winningCard.isLeftBower(trump))){
                        if(winningCard.getValue().getNumericalValue() < aiHighestTrump.getValue().getNumericalValue()){
                            cardIndex = aiHand.getHand().indexOf(aiHighestTrump);
                        }
                        else if(aiHand.getCountOfSuit(trump, true) == aiHand.handSize()){
                            cardIndex = aiHand.getHand().indexOf(aiHand.getLowestTrump(trump));
                        }
                        else{
                            cardIndex = aiHand.getHand().indexOf(aiHand.getLowestNonTrump(trump));
                        }
                    }
                    //if a bower played and AI can't beat it
                    else if(winningCard.isRightBower(trump)
                            ||(winningCard.isLeftBower(trump) && !aiHighestTrump.isRightBower(trump))){
                        //If only trump in hand
                        if(aiHand.getCountOfSuit(trump, true) == aiHand.handSize()){
                            cardIndex = aiHand.getHand().indexOf(aiHand.getLowestTrump(trump));
                        }
                        else{
                            cardIndex = aiHand.getHand().indexOf(aiHand.getLowestNonTrump(trump));
                        }
                    }
                    //AI can play right bower
                    else{
                        cardIndex = aiHand.getHand().indexOf(aiHighestTrump);
                    }
                }
                else {
                    cardIndex = aiHand.getHand().indexOf(aiHand.getLowestTrump(trump));
                }
            }
            //Else, play off
            else{
                cardIndex = aiHand.getHand().indexOf(aiHand.getLowestNonTrump(trump));
            }
        }
        return aiHand.removeCard(cardIndex);
    }

    /**
     * Helper method for determinePlay()
     * Gets the index of the card the player's teammate played in cardsPlayed arrayList
     *
     * @param cardsPlayedNum the size of the arrayList
     * @return the index of teammate's play
     */
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

    /**
     * Helper method for determinePlay()
     * Gets the index of the card in cardsPlayed that would currently take the trick.
     *
     * @param cardsPlayed An arrayList of the cards played by other players thus far
     * @param trump suit that was called as trump for the round
     * @return the index of winning card
     */
    private int getCurrentWinningCardIndex(ArrayList<Card> cardsPlayed, Suit trump){
        int highestCardValue = 0;
        Card highestCard = null;
        int index = 0;
        for(int i = 0; i < cardsPlayed.size(); i++) {
            if (cardsPlayed.get(i).isRightBower(trump)) {
                return i;
            }
            if (cardsPlayed.get(i).isLeftBower(trump)) {
                index = i;
                continue;
            }
            if (cardsPlayed.get(index).isLeftBower(trump)) {
                continue;
            }

            int temp = cardsPlayed.get(i).getValue().getNumericalValue();
            if (cardsPlayed.get(i).getSuit() == cardsPlayed.get(0).getSuit()) {
                temp *= 2;
            }
            if (cardsPlayed.get(i).isTrump(trump)) {
                temp *= 4;
            }

            if (highestCardValue < temp) {
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
