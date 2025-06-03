package com.pavelshapel.football.world.cup.score.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.ARRAY;
import static org.junit.jupiter.api.Assertions.assertTimeout;

class FootballWorldCupScoreBoardServiceApplicationTest {
    @Test
    void shouldReturnFalseForConfigurationAnnotationPropertyProxyBeanMethods() {
        var annotation = FootballWorldCupScoreBoardServiceApplication.class
                .getAnnotation(SpringBootApplication.class);

        assertThat(annotation)
                .isNotNull()
                .extracting(SpringBootApplication::scanBasePackages)
                .asInstanceOf(ARRAY)
                .containsExactly("com.pavelshapel.football.world.cup.score.board");
    }

    @Test
    void contextLoads() {
        assertTimeout(ofSeconds(10), () -> {
            FootballWorldCupScoreBoardServiceApplication.main(new String[]{});
        });
    }
}