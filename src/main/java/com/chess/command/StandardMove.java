package com.chess.command;

import com.chess.model.Board;
import com.chess.model.Position;
import com.chess.piece.Piece;

public class StandardMove extends Move {

    public StandardMove(Position start, Position end, Piece movedPiece, Piece capturedPiece) {
        super(start, end, movedPiece, capturedPiece);
    }

    @Override
    public void execute(Board board) {
        board.clearPiece(start);
        board.setPiece(end, movedPiece);
        movedPiece.setMoved(true);
        board.setLastMove(this);
    }

    @Override
    public void undo(Board board) {
        board.setPiece(start, movedPiece);
        board.setPiece(end, capturedPiece);
        movedPiece.setMoved(wasMovedBefore);
    }
}
