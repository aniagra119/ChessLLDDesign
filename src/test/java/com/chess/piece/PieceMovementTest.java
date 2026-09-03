package com.chess.piece;

import com.chess.enums.PieceColor;
import com.chess.model.Board;
import com.chess.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PieceMovementTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void testKnightMoves() {
        Knight knight = new Knight(PieceColor.WHITE);
        Position d4 = Position.of("d4");
        board.setPiece(d4, knight);

        List<Position> moves = knight.getPseudoLegalMoves(board, d4);
        assertEquals(8, moves.size());
    }

    @Test
    void testRookLinearRay() {
        Rook rook = new Rook(PieceColor.WHITE);
        Position d4 = Position.of("d4");
        board.setPiece(d4, rook);

        // Place blocking friendly piece at d6
        board.setPiece(Position.of("d6"), new Pawn(PieceColor.WHITE));
        // Place enemy piece at d2
        board.setPiece(Position.of("d2"), new Pawn(PieceColor.BLACK));

        List<Position> moves = rook.getPseudoLegalMoves(board, d4);
        assertTrue(moves.contains(Position.of("d5")));
        assertFalse(moves.contains(Position.of("d6"))); // blocked by friendly
        assertTrue(moves.contains(Position.of("d2"))); // captures enemy
        assertFalse(moves.contains(Position.of("d1"))); // cannot pass enemy
    }

    @Test
    void testBishopDiagonals() {
        Bishop bishop = new Bishop(PieceColor.WHITE);
        Position c1 = Position.of("c1");
        board.setPiece(c1, bishop);

        List<Position> moves = bishop.getPseudoLegalMoves(board, c1);
        assertTrue(moves.contains(Position.of("a3")));
        assertTrue(moves.contains(Position.of("f4")));
        assertTrue(moves.contains(Position.of("h6")));
    }
}
