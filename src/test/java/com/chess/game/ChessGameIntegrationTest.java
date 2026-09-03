package com.chess.game;

import com.chess.enums.GameStatus;
import com.chess.enums.PieceColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChessGameIntegrationTest {

    @Test
    void testScholarsMateCheckmate() {
        ChessGame game = new GameBuilder().build();

        // 1. e4 e5
        assertTrue(game.makeMove("e2", "e4"));
        assertTrue(game.makeMove("e7", "e5"));

        // 2. Bc4 Nc6
        assertTrue(game.makeMove("f1", "c4"));
        assertTrue(game.makeMove("b8", "c6"));

        // 3. Qh5 Nf6??
        assertTrue(game.makeMove("d1", "h5"));
        assertTrue(game.makeMove("g8", "f6"));

        // 4. Qxf7# Checkmate
        assertTrue(game.makeMove("h5", "f7"));
        assertEquals(GameStatus.CHECKMATE, game.getStatus());
        assertEquals(PieceColor.BLACK, game.getCurrentTurn().getColor());
    }

    @Test
    void testFoolsMateTwoMoveCheckmate() {
        ChessGame game = new GameBuilder().build();

        // 1. f3 e5
        assertTrue(game.makeMove("f2", "f3"));
        assertTrue(game.makeMove("e7", "e5"));

        // 2. g4 Qh4#
        assertTrue(game.makeMove("g2", "g4"));
        assertTrue(game.makeMove("d8", "h4"));

        assertEquals(GameStatus.CHECKMATE, game.getStatus());
        assertEquals(PieceColor.WHITE, game.getCurrentTurn().getColor());
    }

    @Test
    void testTurnSwitchingAndInvalidMoveRejected() {
        ChessGame game = new GameBuilder().build();

        // Cannot move black piece on white's turn
        assertFalse(game.makeMove("e7", "e5"));

        // Valid move
        assertTrue(game.makeMove("e2", "e4"));
        assertEquals(PieceColor.BLACK, game.getCurrentTurn().getColor());

        // Cannot move white piece on black's turn
        assertFalse(game.makeMove("d2", "d4"));
    }
}
