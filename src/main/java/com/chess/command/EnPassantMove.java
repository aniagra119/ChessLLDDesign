package com.chess.command;

import com.chess.model.Board;
import com.chess.model.Position;
import com.chess.piece.Piece;

public class EnPassantMove extends Move {
    private final Position capturedPawnPos;

    public EnPassantMove(Position start, Position end, Piece pawn, Piece capturedPawn, Position capturedPawnPos) {
        super(start, end, pawn, capturedPawn);
        this.capturedPawnPos = capturedPawnPos;
    }

    @Override
    public void execute(Board board) {
        board.clearPiece(start);
        board.setPiece(end, movedPiece);
        board.clearPiece(capturedPawnPos);
        movedPiece.setMoved(true);
        board.setLastMove(this);
    }

    @Override
    public void undo(Board board) {
        board.setPiece(start, movedPiece);
        board.clearPiece(end);
        board.setPiece(capturedPawnPos, capturedPiece);
        movedPiece.setMoved(wasMovedBefore);
    }
}
