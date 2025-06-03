package com.pavelshapel.football.world.cup.score.board.dao.model;

import org.junit.jupiter.api.Test;

import static com.pavelshapel.football.world.cup.score.board.dao.model.Status.FINISHED;
import static com.pavelshapel.football.world.cup.score.board.dao.model.Status.IN_PROGRESS;
import static com.pavelshapel.football.world.cup.score.board.dao.model.Status.STARTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class GameTest {

    @Test
    void shouldCreateGameWhenValidDataProvided() {
        var rivals = new Rivals(
                new Team("homeTeam", 10),
                new Team("awayTeam", 15)
        );

        var result = new Game(1, rivals, FINISHED);

        assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("rivals.homeTeam.title", "homeTeam")
                .hasFieldOrPropertyWithValue("rivals.homeTeam.score", 10)
                .hasFieldOrPropertyWithValue("rivals.awayTeam.title", "awayTeam")
                .hasFieldOrPropertyWithValue("rivals.awayTeam.score", 15)
                .hasFieldOrPropertyWithValue("status", FINISHED);
    }

    @Test
    void shouldThrowExceptionWhenRivalsIsNull() {
        assertThatThrownBy(() -> new Game(1, null, STARTED))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Rivals and status cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenStatusIsNull() {
        var rivals = mock(Rivals.class);

        assertThatThrownBy(() -> new Game(1, rivals, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Rivals and status cannot be null");
    }

    @Test
    void shouldReturnCorrectSumOfHomeAndAwayScores() {
        var rivals = new Rivals(
                new Team("homeTeam", 20),
                new Team("awayTeam", 30)
        );
        var game = new Game(1, rivals, IN_PROGRESS);

        var result = game.score();

        assertThat(result)
                .isEqualTo(50);
    }

    @Test
    void shouldReturnCorrectComparisonWhenScoresAreDifferent() {
        var rivals1 = new Rivals(
                new Team("homeTeam1", 10),
                new Team("awayTeam1", 20)
        );
        var rivals2 = new Rivals(
                new Team("homeTeam2", 15),
                new Team("awayTeam2", 25)
        );
        var game1 = new Game(1, rivals1, IN_PROGRESS);
        var game2 = new Game(2, rivals2, IN_PROGRESS);

        var result = game1.compareTo(game2);

        assertThat(result)
                .isEqualTo(1);
    }

    @Test
    void shouldReturnCorrectComparisonWhenScoresAreEqual() {
        var rivals1 = new Rivals(
                new Team("homeTeam1", 10),
                new Team("awayTeam1", 20)
        );
        var rivals2 = new Rivals(
                new Team("homeTeam2", 10),
                new Team("awayTeam2", 20)
        );
        var game1 = new Game(1, rivals1, IN_PROGRESS);
        var game2 = new Game(2, rivals2, IN_PROGRESS);

        var result = game1.compareTo(game2);

        assertThat(result)
                .isEqualTo(1);
    }

    @Test
    void shouldReturnCorrectStringRepresentation() {
        var rivals = new Rivals(
                new Team("homeTeam", 15),
                new Team("awayTeam", 10)
        );
        var game = new Game(1, rivals, FINISHED);

        var result = game.toString();

        assertThat(result)
                .isEqualTo("|        homeTeam - awayTeam        |              15 - 10              |\n");
    }
}