
package chess.gui;

import chess.board.Board;
import chess.board.Position;
import chess.pieces.Piece;
import chess.utils.GameStorage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ChessboardGUI extends JFrame {

    private static final int SIZE = 8;

    private static final Color LIGHT =
            new Color(240, 217, 181);

    private static final Color DARK =
            new Color(181, 136, 99);

    private final Board board;
    private final JButton[][] squares = new JButton[SIZE][SIZE];
    private chess.pieces.Color currentTurn = chess.pieces.Color.WHITE;

    public ChessboardGUI() {

        board = new Board();

        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Attach Game Menu Bar (GUI Feature 1)
        GameMenuBar menuBar = new GameMenuBar(this::handleNewGame, this::handleSaveGame, this::handleLoadGame);
        setJMenuBar(menuBar);

        JPanel chessboard = new JPanel(
                new GridLayout(SIZE, SIZE)
        );

        // Create the 8x8 chessboard.
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {

                JButton square = new JButton();

                square.setOpaque(true);
                square.setBorderPainted(false);
                square.setFocusPainted(false);
                square.setMargin(new Insets(0, 0, 0, 0));

                // Alternate the square colors.
                if ((row + col) % 2 == 0) {
                    square.setBackground(LIGHT);
                } else {
                    square.setBackground(DARK);
                }

                square.setFont(
                        new Font("Serif", Font.PLAIN, 48)
                );

                squares[row][col] = square;
                chessboard.add(square);
            }
        }

        add(chessboard, BorderLayout.CENTER);

        updateBoard();

        setSize(650, 650);
        setLocationRelativeTo(null);
    }

    // Handlers for GUI Feature 1: Menu Bar
    private void handleNewGame() {
        board.initializeBoard();
        currentTurn = chess.pieces.Color.WHITE;
        updateBoard();
    }

    private void handleSaveGame() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File saveFile = chooser.getSelectedFile();
            try {
                GameStorage.save(board, currentTurn, saveFile);
                JOptionPane.showMessageDialog(this, "Game saved successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Failed to save game: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleLoadGame() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File saveFile = chooser.getSelectedFile();
            try {
                currentTurn = GameStorage.load(saveFile, board);
                updateBoard();
                JOptionPane.showMessageDialog(this, "Game loaded successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Failed to load game: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Display the pieces from the existing Board class.
    public void updateBoard() {

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
                }
            }
        }
    }

    // Convert the existing piece symbols into chess symbols.
    private String getPieceSymbol(Piece piece) {

        String symbol = piece.getSymbol();
        char type = Character.toLowerCase(symbol.charAt(1));

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

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            ChessboardGUI gui = new ChessboardGUI();
            gui.setVisible(true);
        });
    }
}