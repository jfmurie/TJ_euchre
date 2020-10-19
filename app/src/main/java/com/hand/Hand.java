package com.hand;

import java.util.ArrayList;
import com.card.Card;
import com.suits.*;

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

    //adds card as delt
    public void addCard(Card dealtCard) {
        hand.add(dealtCard);
    }

    //loop through array/arraylist of cards in the hand.
    public boolean hasTrump() {
        for(Card c:hand) {
            if(c.isTrump()){
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

    public Card getHighestTrump() {
        int highIndex = 0;
        //if has no cards
        if (hand.isEmpty()){
            return null;
        }
        ArrayList<Card> trump;
        trump = getTrumpCards();
        //if has no trump cards
        if (trump.isEmpty()){
            return null;
        }
        //if only has 1 trump card
        if (trump.size() == 1){
            return trump.get(0);
        }
        //2 or more trump cards looks for highest
        for (int i = 0; i < trump.size(); i++) {
            if (trump.get(highIndex).getValue().getNumericalValue() < trump.get(i+1).getValue().getNumericalValue()){
                highIndex = i+1;
            }
        }
        return trump.get(highIndex);
    }

    public Card getLowestTrump() {
        int lowIndex = 0;
        //if has no cards
        if (hand.isEmpty()){
            return null;
        }
        ArrayList<Card> trump;
        trump = getTrumpCards();
        //if has no trump cards
        if (trump.isEmpty()){
            return null;
        }
        //if only has 1 trump card
        if (trump.size() == 1){
            return trump.get(0);
        }
        //2 or more trump cards looks for highest
        for (int i = 0; i < trump.size(); i++) {
            if (trump.get(lowIndex).getValue().getNumericalValue() > trump.get(i+1).getValue().getNumericalValue()){
                lowIndex = i+1;
            }
        }
        return trump.get(lowIndex);
    }

    public Card getHighestNonTrump() {
        int highNTIndex = 0;
        //if has no cards
        if (hand.isEmpty()){
            return null;
        }
        ArrayList<Card> nontrump;
        nontrump = getNonTrumpCards();
        //if has no nontrump cards
        if (nontrump.isEmpty()){
            return null;
        }
        //if only has 1 nontrump card
        if (nontrump.size() == 1){
            return nontrump.get(0);
        }
        //2 or more nontrump cards looks for highest
        for (int i = 0; i < nontrump.size(); i++) {
            if (nontrump.get(highNTIndex).getValue().getNumericalValue() < nontrump.get(i+1).getValue().getNumericalValue()){
                highNTIndex = i+1;
            }
        }
        return nontrump.get(highNTIndex);
    }

    public Card getLowestNonTrump() {
        int lowNTIndex = 0;
        //if has no cards
        if (hand.isEmpty()){
            return null;
        }
        ArrayList<Card> nontrump;
        nontrump = getNonTrumpCards();
        //if has no nontrump cards
        if (nontrump.isEmpty()){
            return null;
        }
        //if only has 1 nontrump card
        if (nontrump.size() == 1){
            return nontrump.get(0);
        }
        //2 or more nontrump cards looks for highest
        for (int i = 0; i < nontrump.size(); i++) {
            if (nontrump.get(lowNTIndex).getValue().getNumericalValue() > nontrump.get(i+1).getValue().getNumericalValue()){
                lowNTIndex = i+1;
            }
        }
        return nontrump.get(lowNTIndex);

    }

    public ArrayList<Card> getTrumpCards() {
        ArrayList<Card> trumpInHand = new ArrayList<>();
        for (int k = 0; k < hand.size(); k++) {
            if (hand.get(k).isTrump()) {
                trumpInHand.add(hand.get(k));
            }
        }
        return trumpInHand;
    }

    public ArrayList<Card> getNonTrumpCards() {
        ArrayList<Card> nonTrumpInHand = new ArrayList<>();
        for (int i = 0; i < hand.size(); i++) {
            if(!hand.get(i).isTrump()){
                nonTrumpInHand.add(hand.get(i));
            }
        }
        return nonTrumpInHand;
    }

    public int handSize() {
        return hand.size();
    }

    //play card method
    //take in the index of the card removes that card returns the card identifier
    public Card removeCard(int index){
        return hand.remove(index);
    }
}
