package ru.vsu.cs.maximova.board;


import ru.vsu.cs.maximova.pieces.Piece;
import ru.vsu.cs.maximova.pieces.Fox;
import ru.vsu.cs.maximova.pieces.Goose;
import ru.vsu.cs.maximova.pieces.PieceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс игровой доски.
 * Управляет расположением фигур и состоянием игрового поля.
 */
public class Board {
    private final int width = 9;
    private final int height = 9;
    private Piece[][] grid;
    private List<Piece> geese;
    private List<Piece> foxes;

    /**
     * Создает новую доску и инициализирует начальную расстановку фигур.
     */
    public Board() {
        this.grid = new Piece[width][height];
        this.geese = new ArrayList<>();
        this.foxes = new ArrayList<>();
        initializeBoard();
    }

    /**
     * Инициализирует начальную расстановку фигур на доске.
     * Расставляет 13 гусей в верхней части доски и 1 лису в центре нижней части.
     */
    private void initializeBoard() {
        int geeseCount = 0;
        for (int y = 0; y < 3 && geeseCount < 13; y++) {
            for (int x = 0; x < width && geeseCount < 13; x++) {
                if ((x + y) % 2 == 0) {
                    Position pos = new Position(x, y);
                    Goose goose = new Goose(pos);
                    placePiece(goose, pos);
                    geese.add(goose);
                    geeseCount++;
                }
            }
        }
        Position foxPos = new Position(4, 8);
        Fox fox = new Fox(foxPos);
        placePiece(fox, foxPos);
        foxes.add(fox);
    }

    /**
     * Проверяет, является ли позиция допустимой на доске.
     * Позиция допустима, если находится в пределах доски и на черной клетке.
     *
     * @param pos проверяемая позиция
     * @return true если позиция допустима, иначе false
     */
    public boolean isValidPosition(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        return x >= 0 && x < width && y >= 0 && y < height && (x + y) % 2 == 0;
    }

    /**
     * Возвращает фигуру, находящуюся на указанной позиции.
     *
     * @param pos позиция для проверки
     * @return фигура на позиции или null, если позиция пуста или недопустима
     */
    public Piece getPieceAt(Position pos) {
        if (!isValidPosition(pos)) return null;
        return grid[pos.getX()][pos.getY()];
    }

    /**
     * Размещает фигуру на указанной позиции.
     *
     * @param piece фигура для размещения
     * @param pos позиция для размещения
     */
    public void placePiece(Piece piece, Position pos) {
        if (isValidPosition(pos)) {
            grid[pos.getX()][pos.getY()] = piece;
            piece.setPosition(pos);
        }
    }

    /**
     * Удаляет фигуру с указанной позиции.
     *
     * @param pos позиция, с которой нужно удалить фигуру
     */
    public void removePiece(Position pos) {
        if (!isValidPosition(pos)) {
            return;
        }

        Piece piece = grid[pos.getX()][pos.getY()];
        if (piece == null) {
            return;
        }

        grid[pos.getX()][pos.getY()] = null;

        if (piece.getType() == PieceType.GOOSE) {
            geese.remove(piece);
        } else if (piece.getType() == PieceType.FOX) {
            foxes.remove(piece);
        }
    }

    public Piece[][] getGrid() {
        return grid;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Piece> getGeese() {
        return geese;
    }

    public List<Piece> getFoxes() {
        return foxes;
    }

    /**
     * Отображает текстовое представление доски в консоли.
     * Используется для отладки.
     */
    public void display() {
        System.out.println("Доска (9x9):");
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                Position pos = new Position(x, y);
                if (!isValidPosition(pos)) {
                    System.out.print("   ");
                } else {
                    Piece piece = getPieceAt(pos);
                    if (piece == null) {
                        System.out.print(" . ");
                    } else if (piece.getType() == PieceType.FOX) {
                        System.out.print(" F ");
                    } else {
                        System.out.print(" G ");
                    }
                }
            }
            System.out.println();
        }
    }
}
