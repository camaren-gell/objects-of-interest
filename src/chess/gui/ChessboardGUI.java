
package chess.gui;

import chess.board.Board;
import chess.board.Position;
import chess.pieces.King;
import chess.pieces.Piece;
import chess.utils.GameStorage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Phase 2 Swing chess interface.
 * Combines click-to-move and king capture with the
 * customizable chessboard and game menu controls.
 */
public class ChessboardGUI extends JFrame {

    private static final int SIZE = 8;
    private static final Color SELECTED =
            new Color(255, 235, 90);

    private final Board board;
    private final JButton[][] squares =
            new JButton[SIZE][SIZE];
    private final JPanel chessboard;

    private final JLabel turnLabel =
            new JLabel("White's Turn", SwingConstants.CENTER);

    private chess.pieces.Color currentTurn =
            chess.pieces.Color.WHITE;

    private Position selectedPosition;
    private BufferedImage spiderEyesLogo;

    private String currentBoardStyle = "Spider-Man";
    private String currentPieceStyle = "Black & Green";
    private String currentBoardSize = "Medium";

    /**
     * Constructs the customizable chessboard
     * and attaches input handlers.
     */
    public ChessboardGUI() {

        board = new Board();
        loadSpiderEyesLogo();

        setTitle("Spider-Man Chess - Phase 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Game controls: New, Save, and Load.
        setJMenuBar(new GameMenuBar(
                this::handleNewGame,
                this::handleSaveGame,
                this::handleLoadGame
        ));

        // Top panel showing turn and settings button.
        JPanel topPanel = new JPanel(new BorderLayout());

        turnLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        topPanel.add(turnLabel, BorderLayout.CENTER);

        JButton settingsButton = new JButton("Settings");

        settingsButton.addActionListener(e -> {
            SettingsWindow settings =
                    new SettingsWindow(this);
            settings.setVisible(true);
        });

        topPanel.add(settingsButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Draw the Spider-Man watermark behind the board.
        chessboard = new JPanel(new GridLayout(SIZE, SIZE)) {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                if ("Spider-Man".equals(currentBoardStyle)
                        && spiderEyesLogo != null) {

                    Graphics2D g2 =
                            (Graphics2D) g.create();

                    g2.setRenderingHint(
                            RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR
                    );

                    g2.setComposite(
                            AlphaComposite.getInstance(
                                    AlphaComposite.SRC_OVER,
                                    0.55f
                            )
                    );

                    int logoSize = (int) (
                            Math.min(getWidth(), getHeight()) * 0.60
                    );

                    int x = (getWidth() - logoSize) / 2;
                    int y = (getHeight() - logoSize) / 2;

                    g2.drawImage(
                            spiderEyesLogo,
                            x, y,
                            logoSize, logoSize,
                            this
                    );

                    g2.dispose();
                }
            }
        };

        chessboard.setOpaque(true);
        chessboard.setPreferredSize(
                new Dimension(600, 600)
        );

        // Create 64 clickable squares.
        for (int row = 0; row < SIZE; row++) {

            for (int col = 0; col < SIZE; col++) {

                JButton square = new SpiderSquare();

                square.setMargin(
                        new Insets(0, 0, 0, 0)
                );

                square.setFont(
                        new Font("Serif", Font.PLAIN, 54)
                );

                final int clickRow = row;
                final int clickCol = col;

                square.addActionListener(e ->
                        handleSquareClick(clickRow, clickCol)
                );

                squares[row][col] = square;
                chessboard.add(square);
            }
        }

        // Add coordinate labels.
        JPanel labelsAndBoard =
                new JPanel(new BorderLayout());

        JPanel rows =
                new JPanel(new GridLayout(SIZE, 1));

        for (int rank = 8; rank >= 1; rank--) {

            JLabel label = new JLabel(
                    " " + rank + " ",
                    SwingConstants.CENTER
            );

            label.setFont(
                    new Font("Arial", Font.BOLD, 16)
            );

            rows.add(label);
        }

        JPanel columns =
                new JPanel(new GridLayout(1, SIZE));

        for (char file = 'A'; file <= 'H'; file++) {

            JLabel label = new JLabel(
                    String.valueOf(file),
                    SwingConstants.CENTER
            );

            label.setFont(
                    new Font("Arial", Font.BOLD, 16)
            );

            columns.add(label);
        }

        JPanel bottom = new JPanel(new BorderLayout());

        JLabel corner = new JLabel(" 8 ");

        corner.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        corner.setForeground(bottom.getBackground());

        bottom.add(corner, BorderLayout.WEST);
        bottom.add(columns, BorderLayout.CENTER);

        labelsAndBoard.add(rows, BorderLayout.WEST);
        labelsAndBoard.add(chessboard, BorderLayout.CENTER);
        labelsAndBoard.add(bottom, BorderLayout.SOUTH);

        add(labelsAndBoard, BorderLayout.CENTER);

        applyBoardAppearance();
        updateBoard();

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Resets the game to the initial chess positions.
     */
    private void handleNewGame() {

        board.initializeBoard();
        currentTurn = chess.pieces.Color.WHITE;
        selectedPosition = null;

        updateBoard();
    }

    /**
     * Saves the current board and player turn.
     */
    private void handleSaveGame() {

        JFileChooser chooser = new JFileChooser();

        if (chooser.showSaveDialog(this)
                == JFileChooser.APPROVE_OPTION) {

            try {

                GameStorage.save(
                        board,
                        currentTurn,
                        chooser.getSelectedFile()
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Game saved successfully!"
                );

            } catch (IOException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to save game: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Loads a saved board and restores the player turn.
     */
    private void handleLoadGame() {

        JFileChooser chooser = new JFileChooser();

        if (chooser.showOpenDialog(this)
                == JFileChooser.APPROVE_OPTION) {

            try {

                currentTurn = GameStorage.load(
                        chooser.getSelectedFile(),
                        board
                );

                selectedPosition = null;
                updateBoard();

                JOptionPane.showMessageDialog(
                        this,
                        "Game loaded successfully!"
                );

            } catch (IOException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to load game: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Handles selecting and moving chess pieces.
     *
     * Phase 2 allows pieces to move to any square.
     * Players cannot capture their own pieces.
     * Capturing a King ends the game.
     *
     * @param row clicked row
     * @param col clicked column
     */
    private void handleSquareClick(int row, int col) {

        Position destination =
                new Position(row, col);

        Piece target =
                board.getPiece(destination);

        // First click selects a piece.
        if (selectedPosition == null) {

            if (target != null
                    && target.getColor() == currentTurn) {

                selectedPosition = destination;
                updateBoard();
            }

            return;
        }

        // Clicking the selected piece cancels selection.
        if (selectedPosition.getRow() == row
                && selectedPosition.getColumn() == col) {

            selectedPosition = null;
            updateBoard();
            return;
        }

        Piece movingPiece =
                board.getPiece(selectedPosition);

        if (movingPiece == null) {

            selectedPosition = null;
            updateBoard();
            return;
        }

        // Clicking a friendly piece changes selection.
        if (target != null
                && target.getColor() == movingPiece.getColor()) {

            selectedPosition = destination;
            updateBoard();
            return;
        }

        // Remember what was captured before moving.
        Piece capturedPiece = target;

        boolean moved = board.movePieceFree(
                selectedPosition,
                destination
        );

        if (!moved) {
            return;
        }

        selectedPosition = null;

        // King capture detection.
        if (capturedPiece instanceof King) {

            updateBoard();

            String winner =
                    movingPiece.getColor()
                    == chess.pieces.Color.WHITE
                    ? "White"
                    : "Black";

            JOptionPane.showMessageDialog(
                    this,
                    winner
                            + " wins! The opponent's King was captured.",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();
            System.exit(0);
            return;
        }

        // Change active player.
        currentTurn =
                currentTurn == chess.pieces.Color.WHITE
                ? chess.pieces.Color.BLACK
                : chess.pieces.Color.WHITE;

        updateBoard();
    }

    /**
     * Updates the board pieces, selected square,
     * and current turn display.
     */
    public void updateBoard() {

        Color[] boardColors = getBoardColors();

        for (int row = 0; row < SIZE; row++) {

            for (int col = 0; col < SIZE; col++) {

                JButton square = squares[row][col];

                boolean selected =
                        selectedPosition != null
                        && selectedPosition.getRow() == row
                        && selectedPosition.getColumn() == col;

                if (selected) {

                    square.setBackground(SELECTED);

                } else {

                    square.setBackground(
                            boardColors[(row + col) % 2]
                    );
                }

                Piece piece = board.getPiece(
                        new Position(row, col)
                );

                square.setText(
                        piece == null
                        ? ""
                        : getPieceSymbol(piece)
                );

                if (piece != null) {
                    square.setForeground(
                            getPieceColor(piece)
                    );
                }
            }
        }

        turnLabel.setText(
                currentTurn == chess.pieces.Color.WHITE
                ? "White's Turn"
                : "Black's Turn"
        );

        chessboard.repaint();
    }

    /**
     * Applies customization choices from SettingsWindow.
     *
     * @param boardStyle selected board theme
     * @param pieceStyle selected piece color theme
     * @param boardSize selected board size
     */
    public void applySettings(
            String boardStyle,
            String pieceStyle,
            String boardSize) {

        currentBoardStyle = boardStyle;
        currentPieceStyle = pieceStyle;
        currentBoardSize = boardSize;

        int dimension;

        switch (currentBoardSize) {

            case "Small":
                dimension = 480;
                break;

            case "Large":
                dimension = 720;
                break;

            case "Medium":
            default:
                dimension = 600;
                break;
        }

        chessboard.setPreferredSize(
                new Dimension(dimension, dimension)
        );

        int fontSize = dimension / 11;

        for (int row = 0; row < SIZE; row++) {

            for (int col = 0; col < SIZE; col++) {

                squares[row][col].setFont(
                        new Font(
                                "Serif",
                                Font.PLAIN,
                                fontSize
                        )
                );
            }
        }

        applyBoardAppearance();
        updateBoard();

        chessboard.revalidate();
        revalidate();

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Updates chessboard colors for the selected theme.
     */
    private void applyBoardAppearance() {

        Color[] colors = getBoardColors();

        for (int row = 0; row < SIZE; row++) {

            for (int col = 0; col < SIZE; col++) {

                squares[row][col].setBackground(
                        colors[(row + col) % 2]
                );
            }
        }

        chessboard.repaint();
    }

    /**
     * Returns the light and dark colors for the current theme.
     *
     * @return two alternating square colors
     */
    private Color[] getBoardColors() {

        switch (currentBoardStyle) {

            case "Marble":
                return new Color[]{
                        new Color(235, 235, 235),
                        new Color(125, 130, 140)
                };

            case "Wooden":
                return new Color[]{
                        new Color(240, 217, 181),
                        new Color(181, 136, 99)
                };

            case "Spider-Man":
            default:
                return new Color[]{
                        new Color(195, 35, 48),
                        new Color(23, 65, 130)
                };
        }
    }

    /**
     * Loads the Spider-Man eyes watermark image.
     */
    private void loadSpiderEyesLogo() {

        try {

            java.net.URL resource =
                    getClass().getResource(
                            "spider-eyes-watermark.png"
                    );

            if (resource != null) {

                spiderEyesLogo = ImageIO.read(resource);

            } else {

                File file = new File(
                        "src/chess/gui/spider-eyes-watermark.png"
                );

                if (file.exists()) {
                    spiderEyesLogo = ImageIO.read(file);
                }
            }

            if (spiderEyesLogo == null) {

                System.out.println(
                        "Spider-Man eyes image could not be loaded."
                );
            }

        } catch (IOException e) {

            System.out.println(
                    "Error loading Spider-Man logo: "
                            + e.getMessage()
            );
        }
    }

    /**
     * Converts chess pieces into Unicode chess symbols.
     *
     * @param piece chess piece
     * @return Unicode chess symbol
     */
    private String getPieceSymbol(Piece piece) {

        String symbol = piece.getSymbol();

        char type =
                Character.toLowerCase(symbol.charAt(1));

        boolean white =
                symbol.charAt(0) == 'w';

        switch (type) {

            case 'p':
                return white ? "\u2659" : "\u265F";

            case 'r':
                return white ? "\u2656" : "\u265C";

            case 'n':
                return white ? "\u2658" : "\u265E";

            case 'b':
                return white ? "\u2657" : "\u265D";

            case 'q':
                return white ? "\u2655" : "\u265B";

            case 'k':
                return white ? "\u2654" : "\u265A";

            default:
                return "";
        }
    }

    /**
     * Returns the piece color selected in Settings.
     *
     * @param piece chess piece
     * @return display color
     */
    private Color getPieceColor(Piece piece) {

        boolean white =
                piece.getSymbol().charAt(0) == 'w';

        switch (currentPieceStyle) {

            case "Black & Green":
                return white
                        ? new Color(57, 211, 83)
                        : new Color(15, 15, 15);

            case "Gold & Silver":
                return white
                        ? new Color(212, 175, 55)
                        : new Color(160, 165, 175);

            case "Blue & Red":
                return white
                        ? new Color(30, 90, 210)
                        : new Color(200, 35, 45);

            case "Black & White":
            default:
                return white
                        ? new Color(250, 250, 250)
                        : new Color(25, 25, 25);
        }
    }

    /**
     * Custom transparent square that allows the
     * Spider-Man watermark to appear through the board.
     */
    private static class SpiderSquare extends JButton {

        SpiderSquare() {

            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setComposite(
                    AlphaComposite.getInstance(
                            AlphaComposite.SRC_OVER,
                            0.72f
                    )
            );

            g2.setColor(getBackground());

            g2.fillRect(
                    0, 0,
                    getWidth(),
                    getHeight()
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    /**
     * Launches the Java Swing chess GUI.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new ChessboardGUI().setVisible(true)
        );
    }
}
