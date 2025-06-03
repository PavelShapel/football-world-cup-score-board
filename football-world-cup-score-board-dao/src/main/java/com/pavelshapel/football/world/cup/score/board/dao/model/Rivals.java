package com.pavelshapel.football.world.cup.score.board.dao.model;

public record Rivals(
        Team homeTeam,
        Team awayTeam
) {
    public Rivals {
        if (homeTeam == null || awayTeam == null) {
            throw new IllegalArgumentException("Teams cannot be null");
        }
    }
}
