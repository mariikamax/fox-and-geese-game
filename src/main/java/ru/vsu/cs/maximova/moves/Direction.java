package ru.vsu.cs.maximova.moves;

/**
 * Перечисление направлений движения на доске.
 * Определяет возможные направления хода фигур.
 */
public enum Direction {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP_LEFT(-1, 1),
    UP_RIGHT(1, 1),
    DOWN_LEFT(-1, -1),
    DOWN_RIGHT(1, -1);

    private final int dx;
    private final int dy;

    /**
     * Создает новое направление.
     *
     * @param dx изменение по оси X
     * @param dy изменение по оси Y
     */
    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
