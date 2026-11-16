
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

public class ChessboardGUI extends JFrame {

    private static final int SIZE = 8;

    private final Board board;
    private final JButton[][] squares = new JButton[SIZE][SIZE];
    private final JPanel chessboard;

    private BufferedImage spiderEyesLogo;

    // Settings
    private String currentBoardStyle = "Spider-Man";
    private String currentPieceStyle = "Black & Green";
    private String currentBoardSize = "Medium";

    private static final Color SELECTED = new Color(255, 235, 90);
    private Position selectedPosition = null;
    private final JLabel turnLabel = new JLabel("White's Turn", SwingConstants.CENTER);

    // Phase 2 game storage
    private chess.pieces.Color currentTurn = chess.pieces.Color.WHITE;

    public ChessboardGUI() {
        board = new Board();

        loadSpiderEyesLogo();

        setTitle("Spider-Man Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Phase 2: New, Save, and Load menu
// Phase 2: Game Menu, turn display, and Settings
JPanel topPanel = new JPanel(new BorderLayout());

turnLabel.setFont(new Font("Arial", Font.BOLD, 18));
topPanel.add(turnLabel, BorderLayout.CENTER);

JButton settingsButton = new JButton("Settings");

settingsButton.addActionListener(e -> {
    SettingsWindow settings = new SettingsWindow(this);
    settings.setVisible(true);
});

GameMenuButton gameMenuButton = new GameMenuButton(
        settingsButton,
        this::handleNewGame,
        this::handleSaveGame,
        this::handleLoadGame
);

JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
controls.add(gameMenuButton);
controls.add(settingsButton);

topPanel.add(controls, BorderLayout.EAST);
add(topPanel, BorderLayout.NORTH);


        // Chessboard with centered Spider-Man watermark
        chessboard = new JPanel(new GridLayout(SIZE, SIZE)) {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (currentBoardStyle.equals("Spider-Man")
                        && spiderEyesLogo != null) {

                    Graphics2D g2 = (Graphics2D) g.create();

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
                            x,
                            y,
                            logoSize,
                            logoSize,
                            this
                    );

                    g2.dispose();
                }
            }
        };

        chessboard.setOpaque(true);
        chessboard.setPreferredSize(new Dimension(600, 600));

        // Create 64 individual squares
        for (int row = 0; row < SIZE; row++) {

            for (int col = 0; col < SIZE; col++) {

                SpiderSquare square = new SpiderSquare();

                square.setFont(
                        new Font("Serif", Font.PLAIN, 54)
                );

                final int selectedRow = row;
                final int selectedCol = col;
                square.addActionListener(e -> handleSquareClick(selectedRow, selectedCol));

                squares[row][col] = square;
                chessboard.add(square);
            }
        }

        JPanel boardWithCoordinates = new JPanel(new BorderLayout());
        JPanel columnLabels = new JPanel(new GridLayout(1, SIZE));
        for (char col = 'A'; col <= 'H'; col++) {
            JLabel label = new JLabel(String.valueOf(col), SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 16));
            columnLabels.add(label);
        }
        JPanel rowLabels = new JPanel(new GridLayout(SIZE, 1));
        for (int row = 8; row >= 1; row--) {
            JLabel label = new JLabel(" " + row + " ", SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 16));
            rowLabels.add(label);
        }
        boardWithCoordinates.add(columnLabels, BorderLayout.SOUTH);
        boardWithCoordinates.add(rowLabels, BorderLayout.WEST);
        boardWithCoordinates.add(chessboard, BorderLayout.CENTER);
        add(boardWithCoordinates, BorderLayout.CENTER);

        applyBoardAppearance();
        updateBoard();

        pack();
        setLocationRelativeTo(null);
    }

    // Load Spider-Man eyes image
    private void loadSpiderEyesLogo() {

        try {

            java.net.URL imageURL =
                    getClass().getResource("spider-eyes-watermark.png");

            if (imageURL != null) {

                spiderEyesLogo = ImageIO.read(imageURL);

            } else {

                File imageFile = new File(
                        "src/chess/gui/spider-eyes-watermark.png"
                );

                if (imageFile.exists()) {
                    spiderEyesLogo = ImageIO.read(imageFile);
                }
            }

            if (spiderEyesLogo == null) {
                System.out.println(
                        "Spider-Man eyes image could not be loaded."
                );
            }

        } catch (IOException e) {

            System.out.println(
                    "Error loading Spider-Man logo: " + e.getMessage()
            );
        }
    }

    // Apply SettingsWindow selections
    public void applySettings(
            String boardStyle,
            String pieceStyle,
            String boardSize
    ) {

        currentBoardStyle = boardStyle;
        currentPieceStyle = pieceStyle;
        currentBoardSize = boardSize;

        applyBoardAppearance();
        updateBoard();

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
                        new Font("Serif", Font.PLAIN, fontSize)
                );
            }
        }

        chessboard.revalidate();
        chessboard.repaint();

        revalidate();
        pack();
        setLocationRelativeTo(null);
    }

    // Change board colors
    private void applyBoardAppearance() {

        Color light;
        Color dark;

        switch (currentBoardStyle) {

            case "Marble":

                light = new Color(235, 235, 235);
                dark = new Color(125, 130, 140);
                break;

            case "Wooden":

                light = new Color(240, 217, 181);
                dark = new Color(181, 136, 99);
                break;

            case "Spider-Man":

            default:

                light = new Color(195, 35, 48);
                dark = new Color(23, 65, 130);
                break;
        }

        for (int row = 0; row < SIZE; row++) {

            for (int col = 0; col < SIZE; col++) {

                if ((row + col) % 2 == 0) {
                    squares[row][col].setBackground(light);
                } else {
                    squares[row][col].setBackground(dark);
                }
            }
        }

        chessboard.repaint();
    }

    // Phase 2: New Game
    private void handleNewGame() {

        board.initializeBoard();
        currentTurn = chess.pieces.Color.WHITE;
        selectedPosition = null;
        updateBoard();
    }

    // Phase 2: Save Game
    private void handleSaveGame() {

        JFileChooser chooser = new JFileChooser();

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

            File saveFile = chooser.getSelectedFile();

            try {

                GameStorage.save(board, currentTurn, saveFile);

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

    // Phase 2: Load Game
    private void handleLoadGame() {

        JFileChooser chooser = new JFileChooser();

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

            File saveFile = chooser.getSelectedFile();

            try {

                currentTurn = GameStorage.load(saveFile, board);
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
     * Handles selecting pieces, moving pieces, capturing
     * opponents, and detecting King captures.
     *
     * @param row clicked row
     * @param col clicked column
     */
    private void handleSquareClick(int row, int col) {

        Position clickedPosition =
                new Position(row, col);

        Piece clickedPiece =
                board.getPiece(clickedPosition);

        // First click: select a piece.
        if (selectedPosition == null) {

            if (clickedPiece == null) {
                return;
            }

            if (clickedPiece.getColor() != currentTurn) {
                return;
            }

            selectedPosition = clickedPosition;

            updateBoard();
            return;
        }

        // Clicking the selected piece again cancels.
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

        // Selecting another friendly piece changes selection.
        if (clickedPiece != null
                && clickedPiece.getColor()
                == movingPiece.getColor()) {

            selectedPosition = clickedPosition;

            updateBoard();
            return;
        }

        // Remember whether an opponent's King is captured.
        Piece capturedPiece = clickedPiece;

        boolean moved = board.movePieceFree(
                selectedPosition,
                clickedPosition
        );

        if (!moved) {
            return;
        }

        selectedPosition = null;

        updateBoard();

        // Declare the winner if a King is captured.
        if (capturedPiece instanceof King) {

            String winner =
                    movingPiece.getColor()
                    == chess.pieces.Color.WHITE
                    ? "White"
                    : "Black";

            JOptionPane.showMessageDialog(
                    this,
                    winner + " wins! The opponent's King was captured.",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();
            System.exit(0);
            return;
        }

        // Switch turns.
        currentTurn =
                currentTurn == chess.pieces.Color.WHITE
                ? chess.pieces.Color.BLACK
                : chess.pieces.Color.WHITE;

        updateBoard();
    }

    // Display chess pieces
    public void updateBoard() {

        applyBoardAppearance();

        for (int row = 0; row < SIZE; row++) {

            for (int col = 0; col < SIZE; col++) {

                Position position = new Position(row, col);

                Piece piece = board.getPiece(position);

                if (piece == null) {

                    squares[row][col].setText("");

                } else {

                    squares[row][col].setText(
                            getPieceSymbol(piece)
                    );

                    squares[row][col].setForeground(
                            getPieceColor(piece)
                    );
                }
            }
        }

        // Preserve the selected-square highlight without resetting the theme.
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (selectedPosition != null
                        && selectedPosition.getRow() == row
                        && selectedPosition.getColumn() == col) {
                    squares[row][col].setBackground(SELECTED);
                }
            }
        }
        turnLabel.setText(currentTurn == chess.pieces.Color.WHITE
                ? "White's Turn" : "Black's Turn");
        chessboard.repaint();
    }

    // Convert piece symbols
    private String getPieceSymbol(Piece piece) {

        String symbol = piece.getSymbol();

        char type = Character.toLowerCase(
                symbol.charAt(1)
        );

        boolean white = symbol.charAt(0) == 'w';

        switch (type) {

            case 'p':
                return white ? "♙" : "♟";

            case 'r':
                return white ? "♖" : "♜";

            case 'n':
                return white ? "♘" : "♞";

            case 'b':
                return white ? "♗" : "♝";

            case 'q':
                return white ? "♕" : "♛";

            case 'k':
                return white ? "♔" : "♚";

            default:
                return "";
        }
    }

    // Change chess piece colors
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

    // Custom square that allows watermark to show through
    private static class SpiderSquare extends JButton {

        public SpiderSquare() {

            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setMargin(new Insets(0, 0, 0, 0));
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();

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

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            ChessboardGUI gui = new ChessboardGUI();

            gui.setVisible(true);
        });
    }
}
