package ru.vsu.cs.maximova.moves;

import ru.vsu.cs.maximova.board.Board;
import ru.vsu.cs.maximova.board.Position;
import ru.vsu.cs.maximova.pieces.Piece;
import ru.vsu.cs.maximova.pieces.PieceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для проверки допустимости ходов.
 * Определяет правила движения фигур и проверяет корректность ходов.
 */
public class MoveValidator {
    private final Board board;

    public MoveValidator(Board board) {
        this.board = board;
    }

    /**
     * Возвращает все допустимые ходы для указанной фигуры.
     *
     * @param piece фигура, для которой нужно получить ходы
     * @return список допустимых ходов
     */
    public List<Move> getValidMoves(Piece piece) {
        List<Move> validMoves = new ArrayList<>();
        Position current = piece.getPosition();

        if (piece.getType() == PieceType.FOX) {
            addFoxRegularMoves(validMoves, piece, current);
            addFoxCaptureMoves(validMoves, piece, current);
        } else {
            addGooseMoves(validMoves, piece, current);
        }

        return validMoves;
    }

    /**
     * Добавляет обычные ходы лисы в список.
     * Лиса может ходить на одну клетку в любом направлении.
     *
     * @param moves   список для добавления ходов
     * @param fox     фигура лисы
     * @param current текущая позиция лисы
     */
    private void addFoxRegularMoves(List<Move> moves, Piece fox, Position current) {
        for (Direction dir : Direction.values()) {
            Position newPos = new Position(
                    current.getX() + dir.getDx(),
                    current.getY() + dir.getDy()
            );

            if (board.isValidPosition(newPos) && board.getPieceAt(newPos) == null) {
                moves.add(new Move(fox, current, newPos, false));
            }
        }
    }

    /**
     * Добавляет ходы-захваты лисы в список.
     * Лиса может перепрыгивать через гусей при наличии свободной клетки за ним.
     *
     * @param moves   список для добавления ходов
     * @param fox     фигура лисы
     * @param current текущая позиция лисы
     */
    private void addFoxCaptureMoves(List<Move> moves, Piece fox, Position current) {
        for (Direction dir : Direction.values()) {
            Position jumpedPos = new Position(
                    current.getX() + dir.getDx(),
                    current.getY() + dir.getDy()
            );

            Position landingPos = new Position(
                    current.getX() + 2 * dir.getDx(),
                    current.getY() + 2 * dir.getDy()
            );

            if (isValidCapture(current, jumpedPos, landingPos)) {
                moves.add(new Move(fox, current, landingPos, true));
            }
        }
    }

    /**
     * Проверяет, является ли захват допустимым.
     *
     * @param from    позиция лисы
     * @param jumped  позиция перепрыгиваемого гуся
     * @param landing позиция приземления
     * @return true если захват допустим, иначе false
     */
    private boolean isValidCapture(Position from, Position jumped, Position landing) {
        if (!board.isValidPosition(jumped) || !board.isValidPosition(landing)) {
            return false;
        }

        Piece jumpedPiece = board.getPieceAt(jumped);
        Piece landingPiece = board.getPieceAt(landing);

        return jumpedPiece != null &&
                jumpedPiece.getType() == PieceType.GOOSE &&
                landingPiece == null;
    }

    /**
     * Добавляет ходы гусей в список.
     * Гуси могут ходить только вперед (вверх) по диагонали.
     *
     * @param moves   список для добавления ходов
     * @param goose   фигура гуся
     * @param current текущая позиция гуся
     */
    private void addGooseMoves(List<Move> moves, Piece goose, Position current) {
        for (Direction dir : new Direction[]{Direction.UP, Direction.UP_LEFT, Direction.UP_RIGHT}) {
            Position newPos = new Position(
                    current.getX() + dir.getDx(),
                    current.getY() + dir.getDy()
            );

            if (board.isValidPosition(newPos) && board.getPieceAt(newPos) == null) {
                moves.add(new Move(goose, current, newPos, false));
            }
        }
    }

    /**
     * Проверяет, является ли ход допустимым.
     *
     * @param move проверяемый ход
     * @return true если ход допустим, иначе false
     */
    public boolean isValidMove(Move move) {
        List<Move> validMoves = getValidMoves(move.getPiece());

        return validMoves.stream()
                .anyMatch(validMove ->
                        validMove.getTo().equals(move.getTo()) &&
                                validMove.isCapture() == move.isCapture());
    }
}
