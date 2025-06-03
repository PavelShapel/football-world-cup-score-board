package com.pavelshapel.football.world.cup.score.board.dao.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RivalsTest {
    @Test
    void shouldCreateRivalsWhenValidDataProvided() {
        var teamTitle1 = new Team("teamTitle1", 10);
        var teamTitle2 = new Team("teamTitle2", 20);

        var result = new Rivals(teamTitle1, teamTitle2);

        assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("homeTeam.title", "teamTitle1")
                .hasFieldOrPropertyWithValue("homeTeam.score", 10)
                .hasFieldOrPropertyWithValue("awayTeam.title", "teamTitle2")
                .hasFieldOrPropertyWithValue("awayTeam.score", 20);
    }

    @Test
    void shouldThrowExceptionWhenHomeTeamIsNull() {
        var awayTeam = new Team("awayTeam", 20);

        assertThatThrownBy(() -> new Rivals(null, awayTeam))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Teams cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenAwayTeamIsNull() {
        var homeTeam = new Team("homeTeam", 10);

        assertThatThrownBy(() -> new Rivals(homeTeam, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Teams cannot be null");
    }
}