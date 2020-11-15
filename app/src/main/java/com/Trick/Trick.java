package com.Trick;

import com.card.Card;
import com.player.Player;
import com.suits.Suit;

import java.util.ArrayList;

public class Trick {
    ArrayList<Card> playedCards;


    public Trick(){
        playedCards = new ArrayList<>();
    }

    //try and use the AI method to get the winning card.

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

    /**
     *
     *
     * @param players the players in the game
     * @param lead index of player leading this trick
     * @return the index of the player who took the trick
     */
    public int playTrick(Player[] players, int lead, Suit trump){
        int playerIndex = lead;
        ArrayList<Card> cardsPlayed = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            Card c;
            if(!players[playerIndex].isAI()){
                c = players[playerIndex].playCard();
            }
            else{
                c = players[playerIndex].playCard(cardsPlayed, trump);

            }
            cardsPlayed.add(c);
            playerIndex = (playerIndex + 1) % 4;
        }

        int winningCard = getCurrentWinningCardIndex(cardsPlayed, trump);
        return (lead + winningCard)%4;
    }
}
