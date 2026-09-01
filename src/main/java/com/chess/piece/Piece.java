package com.chess.piece;

import com.chess.enums.PieceColor;
import com.chess.enums.PieceType;
import com.chess.model.Board;
import com.chess.model.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    protected final PieceColor color;
    protected final PieceType type;
    protected boolean hasMoved;

    public Piece(PieceColor color, PieceType type) {
        this.color = color;
        this.type = type;
        this.hasMoved = false;
    }

    public PieceColor getColor() {
        return color;
    }

    public PieceType getType() {
        return type;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public abstract List<Position> getPseudoLegalMoves(Board board, Position currentPos);

    protected List<Position> getLinearRayMoves(Board board, Position start, int rowDir, int colDir) {
        List<Position> moves = new ArrayList<>();
        int currRow = start.row() + rowDir;
        int currCol = start.col() + colDir;

        while (Position.isValid(currRow, currCol)) {
            Position target = Position.of(currRow, currCol);
            Piece targetPiece = board.getPiece(target);

            if (targetPiece == null) {
                moves.add(target);
            } else {
                if (targetPiece.getColor() != this.color) {
                    moves.add(target);
                }
                break;
            }
            currRow += rowDir;
            currCol += colDir;
        }
        return moves;
    }

    @Override
    public String toString() {
        return color.name().charAt(0) + "_" + type.name();
    }
}
