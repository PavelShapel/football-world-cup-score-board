package com.pavelshapel.football.world.cup.score.board.service.board.comparator;

import com.pavelshapel.football.world.cup.score.board.dao.model.Game;

import java.util.Comparator;
import java.util.function.Supplier;

public interface GameComparatorSupplier extends Supplier<Comparator<Game>> {
}
