package com.chess.validator;

import com.chess.command.*;
import com.chess.enums.PieceColor;
import com.chess.enums.PieceType;
import com.chess.model.Board;
import com.chess.model.Position;
import com.chess.piece.*;

import java.util.ArrayList;
import java.util.List;

public class MoveValidator {

    public static Move createMove(Board board, Position start, Position end, PieceType promoteTo) {
        Piece piece = board.getPiece(start);
        if (piece == null) return null;

        Piece captured = board.getPiece(end);

        // Castling move
        if (piece instanceof King && Math.abs(start.col() - end.col()) == 2) {
            int row = start.row();
            if (end.col() == 6) { // Kingside
                Rook rook = (Rook) board.getPiece(Position.of(row, 7));
                return new CastlingMove(start, end, piece, Position.of(row, 7), Position.of(row, 5), rook);
            } else if (end.col() == 2) { // Queenside
                Rook rook = (Rook) board.getPiece(Position.of(row, 0));
                return new CastlingMove(start, end, piece, Position.of(row, 0), Position.of(row, 3), rook);
            }
        }

        // En Passant move
        if (piece instanceof Pawn && captured == null && start.col() != end.col()) {
            Position capturedPawnPos = Position.of(start.row(), end.col());
            Piece capturedPawn = board.getPiece(capturedPawnPos);
            return new EnPassantMove(start, end, piece, capturedPawn, capturedPawnPos);
        }

        // Promotion move
        if (piece instanceof Pawn && (end.row() == 7 || end.row() == 0)) {
            PieceType promotionType = (promoteTo != null) ? promoteTo : PieceType.QUEEN;
            Piece promoted = switch (promotionType) {
                case ROOK -> new Rook(piece.getColor());
                case BISHOP -> new Bishop(piece.getColor());
                case KNIGHT -> new Knight(piece.getColor());
                default -> new Queen(piece.getColor());
            };
            return new PromotionMove(start, end, piece, captured, promoted);
        }

        return new StandardMove(start, end, piece, captured);
    }

    public static boolean isLegalMove(Board board, Move move, PieceColor activeColor) {
        if (move == null || move.getMovedPiece() == null) {
            return false;
        }
        if (move.getMovedPiece().getColor() != activeColor) {
            return false;
        }

        List<Position> pseudoLegalMoves = move.getMovedPiece().getPseudoLegalMoves(board, move.getStart());
        if (!pseudoLegalMoves.contains(move.getEnd())) {
            return false;
        }

        // Two-phase validation: simulate move, check King safety, then undo
        Move previousLastMove = board.getLastMove();
        move.execute(board);
        boolean kingInCheck = board.isKingInCheck(activeColor);
        move.undo(board);
        board.setLastMove(previousLastMove);

        return !kingInCheck;
    }

    public static List<Move> getAllLegalMoves(Board board, PieceColor color) {
        List<Move> legalMoves = new ArrayList<>();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Position from = Position.of(r, c);
                Piece piece = board.getPiece(from);
                if (piece != null && piece.getColor() == color) {
                    List<Position> targets = piece.getPseudoLegalMoves(board, from);
                    for (Position to : targets) {
                        Move candidate = createMove(board, from, to, PieceType.QUEEN);
                        if (isLegalMove(board, candidate, color)) {
                            legalMoves.add(candidate);
                        }
                    }
                }
            }
        }
        return legalMoves;
    }
}
