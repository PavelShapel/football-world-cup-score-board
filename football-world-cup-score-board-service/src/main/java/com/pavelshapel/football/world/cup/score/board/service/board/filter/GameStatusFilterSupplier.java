package com.pavelshapel.football.world.cup.score.board.service.board.filter;

import com.pavelshapel.football.world.cup.score.board.dao.model.Game;

import java.util.function.Predicate;
import java.util.function.Supplier;

public interface GameStatusFilterSupplier extends Supplier<Predicate<Game>> {
}
