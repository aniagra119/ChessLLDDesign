package com.chess.piece;

import com.chess.enums.PieceColor;
import com.chess.enums.PieceType;
import com.chess.model.Board;
import com.chess.model.Position;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    private static final int[][] JUMPS = {
        {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
        {1, -2}, {1, 2}, {2, -1}, {2, 1}
    };

    public Knight(PieceColor color) {
        super(color, PieceType.KNIGHT);
    }

    @Override
    public List<Position> getPseudoLegalMoves(Board board, Position currentPos) {
        List<Position> moves = new ArrayList<>();
        for (int[] jump : JUMPS) {
            Position target = currentPos.offset(jump[0], jump[1]);
            if (target != null) {
                Piece dest = board.getPiece(target);
                if (dest == null || dest.getColor() != this.color) {
                    moves.add(target);
                }
            }
        }
        return moves;
    }
}
