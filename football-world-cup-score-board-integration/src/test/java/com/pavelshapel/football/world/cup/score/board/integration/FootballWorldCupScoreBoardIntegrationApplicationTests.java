package com.pavelshapel.football.world.cup.score.board.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FootballWorldCupScoreBoardIntegrationApplicationTests {

    @Autowired
    private BoardService boardService;

    @Test
    void shouldReturnBoardSortedByScoreAndMostRecentlyAddedToSystem() {
        // Step 1: Start games

        var gameMexicoCanada = boardService.startGame("Mexico", "Canada");
        var gameSpainBrazil = boardService.startGame("Spain", "Brazil");

        var boarSummary = boardService.getSummary();

        assertThat(boarSummary)
                .isNotEmpty()
                .hasSize(2)
                .containsExactly(
                        gameSpainBrazil,
                        gameMexicoCanada
                );

        var gameGermanyFrance = boardService.startGame("Germany", "France");
        var gameUruguayItaly = boardService.startGame("Uruguay", "Italy");
        var gameArgentinaAustralia = boardService.startGame("Argentina", "Australia");

        boarSummary = boardService.getSummary();

        assertThat(boarSummary)
                .isNotEmpty()
                .hasSize(5)
                .containsExactly(
                        gameArgentinaAustralia,
                        gameUruguayItaly,
                        gameGermanyFrance,
                        gameSpainBrazil,
                        gameMexicoCanada
                );

        // Step 2: Games are on

        // Mexico - Canada : 0 - 5
        gameMexicoCanada = updateGame(gameMexicoCanada, 0, 5);
        // Spain - Brazil : 10 - 2
        gameSpainBrazil = updateGame(gameSpainBrazil, 10, 2);
        // Germany - France : 2 - 2
        gameGermanyFrance = updateGame(gameGermanyFrance, 2, 2);
        // Uruguay - Italy : 6 - 6
        gameUruguayItaly = updateGame(gameUruguayItaly, 6, 6);
        // Argentina - Australia : 3 - 1
        gameArgentinaAustralia = updateGame(gameArgentinaAustralia, 3, 1);

        boarSummary = boardService.getSummary();

        assertThat(boarSummary)
                .isNotEmpty()
                .hasSize(5)
                .containsExactly(
                        gameUruguayItaly,
                        gameSpainBrazil,
                        gameMexicoCanada,
                        gameArgentinaAustralia,
                        gameGermanyFrance
                );

        System.out.printf("%55s %n%n %s", "Football World Cup Score Board", boarSummary);

        // Step 3: Finish games

        boardService.finishGame(gameMexicoCanada);
        boardService.finishGame(gameSpainBrazil);

        boarSummary = boardService.getSummary();

        assertThat(boarSummary)
                .isNotEmpty()
                .hasSize(3)
                .containsExactly(
                        gameUruguayItaly,
                        gameArgentinaAustralia,
                        gameGermanyFrance
                );


        boardService.finishGame(gameGermanyFrance);
        boardService.finishGame(gameUruguayItaly);
        boardService.finishGame(gameArgentinaAustralia);

        boarSummary = boardService.getSummary();

        assertThat(boarSummary)
                .isEmpty();
    }

    private Game updateGame(
            Game game,
            int homeTeamGoals,
            int awayTeamGoals
    ) {
        var updatedGame = game;
        for (int i = 0; i < homeTeamGoals; i++) {
            updatedGame = boardService.homeTeamGoal(updatedGame);
        }
        for (int i = 0; i < awayTeamGoals; i++) {
            updatedGame = boardService.awayTeamGoal(updatedGame);
        }
        return updatedGame;
    }
}
