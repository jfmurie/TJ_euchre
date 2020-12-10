package com.Round;

/* the original statement: android.util.Pair
 * changed because that import doesn't work with unit testing
 * https://stackoverflow.com/questions/35979397/android-immediately-created-pair-elements-are-null
 */

import com.Trick.Trick;
import com.cardvalues.CardValue;
import com.player.AI;
import com.player.Player;
import com.suits.Suit;
import com.team.Team;

public class Round {
    private Suit trump;
    Team teamCalledTrump;
    int tricksPlayed;
    Trick currentTrick;

    /**
     * Constructor for the Round class.
     */
    public Round() {
        this.tricksPlayed = 0;
    }

    public void setTeamCalledTrump(Team teamNumber){
        this.teamCalledTrump = teamNumber;
    }

    public void setCurrentTrick(Trick trick){
        this.currentTrick = trick;
    }

    public Trick getCurrentTrick(){
        return this.currentTrick;
    }

    public int getNumTricksPlayed(){
        return this.tricksPlayed;
    }
    public void incrementTricksPlayed(){
        this.tricksPlayed++;
    }

    public void setTrump(Suit trump){
        this.trump = trump;
    }

    public Suit getTrump() {
        return this.trump;
    }

    public void awardPoints(Team team1, Team team2){
        if(this.teamCalledTrump == team1){
            if(team1.getTricksTaken() == 5){
                team1.updateTeamScore(2);
                System.out.println("Team 1 got 2 points.");
            }
            else if(team1.getTricksTaken() >= 3){
                team1.updateTeamScore(1);
                System.out.println("Team 1 got a point.");
            }
            else{
                team2.updateTeamScore(2);
                System.out.println("Team 2 got 2 points.");
            }
        }
        else{
            if(team2.getTricksTaken() == 5){
                team2.updateTeamScore(2);
                System.out.println("Team 2 got 2 points.");
            }
            else if(team2.getTricksTaken() >= 3){
                team2.updateTeamScore(1);
                System.out.println("Team 2 got a point.");
            }
            else{
                team1.updateTeamScore(2);
                System.out.println("Team 1 got 2 points.");
            }
        }
    }

    public void updateAIOnHRTC(Player[] players){
        for (Player p: players) {
            if(p.isAI()){
                ((AI) p).setHighestRemainingTrumpCard(this.trump, CardValue.JACK);
            }
        }
    }
}
