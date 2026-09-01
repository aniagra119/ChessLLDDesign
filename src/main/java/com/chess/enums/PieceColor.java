package com.chess.enums;

public enum PieceColor {
    WHITE,
    BLACK;

    public PieceColor getOpposite() {
        return this == WHITE ? BLACK : WHITE;
    }
}
