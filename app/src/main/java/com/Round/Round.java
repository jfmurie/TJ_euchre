package com.Round;

/* the original statement: android.util.Pair
 * changed because that import doesn't work with unit testing
 * https://stackoverflow.com/questions/35979397/android-immediately-created-pair-elements-are-null
 */

import com.Trick.Trick;
import com.card.Card;
import com.cardvalues.CardValue;
import com.player.AI;
import com.player.Player;
import com.suits.Suit;
import com.team.Team;

public class Round {

    private Suit trump;
    private int sitOut;

    Team teamCalledTrump;

    int tricksPlayed;
    Trick currentTrick;

    /**
     * Constructor for the Round class.
     */
    public Round() {
        this.sitOut = -1;
        this.tricksPlayed = 0;
    }

    public void setTeamCalledTrump(Team teamNumber){
        this.teamCalledTrump = teamNumber;
    }

    public Team getTeamCalledTrump(){
        return this.teamCalledTrump;
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
//        Suit suit;
//        Boolean goAlone;
//        boolean turnedDown = false;
//        boolean isDealer;
//        int playerIndex = (dealerIndex + 1) % 4;
//
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 4; j++) {
//                isDealer = j == dealerIndex;
//                Pair<Suit, Boolean> suitGoAlonePair = players[playerIndex].callTrump(turnedUp, turnedDown, isDealer);
//                suit = suitGoAlonePair.first;
//                goAlone = suitGoAlonePair.second;
//
//                if (suit != Suit.PASS) {
//                    this.trump = suit;
//                    if(j == 0 || j == 2){
//                        teamCalledTrump = 1;
//                    }else{
//                        teamCalledTrump = 2;
//                    }
//
//                    if(!turnedDown) {
//                        players[playerIndex].pickItUp(turnedUp);
//                    }
//
//                    if (goAlone != null && goAlone) {
//                        this.sitOut = playerIndex + 2 % 4;
//                    }
//                    updateAIOnHRTC(suit, players);
//                    return;
//                }
//
//                playerIndex = (playerIndex + 1) % 4;
//            }
//            turnedDown = true;
//        }
    }


    public void playRound(Team team1, Team team2, Player[] players, int dealerIndex){
//        int team1TricksTaken = 0;
//        int team2TricksTaken = 0;
//        for (int i = 0; i < 5; i++){
//            Trick currentTrick = new Trick();
//            int lead = (dealerIndex+1)%4;
//            int winner = currentTrick.playTrick(players, lead, this.trump);
//            if(winner == 0 || winner == 2){
//                team1TricksTaken++;
//            }else{
//                team2TricksTaken++;
//            }
//        }
//        switch (teamCalledTrump){
//            case 1:
//                if(team1TricksTaken >= 3){
//                    if(team1TricksTaken == 5){
//                        team1.updateTeamScore(2);
//                    }else {
//                        team1.updateTeamScore(1);
//                    }
//                }else{
//                    team2.updateTeamScore(2);
//                }
//                break;
//            case 2:
//                if(team2TricksTaken >= 3){
//                    if(team2TricksTaken == 5){
//                        team2.updateTeamScore(2);
//                    }else{
//                        team2.updateTeamScore(1);
//                    }
//                }else{
//                    team1.updateTeamScore(2);
//                }
//                break;
//            default: break;
//        }
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
