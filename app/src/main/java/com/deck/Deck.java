package com.deck;

import com.card.Card;
import com.cardvalues.CardValue;
import com.player.Player;
import com.suits.Suit;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This is the class for the deck object.
 * It is implemented with an array list of cards.
 *
 */
public class Deck {

    public final ArrayList<Card> deck;
    private int topCardIndex;


    /**
     * Constructor for the deck object.  This constructor
     * initializes the card ArrayList to have one card of each suit and value.
     */
    public Deck() {
        this.deck = new ArrayList<>();
        Suit[] suitArray = {Suit.SPADES, Suit.CLUBS, Suit.HEARTS, Suit.DIAMONDS};
        CardValue[] valuesArray = {CardValue.NINE,
                CardValue.TEN,
                CardValue. JACK,
                CardValue.QUEEN,
                CardValue.KING,
                CardValue.ACE};

        for (Suit suit : suitArray) {
            for (CardValue value : valuesArray) {
                deck.add(new Card(suit, value));
            }
        }

        this.topCardIndex = deck.size() - 1;

    }

    /**
     * Shuffles the deck using Collections.shuffle(list) method.  Also resets the top card
     * index to 0.  This method should always be called before each deal.
     *
     * https://docs.oracle.com/javase/6/docs/api/java/util/Collections.html#shuffle(java.util.List)
     *
     */
    public void shuffleDeck() {
        Collections.shuffle(this.deck);
        this.topCardIndex = deck.size() - 1;
    }

    /**
     * Gets the top card of the deck.
     *
     * @return  Top card of the deck
     */
    public Card getTopCard(){
        return this.deck.get(this.topCardIndex--);
    }


    /**
     * This method deals 5 cards to each player according to game rules
     *
     * @param players the players in the game
     * @param dealerIndex index of player who is dealing
     */
    public Card deal(Player[] players, int dealerIndex){
        int playerIndex = (dealerIndex + 1) % 4;

        for(int i = 0; i < 4; i++){
            if(dealerIndex % 2 == 0 && playerIndex % 2 == 0){               //Dealer is even & player getting cards is even
                players[playerIndex].receiveCardFromDealer(getTopCard());
                players[playerIndex].receiveCardFromDealer(getTopCard());
            }
            else if(dealerIndex % 2 == 0){                                  //Dealer is even & player getting cards is odd
                players[playerIndex].receiveCardFromDealer(getTopCard());
                players[playerIndex].receiveCardFromDealer(getTopCard());
                players[playerIndex].receiveCardFromDealer(getTopCard());
            }
            else if(playerIndex % 2 == 0){                                  //Dealer is odd & player getting cards is even
                players[playerIndex].receiveCardFromDealer(getTopCard());
                players[playerIndex].receiveCardFromDealer(getTopCard());
                players[playerIndex].receiveCardFromDealer(getTopCard());
            }
            else {                                                          //Dealer is odd & player getting cards is odd
                players[playerIndex].receiveCardFromDealer(getTopCard());
                players[playerIndex].receiveCardFromDealer(getTopCard());
            }
            playerIndex = (playerIndex + 1) % 4;
        }

        //do it again but flip it
        for(int i = 0; i < 4; i++){
            if(dealerIndex % 2 == 0 && playerIndex % 2 == 0){               //Dealer is even & player getting cards is even
                players[playerIndex].receiveCardFromDealer(getTopCard());
                players[playerIndex].receiveCardFromDealer(getTopCard());
                players[playerIndex].receiveCardFromDealer(getTopCard());
            }
            else if(dealerIndex % 2 == 0){                                  //Dealer is even & player getting cards is odd
                players[playerIndex].receiveCardFromDealer(getTopCard());
                players[playerIndex].receiveCardFromDealer(getTopCard());
            }
            else if(playerIndex % 2 == 0){                                  //Dealer is odd & player getting cards is even
                players[playerIndex].receiveCardFromDealer(getTopCard());
                players[playerIndex].receiveCardFromDealer(getTopCard());
            }
            else {                                                          //Dealer is odd & player getting cards is odd
                players[playerIndex].receiveCardFromDealer(getTopCard());
                players[playerIndex].receiveCardFromDealer(getTopCard());
                players[playerIndex].receiveCardFromDealer(getTopCard());
            }
            playerIndex = (playerIndex + 1) % 4;
        }



        return this.getTopCard();
    }

}
