import org.example.Game;
import org.example.exceptions.GameNotFoundException;
import org.example.Scoreboard;
import org.example.TeamName;
import org.example.exceptions.GameWithSameTeamAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreboardTest {
    @Test
    public void givenValidTeams_whenStartNewGame_thenTeamsShouldBeAddedToScoreboard() {
        final Scoreboard validScoreboard = new Scoreboard();
        final TeamName homeTeam = TeamName.AUSTRALIA;
        final TeamName awayTeam = TeamName.ITALY;

        validScoreboard.startNewGame(homeTeam, awayTeam);

        assertEquals(1, validScoreboard.getSummary().size());
    }

    @Test
    public void givenValidScoreboardAndValidGames_whenUpdateScores_thenScoreboardShouldBeReturnedSorted() throws InterruptedException {
        final Scoreboard validScoreboard = new Scoreboard();
        List<Game> validGames = addValidGamesToScoreBoard(validScoreboard);
        final Game AustraliaItalyGame = validGames.get(0);
        final Game ArgentinaMexicoGame = validGames.get(1);

        assertAll(() -> {
            assertEquals(2, validScoreboard.getSummary().size());
            assertTrue(validScoreboard.getSummary().containsAll(validGames));
            assertEquals(ArgentinaMexicoGame, validScoreboard.getSummary().get(0));
        });

        validScoreboard.updateScore(3, 2, AustraliaItalyGame);

        assertEquals(AustraliaItalyGame, validScoreboard.getSummary().get(0));

        validScoreboard.updateScore(3, 3, ArgentinaMexicoGame);

        assertEquals(ArgentinaMexicoGame, validScoreboard.getSummary().get(0));

        validScoreboard.updateScore(3, 3, AustraliaItalyGame);
        assertEquals(ArgentinaMexicoGame, validScoreboard.getSummary().get(0));
    }

    @Test
    public void givenValidScoreboardAndInValidGames_whenUpdateScores_thenThrowGameNotFoundException() throws InterruptedException {
        final Scoreboard validScoreboard = new Scoreboard();
        addValidGamesToScoreBoard(validScoreboard);

        assertThrows(GameNotFoundException.class, () -> validScoreboard.updateScore(3, 2, new Game(TeamName.AUSTRALIA, TeamName.ITALY)));

    }

    @Test
    public void givenValidScoreboardAndValidGames_whenFinishGame_thenGameShouldBeRemovedFromScoreboard() throws InterruptedException {
        final Scoreboard validScoreboard = new Scoreboard();
        List<Game> validGames = addValidGamesToScoreBoard(validScoreboard);

        validScoreboard.finishGame(validGames.get(0));

        assertEquals(1, validScoreboard.getSummary().size());
    }

    @Test
    public void givenValidScoreboardAndInValidGames_whenFinishGame_thenThrowGameNotFoundException() throws InterruptedException {
        final Scoreboard validScoreboard = new Scoreboard();
        addValidGamesToScoreBoard(validScoreboard);

        assertThrows(GameNotFoundException.class, () -> validScoreboard.finishGame(new Game(TeamName.AUSTRALIA, TeamName.ITALY)));

    }

    @Test
    public void givenValidScoreboardAndInValidGameWithSameTeams_whenFinishGame_thenThrowGameNotFoundException() throws InterruptedException {
        final Scoreboard validScoreboard = new Scoreboard();
        addValidGamesToScoreBoard(validScoreboard);

        assertThrows(GameNotFoundException.class, () -> validScoreboard.finishGame(new Game(TeamName.AUSTRALIA, TeamName.ITALY)));
    }

    @ParameterizedTest
    @MethodSource("provideGamesWithTeamsInAnotherGame")
    public void givenValidScoreboard_whenStarNewGameWithTeamAlreadyInGame_thenThrowGameWithSameTeamAlreadyExistsException(TeamName homeTeam, TeamName awayTeam)
            throws InterruptedException {
        final Scoreboard validScoreboard = new Scoreboard();
        addValidGamesToScoreBoard(validScoreboard);

        assertThrows(GameWithSameTeamAlreadyExistsException.class, () -> validScoreboard.startNewGame(homeTeam, awayTeam));
    }

    @Test
    public void givenEmptyScoreboard_whenGetSummary_thenSummaryShouldBeEmpty() {
        final Scoreboard validScoreboard = new Scoreboard();

        assertEquals(0, validScoreboard.getSummary().size());
    }

    private List<Game> addValidGamesToScoreBoard(Scoreboard scoreboard) throws InterruptedException {
        final TeamName australia = TeamName.AUSTRALIA;
        final TeamName italy = TeamName.ITALY;
        final TeamName argentina = TeamName.ARGENTINA;
        final TeamName mexico = TeamName.MEXICO;

        final Game AustraliaItalyGame = scoreboard.startNewGame(australia, italy);
        Thread.sleep(10);
        final Game ArgentinaMexicoGame = scoreboard.startNewGame(argentina, mexico);
        return List.of(AustraliaItalyGame, ArgentinaMexicoGame);
    }

    private static Stream<Arguments> provideGamesWithTeamsInAnotherGame() {
        return Stream.of(
                Arguments.of(TeamName.AUSTRALIA, TeamName.URUGUAY),
                Arguments.of(TeamName.URUGUAY, TeamName.AUSTRALIA),
                Arguments.of(TeamName.ITALY, TeamName.URUGUAY),
                Arguments.of(TeamName.URUGUAY, TeamName.ITALY));
    }
}
