package com.pavelshapel.football.world.cup.score.board.dao.model;

public record Game(
        Integer id,
        Rivals rivals,
        Status status
) implements Comparable<Game> {
    public Game {
        if (rivals == null || status == null) {
            throw new IllegalArgumentException("Rivals and status cannot be null");
        }
    }

    public int score() {
        return rivals().homeTeam().score() + rivals().awayTeam().score();
    }

    @Override
    public int compareTo(
            final Game game
    ) {
        if (score() < game.score()) {
            return 1;
        } else if (this.score() > game.score()) {
            return -1;
        } else {
            return game.id.compareTo(id);
        }
    }

    @Override
    public String toString() {
        Team homeTeam = rivals.homeTeam();
        Team awayTeam = rivals.awayTeam();
        return String.format(
                "| %15s - %-15s | %15d - %-15d |%n",
                homeTeam.title(),
                awayTeam.title(),
                homeTeam.score(),
                awayTeam.score()
        );
    }
}