package com.chess.game;

import com.chess.enums.PieceColor;
import com.chess.model.Board;
import com.chess.model.Player;
import com.chess.model.Position;
import com.chess.piece.Piece;

public class GameBuilder {
    private String whitePlayerName = "White";
    private String blackPlayerName = "Black";
    private Board board = new Board();
    private boolean standardSetup = true;

    public GameBuilder withWhitePlayer(String name) {
        this.whitePlayerName = name;
        return this;
    }

    public GameBuilder withBlackPlayer(String name) {
        this.blackPlayerName = name;
        return this;
    }

    public GameBuilder withCustomBoard() {
        this.standardSetup = false;
        return this;
    }

    public GameBuilder withPiece(Position pos, Piece piece) {
        this.board.setPiece(pos, piece);
        return this;
    }

    public ChessGame build() {
        if (standardSetup) {
            board.initializeStandardBoard();
        }
        Player white = new Player(whitePlayerName, PieceColor.WHITE);
        Player black = new Player(blackPlayerName, PieceColor.BLACK);
        return new ChessGame(board, white, black);
    }
}
