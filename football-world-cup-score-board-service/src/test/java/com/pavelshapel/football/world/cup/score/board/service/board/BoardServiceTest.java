package com.pavelshapel.football.world.cup.score.board.service.board;

import com.pavelshapel.football.world.cup.score.board.dao.model.Game;
import com.pavelshapel.football.world.cup.score.board.service.board.comparator.GameComparatorSupplier;
import com.pavelshapel.football.world.cup.score.board.service.game.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Comparator;
import java.util.List;

import static com.pavelshapel.football.world.cup.score.board.dao.model.Status.FINISHED;
import static com.pavelshapel.football.world.cup.score.board.dao.model.Status.IN_PROGRESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    private GameService gameService;

    @Mock
    private GameComparatorSupplier gameComparatorSupplier;

    private BoardService boardService;

    @BeforeEach
    void setUp() {
        boardService = new BoardService(gameService, gameComparatorSupplier);
    }

    @Test
    void shouldInvokeGameServiceStart() {
        var homeTeamTitle = "homeTeam";
        var awayTeamTitle = "awayTeam";

        boardService.startGame(homeTeamTitle, awayTeamTitle);

        verify(gameService).start(homeTeamTitle, awayTeamTitle);
    }

    @Test
    void shouldInvokeGameServiceHomeTeamGoal() {
        var game = mock(Game.class);

        boardService.homeTeamGoal(game);

        verify(gameService).homeTeamGoal(game);
    }

    @Test
    void shouldInvokeGameServiceAwayTeamGoal() {
        var game = mock(Game.class);

        boardService.awayTeamGoal(game);

        verify(gameService).awayTeamGoal(game);
    }

    @Test
    void shouldInvokeGameServiceFinish() {
        var game = mock(Game.class);

        boardService.finishGame(game);

        verify(gameService).finish(game);
    }

    @Test
    void shouldInvokeGameServiceGetSummary() {
        var game1 = mock(Game.class);
        var game2 = mock(Game.class);
        var games = List.of(game1, game2);
        doReturn(games).when(gameService).getAllGames();
        doReturn(IN_PROGRESS).when(game1).status();
        doReturn(FINISHED).when(game2).status();
        var gameComparator = mock(Comparator.class);
        doReturn(gameComparator).when(gameComparatorSupplier).get();

        var result = boardService.getSummary();

        assertAll(
                () -> assertThat(result).containsExactly(game1),
                () -> verify(gameService).getAllGames()
        );
    }
}