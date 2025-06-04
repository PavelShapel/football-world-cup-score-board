package com.pavelshapel.football.world.cup.score.board.service.board.filter.impl;

import com.pavelshapel.football.world.cup.score.board.dao.model.Game;
import com.pavelshapel.football.world.cup.score.board.service.board.filter.GameStatusFilterSupplier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

import static com.pavelshapel.football.world.cup.score.board.dao.model.Status.FINISHED;

@Service
@Profile("!prod")
public class NotProdGameStatusFilterSupplier implements GameStatusFilterSupplier {
    @Override
    public Predicate<Game> get() {
        return game -> game.status() == FINISHED;
    }
}
