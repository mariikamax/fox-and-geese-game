package ru.vsu.cs.maximova.players;

import ru.vsu.cs.maximova.board.Board;
import ru.vsu.cs.maximova.moves.Move;
import ru.vsu.cs.maximova.pieces.PieceType;

/**
 * Абстрактный класс игрока.
 * Определяет общий интерфейс для всех типов игроков.
 */
public abstract class Player {
    /**
     * Тип фигур, которыми управляет игрок
     */
    protected final PieceType pieceType;

    public Player(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    /**
     * Выбирает и возвращает ход для выполнения.
     *
     * @param board текущее состояние доски
     * @return выбранный ход
     */
    public abstract Move makeMove(Board board);

    /**
     * Возвращает тип фигур игрока.
     *
     * @return тип фигур (FOX или GOOSE)
     */
    public abstract boolean isHuman();

    public PieceType getPieceType() {
        return pieceType;
    }
}
