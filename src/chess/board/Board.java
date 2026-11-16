
package chess.board;

import chess.pieces.Bishop;
import chess.pieces.Color;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;

/**
 * Represents the chessboard and manages piece placement,
 * movement, and captures.
 */
public class Board {

    private final Piece[][] squares = new Piece[8][8];

    /**
     * Creates a chessboard with the standard starting positions.
     */
    public Board() {
        initializeBoard();
    }

    /**
     * Initializes all chess pieces in their starting positions.
     */
    public void initializeBoard() {
        clear();

        for (int col = 0; col < 8; col++) {
            squares[0][col] = createPiece(col, Color.BLACK, 0, col);
            squares[1][col] = new Pawn(Color.BLACK, new Position(1, col));
            squares[6][col] = new Pawn(Color.WHITE, new Position(6, col));
            squares[7][col] = createPiece(col, Color.WHITE, 7, col);
        }
    }

    /**
     * Creates a piece for the back row.
     *
     * @param col column of the piece
     * @param color color of the piece
     * @param row starting row
     * @param column starting column
     * @return the created chess piece
     */
    private Piece createPiece(int col, Color color, int row, int column) {
        Position position = new Position(row, column);

        switch (col) {
            case 0:
            case 7:
                return new Rook(color, position);
            case 1:
            case 6:
                return new Knight(color, position);
            case 2:
            case 5:
                return new Bishop(color, position);
            case 3:
                return new Queen(color, position);
            case 4:
                return new King(color, position);
            default:
                throw new IllegalArgumentException("Invalid column.");
        }
    }

    /**
     * Displays the chessboard in the console.
     */
    public void display() {
        System.out.println("    A    B    C    D    E    F    G    H");
        System.out.println("  +----+----+----+----+----+----+----+----+");

        for (int row = 0; row < 8; row++) {
            System.out.print((8 - row) + " |");

            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                String symbol = piece == null ? "##" : piece.getSymbol();
                System.out.print(" " + symbol + " |");
            }

            System.out.println(" " + (8 - row));
            System.out.println("  +----+----+----+----+----+----+----+----+");
        }

        System.out.println("    A    B    C    D    E    F    G    H");
    }

    /**
     * Checks whether coordinates are inside the board.
     *
     * @param row board row
     * @param col board column
     * @return true if coordinates are valid
     */
    public boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    /**
     * Returns the piece at a specified position.
     *
     * @param position square to inspect
     * @return the piece or null
     */
    public Piece getPiece(Position position) {
        if (position == null || !position.isInBounds()) {
            throw new IllegalArgumentException("Invalid board position.");
        }

        return squares[position.getRow()][position.getColumn()];
    }

    /**
     * Original Phase 1 movement method.
     * Uses standard piece movement validation.
     *
     * @param from starting square
     * @param to destination square
     * @return true if the move succeeds
     */
    public boolean movePiece(Position from, Position to) {
        if (from == null || to == null
                || !from.isInBounds() || !to.isInBounds()) {
            return false;
        }

        Piece piece = getPiece(from);

        if (piece == null || !piece.canMoveTo(this, to)) {
            return false;
        }

        squares[to.getRow()][to.getColumn()] = piece;
        squares[from.getRow()][from.getColumn()] = null;

        piece.move(to);

        return true;
    }

    /**
     * Moves a piece to any valid square for Phase 2.
     *
     * Standard chess movement restrictions are not enforced.
     * An opposing piece at the destination is captured.
     * Friendly pieces cannot be captured.
     *
     * @param from starting square
     * @param to destination square
     * @return true if the move succeeds
     */
    public boolean movePieceFree(Position from, Position to) {
        if (from == null || to == null
                || !from.isInBounds() || !to.isInBounds()) {
            return false;
        }

        if (from.getRow() == to.getRow()
                && from.getColumn() == to.getColumn()) {
            return false;
        }

        Piece movingPiece = getPiece(from);
        Piece targetPiece = getPiece(to);

        if (movingPiece == null) {
            return false;
        }

        // Prevent capturing a friendly piece.
        if (targetPiece != null
                && targetPiece.getColor() == movingPiece.getColor()) {
            return false;
        }

        // Move the piece and capture an opponent if present.
        squares[to.getRow()][to.getColumn()] = movingPiece;
        squares[from.getRow()][from.getColumn()] = null;

        movingPiece.move(to);

        return true;
    }

    /**
     * Removes all pieces from the chessboard.
     */
    public void clear() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col] = null;
            }
        }
    }

    /**
     * Places a piece at a specified position.
     * Used when loading a saved game.
     *
     * @param position destination square
     * @param piece piece to place
     */
    public void setPiece(Position position, Piece piece) {
        if (position == null || !position.isInBounds()) {
            throw new IllegalArgumentException("Invalid board position.");
        }

        squares[position.getRow()][position.getColumn()] = piece;

        if (piece != null) {
            piece.move(position);
        }
    }
}
