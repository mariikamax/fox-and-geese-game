package ru.vsu.cs.maximova.moves;

import ru.vsu.cs.maximova.board.Position;
import ru.vsu.cs.maximova.pieces.Piece;

/**
 * Класс, представляющий ход в игре.
 * Содержит информацию о фигуре, начальной и конечной позициях, и типе хода.
 */
public class Move {
    private final Piece piece;
    private final Position from;
    private final Position to;
    private final boolean isCapture;

    /**
     * Создает новый объект хода.
     *
     * @param piece фигура, совершающая ход
     * @param from начальная позиция
     * @param to конечная позиция
     * @param isCapture true если ход является захватом, иначе false
     */
    public Move(Piece piece, Position from, Position to, boolean isCapture) {
        this.piece = piece;
        this.from = from;
        this.to = to;
        this.isCapture = isCapture;
    }

    public Piece getPiece() { return piece; }
    public Position getFrom() { return from; }
    public Position getTo() { return to; }
    public boolean isCapture() { return isCapture; }
}
