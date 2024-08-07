package org.example.exceptions;

import org.example.Game;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(Game game) {
        super(String.format("Game not found %s vs %s", game.getHomeTeam(), game.getAwayTeam()));
    }
}
