
package chess.gui;

import chess.board.Board;
import chess.board.Position;
import chess.pieces.Piece;

import javax.swing.*;
import javax.imageio.ImageIO;
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

    // Default settings
    private String currentBoardStyle = "Spider-Man";
    private String currentPieceStyle = "Black & Green";
    private String currentBoardSize = "Medium";

    public ChessboardGUI() {

        board = new Board();

        loadSpiderEyesLogo();

        setTitle("Spider-Man Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Settings button
        JPanel topPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT)
        );

        JButton settingsButton = new JButton("⚙ Settings");

        settingsButton.addActionListener(e -> {
            SettingsWindow settings = new SettingsWindow(this);
            settings.setVisible(true);
        });

        topPanel.add(settingsButton);
        add(topPanel, BorderLayout.NORTH);

        // Chessboard with one centered background logo
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

                    // Draw one centered watermark
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

        // Create 64 individual clickable squares
        for (int row = 0; row < SIZE; row++) {

            for (int col = 0; col < SIZE; col++) {

                SpiderSquare square = new SpiderSquare();

                square.setFont(
                        new Font("Serif", Font.PLAIN, 54)
                );

                squares[row][col] = square;
                chessboard.add(square);
            }
        }

        add(chessboard, BorderLayout.CENTER);

        applyBoardAppearance();
        updateBoard();

        pack();
        setLocationRelativeTo(null);
    }

    // Load the transparent Spider-Man eyes image
    private void loadSpiderEyesLogo() {

        try {

            // First try loading from the Java classpath
            java.net.URL imageURL =
                    getClass().getResource("spider-eyes-watermark.png");

            if (imageURL != null) {

                spiderEyesLogo = ImageIO.read(imageURL);

            } else {

                // Fallback to the project folder
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

    // Apply settings from SettingsWindow
    public void applySettings(
            String boardStyle,
            String pieceStyle,
            String boardSize) {

        currentBoardStyle = boardStyle;
        currentPieceStyle = pieceStyle;
        currentBoardSize = boardSize;

        applyBoardAppearance();
        updateBoard();

        // Actual chessboard size
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

        // Resize chess piece symbols
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

    // Change board colors based on selected theme
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

    // Display existing chess pieces
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

                    squares[row][col].setForeground(
                            getPieceColor(piece)
                    );
                }
            }
        }
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

    // Custom square that allows the center logo to show through
    private static class SpiderSquare extends JButton {

        public SpiderSquare() {

            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();

            // Slight transparency for the watermark
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

            // Draw chess pieces normally
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


