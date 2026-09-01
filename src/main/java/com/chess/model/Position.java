package com.chess.model;

import java.util.Objects;

public record Position(int row, int col) {

    public Position {
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            throw new IllegalArgumentException("Position coordinates out of bounds: [" + row + ", " + col + "]");
        }
    }

    public static Position of(int row, int col) {
        return new Position(row, col);
    }

    public static Position of(String algebraic) {
        if (algebraic == null || algebraic.length() != 2) {
            throw new IllegalArgumentException("Invalid algebraic notation: " + algebraic);
        }
        char file = Character.toLowerCase(algebraic.charAt(0));
        char rank = algebraic.charAt(1);

        int col = file - 'a';
        int row = rank - '1';
        return new Position(row, col);
    }

    public static boolean isValid(int row, int col) {
        return row >= 0 && row <= 7 && col >= 0 && col <= 7;
    }

    public Position offset(int rowDelta, int colDelta) {
        int newRow = this.row + rowDelta;
        int newCol = this.col + colDelta;
        if (!isValid(newRow, newCol)) {
            return null;
        }
        return new Position(newRow, newCol);
    }

    public String toAlgebraic() {
        char file = (char) ('a' + col);
        char rank = (char) ('1' + row);
        return "" + file + rank;
    }

    @Override
    public String toString() {
        return toAlgebraic();
    }
}
