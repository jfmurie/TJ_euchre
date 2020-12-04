package com;

import com.Round.Round;
import com.card.Card;
import com.cardvalues.CardValue;
import com.color.Color;
import com.deck.Deck;
import com.player.AI;
import com.player.Player;
import com.player.UserPlayer;
import com.team.Team;

public class PlayGame {

    public void play() {
        Player userPlayer = new UserPlayer();
        Player ai1 = new AI(1);
        Player ai2 = new AI(2);
        Player ai3 = new AI(3);

        Player[] players = {userPlayer, ai1, ai2, ai3};

        Team userTeam = new Team(userPlayer, ai2);
        Team pureAITeam = new Team(ai1, ai3);

        Deck deck = new Deck();
        deck.shuffleDeck();
        int dealerIndex = 0;
        //dealerIndex = (int)(Math.random() * 4); // set the dealer index to a random num between 0 and 4

        // Player left of the user player deals 1 card to each player face up until
        // first black jack gets to deal first
        for (int i = 1; i < 25; i++) {
            Card current = deck.getTopCard();
            if (  current.getValue() == CardValue.JACK &&
                    current.getColor(current.getSuit()) == Color.BLACK) {
                    dealerIndex = i % 4;
                    break;
            }
        }

        while(userTeam.getTeamScore() < 10 && pureAITeam.getTeamScore() < 10) {
            deck.shuffleDeck();
            Card topCard = deck.deal(players, dealerIndex);
            Round round = new Round();

            round.decideTrump(players, topCard);
            round.playRound(userTeam, pureAITeam, players, dealerIndex);

            dealerIndex = (dealerIndex + 1) % 4;  // increment the dealer index
        }

    }


}
