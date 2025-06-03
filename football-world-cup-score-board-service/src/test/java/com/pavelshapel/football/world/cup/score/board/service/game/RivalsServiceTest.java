package com.pavelshapel.football.world.cup.score.board.service.game;

import com.pavelshapel.football.world.cup.score.board.dao.model.Rivals;
import com.pavelshapel.football.world.cup.score.board.dao.model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class RivalsServiceTest {
    @Mock
    private TeamService teamService;

    private RivalsService rivalsService;

    @BeforeEach
    void setUp() {
        rivalsService = new RivalsService(teamService);
    }

    @Test
    void shouldCreateRivalsWithGivenTeams() {
        var homeTeamTitle = "homeTeamTitle";
        var awayTeamTitle = "awayTeamTitle";
        var homeTeam = new Team(homeTeamTitle, 0);
        var awayTeam = new Team(awayTeamTitle, 0);
        doReturn(homeTeam).when(teamService).create(homeTeamTitle);
        doReturn(awayTeam).when(teamService).create(awayTeamTitle);

        var result = rivalsService.create(homeTeamTitle, awayTeamTitle);

        assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("homeTeam.title", homeTeamTitle)
                .hasFieldOrPropertyWithValue("homeTeam.score", 0)
                .hasFieldOrPropertyWithValue("awayTeam.title", awayTeamTitle)
                .hasFieldOrPropertyWithValue("awayTeam.score", 0);
    }

    @Test
    void shouldUpdateRivalsWithHomeTeamGoal() {
        var homeTeamTitle = "homeTeamTitle";
        var awayTeamTitle = "awayTeamTitle";
        var homeTeam = new Team(homeTeamTitle, 0);
        var awayTeam = new Team(awayTeamTitle, 0);
        var rivals = new Rivals(homeTeam, awayTeam);
        var updatedHomeTeam = new Team(homeTeamTitle, 1);
        doReturn(updatedHomeTeam).when(teamService).update(homeTeam, 1);

        var result = rivalsService.homeTeamGoal(rivals);

        assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("homeTeam.title", homeTeamTitle)
                .hasFieldOrPropertyWithValue("homeTeam.score", 1)
                .hasFieldOrPropertyWithValue("awayTeam.title", awayTeamTitle)
                .hasFieldOrPropertyWithValue("awayTeam.score", 0);
    }

    @Test
    void shouldUpdateRivalsWithAwayTeamGoal() {
        var homeTeamTitle = "homeTeamTitle";
        var awayTeamTitle = "awayTeamTitle";
        var homeTeam = new Team(homeTeamTitle, 0);
        var awayTeam = new Team(awayTeamTitle, 0);
        var rivals = new Rivals(homeTeam, awayTeam);
        var updatedAwayTeam = new Team(awayTeamTitle, 1);
        doReturn(updatedAwayTeam).when(teamService).update(awayTeam, 1);

        var result = rivalsService.awayTeamGoal(rivals);

        assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("homeTeam.title", homeTeamTitle)
                .hasFieldOrPropertyWithValue("homeTeam.score", 0)
                .hasFieldOrPropertyWithValue("awayTeam.title", awayTeamTitle)
                .hasFieldOrPropertyWithValue("awayTeam.score", 1);
    }
}