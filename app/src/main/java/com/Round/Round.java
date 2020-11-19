package com.Round;

/* the original statement: android.util.Pair
 * changed because that import doesn't work with unit testing
 * https://stackoverflow.com/questions/35979397/android-immediately-created-pair-elements-are-null
 */
import android.support.v4.util.Pair;

import com.Trick.Trick;
import com.card.Card;
import com.player.Player;
import com.suits.Suit;
import com.team.Team;

public class Round {

    private Suit trump;

    private int dealerIndex;

    private int sitOut;

    int teamCalledTrump;

    /**
     * Constructor for the Round class.
     */
    public Round() {
        sitOut = -1;
        teamCalledTrump = -1;
    }

    public Suit getTrump() {
        return trump;
    }

    public void setSitOut(int sitOut) {
        this.sitOut = sitOut;
    }

    /**
     * Loops through the players until one calls trump.  Implemented with screw the dealer
     * logic only.
     *
     * @param players List of players
     * @param turnedUp Suit of the card that was turned up
     */
    public void decideTrump(Player[] players, Card turnedUp) {
        Suit suit;
        Boolean goAlone;
        boolean turnedDown = false;
        int playerIndex = (dealerIndex + 1) % 4;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                Pair<Suit, Boolean> suitGoAlonePair = players[playerIndex].callTrump(turnedUp, turnedDown, false);
                suit = suitGoAlonePair.first;
                goAlone = suitGoAlonePair.second;

                if (suit != null) {
                    this.trump = suit;
                    if(j == 0 || j == 2){
                        teamCalledTrump = 1;
                    }else{
                        teamCalledTrump = 2;
                    }

                    if(!turnedDown) {
                        players[playerIndex].pickItUp(turnedUp);
                    }

                    if (goAlone != null && goAlone) {
                        this.sitOut = playerIndex + 2 % 4;
                    }
                    return;
                }

                playerIndex = (playerIndex + 1) % 4;
            }
            turnedDown = true;
        }
    }


    public void playRound(Team team1, Team team2, Player[] players, int dealerIndex){
        int team1TricksTaken = 0;
        int team2TricksTaken = 0;
        for (int i = 0; i < 5; i++){
            Trick currentTrick = new Trick();
            int lead = (dealerIndex+1)%4;
            int winner = currentTrick.playTrick(players, lead, this.trump);
            if(winner == 0 || winner == 2){
                team1TricksTaken++;
            }else{
                team2TricksTaken++;
            }
        }
        switch (teamCalledTrump){
            case 1:
                if(team1TricksTaken >= 3){
                    if(team1TricksTaken == 5){
                        team1.updateTeamScore(2);
                    }else {
                        team1.updateTeamScore(1);
                    }
                }else{
                    team2.updateTeamScore(2);
                }
                break;
            case 2:
                if(team2TricksTaken >= 3){
                    if(team2TricksTaken == 5){
                        team2.updateTeamScore(2);
                    }else{
                        team2.updateTeamScore(1);
                    }
                }else{
                    team1.updateTeamScore(2);
                }
                break;
            default: break;
        }
    }
}
