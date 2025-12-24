package ru.vsu.cs.maximova;

import ru.vsu.cs.maximova.game.Game;
import ru.vsu.cs.maximova.game.GameConfig;
import ru.vsu.cs.maximova.ui.GraphicalUI;

import javax.swing.*;
import java.util.Scanner;

/**
 * Главный класс приложения.
 * Точка входа в программу, запрашивает режим игры через консоль.
 */

public class Main {
    /**
     * Точка входа в приложение.
     * Запрашивает режим игры и сторону через консольный ввод.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=".repeat(60));
        System.out.println("ИГРА 'ЛИСА И ГУСИ'");
        System.out.println("=".repeat(60));
        System.out.println("Доступные режимы: ");
        System.out.println("1. Интерактивный режим (Игрок vs AI). Введите 'Хочу играть! ");
        System.out.println("2. Режим наблюдателя (AI vs AI). Введите 'Я наблюдатель'");
        System.out.println("=".repeat(60));
        System.out.print("Выберите режим игры: ");

        String input = scanner.nextLine().trim().toLowerCase();

        while (!input.equals("хочу играть!") && !input.equals("я наблюдатель")) {
            System.out.println("Неверный ввод! Пожалуйста, введите:");
            System.out.println("  'Хочу играть!' - для интерактивного режима");
            System.out.println("  'Я наблюдатель' - для режима наблюдателя");
            System.out.print("Выберите режим игры: ");
            input = scanner.nextLine().trim().toLowerCase();
        }

        String playerSide = null;
        if (input.equals("хочу играть!")) {
            System.out.println("=".repeat(60));
            System.out.println("Выберите сторону за которую будете играть:");
            System.out.println("1. За лису (введите 'за лису')");
            System.out.println("2. За гусей (введите 'за гусей')");
            System.out.println("=".repeat(60));
            System.out.print("Ваш выбор: ");

            playerSide = scanner.nextLine().trim().toLowerCase();

            while (!playerSide.equals("за лису") && !playerSide.equals("за гусей")) {
                System.out.println("Неверный ввод! Пожалуйста, введите:");
                System.out.println("  'за лису' - чтобы играть за лису");
                System.out.println("  'за гусей' - чтобы играть за гусей");
                System.out.print("Ваш выбор: ");
                playerSide = scanner.nextLine().trim().toLowerCase();
            }
        }

        scanner.close();

        GameConfig config = GameConfig.getDefault();

        final String finalInput = input;
        final String finalPlayerSide = playerSide;

        SwingUtilities.invokeLater(() -> {
            if (finalInput.equals("я наблюдатель")) {
                launchObserverMode(config);
            } else {
                launchPlayerMode(config, finalPlayerSide);
            }
        });
    }

    /**
     * Запускает игру в режиме наблюдателя (AI против AI).
     *
     * @param config конфигурация игры
     */
    private static void launchObserverMode(GameConfig config) {
        System.out.println("Запуск игры в режиме наблюдателя (AI против AI)...");

        Game game = new Game(config, false, false);
        GraphicalUI ui = new GraphicalUI(game);
        game.setUI(ui);

        Timer timer = new Timer(1000, e -> {
            game.start();
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Запускает игру в интерактивном режиме (человек против AI).
     *
     * @param config конфигурация игры
     * @param playerSide сторона, за которую играет человек ("за лису" или "за гусей")
     */
    private static void launchPlayerMode(GameConfig config, String playerSide) {
        boolean playAsFox = playerSide.equals("за лису");

        if (playAsFox) {
            System.out.println("Запуск игры: Вы играете за лису, AI играет за гусей...");
        } else {
            System.out.println("Запуск игры: Вы играете за гусей, AI играет за лису...");
        }

        Game game = new Game(config, true, playAsFox);
        GraphicalUI ui = new GraphicalUI(game);
        game.setUI(ui);
        game.start();
    }
}