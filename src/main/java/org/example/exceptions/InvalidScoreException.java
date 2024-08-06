package org.example.exceptions;

public class InvalidScoreException extends RuntimeException {
    public InvalidScoreException(int homeScore, int awayScore) {
        super(String.format("Invalid score: home score: %d, away score: %d", homeScore, awayScore));
    }
}
