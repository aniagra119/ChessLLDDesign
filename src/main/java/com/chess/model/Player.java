package com.chess.model;

import com.chess.enums.PieceColor;
import com.chess.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final PieceColor color;
    private final List<Piece> capturedPieces = new ArrayList<>();

    public Player(String name, PieceColor color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public PieceColor getColor() {
        return color;
    }

    public List<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    public void addCapturedPiece(Piece piece) {
        if (piece != null) {
            capturedPieces.add(piece);
        }
    }
}
