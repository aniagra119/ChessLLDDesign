package com.chess.special;

import com.chess.enums.PieceColor;
import com.chess.enums.PieceType;
import com.chess.game.ChessGame;
import com.chess.game.GameBuilder;
import com.chess.model.Position;
import com.chess.piece.King;
import com.chess.piece.Pawn;
import com.chess.piece.Rook;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SpecialMovesTest {

    @Test
    void testKingsideCastling() {
        ChessGame game = new GameBuilder()
                .withCustomBoard()
                .withPiece(Position.of("e1"), new King(PieceColor.WHITE))
                .withPiece(Position.of("h1"), new Rook(PieceColor.WHITE))
                .withPiece(Position.of("e8"), new King(PieceColor.BLACK))
                .build();

        assertTrue(game.makeMove("e1", "g1"));
        assertNull(game.getBoard().getPiece(Position.of("e1")));
        assertTrue(game.getBoard().getPiece(Position.of("g1")) instanceof King);
        assertNull(game.getBoard().getPiece(Position.of("h1")));
        assertTrue(game.getBoard().getPiece(Position.of("f1")) instanceof Rook);

        // Test undo
        assertTrue(game.undoLastMove());
        assertTrue(game.getBoard().getPiece(Position.of("e1")) instanceof King);
        assertTrue(game.getBoard().getPiece(Position.of("h1")) instanceof Rook);
        assertNull(game.getBoard().getPiece(Position.of("g1")));
        assertNull(game.getBoard().getPiece(Position.of("f1")));
    }

    @Test
    void testEnPassantCapture() {
        ChessGame game = new GameBuilder()
                .withCustomBoard()
                .withPiece(Position.of("e1"), new King(PieceColor.WHITE))
                .withPiece(Position.of("e8"), new King(PieceColor.BLACK))
                .withPiece(Position.of("e5"), new Pawn(PieceColor.WHITE))
                .withPiece(Position.of("d7"), new Pawn(PieceColor.BLACK))
                .build();

        // White dummy move
        assertTrue(game.makeMove("e1", "f1"));
        // Black advances pawn 2 squares d7 -> d5
        assertTrue(game.makeMove("d7", "d5"));

        // White captures en passant e5 -> d6
        assertTrue(game.makeMove("e5", "d6"));
        assertNull(game.getBoard().getPiece(Position.of("d5"))); // Captured black pawn
        assertTrue(game.getBoard().getPiece(Position.of("d6")) instanceof Pawn);
    }

    @Test
    void testPawnPromotion() {
        ChessGame game = new GameBuilder()
                .withCustomBoard()
                .withPiece(Position.of("e1"), new King(PieceColor.WHITE))
                .withPiece(Position.of("e8"), new King(PieceColor.BLACK))
                .withPiece(Position.of("a7"), new Pawn(PieceColor.WHITE))
                .build();

        assertTrue(game.makeMove("a7", "a8", PieceType.QUEEN));
        assertEquals(PieceType.QUEEN, game.getBoard().getPiece(Position.of("a8")).getType());
    }
}
