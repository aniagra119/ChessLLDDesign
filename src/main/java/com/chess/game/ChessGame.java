package com.chess.game;

import com.chess.command.Move;
import com.chess.enums.GameStatus;
import com.chess.enums.PieceColor;
import com.chess.enums.PieceType;
import com.chess.model.Board;
import com.chess.model.Player;
import com.chess.model.Position;
import com.chess.state.GameStateEvaluator;
import com.chess.validator.MoveValidator;

import java.util.ArrayList;
import java.util.List;

public class ChessGame {
    private final Board board;
    private final Player whitePlayer;
    private final Player blackPlayer;
    private Player currentTurn;
    private GameStatus status;
    private final List<Move> moveHistory = new ArrayList<>();

    public ChessGame(Board board, Player whitePlayer, Player blackPlayer) {
        this.board = board;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.currentTurn = whitePlayer;
        this.status = GameStatus.IN_PROGRESS;
    }

    public Board getBoard() {
        return board;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public Player getCurrentTurn() {
        return currentTurn;
    }

    public GameStatus getStatus() {
        return status;
    }

    public List<Move> getMoveHistory() {
        return moveHistory;
    }

    public boolean makeMove(String startAlg, String endAlg) {
        return makeMove(Position.of(startAlg), Position.of(endAlg), null);
    }

    public boolean makeMove(String startAlg, String endAlg, PieceType promoteTo) {
        return makeMove(Position.of(startAlg), Position.of(endAlg), promoteTo);
    }

    public boolean makeMove(Position start, Position end) {
        return makeMove(start, end, null);
    }

    public boolean makeMove(Position start, Position end, PieceType promoteTo) {
        if (status == GameStatus.CHECKMATE || status == GameStatus.STALEMATE || status == GameStatus.DRAW) {
            return false;
        }

        Move move = MoveValidator.createMove(board, start, end, promoteTo);
        if (!MoveValidator.isLegalMove(board, move, currentTurn.getColor())) {
            return false;
        }

        move.execute(board);
        moveHistory.add(move);
        if (move.getCapturedPiece() != null) {
            currentTurn.addCapturedPiece(move.getCapturedPiece());
        }

        // Switch turn
        currentTurn = (currentTurn == whitePlayer) ? blackPlayer : whitePlayer;

        // Evaluate state
        status = GameStateEvaluator.evaluate(board, currentTurn.getColor());
        return true;
    }

    public boolean undoLastMove() {
        if (moveHistory.isEmpty()) {
            return false;
        }

        Move last = moveHistory.remove(moveHistory.size() - 1);
        last.undo(board);

        // Switch turn back
        currentTurn = (currentTurn == whitePlayer) ? blackPlayer : whitePlayer;

        // Restore board last move
        Move prev = moveHistory.isEmpty() ? null : moveHistory.get(moveHistory.size() - 1);
        board.setLastMove(prev);

        status = GameStateEvaluator.evaluate(board, currentTurn.getColor());
        return true;
    }
}
