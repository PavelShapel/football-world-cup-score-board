package com.pavelshapel.football.world.cup.score.board.dao.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TeamTest {

    @Test
    void shouldCreateTeamWhenValidDataProvided() {
        var result = new Team("validTitle", 10);

        assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("title", "validTitle")
                .hasFieldOrPropertyWithValue("score", 10);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    void shouldThrowExceptionWhenTitleIsNullOrEmpty(
            String title
    ) {
        assertThatThrownBy(() -> new Team(title, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Title cannot be null or blank");
    }

    @Test
    void shouldThrowExceptionWhenScoreIsNegative() {
        assertThatThrownBy(() -> new Team("validTitle", -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Score cannot be negative");
    }
}