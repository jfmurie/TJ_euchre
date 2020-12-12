package com.team;

import com.player.Player;

/**
 * Team Class implementation
 */
public class Team {

    /**
     * This array stores both players on the team.
     */
    private final Player[] team;

    /**
     * This integer keeps track of the team score.
     */
    private int teamScore;

    private int tricksTaken;


    /**
     * Constructor for the team object.  Sets teamScore to 0 and team to the players that are passed in.
     *
     * @param player1 The first player to be added to the list
     * @param player2 The second player to be added to the list
     */
    public Team(Player player1, Player player2) {
        this.team = new Player[] {player1, player2};
        this.teamScore = 0;
        this.tricksTaken = 0;
    }

    /**
     * This method returns the team's current score.
     *
     * @return The score of the team.
     */
    public int getTeamScore() {
        return this.teamScore;
    }

    public int getTricksTaken(){
        return this.tricksTaken;
    }

    public void resetTricksTaken(){
        this.tricksTaken = 0;
    }


    public void incrementTricksTaken(){
        this.tricksTaken++;
    }

    public boolean isPartOfTeam(Player player){
        return player == team[0] || player == team[1];
    }

    /**
     * This method takes in a player and returns their partner.  If the partner is not on
     * that team, the method returns null (should I throw an exception)?
     *
     * @param player The player who's partner we want to find.
     * @return The partner of the player, or null if the player is no on this team.
     */
    public Player getPartner(Player player) {
        if (this.team[0] == player) {
            return this.team[1];
        }

        else if(this.team[1] == player) {
            return this.team[0];
        }
        // @TODO testing baby
        // should I throw an exception ?
        else {
            return null;
        }
    }

    /**
     * This method updates the team score based on the passed in points.
     *
     * @param awarded_points The number of points to be added to teamScore.  Should be > 0.
     * @return The score of the team or awarded_points if awarded_points is nonsensical.
     */
    public int updateTeamScore(int awarded_points) {
        if (awarded_points >= 0) {
            this.teamScore += awarded_points;
            return this.teamScore;
        }
        else return awarded_points;
    }
}
