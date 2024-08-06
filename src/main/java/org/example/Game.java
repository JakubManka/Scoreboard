package org.example;

import org.example.exceptions.InvalidScoreException;
import org.example.exceptions.NullTeamNameException;
import org.example.exceptions.SameTeamsException;

public class Game {
    private final TeamName homeTeam;
    private final TeamName awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;
    private final long timestamp;


    public Game(TeamName homeTeam, TeamName awayTeam) {
        validateTeams(homeTeam, awayTeam);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = 0;
        this.awayTeamScore = 0;
        this.timestamp = System.currentTimeMillis();
    }

    public void updateScores(int homeTeamScore, int awayTeamScore) {
        validateScores(homeTeamScore, awayTeamScore);
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }

    private void validateTeams(TeamName homeTeam, TeamName awayTeam) {
        if (homeTeam == null || awayTeam == null) {
            throw new NullTeamNameException();
        }
        if (homeTeam.equals(awayTeam)) {
            throw new SameTeamsException(homeTeam, awayTeam);
        }
    }

    private void validateScores(int homeTeamScore, int awayTeamScore) {
        if (homeTeamScore < 0 || awayTeamScore < 0) {
            throw new InvalidScoreException(homeTeamScore, awayTeamScore);
        }
    }

    public TeamName getHomeTeam() {
        return homeTeam;
    }

    public TeamName getAwayTeam() {
        return awayTeam;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Game{" +
                "homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", homeTeamScore=" + homeTeamScore +
                ", awayTeamScore=" + awayTeamScore +
                ", timestamp=" + timestamp +
                '}';
    }
}
