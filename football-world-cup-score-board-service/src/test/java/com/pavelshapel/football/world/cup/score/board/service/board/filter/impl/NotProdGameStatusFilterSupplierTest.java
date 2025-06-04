package com.pavelshapel.football.world.cup.score.board.service.board.filter.impl;

import com.pavelshapel.football.world.cup.score.board.dao.model.Game;
import com.pavelshapel.football.world.cup.score.board.dao.model.Rivals;
import com.pavelshapel.football.world.cup.score.board.dao.model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.context.annotation.Profile;

import java.util.stream.Stream;

import static com.pavelshapel.football.world.cup.score.board.dao.model.Status.FINISHED;
import static com.pavelshapel.football.world.cup.score.board.dao.model.Status.IN_PROGRESS;
import static com.pavelshapel.football.world.cup.score.board.dao.model.Status.STARTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.ARRAY;

class NotProdGameStatusFilterSupplierTest {

    private NotProdGameStatusFilterSupplier gameStatusFilterSupplier;

    @BeforeEach
    void setUp() {
        gameStatusFilterSupplier = new NotProdGameStatusFilterSupplier();
    }

    @Test
    void shouldReturnProdForProfileAnnotationPropertyValue() {
        var annotation = NotProdGameStatusFilterSupplier.class
                .getAnnotation(Profile.class);

        assertThat(annotation)
                .isNotNull()
                .extracting(Profile::value)
                .asInstanceOf(ARRAY)
                .containsExactly("!prod");
    }

    @ParameterizedTest
    @MethodSource("gameComparatorTestCase")
    void shouldProvideStatusFilter(
            GameComparatorTestCase testCase
    ) {
        var result = gameStatusFilterSupplier.get().test(testCase.game);

        assertThat(result)
                .isEqualTo(testCase.expected);
    }

    private static Stream<Arguments> gameComparatorTestCase() {
        return Stream.of(
                Arguments.of(
                        new GameComparatorTestCase(
                                new Game(
                                        1,
                                        new Rivals(
                                                new Team("homeTeam", 1),
                                                new Team("awayTeam", 1)),
                                        STARTED
                                ),
                                false
                        )
                ),
                Arguments.of(
                        new GameComparatorTestCase(
                                new Game(
                                        1,
                                        new Rivals(
                                                new Team("homeTeam", 1),
                                                new Team("awayTeam", 1)),
                                        IN_PROGRESS
                                ),
                                false
                        )
                ), Arguments.of(
                        new GameComparatorTestCase(
                                new Game(
                                        1,
                                        new Rivals(
                                                new Team("homeTeam", 1),
                                                new Team("awayTeam", 1)),
                                        FINISHED
                                ),
                                true
                        )
                )
        );
    }

    private record GameComparatorTestCase(
            Game game,
            boolean expected
    ) {
    }
}