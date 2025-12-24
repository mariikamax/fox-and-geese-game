package ru.vsu.cs.maximova.board;

/**
 * Класс для представления позиции на игровой доске.
 * Хранит координаты x и y.
 */
public class Position {
    private final int x;
    private final int y;

    /**
     * Создает новую позицию.
     *
     * @param x координата X
     * @param y координата Y
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Проверяет равенство позиций.
     * Две позиции равны, если их координаты совпадают.
     *
     * @param obj объект для сравнения
     * @return true если позиции равны, иначе false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }

    /**
     * Возвращает хеш-код позиции.
     *
     * @return хеш-код, вычисленный на основе координат
     */
    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    /**
     * Возвращает строковое представление позиции.
     *
     * @return строка в формате "(x,y)"
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}