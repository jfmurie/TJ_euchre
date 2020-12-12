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

    public void playerPlaysCard(Card play){
        playedCards.add(play);
    }

    public ArrayList<Card> getPlayedCards(){
        return playedCards;
    }

    /**
     *
     * @param trump
     * @return index of winning card
     * Called by getWinningPlayerIndex and looks at the cards played during that trick
     * it picks the card that has the highest value for that round
     * This means that trump cards are valued more and non suit cards are less.
     * Then it returns the index of the card that wins the trick
     */
    public int getCurrentWinningCardIndex(Suit trump){
        int highestCardValue = 0;
        int index = 0;
        for(int i = 0; i < playedCards.size(); i++){
            if(playedCards.get(i).isRightBower(trump)){
                return i;
            }
            if(playedCards.get(i).isLeftBower(trump)){
                index = i;
                continue;
            }
            if(playedCards.get(index).isLeftBower(trump)){
                continue;
            }

            int temp = playedCards.get(i).getValue().getNumericalValue();
            if(playedCards.get(i).getSuit() == playedCards.get(0).getSuit()){
                temp *= 2;
            }
            if(playedCards.get(i).isTrump(trump)){
                temp *= 4;
            }

            if(highestCardValue < temp){
                highestCardValue = temp;
                index = i;
            }
        }
//        System.out.println("Winning Card: " + this.playedCards.get(index).getValue() + " of " + this.playedCards.get(index).getSuit());
//        System.out.println("Winning Card Index: " + index);
        return index;
    }

    /**
     *
     * @param players
     * @param trump
     * @return Winning player index
     * Grabs the array of players and the current trump suit
     * Then it calls getCurrentWinningCardIndex() and once that returns which card wins the trick
     * it determines which player played the winning card and returns their index.
     */

    public int getWinningPlayerIndex(Player[] players, Suit trump){
        int leadPlayer = -1;
        for(Player p: players){
            if(p.isLead()){
                leadPlayer = p.getPlayerNum();
                break;
            }
        }
        if(leadPlayer == -1){
            return -1; //error
        }

        int winningCardIndex = getCurrentWinningCardIndex(trump);

//        System.out.println("Lead Player Index: " + leadPlayer);
//        System.out.println("Winning Player: " + ((leadPlayer + winningCardIndex) % 4));
        return (leadPlayer + winningCardIndex) % 4;
    }
}
