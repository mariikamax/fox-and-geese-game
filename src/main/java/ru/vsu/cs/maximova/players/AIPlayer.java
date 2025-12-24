package ru.vsu.cs.maximova.players;

import ru.vsu.cs.maximova.board.Board;
import ru.vsu.cs.maximova.moves.Move;
import ru.vsu.cs.maximova.moves.MoveValidator;
import ru.vsu.cs.maximova.pieces.PieceType;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Класс AI-игрока.
 * Реализует логику принятия решений для компьютерного игрока.
 */

public class AIPlayer extends Player {
    private final Random random;
    private final int thinkingTimeMs;

    /**
     * Конструктор AI-игрока.
     *
     * @param pieceType      тип фигур, которыми управляет игрок
     * @param thinkingTimeMs время размышления в миллисекундах
     */
    public AIPlayer(PieceType pieceType, int thinkingTimeMs) {
        super(pieceType);
        this.random = new Random();
        this.thinkingTimeMs = thinkingTimeMs;
    }

    /**
     * Выбирает и возвращает ход для выполнения.
     * AI сначала проверяет доступные захваты, затем обычные ходы.
     *
     * @param board текущее состояние доски
     * @return выбранный ход или null, если ходов нет
     */
    @Override
    public Move makeMove(Board board) {
        try {
            Thread.sleep(thinkingTimeMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        MoveValidator validator = new MoveValidator(board);
        List<Move> allMoves = getAllPossibleMoves(board, validator);

        if (allMoves.isEmpty()) {
            return null;
        }

        List<Move> captures = allMoves.stream()
                .filter(Move::isCapture)
                .collect(Collectors.toList());

        if (!captures.isEmpty()) {
            return captures.get(random.nextInt(captures.size()));
        }

        return allMoves.get(random.nextInt(allMoves.size()));
    }

    /**
     * Получает все возможные ходы для фигур текущего игрока.
     *
     * @param board     текущая доска
     * @param validator валидатор ходов
     * @return список всех допустимых ходов
     */
    private List<Move> getAllPossibleMoves(Board board, MoveValidator validator) {
        if (pieceType == PieceType.FOX) {
            return board.getFoxes().stream()
                    .flatMap(fox -> validator.getValidMoves(fox).stream())
                    .collect(Collectors.toList());
        } else {
            return board.getGeese().stream()
                    .flatMap(goose -> validator.getValidMoves(goose).stream())
                    .collect(Collectors.toList());
        }
    }

    /**
     * Проверяет, является ли игрок человеком.
     *
     * @return всегда false для AI-игрока
     */

    @Override
    public boolean isHuman() {
        return false;
    }
}