package com.hand;

/* the original statement: android.util.Pair
 * changed because that import doesn't work with unit testing
 * https://stackoverflow.com/questions/35979397/android-immediately-created-pair-elements-are-null
 */
import android.support.v4.util.Pair;

import com.card.Card;
import com.cardvalues.CardValue;
import com.suits.Suit;

import java.util.ArrayList;

public class Hand {
    ArrayList<Card> hand;
    //cards get added to this when they are dealt

    public Hand() {
        hand = new ArrayList<>();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> newHand) {
        hand = newHand;
    }

    //adds card as dealt
    public void addCard(Card dealtCard) {
        hand.add(dealtCard);
    }

    //loop through array/arraylist of cards in the hand.
    public boolean hasTrump(Suit trump) {
        for(Card c:hand) {
            if(c.isTrump(trump)){
                return true;
            }
        }
        return false;
    }

    public boolean hasSuit(Suit s){
        for(Card c:hand) {
            if(c.getSuit() == s) {
                return true;
            }
        }
        return false;
    }


    //for these does this mean that a jack will always be highest or
    //does it count trump so trump 10 is higher than non-trump queen?
    //Trump counts higher than others
    public Card getHighestCard() {
        //loop through all cards and find the highest value card
        int temp = 0;
        for(int i = 0; i < hand.size(); i++){
            if(hand.get(temp).getValue().getNumericalValue() < hand.get(i+1).getValue().getNumericalValue()) {
                temp = i+1;
            }
        }
        return hand.get(temp);
    }

    public int getHighestCardIndex() {
        //loop through all cards and find the highest value card
        int index = 0;
        for(int i = 0; i < hand.size(); i++){
            if(hand.get(index).getValue().getNumericalValue() < hand.get(i+1).getValue().getNumericalValue()) {
                index = i+1;
            }
        }
        return index;
    }

    public Card getHighestTrump(Suit trump) {
        int highIndex = 0;
        //if has no cards
        if (hand.isEmpty()){
            return null;
        }
        ArrayList<Card> trumpCards;
        trumpCards = getTrumpCards(trump);
        //if has no trump cards
        if (trumpCards.isEmpty()){
            return null;
        }

        CardValue highestVal = null;
        Card highestTrump = null;
        for (Card c: trumpCards) {
            if(highestVal == null){
                highestVal = c.getValue();
                highestTrump = c;
            }
            if(c.isRightBower(trump)){
                return c;
            }
            else if(c.isLeftBower(trump)){
                highestVal = c.getValue();
                highestTrump = c;
            }
            else if(highestVal.getNumericalValue() < c.getValue().getNumericalValue()){
                if (highestTrump.isLeftBower(trump)) {
                    continue;
                }
                highestVal = c.getValue();
                highestTrump = c;
            }
        }
        return highestTrump;
    }

    public Card getLowestTrump(Suit trump) {
        int lowIndex = 0;
        //if has no cards
        if (hand.isEmpty()){
            return null;
        }
        ArrayList<Card> trumpCards;
        trumpCards = getTrumpCards(trump);
        //if has no trump cards
        if (trumpCards.isEmpty()){
            return null;
        }
        //if only has 1 trump card
        if (trumpCards.size() == 1){
            return trumpCards.get(0);
        }
        //2 or more trump cards looks for highest
        for (int i = 0; i < trumpCards.size(); i++) {
            if (trumpCards.get(lowIndex).getValue().getNumericalValue() > trumpCards.get(i+1).getValue().getNumericalValue()){
                lowIndex = i+1;
            }
        }
        return trumpCards.get(lowIndex);
    }

    public Card getHighestNonTrump(Suit trump) {
        int highNTIndex = 0;
        //if has no cards
        if (hand.isEmpty()){
            return null;
        }
        ArrayList<Card> nontrump;
        nontrump = getNonTrumpCards(trump);
        //if has no nontrump cards
        if (nontrump.isEmpty()){
            return null;
        }

        CardValue highestVal = null;
        Card highestNonTrump = null;
        for (Card c: nontrump) {
            if(highestVal == null){
                highestVal = c.getValue();
                highestNonTrump = c;
            }
            else if(highestVal.getNumericalValue() < c.getValue().getNumericalValue()){
                highestVal = c.getValue();
                highestNonTrump = c;
            }
        }
        return highestNonTrump;
    }

    public Card getLowestNonTrump(Suit trump) {
        //if has no cards
        if (hand.isEmpty()){
            return null;
        }
        ArrayList<Card> nontrump;
        nontrump = getNonTrumpCards(trump);

        //if has no nontrump cards
        if (nontrump.isEmpty()){
            return null;
        }

        CardValue lowestVal = null;
        Card lowestNonTrump = null;
        for (Card c: nontrump) {
            if(lowestVal == null){
                lowestVal = c.getValue();
                lowestNonTrump = c;
            }
            else if(lowestVal.getNumericalValue() > c.getValue().getNumericalValue()){
                lowestVal = c.getValue();
                lowestNonTrump = c;
            }
        }
        return lowestNonTrump;
    }

    public ArrayList<Card> getTrumpCards(Suit trump) {
        ArrayList<Card> trumpInHand = new ArrayList<>();
        for(Card c: this.hand){
            if(c.isTrump(trump)){
                trumpInHand.add(c);
            }
        }
        return trumpInHand;
    }

    public ArrayList<Card> getNonTrumpCards(Suit trump) {
        ArrayList<Card> nonTrumpInHand = new ArrayList<>();
        for(Card c: this.hand){
            if(!c.isTrump(trump)){
                nonTrumpInHand.add(c);
            }
        }
        return nonTrumpInHand;
    }

    public int handSize() {
        return hand.size();
    }

    //play card method
    //take in the index of the card removes that card returns the card identifier
    //could I have it so that it removes the card and adds it to a cards played list to use in trick??
    public Card removeCard(int index){
        return hand.remove(index);
    }


    /**
     *
     * @return the suit that the player has the most cards of and the count of the suit
     */
    public Pair<Suit, Integer> mostCardsOfThisSuit(){
        Suit[] suits = {Suit.SPADES, Suit.CLUBS, Suit.HEARTS, Suit.DIAMONDS};
        int[] numOfSuits = new int[4];


        if(hand == null || hand.size() == 0){
            return null;
        }

        //Count the number of different suits
        for (Card c:hand) {
            switch (c.getSuit()){
                case SPADES:
                    numOfSuits[0]++;
                    break;
                case CLUBS:
                    numOfSuits[1]++;
                    break;
                case HEARTS:
                    numOfSuits[2]++;
                    break;
                case DIAMONDS:
                    numOfSuits[3]++;
                    break;
                default:
                    break;
            }
        }

        //Get the suit with most cards in hand
        int mostOf = 0;
        int indexOfMost = -1;
        for(int i = 0; i < 4; i++){
            if(numOfSuits[i] > mostOf){
                mostOf = numOfSuits[i];
                indexOfMost = i;
            }
        }

        if(indexOfMost == -1){
            return null;
        }

        return new Pair<>(suits[indexOfMost], numOfSuits[indexOfMost]);
    }

    public int getCountOfSuit(Suit s, boolean isTrump){
        if(this.hand == null || this.hand.size() == 0){
            return -1;
        }
        int count = 0;
        for(Card c:this.hand){
            if(c.getSuit() == s){
                count++;
            } else if(isTrump && c.isLeftBower(s)){
                count++;
            }
        }
        return count;
    }



    public boolean hasCard(Suit s, CardValue v){
        for(Card c:this.hand) {
           if(c.isCard(s, v)){
               return true;
           }
        }
        return false;
    }

    public boolean hasRightBower(Suit trump){
        return hasCard(trump, CardValue.JACK);
    }

    public boolean hasLeftBower(Suit trump){
        Suit leftBowerSuit = null;
        switch (trump){
            case CLUBS:
                leftBowerSuit = Suit.SPADES;
                break;
            case DIAMONDS:
                leftBowerSuit = Suit.HEARTS;
                break;
            case HEARTS:
                leftBowerSuit = Suit.DIAMONDS;
                break;
            case SPADES:
                leftBowerSuit = Suit.CLUBS;
                break;
        }
        return hasCard(leftBowerSuit, CardValue.JACK);
    }

    /**
     * Finds the highest valued card in hand of specified suit
     *
     * @param suit the suit we are looking at
     * @return the lowest card in hand of that suit, null if player had none of that suit
     */
    public Card getHighestofSuit(Suit suit){
        //if has no cards
        if (hand.isEmpty()){
            return null;
        }

        Card highest = null;
        for(Card c: hand){
            if(c.getSuit() == suit){
                if(highest == null){
                    highest = c;
                }
                else if(c.getValue().getNumericalValue() > highest.getValue().getNumericalValue()){
                    highest = c;
                }
            }
        }

        return highest;
    }

    /**
     * Finds the lowest valued card in hand of specified suit
     *
     * @param suit suit being looked at
     * @return the Card with the lowest numerical value of the suit, null if Hand does not contain
     *          any of the suit or is empty
     */
    public Card getLowestofSuit(Suit suit){
        //if has no cards
        if (hand.isEmpty()){
            return null;
        }

        Card lowest = null;
        for(Card c: hand){
            if(c.getSuit() == suit){
                if(lowest == null){
                    lowest = c;
                    continue;
                }
                if(c.getValue().getNumericalValue() < lowest.getValue().getNumericalValue()){
                    lowest = c;
                }
            }
        }

        return lowest;
    }

    public boolean hasSuit(Suit s, Suit trump){
        if(s == trump){
            return hasTrump(trump);
        }

        for(Card c:hand) {
            if(c.getSuit() == s) {
                if(c.isLeftBower(trump)){
                    continue;
                }
                return true;
            }
        }
        return false;
    }



}
