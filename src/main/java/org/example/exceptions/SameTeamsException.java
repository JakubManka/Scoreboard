package org.example.exceptions;

import org.example.TeamName;

public class SameTeamsException extends RuntimeException{
    public SameTeamsException(TeamName homeTeam, TeamName awayTeam) {
        super(String.format("Teams must be different. Home team - %s, away team - %s", homeTeam, awayTeam));
    }
}
