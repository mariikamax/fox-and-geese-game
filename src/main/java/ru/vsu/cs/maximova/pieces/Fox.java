package ru.vsu.cs.maximova.pieces;

import ru.vsu.cs.maximova.board.Position;

/**
 * Класс фигуры лисы.
 * Наследует базовый класс Piece.
 */
public class Fox extends Piece {

    /**
     * Создает новую лису.
     *
     * @param position начальная позиция лисы
     */
    public Fox(Position position) {
        super(position, PieceType.FOX);
    }
}
