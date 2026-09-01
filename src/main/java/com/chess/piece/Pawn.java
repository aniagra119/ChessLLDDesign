package com.chess.piece;

import com.chess.enums.PieceColor;
import com.chess.enums.PieceType;
import com.chess.model.Board;
import com.chess.model.Position;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(PieceColor color) {
        super(color, PieceType.PAWN);
    }

    @Override
    public List<Position> getPseudoLegalMoves(Board board, Position currentPos) {
        List<Position> moves = new ArrayList<>();
        int forward = (color == PieceColor.WHITE) ? 1 : -1;
        int startRow = (color == PieceColor.WHITE) ? 1 : 6;

        // 1. Single-step forward
        Position oneStep = currentPos.offset(forward, 0);
        if (oneStep != null && board.getPiece(oneStep) == null) {
            moves.add(oneStep);

            // 2. Double-step forward from starting rank
            if (currentPos.row() == startRow) {
                Position twoStep = currentPos.offset(2 * forward, 0);
                if (twoStep != null && board.getPiece(twoStep) == null) {
                    moves.add(twoStep);
                }
            }
        }

        // 3. Diagonal captures (left and right)
        int[] captureCols = {-1, 1};
        for (int colOffset : captureCols) {
            Position diag = currentPos.offset(forward, colOffset);
            if (diag != null) {
                Piece target = board.getPiece(diag);
                if (target != null && target.getColor() != this.color) {
                    moves.add(diag);
                } else if (target == null && board.isEnPassantEligible(currentPos, diag)) {
                    // En Passant eligible target square
                    moves.add(diag);
                }
            }
        }

        return moves;
    }
}
