package chess.gui;

import chess.pieces.Color;
import chess.pieces.Piece;
import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import javax.swing.Icon;

/** Draws chess pieces as filled, outlined Unicode shapes. */
public final class PieceGlyphs {
    private PieceGlyphs() {
    }

    /** Returns the Unicode symbol for the piece's type. */
    public static String glyph(Piece piece) {
        // Second character of the piece symbol is the type: p, R, N, B, Q, K
        switch (piece.getSymbol().charAt(1)) {
            case 'p': return "\u265F";
            case 'R': return "\u265C";
            case 'N': return "\u265E";
            case 'B': return "\u265D";
            case 'Q': return "\u265B";
            case 'K': return "\u265A";
            default: return "?";
        }
    }

    /** Paints the piece centered in a square whose top-left corner is (x, y). */
    public static void paint(Graphics2D g, Piece piece, int x, int y, int size) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Segoe UI Symbol", Font.PLAIN, (int) (size * 0.8));
        TextLayout layout = new TextLayout(glyph(piece), font, g.getFontRenderContext());

        // Center the glyph in the square
        Rectangle2D bounds = layout.getBounds();
        double tx = x + (size - bounds.getWidth()) / 2 - bounds.getX();
        double ty = y + (size - bounds.getHeight()) / 2 - bounds.getY();
        Shape outline = layout.getOutline(AffineTransform.getTranslateInstance(tx, ty));

        // Fill with the piece's color, outline in black so white pieces show on light squares
        g.setColor(piece.getColor() == Color.WHITE ? java.awt.Color.WHITE : java.awt.Color.BLACK);
        g.fill(outline);
        g.setColor(java.awt.Color.BLACK);
        g.setStroke(new BasicStroke(1.5f));
        g.draw(outline);
    }

    /** Returns an icon of the piece that can be set on a button with setIcon. */
    public static Icon icon(Piece piece, int size) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                paint(g2, piece, x, y, size);
                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return size;
            }

            @Override
            public int getIconHeight() {
                return size;
            }
        };
    }
}