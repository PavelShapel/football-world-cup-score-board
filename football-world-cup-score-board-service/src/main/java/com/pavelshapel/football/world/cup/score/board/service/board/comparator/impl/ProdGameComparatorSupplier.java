package com.pavelshapel.football.world.cup.score.board.service.board.comparator.impl;

import com.pavelshapel.football.world.cup.score.board.dao.model.Game;
import com.pavelshapel.football.world.cup.score.board.service.board.comparator.GameComparatorSupplier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@Profile("prod")
public class ProdGameComparatorSupplier implements GameComparatorSupplier {

    @Override
    public Comparator<Game> get() {
        return Comparator.comparing(Game::score, Comparator.reverseOrder())
                .thenComparing(Game::id, Comparator.reverseOrder());
    }
}
