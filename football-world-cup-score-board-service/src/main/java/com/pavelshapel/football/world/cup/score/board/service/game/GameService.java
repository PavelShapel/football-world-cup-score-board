package com.pavelshapel.football.world.cup.score.board.service.game;

import com.pavelshapel.football.world.cup.score.board.dao.model.Game;
import com.pavelshapel.football.world.cup.score.board.dao.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static com.pavelshapel.football.world.cup.score.board.dao.model.Status.FINISHED;
import static com.pavelshapel.football.world.cup.score.board.dao.model.Status.IN_PROGRESS;
import static com.pavelshapel.football.world.cup.score.board.dao.model.Status.STARTED;

@Service
public class GameService {
    private final RivalsService rivalsService;
    private final GameRepository gameRepository;

    public GameService(
            final RivalsService rivalsService,
            final GameRepository gameRepository
    ) {
        this.rivalsService = rivalsService;
        this.gameRepository = gameRepository;
    }

    public Game start(
            final String homeTeamTitle,
            final String awayTeamTitle
    ) {
        var rivals = rivalsService.create(homeTeamTitle, awayTeamTitle);
        var game = new Game(null, rivals, STARTED);
        return gameRepository.save(game);
    }

    public Game homeTeamGoal(
            final Game game
    ) {
        var rivals = rivalsService.homeTeamGoal(game.rivals());
        var updatedGame = new Game(game.id(), rivals, IN_PROGRESS);
        return gameRepository.save(updatedGame);
    }

    public Game awayTeamGoal(
            final Game game
    ) {
        var rivals = rivalsService.awayTeamGoal(game.rivals());
        var updatedGame = new Game(game.id(), rivals, IN_PROGRESS);
        return gameRepository.save(updatedGame);
    }

    public Game finish(
            final Game game
    ) {
        var updatedGame = new Game(game.id(), game.rivals(), FINISHED);
        return gameRepository.save(updatedGame);
    }

    public Collection<Game> getAllGames() {
        return gameRepository.findAll();
    }
}
