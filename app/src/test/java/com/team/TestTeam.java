package com.team;

import com.player.AI;
import com.player.Player;
import com.player.UserPlayer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * Test class for the team class
 *
 * Author:  Alan Bouwman akbouwma@mtu.edu
 */
public class TestTeam {

    Player p1;
    Player p2;
    Player p3;
    Player p4;

    int score = 0;
    int toAdd = 1;
    int toAddAbsurd = -2;

    Team team1;
    Team team2;


    @Before
    public void setUp() {
        p1 = new UserPlayer();
        p2 = new AI();
        p3 = new AI();
        p4 = new AI();

        team1 = new Team(p1, p3);
        team2 = new Team(p2, p4);
    }

    @Test
    public void testGetPartner_p1sPartnerOnTeam1_returnsP2() {
        assertEquals(p3, team1.getPartner(p1));
    }


    @Test
    public void testGetPartner_p1sPartnerOnTeam2_returnsNull() {
        assertNull(team2.getPartner(p1));
    }


    @Test
    public void testUpdateTeamScore_addToAddToTeam1Score_returnsScorePlusToAdd() {
        assertEquals(score + toAdd, team1.updateTeamScore(toAdd));
    }


    @Test
    public void testUpdateTeamScore_addToAddAbsurdToTeam1Score_returnsToAddAbsurd() {
        assertEquals(toAddAbsurd, team1.updateTeamScore(toAddAbsurd));
    }


    @Test
    public void testUpdateTeamScore_addZeroTeam1Score_returnsScore() {
        assertEquals(score, team1.updateTeamScore(0));
    }


    @Test
    public void testUpdateTeamScore_addMultipleTimesTeam1Score_returns3() {
        team1.updateTeamScore(2);
        assertEquals(3, team1.updateTeamScore(1));
    }

}
