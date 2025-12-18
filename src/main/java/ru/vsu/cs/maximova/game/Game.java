package ru.vsu.cs.maximova.game;

import ru.vsu.cs.maximova.board.Board;
import ru.vsu.cs.maximova.board.Position;
import ru.vsu.cs.maximova.moves.Move;
import ru.vsu.cs.maximova.moves.MoveValidator;
import ru.vsu.cs.maximova.players.Player;
import ru.vsu.cs.maximova.players.HumanPlayer;
import ru.vsu.cs.maximova.players.AIPlayer;
import ru.vsu.cs.maximova.pieces.Piece;
import ru.vsu.cs.maximova.pieces.PieceType;
import ru.vsu.cs.maximova.ui.GameUI;

import javax.swing.*;

/**
 * Основной класс игры "Лиса и гуси".
 * Управляет игровым процессом, обработкой ходов и состоянием игры.
 */
public class Game {
    private Board board;
    private MoveValidator validator;
    private Player foxPlayer;
    private Player geesePlayer;
    private Player currentPlayer;
    private GameUI ui;
    private boolean gameOver;
    private int movesWithoutCapture;
    private GameConfig config;

    public Game(GameConfig config, boolean interactive) {
        this.config = config;
        this.board = new Board();
        this.validator = new MoveValidator(board);
        this.movesWithoutCapture = 0;
        this.gameOver = false;

        if (interactive) {
            this.foxPlayer = new HumanPlayer(PieceType.FOX);
            this.geesePlayer = new AIPlayer(PieceType.GOOSE, config.getAiThinkingTimeMs());
        } else {
            this.foxPlayer = new AIPlayer(PieceType.FOX, config.getAiThinkingTimeMs());
            this.geesePlayer = new AIPlayer(PieceType.GOOSE, config.getAiThinkingTimeMs());
        }

        this.currentPlayer = geesePlayer;
    }

    /**
     * Устанавливает пользовательский интерфейс для игры.
     *
     * @param ui объект пользовательского интерфейса
     */
    public void setUI(GameUI ui) {
        this.ui = ui;
    }

    /**
     * Начинает игру.
     * Инициализирует отображение и начинает игровой процесс.
     */
    public void start() {
        if (ui != null) {
            ui.display();
            ui.updateBoard();
            ui.showMessage("Ходят: " +
                    (currentPlayer.getPieceType() == PieceType.GOOSE ? "Гуси" : "Лиса"));
        }

        if (!currentPlayer.isHuman()) {
            makeAIMove();
        }
    }

    /**
     * Обрабатывает ход, сделанный человеком.
     *
     * @param from начальная позиция фигуры
     * @param to   конечная позиция для хода
     */
    public void makeHumanMove(Position from, Position to) {
        if (gameOver || currentPlayer.isHuman() == false) {
            return;
        }

        Piece piece = board.getPieceAt(from);
        if (piece == null || piece.getType() != currentPlayer.getPieceType()) {
            ui.showMessage("Нельзя ходить этой фигурой!");
            return;
        }

        Move move = new Move(piece, from, to, false);
        if (!validator.isValidMove(move)) {
            move = new Move(piece, from, to, true);
            if (!validator.isValidMove(move)) {
                ui.showMessage("Неверный ход!");
                return;
            }
        }

        executeMove(move);

        if (move.isCapture()) {
            movesWithoutCapture = 0;
        } else {
            movesWithoutCapture++;
        }

        checkGameOver();

        if (!gameOver) {
            switchPlayer();

            ui.updateBoard();
            ui.showMessage("Ходят: " +
                    (currentPlayer.getPieceType() == PieceType.GOOSE ? "Гуси" : "Лиса"));

            if (!currentPlayer.isHuman()) {
                new Thread(this::makeAIMove).start();
            }
        }
    }

    /**
     * Выполняет ход, выбранный AI-игроком.
     * Метод запускается в отдельном потоке для избежания блокировки UI.
     */
    private void makeAIMove() {
        try {
            Move move = currentPlayer.makeMove(board);

            if (move != null && !gameOver) {
                SwingUtilities.invokeLater(() -> {
                    try {
                        executeMove(move);

                        if (move.isCapture()) {
                            movesWithoutCapture = 0;
                        } else {
                            movesWithoutCapture++;
                        }

                        checkGameOver();

                        if (!gameOver) {
                            switchPlayer();
                            if (ui != null) {
                                ui.updateBoard();
                                ui.showMessage("Ходят: " +
                                        (currentPlayer.getPieceType() == PieceType.GOOSE ? "Гуси" : "Лиса"));
                            }

                            if (!currentPlayer.isHuman()) {
                                Timer timer = new Timer(1000, e -> makeAIMove());
                                timer.setRepeats(false);
                                timer.start();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (ui != null) {
                            ui.showMessage("Ошибка при выполнении хода: " + e.getMessage());
                        }
                    }
                });
            } else if (move == null) {
                checkGameOver();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (ui != null) {
                ui.showMessage("Ошибка AI: " + e.getMessage());
            }
        }
    }

    /**
     * Выполняет ход на доске.
     *
     * @param move ход для выполнения
     */
    private void executeMove(Move move) {
        if (move == null || move.getPiece() == null) return;

        Piece piece = move.getPiece();
        Piece[][] grid = board.getGrid();

        if (move.isCapture()) {
            int jumpedX = (move.getFrom().getX() + move.getTo().getX()) / 2;
            int jumpedY = (move.getFrom().getY() + move.getTo().getY()) / 2;

            Piece jumpedPiece = grid[jumpedX][jumpedY];
            if (jumpedPiece != null && jumpedPiece.getType() == PieceType.GOOSE) {
                grid[jumpedX][jumpedY] = null;
                board.getGeese().remove(jumpedPiece);
            }
        }

        grid[move.getFrom().getX()][move.getFrom().getY()] = null;
        grid[move.getTo().getX()][move.getTo().getY()] = piece;
        piece.setPosition(move.getTo());

        if (ui != null) {
            ui.highlightMove(move.getFrom(), move.getTo());
            ui.updateBoard();
        }
    }

    /**
     * Проверяет условия завершения игры.
     * Определяет победителя или объявляет ничью.
     */
    private void checkGameOver() {
        if (board.getFoxes().isEmpty()) {
            gameOver = true;
            if (ui != null) {
                ui.showMessage("Лиса пропала! Игра окончена.");
            }
            return;
        }

        var fox = board.getFoxes().get(0);
        var foxMoves = validator.getValidMoves(fox);

        if (foxMoves.isEmpty()) {
            gameOver = true;
            if (ui != null) {
                ui.showMessage("Гуси победили! Лиса заблокирована.");
            }
            return;
        }

        if (board.getGeese().size() <= 4) {
            gameOver = true;
            if (ui != null) {
                ui.showMessage("Лиса победила! Съедено слишком много гусей.");
            }
            return;
        }

        if (movesWithoutCapture >= config.getMaxMovesWithoutCapture()) {
            gameOver = true;
            if (ui != null) {
                ui.showMessage("Ничья! Превышен лимит ходов без захвата.");
            }
        }
    }

    /**
     * Переключает текущего игрока.
     */
    private void switchPlayer() {
        currentPlayer = (currentPlayer == geesePlayer) ? foxPlayer : geesePlayer;
    }

    /**
     * Возвращает игровую доску.
     *
     * @return текущая игровая доска
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Возвращает текущего игрока.
     *
     * @return игрок, чей сейчас ход
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Проверяет, завершена ли игра.
     *
     * @return true если игра завершена, иначе false
     */
    public boolean isGameOver() {
        return gameOver;
    }
}