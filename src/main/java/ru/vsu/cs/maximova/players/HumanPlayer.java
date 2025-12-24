package ru.vsu.cs.maximova.players;

import ru.vsu.cs.maximova.board.Board;
import ru.vsu.cs.maximova.moves.Move;
import ru.vsu.cs.maximova.pieces.PieceType;

/**
 * Класс игрока-человека.
 * Представляет пользователя, управляющего фигурами через графический интерфейс.
 */
public class HumanPlayer extends Player {

    /**
     * Создает нового игрока-человека.
     *
     * @param pieceType тип фигур, которыми управляет игрок
     */
    public HumanPlayer(PieceType pieceType) {
        super(pieceType);
    }

    /**
     * Метод не используется для человека, так как ход обрабатывается через UI.
     *
     * @param board текущая доска (не используется)
     * @return всегда null
     */
    @Override
    public Move makeMove(Board board) {
        return null;
    }

    /**
     * Проверяет, является ли игрок человеком.
     *
     * @return всегда true для HumanPlayer
     */
    @Override
    public boolean isHuman() {
        return true;
    }
}