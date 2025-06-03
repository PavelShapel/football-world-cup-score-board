package com.pavelshapel.football.world.cup.score.board.service.game;

import com.pavelshapel.football.world.cup.score.board.dao.model.Rivals;
import org.springframework.stereotype.Service;

@Service
public class RivalsService {
    private final TeamService teamsService;

    public RivalsService(
            final TeamService teamsService
    ) {
        this.teamsService = teamsService;
    }

    public Rivals create(
            final String homeTeamTitle,
            final String awayTeamTitle
    ) {
        var homeTeam = teamsService.create(homeTeamTitle);
        var awayTeam = teamsService.create(awayTeamTitle);
        return new Rivals(homeTeam, awayTeam);
    }

    public Rivals homeTeamGoal(
            final Rivals rivals
    ) {
        var homeTeam = rivals.homeTeam();
        var updatedHomeTeam = teamsService.update(homeTeam, homeTeam.score() + 1);
        return new Rivals(updatedHomeTeam, rivals.awayTeam());
    }

    public Rivals awayTeamGoal(
            final Rivals rivals
    ) {
        var awayTeam = rivals.awayTeam();
        var updatedAwayTeam = teamsService.update(awayTeam, awayTeam.score() + 1);
        return new Rivals(rivals.homeTeam(), updatedAwayTeam);
    }
}
