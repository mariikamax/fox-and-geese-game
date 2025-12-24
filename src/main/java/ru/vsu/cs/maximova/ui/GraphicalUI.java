package ru.vsu.cs.maximova.ui;

import ru.vsu.cs.maximova.board.Board;
import ru.vsu.cs.maximova.board.Position;
import ru.vsu.cs.maximova.game.Game;
import ru.vsu.cs.maximova.game.GameConfig;
import ru.vsu.cs.maximova.pieces.Piece;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Графический пользовательский интерфейс игры.
 * Реализует интерфейс GameUI и предоставляет визуальное представление игры.
 */
public class GraphicalUI extends JFrame implements GameUI {
    private Game game;
    private Board board;
    private final int cellSize = 60;
    private BoardPanel boardPanel;
    private JLabel statusLabel;
    private Position selectedPosition;
    private final Map<String, ImageIcon> pieceImages;

    /**
     * Создает графический интерфейс для указанной игры.
     *
     * @param game объект игры
     */
    public GraphicalUI(Game game) {
        this.game = game;
        this.board = game.getBoard();
        this.pieceImages = loadPieceImages();

        setupUI();
    }

    /**
     * Создает графический интерфейс для отображения доски.
     * Используется для отладки без игрового объекта.
     *
     * @param board игровая доска
     */
    public GraphicalUI(Board board) {
        this.game = null;
        this.board = board;
        this.pieceImages = loadPieceImages();

        setupUI();
    }

    /**
     * Настраивает элементы пользовательского интерфейса.
     * Создает и размещает все необходимые компоненты.
     */
    private void setupUI() {
        setTitle("Лиса и Гуси");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        statusLabel = new JLabel("Гуси ходят первыми. Выберите фигуру.");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(statusLabel, BorderLayout.NORTH);

        boardPanel = new BoardPanel();
        add(boardPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton newGameButton = new JButton("Новая игра");
        JButton exitButton = new JButton("Выход");

        newGameButton.addActionListener(e -> restartGame());
        exitButton.addActionListener(e -> System.exit(0));

        controlPanel.add(newGameButton);
        controlPanel.add(exitButton);
        add(controlPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Загружает изображения фигур.
     * Создает иконки для лисы и гусей.
     *
     * @return карта с загруженными изображениями
     */
    private Map<String, ImageIcon> loadPieceImages() {
        Map<String, ImageIcon> images = new HashMap<>();

        images.put("fox", createPieceIcon(Color.RED, "Л"));
        images.put("goose", createPieceIcon(Color.BLUE, "Г"));

        return images;
    }

    /**
     * Создает иконку для фигуры.
     *
     * @param color фона иконки
     * @param text текст, отображаемый на иконке
     * @return созданная иконка
     */
    private ImageIcon createPieceIcon(Color color, String text) {
        int size = cellSize - 10;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(color);
        g2d.fillOval(0, 0, size, size);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(0, 0, size, size);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        g2d.drawString(text,
                (size - textWidth) / 2,
                (size + textHeight) / 2 - 4);

        g2d.dispose();
        return new ImageIcon(image);
    }

    /**
     * Внутренний класс для отображения игровой доски.
     */
    private class BoardPanel extends JPanel {

        /**
         * Создает панель доски с обработкой кликов мыши.
         */
        public BoardPanel() {
            setPreferredSize(new Dimension(
                    board.getWidth() * cellSize,
                    board.getHeight() * cellSize
            ));

            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    handleBoardClick(e.getX(), e.getY());
                }
            });
        }

        /**
         * Отрисовывает содержимое панели.
         *
         * @param g графический контекст
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            drawBoard(g2d);
            drawPieces(g2d);
            drawSelection(g2d);
        }

        /**
         * Отрисовывает игровую доску.
         *
         * @param g2d графический контекст
         */
        private void drawBoard(Graphics2D g2d) {
            for (int x = 0; x < board.getWidth(); x++) {
                for (int y = 0; y < board.getHeight(); y++) {
                    Position pos = new Position(x, y);
                    if (board.isValidPosition(pos)) {
                        Color color = (x + y) % 2 == 0 ?
                                new Color(240, 217, 181) : // светлая
                                new Color(181, 136, 99);   // темная

                        g2d.setColor(color);
                        g2d.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);

                        g2d.setColor(Color.BLACK);
                        g2d.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
                    }
                }
            }
        }

        /**
         * Отрисовывает фигуры на доске.
         *
         * @param g2d графический контекст
         */
        private void drawPieces(Graphics2D g2d) {
            for (Piece goose : board.getGeese()) {
                Position pos = goose.getPosition();
                ImageIcon icon = pieceImages.get("goose");
                icon.paintIcon(this, g2d,
                        pos.getX() * cellSize + 5,
                        pos.getY() * cellSize + 5);
            }

            for (Piece fox : board.getFoxes()) {
                Position pos = fox.getPosition();
                ImageIcon icon = pieceImages.get("fox");
                icon.paintIcon(this, g2d,
                        pos.getX() * cellSize + 5,
                        pos.getY() * cellSize + 5);
            }
        }

        /**
         * Отрисовывает выделение выбранной клетки.
         *
         * @param g2d графический контекст
         */
        private void drawSelection(Graphics2D g2d) {
            if (selectedPosition != null) {
                g2d.setColor(new Color(0, 255, 0, 100)); // Полупрозрачный зеленый
                g2d.fillRect(
                        selectedPosition.getX() * cellSize,
                        selectedPosition.getY() * cellSize,
                        cellSize, cellSize
                );
            }
        }
    }

    /**
     * Обрабатывает клик мыши по доске.
     *
     * @param mouseX координата X клика
     * @param mouseY координата Y клика
     */
    private void handleBoardClick(int mouseX, int mouseY) {
        if (game == null || game.isGameOver()) {
            return;
        }

        int boardX = mouseX / cellSize;
        int boardY = mouseY / cellSize;
        Position clickedPosition = new Position(boardX, boardY);

        if (!board.isValidPosition(clickedPosition)) {
            return;
        }

        if (selectedPosition == null) {
            Piece piece = board.getPieceAt(clickedPosition);
            if (piece != null && piece.getType() == game.getCurrentPlayer().getPieceType()) {
                selectedPosition = clickedPosition;
                updateStatus("Выбрана фигура в " + clickedPosition);
            }
        } else {
            game.makeHumanMove(selectedPosition, clickedPosition);
            selectedPosition = null;
            updateStatus("Ход сделан...");
        }

        boardPanel.repaint();
    }

    @Override
    public void display() {
    }

    /**
     * Обновляет отображение доски.
     */
    @Override
    public void updateBoard() {
        boardPanel.repaint();
    }

    /**
     * Показывает сообщение в диалоговом окне.
     *
     * @param message текст сообщения
     */
    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * Подсвечивает выполненный ход.
     *
     * @param from начальная позиция
     * @param to конечная позиция
     */
    @Override
    public void highlightMove(Position from, Position to) {
        boardPanel.repaint();
    }

    /**
     * Обновляет текст статусной строки.
     *
     * @param text новый текст статуса
     */
    private void updateStatus(String text) {
        statusLabel.setText(text);
    }

    /**
     * Перезапускает игру с новыми параметрами.
     * Запрашивает подтверждение у пользователя перед сбросом текущего прогресса.
     */
    private void restartGame() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Начать новую игру? Текущий прогресс будет потерян.",
                "Новая игра",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            GameConfig config = GameConfig.getDefault();

            Game newGame = new Game(config, true);

            this.game = newGame;
            this.board = newGame.getBoard();

            newGame.setUI(this);

            newGame.start();

            selectedPosition = null;

            updateStatus("Новая игра! Гуси ходят первыми.");

            boardPanel.repaint();
        }
    }
}