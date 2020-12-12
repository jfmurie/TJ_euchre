package com.round;

import com.Round.Round;
import com.player.AI;
import com.player.Player;
import com.player.UserPlayer;
import com.team.Team;

import org.junit.Test;
import org.junit.Assert;

public class testRound {

    @Test
    public void testRoundAwardPoints(){
        Round testRound = new Round();
        Team userTeam;
        Team pureAITeam;
        Player userPlayer = new UserPlayer();
        Player ai1 = new AI(1);
        Player ai2 = new AI(2);
        Player ai3 = new AI(3);
        userTeam = new Team(userPlayer, ai2);
        pureAITeam = new Team(ai1, ai3);
        testRound.awardPoints(userTeam, pureAITeam);
//        System.out.println("Team1/UserTeam should win and score points");
        Assert.assertEquals(userTeam.getTeamScore(), 2);
    }

    @Test
    public void testRoundUpdateAIOnHRTC(){
        Round testRound = new Round();
        Player[] players;
        Player userPlayer = new UserPlayer();
        Player ai1 = new AI(1);
        Player ai2 = new AI(2);
        Player ai3 = new AI(3);
        players = new Player[]{userPlayer, ai1, ai2, ai3};
        testRound.updateAIOnHRTC(players);
        System.out.println("Ai highest remaining trump is: ");
    }
}
