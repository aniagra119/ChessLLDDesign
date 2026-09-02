package com.chess.command;

import com.chess.model.Board;
import com.chess.model.Position;
import com.chess.piece.Piece;

public abstract class Move {
    protected final Position start;
    protected final Position end;
    protected final Piece movedPiece;
    protected final Piece capturedPiece;
    protected boolean wasMovedBefore;

    public Move(Position start, Position end, Piece movedPiece, Piece capturedPiece) {
        this.start = start;
        this.end = end;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
        this.wasMovedBefore = (movedPiece != null) && movedPiece.hasMoved();
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public abstract void execute(Board board);

    public abstract void undo(Board board);

    @Override
    public String toString() {
        return start.toAlgebraic() + (capturedPiece != null ? "x" : "-") + end.toAlgebraic();
    }
}
