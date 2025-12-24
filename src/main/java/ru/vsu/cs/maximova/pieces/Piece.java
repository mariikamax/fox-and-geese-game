package ru.vsu.cs.maximova.pieces;

import ru.vsu.cs.maximova.board.Position;

/**
 * Абстрактный класс игровой фигуры.
 * Содержит общие свойства и методы для всех типов фигур.
 */
public abstract class Piece {
    protected Position position;
    protected final PieceType type;

    /**
     * Создает новую фигуру.
     *
     * @param position начальная позиция фигуры
     * @param type тип фигуры
     */
    public Piece(Position position, PieceType type) {
        this.position = position;
        this.type = type;
    }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
    public PieceType getType() { return type; }
}