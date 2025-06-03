package com.pavelshapel.football.world.cup.score.board.service.game;

import com.pavelshapel.football.world.cup.score.board.dao.model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TeamServiceTest {
    private TeamService teamService;

    @BeforeEach
    void setUp() {
        teamService = new TeamService();
    }

    @Test
    void shouldCreateTeamWithGivenTitleAndZeroScore() {
        var title = "teamTitle";

        var result = teamService.create(title);

        assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("title", "teamTitle")
                .hasFieldOrPropertyWithValue("score", 0);
    }

    @Test
    void shouldUpdateTeamScore() {
        var team = new Team("teamTitle", 0);
        var score = 10;

        var result = teamService.update(team, score);

        assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("title", "teamTitle")
                .hasFieldOrPropertyWithValue("score", 10);
    }
}