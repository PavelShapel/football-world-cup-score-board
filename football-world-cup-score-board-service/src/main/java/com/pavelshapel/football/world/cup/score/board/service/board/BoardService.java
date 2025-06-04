package com.pavelshapel.football.world.cup.score.board.service.board;

import com.pavelshapel.football.world.cup.score.board.dao.model.Game;
import com.pavelshapel.football.world.cup.score.board.service.board.comparator.GameComparatorSupplier;
import com.pavelshapel.football.world.cup.score.board.service.game.GameService;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static com.pavelshapel.football.world.cup.score.board.dao.model.Status.FINISHED;

@Service
public class BoardService {
    private final GameService gameService;
    private final GameComparatorSupplier gameComparatorSupplier;

    public BoardService(
            final GameService gameService,
            final GameComparatorSupplier gameComparatorSupplier
    ) {
        this.gameService = gameService;
        this.gameComparatorSupplier = gameComparatorSupplier;
    }

    public Game startGame(
            final String homeTeamTitle,
            final String awayTeamTitle
    ) {
        return gameService.start(homeTeamTitle, awayTeamTitle);
    }

    public Game homeTeamGoal(
            final Game game
    ) {
        return gameService.homeTeamGoal(game);
    }

    public Game awayTeamGoal(
            final Game game
    ) {
        return gameService.awayTeamGoal(game);
    }

    public Game finishGame(
            final Game game
    ) {
        return gameService.finish(game);
    }

    public Collection<Game> getSummary() {
        return gameService.getAllGames()
                .stream()
                .filter(this::isGameNotFinished)
                .sorted(gameComparatorSupplier.get())
                .toList();
    }

    private boolean isGameNotFinished(
            final Game game
    ) {
        return game.status() != FINISHED;
    }
}
