package com.pavelshapel.football.world.cup.score.board.service.board.comparator.impl;

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

import static com.pavelshapel.football.world.cup.score.board.dao.model.Status.IN_PROGRESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.ARRAY;

class ProdGameComparatorSupplierTest {

    private ProdGameComparatorSupplier gameComparatorSupplier;

    @BeforeEach
    void setUp() {
        gameComparatorSupplier = new ProdGameComparatorSupplier();
    }

    @Test
    void shouldReturnProdForProfileAnnotationPropertyValue() {
        var annotation = ProdGameComparatorSupplier.class
                .getAnnotation(Profile.class);

        assertThat(annotation)
                .isNotNull()
                .extracting(Profile::value)
                .asInstanceOf(ARRAY)
                .containsExactly("prod");
    }

    @ParameterizedTest
    @MethodSource("gameComparatorTestCase")
    void shouldProvideComparatorSortingByScoreAndIdInDescendingOrder(
            GameComparatorTestCase testCase
    ) {
        var result = gameComparatorSupplier.get().compare(testCase.game1, testCase.game2);

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
                                        IN_PROGRESS
                                ),
                                new Game(
                                        2,
                                        new Rivals(
                                                new Team("homeTeam", 2),
                                                new Team("awayTeam", 2)),
                                        IN_PROGRESS
                                ),
                                1
                        )
                ),
                Arguments.of(
                        new GameComparatorTestCase(
                                new Game(
                                        3,
                                        new Rivals(
                                                new Team("homeTeam", 2),
                                                new Team("awayTeam", 2)),
                                        IN_PROGRESS
                                ),
                                new Game(
                                        4,
                                        new Rivals(
                                                new Team("homeTeam", 1),
                                                new Team("awayTeam", 1)),
                                        IN_PROGRESS
                                ),
                                -1
                        )
                ), Arguments.of(
                        new GameComparatorTestCase(
                                new Game(
                                        5,
                                        new Rivals(
                                                new Team("homeTeam", 2),
                                                new Team("awayTeam", 2)),
                                        IN_PROGRESS
                                ),
                                new Game(
                                        6,
                                        new Rivals(
                                                new Team("homeTeam", 2),
                                                new Team("awayTeam", 2)),
                                        IN_PROGRESS
                                ),
                                1
                        )
                )
        );
    }

    private record GameComparatorTestCase(
            Game game1,
            Game game2,
            int expected
    ) {
    }
}
