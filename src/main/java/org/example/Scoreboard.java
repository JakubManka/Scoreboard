package org.example;

import org.example.exceptions.GameNotFoundException;
import org.example.exceptions.GameWithSameTeamAlreadyExistsException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Scoreboard {
    private final List<Game> scoreBoard = new ArrayList<>();

    public Game startNewGame(TeamName homeTeam, TeamName awayTeam) {
        Game newGame = new Game(homeTeam, awayTeam);
        validateIfGameExists(newGame);
        scoreBoard.add(newGame);
        return newGame;
    }

    public void updateScore(int homeTeamScore, int awayTeamScore, Game game) {
        validateGame(game);
        game.updateScores(homeTeamScore, awayTeamScore);
    }

    public void finishGame(Game game) {
        validateGame(game);
        scoreBoard.remove(game);
    }

    public List<Game> getSummary() {
        return scoreBoard.stream()
                .sorted(Comparator.comparingInt(this::getTotalScore)
                        .thenComparingLong(Game::getTimestamp)
                        .reversed())
                .collect(Collectors.toList());
    }

    private void validateIfGameExists(Game newGame) {
        boolean isAnyTeamInGame = scoreBoard.stream().anyMatch(gameInProgress -> isAnyTeamAlreadyInGame(gameInProgress, newGame));
        if (isAnyTeamInGame) {
            throw new GameWithSameTeamAlreadyExistsException(newGame);
        }
    }

    private boolean isAnyTeamAlreadyInGame(Game gameInProgress, Game newGame){
        return gameInProgress.getHomeTeam().equals(newGame.getHomeTeam()) || gameInProgress.getHomeTeam().equals(newGame.getAwayTeam())
                ||  gameInProgress.getAwayTeam().equals(newGame.getHomeTeam()) || gameInProgress.getAwayTeam().equals(newGame.getAwayTeam());
    }

    private void validateGame(Game game) {
        if (!scoreBoard.contains(game)) {
            throw new GameNotFoundException(game);
        }
    }

    private int getTotalScore(Game game) {
        return game.getHomeTeamScore() + game.getAwayTeamScore();
    }
}