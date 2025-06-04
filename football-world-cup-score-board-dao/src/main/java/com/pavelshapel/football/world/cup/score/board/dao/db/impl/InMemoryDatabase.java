package com.pavelshapel.football.world.cup.score.board.dao.db.impl;

import com.pavelshapel.football.world.cup.score.board.dao.db.Database;
import com.pavelshapel.football.world.cup.score.board.dao.model.Game;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InMemoryDatabase implements Database {
    private final Map<Integer, Game> games = new ConcurrentHashMap<>();
    private final AtomicInteger atomicCount = new AtomicInteger(0);

    public Game insert(
            final Game game
    ) {
        var key = game.id();
        var savedGame = game;
        if (key == null) {
            key = atomicCount.incrementAndGet();
            savedGame = new Game(key, game.rivals(), game.status());
        }
        this.games.put(key, savedGame);
        return savedGame;
    }

    public Map<Integer, Game> selectAll() {
        return Map.copyOf(games);
    }
}
