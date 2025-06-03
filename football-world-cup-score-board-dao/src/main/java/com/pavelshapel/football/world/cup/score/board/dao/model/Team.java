package com.pavelshapel.football.world.cup.score.board.dao.model;

import static org.springframework.util.StringUtils.hasText;

public record Team(
        String title,
        int score
) {
    public Team {
        if (!hasText(title)) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
        if (score < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
    }
}
