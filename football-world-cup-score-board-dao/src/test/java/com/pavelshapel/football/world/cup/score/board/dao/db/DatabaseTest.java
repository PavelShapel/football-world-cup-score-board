package com.pavelshapel.football.world.cup.score.board.dao.db;

import com.pavelshapel.football.world.cup.score.board.dao.model.Game;
import com.pavelshapel.football.world.cup.score.board.dao.model.Rivals;
import com.pavelshapel.football.world.cup.score.board.dao.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class DatabaseTest {
    private Database database;

    @BeforeEach
    void setUp() {
        database = new Database();
    }

    @Test
    void shouldPersistGameWithExplicitId() {
        var rivals = Mockito.mock(Rivals.class);
        var status = Mockito.mock(Status.class);
        var game = new Game(15, rivals, status);

        var result = database.insert(game);

        assertAll(
                () -> assertThat(result)
                        .isNotNull()
                        .hasFieldOrPropertyWithValue("id", 15)
                        .hasFieldOrPropertyWithValue("rivals", rivals)
                        .hasFieldOrPropertyWithValue("status", status),
                () -> assertThat(database.selectAll())
                        .hasSize(1)
                        .containsKey(15)
        );
    }

    @Test
    void shouldAssignNewIdWhenGameIdIsNull() {
        var rivals = Mockito.mock(Rivals.class);
        var status = Mockito.mock(Status.class);
        var gameWithoutId = new Game(null, rivals, status);

        var result = database.insert(gameWithoutId);

        assertAll(
                () -> assertThat(result)
                        .isNotNull()
                        .hasFieldOrPropertyWithValue("id", 1)
                        .hasFieldOrPropertyWithValue("rivals", rivals)
                        .hasFieldOrPropertyWithValue("status", status),
                () -> assertThat(database.selectAll())
                        .hasSize(1)
                        .containsKey(1)
        );
    }

    @Test
    void shouldIncrementIdWhenSavingMultipleGamesWithoutIds() {
        var rivals = Mockito.mock(Rivals.class);
        var status = Mockito.mock(Status.class);
        var game1 = new Game(null, rivals, status);
        var game2 = new Game(null, rivals, status);

        var savedGame1 = database.insert(game1);
        var savedGame2 = database.insert(game2);

        assertAll(
                () -> assertThat(savedGame1)
                        .isNotNull()
                        .hasFieldOrPropertyWithValue("id", 1)
                        .hasFieldOrPropertyWithValue("rivals", rivals)
                        .hasFieldOrPropertyWithValue("status", status),
                () -> assertThat(savedGame2)
                        .isNotNull()
                        .hasFieldOrPropertyWithValue("id", 2)
                        .hasFieldOrPropertyWithValue("rivals", rivals)
                        .hasFieldOrPropertyWithValue("status", status),
                () -> assertThat(database.selectAll())
                        .hasSize(2)
                        .containsKey(1)
                        .containsKey(2)
        );
    }

    @Test
    void shouldReturnImmutableCopyOfGames() {
        var rivals1 = Mockito.mock(Rivals.class);
        var rivals2 = Mockito.mock(Rivals.class);
        var status = Mockito.mock(Status.class);
        var game1 = new Game(null, rivals1, status);
        var game2 = new Game(null, rivals2, status);
        database.insert(game1);
        database.insert(game2);

        var result = database.selectAll();

        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .containsKey(1)
                .containsKey(2);
    }

    @Test
    void shouldOverwriteExistingGameWhenGameWithSameIdIsSaved() {
        var rivals1 = Mockito.mock(Rivals.class);
        var rivals2 = Mockito.mock(Rivals.class);
        var status1 = Mockito.mock(Status.class);
        var status2 = Mockito.mock(Status.class);
        var originalGame = new Game(1, rivals1, status1);
        var updatedGame = new Game(1, rivals2, status2);
        database.insert(originalGame);

        var result = database.insert(updatedGame);

        assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("rivals", rivals2)
                .hasFieldOrPropertyWithValue("status", status2);
    }
}