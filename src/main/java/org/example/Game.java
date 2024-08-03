package org.example;

public class Game {
    TeamName homeTeam;
    TeamName awayTeam;
    int homeTeamScore;
    int awayTeamScore;

    public Game(TeamName homeTeam, TeamName awayTeam) {
        validateTeams(homeTeam, awayTeam);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = 0;
        this.awayTeamScore = 0;
    }

    public void updateScores(int homeTeamScore, int awayTeamScore) {
        validateScores(homeTeamScore, awayTeamScore);
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }

    private void validateTeams(TeamName homeTeam, TeamName awayTeam) {
        if (homeTeam == null || awayTeam == null) {
            throw new IllegalArgumentException("Team name cannot be null");
        }
    }

    private void validateScores(int homeTeamScore, int awayTeamScore){
        if (homeTeamScore < 0 || awayTeamScore < 0) {
            throw new IllegalArgumentException("Score have to be equal or greater than 0");
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
}
