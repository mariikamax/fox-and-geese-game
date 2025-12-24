package ru.vsu.cs.maximova.pieces;

import ru.vsu.cs.maximova.board.Position;

/**
 * Класс фигуры гуся.
 * Наследует базовый класс Piece.
 */
public class Goose extends Piece {

    /**
     * Создает нового гуся.
     *
     * @param position начальная позиция гуся
     */
    public Goose(Position position) {
        super(position, PieceType.GOOSE);
    }
}
