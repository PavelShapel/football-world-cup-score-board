package com.pavelshapel.football.world.cup.score.board.dao.repository;

import com.pavelshapel.football.world.cup.score.board.dao.db.Database;
import com.pavelshapel.football.world.cup.score.board.dao.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameRepositoryTest {

    @Mock
    private Database database;

    private GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        gameRepository = new GameRepository(database);
    }

    @Test
    void shouldInvokeDatabaseInset() {
        var game = mock(Game.class);

        gameRepository.save(game);

        verify(database).insert(game);
    }

    @Test
    void shouldInvokeDatabaseSelectAll() {
        gameRepository.findAll();

        verify(database).selectAll();
    }
}