package ru.vsu.cs.maximova.game;

/**
 * Класс конфигурации игры.
 * Содержит параметры для настройки игрового процесса.
 */
public class GameConfig {
    /**
     * Ширина игровой доски
     */
    private int boardWidth = 9;
    /**
     * Высота игровой доски
     */
    private int boardHeight = 9;
    /**
     * Количество гусей на доске
     */
    private int geeseCount = 13;
    /**
     * Количество лис на доске
     */
    private int foxesCount = 1;
    /**
     * Таймаут игры в минутах
     */
    private int timeoutMinutes = 5;
    /**
     * Максимальное количество ходов без захвата
     */
    private int maxMovesWithoutCapture = 50;
    /**
     * Время размышления AI в миллисекундах
     */
    private int aiThinkingTimeMs = 1000;


    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public int getGeeseCount() {
        return geeseCount;
    }

    public int getFoxesCount() {
        return foxesCount;
    }

    public int getTimeoutMinutes() {
        return timeoutMinutes;
    }

    public int getMaxMovesWithoutCapture() {
        return maxMovesWithoutCapture;
    }

    public int getAiThinkingTimeMs() {
        return aiThinkingTimeMs;
    }

    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }

    public void setGeeseCount(int geeseCount) {
        this.geeseCount = geeseCount;
    }

    public void setFoxesCount(int foxesCount) {
        this.foxesCount = foxesCount;
    }

    public void setTimeoutMinutes(int timeoutMinutes) {
        this.timeoutMinutes = timeoutMinutes;
    }

    public void setMaxMovesWithoutCapture(int maxMovesWithoutCapture) {
        this.maxMovesWithoutCapture = maxMovesWithoutCapture;
    }

    public void setAiThinkingTimeMs(int aiThinkingTimeMs) {
        this.aiThinkingTimeMs = aiThinkingTimeMs;
    }

    /**
     * Строитель для создания конфигурации игры.
     * Позволяет гибко настраивать параметры через цепочку вызовов.
     */

    public static class Builder {
        private final GameConfig config;

        public Builder() {
            this.config = new GameConfig();
        }

        public Builder boardWidth(int width) {
            config.setBoardWidth(width);
            return this;
        }

        public Builder boardHeight(int height) {
            config.setBoardHeight(height);
            return this;
        }

        public Builder geeseCount(int count) {
            config.setGeeseCount(count);
            return this;
        }

        public Builder foxesCount(int count) {
            config.setFoxesCount(count);
            return this;
        }

        public Builder timeoutMinutes(int minutes) {
            config.setTimeoutMinutes(minutes);
            return this;
        }

        public Builder maxMovesWithoutCapture(int moves) {
            config.setMaxMovesWithoutCapture(moves);
            return this;
        }

        public Builder aiThinkingTimeMs(int ms) {
            config.setAiThinkingTimeMs(ms);
            return this;
        }

        public GameConfig build() {
            return config;
        }
    }

    public static GameConfig getDefault() {
        return new GameConfig();
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Возвращает строковое представление конфигурации.
     *
     * @return строковое описание всех параметров конфигурации
     */
    @Override
    public String toString() {
        return "GameConfig{" +
                "boardWidth=" + boardWidth +
                ", boardHeight=" + boardHeight +
                ", geeseCount=" + geeseCount +
                ", foxesCount=" + foxesCount +
                ", timeoutMinutes=" + timeoutMinutes +
                ", maxMovesWithoutCapture=" + maxMovesWithoutCapture +
                ", aiThinkingTimeMs=" + aiThinkingTimeMs +
                '}';
    }
}