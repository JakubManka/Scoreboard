package org.example.exceptions;

import org.example.Game;

public class GameWithSameTeamAlreadyExistsException extends RuntimeException {
    public GameWithSameTeamAlreadyExistsException(Game game) {
        super(String.format("There is already a game in the scoreboard with these teams: %s vs %s", game.getHomeTeam(), game.getAwayTeam()));
    }
}
