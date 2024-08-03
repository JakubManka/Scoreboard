import org.example.Game;
import org.example.TeamName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.example.TeamName.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest {

    private final Game validGame = new Game(ARGENTINA, URUGUAY);

    @Test
    public void givenValidTeams_whenStartNewGame_thenTeamScoresShouldBeZero() {
        TeamName homeTeam = REAL_MADRID;
        TeamName awayTeam = BARCELONA;
        Game validGame = new Game(homeTeam, awayTeam);

        assertEquals(validGame.getAwayTeamScore(), 0);
        assertEquals(validGame.getHomeTeamScore(), 0);
        assertEquals(validGame.getHomeTeam(), homeTeam);
        assertEquals(validGame.getAwayTeam(), awayTeam);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPairOfTeams")
    public void givenInvalidTeams_whenStartNewGame_thenThrowIllegalArgumentException(TeamName homeTeam, TeamName awayTeam) {
        assertThrows(IllegalArgumentException.class, () -> new Game(homeTeam, awayTeam));
    }

    @Test
    public void givenValidScores_whenUpdateScores_thenScoresShouldBeUpdated() {
        int updatedHomeTeamScore = 3;
        int updatedAwayTeamScore = 2;

        validGame.updateScores(updatedHomeTeamScore, updatedAwayTeamScore);

        assertEquals(validGame.getHomeTeamScore(), updatedHomeTeamScore);
        assertEquals(validGame.getAwayTeamScore(), updatedAwayTeamScore);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPairOfScores")
    public void givenInValidScores_whenUpdateScores_thenThrowIllegalArgumentException(int homeTeamScore, int awayTeamScore) {
        assertThrows(IllegalArgumentException.class, () -> validGame.updateScores(homeTeamScore, awayTeamScore));
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
