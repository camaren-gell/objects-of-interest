
package chess.gui;

import chess.board.Board;
import chess.board.Position;
import chess.pieces.Piece;

import javax.swing.*;
import java.awt.*;

public class ChessboardGUI extends JFrame {

    private static final int SIZE = 8;

    private static final Color LIGHT =
            new Color(240, 217, 181);

    private static final Color DARK =
            new Color(181, 136, 99);

    private final Board board;
    private final JButton[][] squares = new JButton[SIZE][SIZE];

    public ChessboardGUI() {

        board = new Board();

        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

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

