
package chess.board;

import chess.pieces.Bishop;
import chess.pieces.Color;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;

// Handles the chessboard and its pieces.
public class Board {

    private final Piece[][] squares = new Piece[8][8];

    // Sets up the board.
    public Board() {
        initializeBoard();
    }

    // Puts all the pieces in their starting spots.
    public void initializeBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col] = null;
            }
        }

        for (int col = 0; col < 8; col++) {
            squares[0][col] = createPiece(col, Color.BLACK, 0, col);
            squares[1][col] = new Pawn(Color.BLACK, new Position(1, col));
            squares[6][col] = new Pawn(Color.WHITE, new Position(6, col));
            squares[7][col] = createPiece(col, Color.WHITE, 7, col);
        }
    }

    // Creates the correct piece for the back row.
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

    // Prints the board.
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

    // Checks if a position is on the board.
    public boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    // Returns the piece at a position, or null if it's empty.
    public Piece getPiece(Position position) {
        if (position == null || !position.isInBounds()) {
            throw new IllegalArgumentException("Invalid board position.");
        }

        return squares[position.getRow()][position.getColumn()];
    }

    // Moves a piece if the destination is valid and allowed.
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
}
