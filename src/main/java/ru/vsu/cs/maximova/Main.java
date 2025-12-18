package ru.vsu.cs.maximova;

import ru.vsu.cs.maximova.game.Game;
import ru.vsu.cs.maximova.game.GameConfig;
import ru.vsu.cs.maximova.ui.GraphicalUI;

import javax.swing.*;

/**
 * Главный класс приложения.
 * Точка входа в программу, обрабатывает аргументы командной строки.
 */

public class Main {
    /**
     * Точка входа в приложение.
     * Определяет режим игры на основе аргументов командной строки.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        String input = args.length > 0 ? String.join(" ", args).toLowerCase() : "";
        GameConfig config = GameConfig.getDefault();

        SwingUtilities.invokeLater(() -> {
            if (input.contains("я наблюдатель") || input.contains("наблюдатель")) {
                launchObserverMode(config);
            } else {
                launchPlayerMode(config);
            }
        });
    }

    /**
     * Запускает игру в режиме наблюдателя (AI против AI).
     *
     * @param config конфигурация игры
     */
    private static void launchObserverMode(GameConfig config) {
        System.out.println("Игра началась в режиме наблюдателя (AI vs AI)");

        Game game = new Game(config, false);
        GraphicalUI ui = new GraphicalUI(game);
        game.setUI(ui);

        Timer timer = new Timer(1000, e -> {
            game.start();
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Запускает игру в режиме игрока (человек против AI).
     *
     * @param config конфигурация игры
     */
    private static void launchPlayerMode(GameConfig config) {
        System.out.println("Игра началась в режиме игрока (Вы vs AI)");

        Game game = new Game(config, true);
        GraphicalUI ui = new GraphicalUI(game);
        game.setUI(ui);
        game.start();
    }
}