package com.chess.command;

import com.chess.model.Board;
import com.chess.model.Position;
import com.chess.piece.Piece;

public class PromotionMove extends Move {
    private final Piece promotedPiece;

    public PromotionMove(Position start, Position end, Piece pawn, Piece capturedPiece, Piece promotedPiece) {
        super(start, end, pawn, capturedPiece);
        this.promotedPiece = promotedPiece;
    }

    public Piece getPromotedPiece() {
        return promotedPiece;
    }

    @Override
    public void execute(Board board) {
        board.clearPiece(start);
        board.setPiece(end, promotedPiece);
        promotedPiece.setMoved(true);
        board.setLastMove(this);
    }

    @Override
    public void undo(Board board) {
        board.setPiece(start, movedPiece);
        board.setPiece(end, capturedPiece);
        movedPiece.setMoved(wasMovedBefore);
    }
}
