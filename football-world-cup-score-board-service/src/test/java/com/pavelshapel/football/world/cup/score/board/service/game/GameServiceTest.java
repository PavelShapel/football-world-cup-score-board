package com.pavelshapel.football.world.cup.score.board.service.game;

import com.pavelshapel.football.world.cup.score.board.dao.model.Game;
import com.pavelshapel.football.world.cup.score.board.dao.model.Rivals;
import com.pavelshapel.football.world.cup.score.board.dao.model.Team;
import com.pavelshapel.football.world.cup.score.board.dao.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.pavelshapel.football.world.cup.score.board.dao.model.Status.FINISHED;
import static com.pavelshapel.football.world.cup.score.board.dao.model.Status.IN_PROGRESS;
import static com.pavelshapel.football.world.cup.score.board.dao.model.Status.STARTED;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private RivalsService rivalsService;

    @Mock
    private GameRepository gameRepository;

    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameService(rivalsService, gameRepository);
    }

    @Test
    void shouldInvokeGameRepositorySaveWhenGameStarted() {
        var homeTeamTitle = "homeTeam";
        var awayTeamTitle = "awayTeam";
        var rivals = new Rivals(
                new Team(homeTeamTitle, 0),
                new Team(awayTeamTitle, 0)
        );
        doReturn(rivals).when(rivalsService).create(homeTeamTitle, awayTeamTitle);

        gameService.start(homeTeamTitle, awayTeamTitle);

        verify(gameRepository).save(eq(new Game(null, rivals, STARTED)));
    }

    @Test
    void shouldInvokeGameRepositorySaveWhenHomeTeamGoal() {
        var homeTeamTitle = "homeTeam";
        var awayTeamTitle = "awayTeam";
        var rivals = new Rivals(
                new Team(homeTeamTitle, 0),
                new Team(awayTeamTitle, 0)
        );
        var game = new Game(1, rivals, STARTED);
        doReturn(rivals).when(rivalsService).homeTeamGoal(rivals);

        gameService.homeTeamGoal(game);

        verify(gameRepository).save(eq(new Game(1, rivals, IN_PROGRESS)));
    }

    @Test
    void shouldInvokeGameRepositorySaveWhenAwayTeamGoal() {
        var homeTeamTitle = "homeTeam";
        var awayTeamTitle = "awayTeam";
        var rivals = new Rivals(
                new Team(homeTeamTitle, 0),
                new Team(awayTeamTitle, 0)
        );
        var game = new Game(1, rivals, STARTED);
        doReturn(rivals).when(rivalsService).awayTeamGoal(rivals);

        gameService.awayTeamGoal(game);

        verify(gameRepository).save(eq(new Game(1, rivals, IN_PROGRESS)));
    }

    @Test
    void shouldInvokeGameRepositorySaveWhenGameFinished() {
        var homeTeamTitle = "homeTeam";
        var awayTeamTitle = "awayTeam";
        var rivals = new Rivals(
                new Team(homeTeamTitle, 0),
                new Team(awayTeamTitle, 0)
        );
        var game = new Game(1, rivals, IN_PROGRESS);

        gameService.finish(game);

        verify(gameRepository).save(eq(new Game(1, rivals, FINISHED)));
    }

    @Test
    void shouldInvokeGameRepositoryFindAllWhenGetAllGames() {
        gameService.getAllGames();

        verify(gameRepository).findAll();
    }
}