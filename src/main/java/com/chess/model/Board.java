package com.chess.model;

import com.chess.command.Move;
import com.chess.enums.PieceColor;
import com.chess.enums.PieceType;
import com.chess.piece.*;

public class Board {
    private final Piece[][] grid = new Piece[8][8];
    private Move lastMove;

    public Board() {
    }

    public Piece getPiece(Position pos) {
        if (pos == null) return null;
        return grid[pos.row()][pos.col()];
    }

    public void setPiece(Position pos, Piece piece) {
        if (pos != null) {
            grid[pos.row()][pos.col()] = piece;
        }
    }

    public void clearPiece(Position pos) {
        if (pos != null) {
            grid[pos.row()][pos.col()] = null;
        }
    }

    public Move getLastMove() {
        return lastMove;
    }

    public void setLastMove(Move lastMove) {
        this.lastMove = lastMove;
    }

    public void initializeStandardBoard() {
        // Clear
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                grid[r][c] = null;
            }
        }

        // Pawns
        for (int c = 0; c < 8; c++) {
            setPiece(Position.of(1, c), new Pawn(PieceColor.WHITE));
            setPiece(Position.of(6, c), new Pawn(PieceColor.BLACK));
        }

        // White Major Pieces (Row 0)
        setPiece(Position.of(0, 0), new Rook(PieceColor.WHITE));
        setPiece(Position.of(0, 1), new Knight(PieceColor.WHITE));
        setPiece(Position.of(0, 2), new Bishop(PieceColor.WHITE));
        setPiece(Position.of(0, 3), new Queen(PieceColor.WHITE));
        setPiece(Position.of(0, 4), new King(PieceColor.WHITE));
        setPiece(Position.of(0, 5), new Bishop(PieceColor.WHITE));
        setPiece(Position.of(0, 6), new Knight(PieceColor.WHITE));
        setPiece(Position.of(0, 7), new Rook(PieceColor.WHITE));

        // Black Major Pieces (Row 7)
        setPiece(Position.of(7, 0), new Rook(PieceColor.BLACK));
        setPiece(Position.of(7, 1), new Knight(PieceColor.BLACK));
        setPiece(Position.of(7, 2), new Bishop(PieceColor.BLACK));
        setPiece(Position.of(7, 3), new Queen(PieceColor.BLACK));
        setPiece(Position.of(7, 4), new King(PieceColor.BLACK));
        setPiece(Position.of(7, 5), new Bishop(PieceColor.BLACK));
        setPiece(Position.of(7, 6), new Knight(PieceColor.BLACK));
        setPiece(Position.of(7, 7), new Rook(PieceColor.BLACK));
    }

    public Position findKing(PieceColor color) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                if (p instanceof King && p.getColor() == color) {
                    return Position.of(r, c);
                }
            }
        }
        return null;
    }

    public boolean isSquareAttacked(Position target, PieceColor attackingColor) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece attacker = grid[r][c];
                if (attacker != null && attacker.getColor() == attackingColor) {
                    Position from = Position.of(r, c);
                    if (attacker instanceof Pawn) {
                        int forward = (attackingColor == PieceColor.WHITE) ? 1 : -1;
                        if (from.row() + forward == target.row() &&
                            (from.col() - 1 == target.col() || from.col() + 1 == target.col())) {
                            return true;
                        }
                    } else if (attacker instanceof King) {
                        if (Math.abs(from.row() - target.row()) <= 1 && Math.abs(from.col() - target.col()) <= 1) {
                            return true;
                        }
                    } else {
                        if (attacker.getPseudoLegalMoves(this, from).contains(target)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isKingInCheck(PieceColor kingColor) {
        Position kingPos = findKing(kingColor);
        if (kingPos == null) {
            return false;
        }
        return isSquareAttacked(kingPos, kingColor.getOpposite());
    }

    public boolean isEnPassantEligible(Position pawnPos, Position targetPos) {
        if (lastMove == null) return false;
        Piece lastPiece = lastMove.getMovedPiece();
        if (lastPiece instanceof Pawn) {
            // Must have moved 2 squares
            if (Math.abs(lastMove.getStart().row() - lastMove.getEnd().row()) == 2) {
                // Must be on the same rank as pawnPos and adjacent file
                if (lastMove.getEnd().row() == pawnPos.row() && Math.abs(lastMove.getEnd().col() - pawnPos.col()) == 1) {
                    // targetPos must be behind the enemy pawn
                    int expectedRow = (pawnPos.row() + (pawnPos.row() == 4 ? 1 : -1));
                    return targetPos.col() == lastMove.getEnd().col() && targetPos.row() == expectedRow;
                }
            }
        }
        return false;
    }
}
