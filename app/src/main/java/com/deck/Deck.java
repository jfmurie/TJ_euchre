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

    }

    /**
     * TODO: decide if this should return void or the deck
     * Shuffles the deck using Collections.shuffle(list) method.
     *
     * https://docs.oracle.com/javase/6/docs/api/java/util/Collections.html#shuffle(java.util.List)
     *
     */
    public void shuffleDeck() {
        Collections.shuffle(this.deck);
    }

    public Card getTopCard(){
        return this.deck.get(this.deck.size() - 1);
    }

    /**
     * This method deals 5 cards to each player according to game rules
     *
     * @param players the players in the game
     * @param dealerIndex index of player who is dealing
     */
    public void deal(Player[] players, int dealerIndex){
        int playerIndex = (dealerIndex + 1) % 4;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {

                //i = time dealt (0 or 1)
                //player index (0 - 3)
                //if player dealt 1st and not on dealer's team, give three, etc.
                if((i + j)% 2 == 0){
                    players[playerIndex].receiveCardFromDealer(getTopCard());
                    players[playerIndex].receiveCardFromDealer(getTopCard());
                    players[playerIndex].receiveCardFromDealer(getTopCard());
                }
                else{
                    players[playerIndex].receiveCardFromDealer(getTopCard());
                    players[playerIndex].receiveCardFromDealer(getTopCard());
                }
            }
            playerIndex = (playerIndex + 1) % 4;
        }
    }

}
