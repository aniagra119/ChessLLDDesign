package com.chess.state;

import com.chess.command.Move;
import com.chess.enums.GameStatus;
import com.chess.enums.PieceColor;
import com.chess.model.Board;
import com.chess.validator.MoveValidator;

import java.util.List;

public class GameStateEvaluator {

    public static GameStatus evaluate(Board board, PieceColor activeColor) {
        boolean inCheck = board.isKingInCheck(activeColor);
        List<Move> legalMoves = MoveValidator.getAllLegalMoves(board, activeColor);

        if (legalMoves.isEmpty()) {
            if (inCheck) {
                return GameStatus.CHECKMATE;
            } else {
                return GameStatus.STALEMATE;
            }
        }

        if (inCheck) {
            return GameStatus.CHECK;
        }

        return GameStatus.IN_PROGRESS;
    }
}
