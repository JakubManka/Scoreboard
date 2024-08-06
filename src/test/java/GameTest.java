import org.example.Game;
import org.example.TeamName;
import org.example.exceptions.InvalidScoreException;
import org.example.exceptions.NullTeamNameException;
import org.example.exceptions.SameTeamsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.example.TeamName.*;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private final Game validGame = new Game(ARGENTINA, URUGUAY);

    @Test
    public void givenValidTeams_whenStartNewGame_thenTeamScoresShouldBeZero() {
        TeamName homeTeam = REAL_MADRID;
        TeamName awayTeam = BARCELONA;
        Game validGame = new Game(homeTeam, awayTeam);

        assertAll(() -> {
            assertEquals(0, validGame.getAwayTeamScore());
            assertEquals(0, validGame.getHomeTeamScore());
            assertEquals(homeTeam, validGame.getHomeTeam());
            assertEquals(awayTeam, validGame.getAwayTeam());
        });
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPairOfTeams")
    public void givenInvalidTeams_whenStartNewGame_thenThrowNullTeamNameException(TeamName homeTeam, TeamName awayTeam) {
        assertThrows(NullTeamNameException.class, () -> new Game(homeTeam, awayTeam));
    }

    @Test
    public void givenValidScores_whenUpdateScores_thenScoresShouldBeUpdated() {
        int updatedHomeTeamScore = 3;
        int updatedAwayTeamScore = 2;

        validGame.updateScores(updatedHomeTeamScore, updatedAwayTeamScore);

        assertAll(() -> {
            assertEquals(updatedHomeTeamScore, validGame.getHomeTeamScore());
            assertEquals(updatedAwayTeamScore, validGame.getAwayTeamScore());
        });
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPairOfScores")
    public void givenInValidScores_whenUpdateScores_thenThrowInvalidScoreException(int homeTeamScore, int awayTeamScore) {
        assertThrows(InvalidScoreException.class, () -> validGame.updateScores(homeTeamScore, awayTeamScore));
    }

    @Test
    public void givenSameTeams_whenStartNewGame_thenThrowSameTeamsException() {
        assertThrows(SameTeamsException.class, () -> new Game(ARGENTINA, ARGENTINA));
    }

    private static Stream<Arguments> provideInvalidPairOfTeams() {
        TeamName validTeamName = MEXICO;
        TeamName inValidTeamName = null;
        return Stream.of(
                Arguments.of(validTeamName, inValidTeamName),
                Arguments.of(inValidTeamName, validTeamName),
                Arguments.of(inValidTeamName, inValidTeamName)
        );
    }

    private static Stream<Arguments> provideInvalidPairOfScores() {
        int validScore = 3;
        int inValidScore = -1;
        return Stream.of(
                Arguments.of(validScore, inValidScore),
                Arguments.of(inValidScore, validScore),
                Arguments.of(inValidScore, inValidScore)
        );
    }
}
