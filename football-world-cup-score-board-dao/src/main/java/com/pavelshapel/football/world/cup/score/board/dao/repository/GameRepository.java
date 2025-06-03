package com.pavelshapel.football.world.cup.score.board.dao.repository;

import com.pavelshapel.football.world.cup.score.board.dao.db.Database;
import com.pavelshapel.football.world.cup.score.board.dao.model.Game;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class GameRepository {
    private final Database database;

    public GameRepository(
            final Database database) {
        this.database = database;
    }

    public Game save(
            final Game game
    ) {
        return database.insert(game);
    }

    public Collection<Game> findAll() {
        return database.selectAll().values();
    }
}
