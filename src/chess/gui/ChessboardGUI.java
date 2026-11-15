
package chess.gui;

import chess.board.Board;
import chess.board.Position;
import chess.pieces.King;
import chess.pieces.Piece;
import chess.utils.GameStorage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Graphical user interface for Phase 2 of the Chess Game.
 *
 * Provides an 8x8 chessboard, coordinate labels, piece selection,
 * unrestricted movement, capturing, and King capture notification.
 */
public class ChessboardGUI extends JFrame {

    private static final int SIZE = 8;

    private static final Color LIGHT =
            new Color(240, 217, 181);

    private static final Color DARK =
            new Color(181, 136, 99);

    private static final Color SELECTED =
            new Color(255, 235, 90);

    private final Board board;

    private final JButton[][] squares =
            new JButton[SIZE][SIZE];

    private chess.pieces.Color currentTurn =
            chess.pieces.Color.WHITE;

    private Position selectedPosition = null;

    private final JLabel turnLabel =
            new JLabel("White's Turn", SwingConstants.CENTER);

    /**
     * Creates and displays the components of the chess GUI.
     */
    public ChessboardGUI() {

        board = new Board();

        setTitle("Chess Game - Phase 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Existing menu bar with New Game, Save, and Load.
        GameMenuBar menuBar = new GameMenuBar(
                this::handleNewGame,
                this::handleSaveGame,
                this::handleLoadGame
        );

        setJMenuBar(menuBar);

        // Create the chessboard.
        JPanel chessboard = new JPanel(
                new GridLayout(SIZE, SIZE)
        );

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {

                JButton square = new JButton();

                square.setOpaque(true);
                square.setBorderPainted(false);
                square.setFocusPainted(false);
                square.setMargin(new Insets(0, 0, 0, 0));

                square.setFont(
                        new Font("Serif", Font.PLAIN, 48)
                );

                final int selectedRow = row;
                final int selectedCol = col;

                square.addActionListener(e ->
                        handleSquareClick(selectedRow, selectedCol)
                );

                squares[row][col] = square;
                chessboard.add(square);
            }
        }

        // Container for chessboard and coordinate labels.
        JPanel boardWithCoordinates =
                new JPanel(new BorderLayout());

        // Display letters A-H.
        JPanel columnLabels =
                new JPanel(new GridLayout(1, SIZE));

        for (char col = 'A'; col <= 'H'; col++) {

            JLabel label = new JLabel(
                    String.valueOf(col),
                    SwingConstants.CENTER
            );

            label.setFont(
                    new Font("Arial", Font.BOLD, 16)
            );

            columnLabels.add(label);
        }

        // Display numbers 8-1.
        JPanel rowLabels =
                new JPanel(new GridLayout(SIZE, 1));

        for (int row = 8; row >= 1; row--) {

            JLabel label = new JLabel(
                    " " + row + " ",
                    SwingConstants.CENTER
            );

            label.setFont(
                    new Font("Arial", Font.BOLD, 16)
            );

            rowLabels.add(label);
        }

        boardWithCoordinates.add(
                columnLabels, BorderLayout.SOUTH
        );

        boardWithCoordinates.add(
                rowLabels, BorderLayout.WEST
        );

        boardWithCoordinates.add(
                chessboard, BorderLayout.CENTER
        );

        add(boardWithCoordinates, BorderLayout.CENTER);

        // Display the active player's turn.
        turnLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        add(turnLabel, BorderLayout.NORTH);

        updateBoard();

        setSize(700, 700);
        setLocationRelativeTo(null);
    }

    /**
     * Starts a new game.
     */
    private void handleNewGame() {

        board.initializeBoard();

        currentTurn = chess.pieces.Color.WHITE;
        selectedPosition = null;

        updateBoard();
    }

    /**
     * Saves the board and current player turn.
     */
    private void handleSaveGame() {

        JFileChooser chooser = new JFileChooser();

        if (chooser.showSaveDialog(this)
                == JFileChooser.APPROVE_OPTION) {

            File saveFile = chooser.getSelectedFile();

            try {

                GameStorage.save(
                        board,
                        currentTurn,
                        saveFile
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
     * Loads a saved game.
     */
    private void handleLoadGame() {

        JFileChooser chooser = new JFileChooser();

        if (chooser.showOpenDialog(this)
                == JFileChooser.APPROVE_OPTION) {

            File saveFile = chooser.getSelectedFile();

            try {

                currentTurn = GameStorage.load(
                        saveFile,
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

    /**
     * Refreshes the board colors, pieces, and turn display.
     */
    public void updateBoard() {

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {

                JButton square = squares[row][col];

                if (selectedPosition != null
                        && selectedPosition.getRow() == row
                        && selectedPosition.getColumn() == col) {

                    square.setBackground(SELECTED);

                } else if ((row + col) % 2 == 0) {

                    square.setBackground(LIGHT);

                } else {

                    square.setBackground(DARK);
                }

                Position position =
                        new Position(row, col);

                Piece piece =
                        board.getPiece(position);

                if (piece == null) {
                    square.setText("");
                } else {
                    square.setText(getPieceSymbol(piece));
                }
            }
        }

        // Display whose turn it is.
        if (currentTurn == chess.pieces.Color.WHITE) {
            turnLabel.setText("White's Turn");
        } else {
            turnLabel.setText("Black's Turn");
        }

        repaint();
    }

    /**
     * Returns the Unicode chess symbol of a piece.
     *
     * @param piece chess piece
     * @return visual symbol
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
     * Launches the chess game.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            ChessboardGUI gui =
                    new ChessboardGUI();

            gui.setVisible(true);
        });
    }
}
