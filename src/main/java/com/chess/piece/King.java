package com.chess.piece;

import com.chess.enums.PieceColor;
import com.chess.enums.PieceType;
import com.chess.model.Board;
import com.chess.model.Position;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    private static final int[][] DELTAS = {
        {-1, -1}, {-1, 0}, {-1, 1},
        {0, -1},           {0, 1},
        {1, -1},  {1, 0},  {1, 1}
    };

    public King(PieceColor color) {
        super(color, PieceType.KING);
    }

    @Override
    public List<Position> getPseudoLegalMoves(Board board, Position currentPos) {
        List<Position> moves = new ArrayList<>();
        for (int[] delta : DELTAS) {
            Position target = currentPos.offset(delta[0], delta[1]);
            if (target != null) {
                Piece dest = board.getPiece(target);
                if (dest == null || dest.getColor() != this.color) {
                    moves.add(target);
                }
            }
        }

        // Castling checks (Kingside and Queenside)
        if (!this.hasMoved && !board.isKingInCheck(this.color)) {
            int row = currentPos.row();
            // Kingside (e1->g1 or e8->g8)
            Piece kingsideRook = board.getPiece(Position.of(row, 7));
            if (kingsideRook instanceof Rook && !kingsideRook.hasMoved()) {
                if (board.getPiece(Position.of(row, 5)) == null && board.getPiece(Position.of(row, 6)) == null) {
                    if (!board.isSquareAttacked(Position.of(row, 5), this.color.getOpposite()) &&
                        !board.isSquareAttacked(Position.of(row, 6), this.color.getOpposite())) {
                        moves.add(Position.of(row, 6));
                    }
                }
            }

            // Queenside (e1->c1 or e8->c8)
            Piece queensideRook = board.getPiece(Position.of(row, 0));
            if (queensideRook instanceof Rook && !queensideRook.hasMoved()) {
                if (board.getPiece(Position.of(row, 1)) == null &&
                    board.getPiece(Position.of(row, 2)) == null &&
                    board.getPiece(Position.of(row, 3)) == null) {
                    if (!board.isSquareAttacked(Position.of(row, 2), this.color.getOpposite()) &&
                        !board.isSquareAttacked(Position.of(row, 3), this.color.getOpposite())) {
                        moves.add(Position.of(row, 2));
                    }
                }
            }
        }

        return moves;
    }
}
