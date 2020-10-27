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
        Card highestCard;

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

        //Todo: determine the whcih card takes the trick
        // return the index of the player who played that card.
        return -1;
    }

}
