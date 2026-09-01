package com.chess.piece;

import com.chess.enums.PieceColor;
import com.chess.enums.PieceType;
import com.chess.model.Board;
import com.chess.model.Position;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(PieceColor color) {
        super(color, PieceType.QUEEN);
    }

    @Override
    public List<Position> getPseudoLegalMoves(Board board, Position currentPos) {
        List<Position> moves = new ArrayList<>();
        int[][] directions = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1},
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };
        for (int[] dir : directions) {
            moves.addAll(getLinearRayMoves(board, currentPos, dir[0], dir[1]));
        }
        return moves;
    }
}
