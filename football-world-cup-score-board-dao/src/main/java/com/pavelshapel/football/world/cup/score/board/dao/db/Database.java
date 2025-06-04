package com.pavelshapel.football.world.cup.score.board.dao.db;

import com.pavelshapel.football.world.cup.score.board.dao.model.Game;

import java.util.Map;

public interface Database {
    Game insert(
            Game game
    );

    Map<Integer, Game> selectAll();
}
