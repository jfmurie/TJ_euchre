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

    //try and use the AI method to get the winning card.
    private int getCurrentWinningCardIndex(Suit trump){
        int highestCardValue = 0;
        Card highestCard = null;
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
        System.out.println("Winning Card: " + this.playedCards.get(index).getValue() + " of " + this.playedCards.get(index).getSuit());
        System.out.println("Winning Card Index: " + index);
        return index;
    }

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

        System.out.println("Lead Player Index: " + leadPlayer);
        System.out.println("Winning Player: " + ((leadPlayer + winningCardIndex) % 4));
        return (leadPlayer + winningCardIndex) % 4;
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
                c = players[playerIndex].playCard(-1);
            }
            else{
                c = players[playerIndex].playCard(cardsPlayed, trump);
                //Todo: call UI/animation method that shows what the AI played (c)
            }
            cardsPlayed.add(c);
            playerIndex = (playerIndex + 1) % 4;
        }

        int winningCard = getCurrentWinningCardIndex(trump);
        return (lead + winningCard)%4;
    }
}
