package com.pavelshapel.football.world.cup.score.board.service.game;

import com.pavelshapel.football.world.cup.score.board.dao.model.Team;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    public Team create(
            final String title
    ) {
        return new Team(title, 0);
    }

    public Team update(
            final Team team,
            final int score
    ) {
        return new Team(team.title(), score);
    }
}
