import chess.board.Board;
import chess.board.Position;
import chess.gui.PieceGlyphs;
import chess.pieces.Piece;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

/** Shows the starting position with the new piece graphics. */
public class BoardPreview {
    public static void main(String[] args) {
        Board board = new Board();
        int size = 80;
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        g.setColor((row + col) % 2 == 0 ? new Color(240, 217, 181) : new Color(181, 136, 99));
                        g.fillRect(col * size, row * size, size, size);
                        Piece piece = board.getPiece(new Position(row, col));
                        if (piece != null) {
                            PieceGlyphs.paint((Graphics2D) g, piece, col * size, row * size, size);
                        }
                    }
                }
            }
        };
        panel.setPreferredSize(new Dimension(8 * size, 8 * size));
        JFrame frame = new JFrame("Starting position");
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}