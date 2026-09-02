package com.chess.command;

import com.chess.model.Board;
import com.chess.model.Position;
import com.chess.piece.Piece;
import com.chess.piece.Rook;

public class CastlingMove extends Move {
    private final Position rookStart;
    private final Position rookEnd;
    private final Rook rook;
    private final boolean wasRookMovedBefore;

    public CastlingMove(Position kingStart, Position kingEnd, Piece king, Position rookStart, Position rookEnd, Rook rook) {
        super(kingStart, kingEnd, king, null);
        this.rookStart = rookStart;
        this.rookEnd = rookEnd;
        this.rook = rook;
        this.wasRookMovedBefore = rook.hasMoved();
    }

    @Override
    public void execute(Board board) {
        // Move king
        board.clearPiece(start);
        board.setPiece(end, movedPiece);
        movedPiece.setMoved(true);

        // Move rook
        board.clearPiece(rookStart);
        board.setPiece(rookEnd, rook);
        rook.setMoved(true);

        board.setLastMove(this);
    }

    @Override
    public void undo(Board board) {
        board.setPiece(start, movedPiece);
        board.clearPiece(end);
        movedPiece.setMoved(wasMovedBefore);

        board.setPiece(rookStart, rook);
        board.clearPiece(rookEnd);
        rook.setMoved(wasRookMovedBefore);
    }
}
