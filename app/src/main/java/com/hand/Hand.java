package com.hand;

//is needed??? vvv

import com.card.Card;

import java.util.ArrayList;

//imports add here
//Not sure what im importing yet.
//each hand has max 5 cards

public class Hand {
    int maxSize = 5;
    ArrayList<Card> hand;
    //cards get added to this when they are dealt
    //hand.add(new card);
    //ArrayList but only can have 5 cards max????

    public Hand() {
        hand = new ArrayList<>();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    //setter for hand
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
            if(c.isTrump){
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
        //need someway to grab the card value from the array
//        Object holdArray[];
//        holdArray = inHand.toArray();
//        Object holding = inHand[0];
        int temp = 0;
        for(int i = 0; i < hand.size(); i++){
//            if(holding < inHand[i+1]){
//                holding = inHand[i +1];
//            }
            if(hand.get(temp).getValue().getNumericalValue() < hand.get(i+1).getValue().getNumericalValue()) {
                    temp = i+1;
            }
        }
        return /*return card object of highest card*/ hand.get(temp);
    }

    public int getHighestCardIndex() {
        //loop through all cards and find the highest value card
        int index = 0;
        for(int i = 0; i < hand.size(); i++){
            //boolean operators cant be used??
            //.compare???
            if(hand.get(index).getValue().getNumericalValue() < hand.get(i+1).getValue().getNumericalValue()) {
                index = i+1;
            }
        }
        return /*return the index of the card with the highest value*/ index;
    }

    public ArrayList<Card> getTrumpCards() {
        ArrayList<Card> trumpInHand = new ArrayList<>();

        for (int k = 0; k < hand.size(); k++) {
            if (hand.get(k).isTrump) {
                trumpInHand.add(hand.get(k));
            }
        }
        return trumpInHand;
    }
}
