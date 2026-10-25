package chess.board;

/** A square on the board, stored as a zero-based row and column (A8 = 0,0 and H1 = 7,7). */
public class Position {
    /** Number of rows and columns on the board. */
    public static final int BOARD_SIZE = 8;

    /** Row index, where 0 is rank 8 and 7 is rank 1. */
    private final int row;

    /** Column index, where 0 is file A and 7 is file H. */
    private final int column;

    /** Creates a position; off-board values are allowed so they can be checked with isInBounds. */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /** Returns the row index. */
    public int getRow() {
        return row;
    }

    /** Returns the column index. */
    public int getColumn() {
        return column;
    }

    /** Returns true if this position is on the 8x8 board. */
    public boolean isInBounds() {
        return row >= 0 && row < BOARD_SIZE && column >= 0 && column < BOARD_SIZE;
    }

    /** Converts notation like "E2" into a Position; throws IllegalArgumentException if invalid. */
    public static Position fromString(String square) {
        if (square == null) {
            throw new IllegalArgumentException("Square cannot be null.");
        }
        String text = square.trim().toUpperCase();

        // Must be exactly one file letter and one rank digit
        if (text.length() != 2) {
            throw new IllegalArgumentException(
                    "Invalid square \"" + square + "\". Use a letter A-H and a number 1-8, like E2.");
        }
        char file = text.charAt(0);
        char rank = text.charAt(1);
        if (file < 'A' || file > 'H' || rank < '1' || rank > '8') {
            throw new IllegalArgumentException(
                    "Invalid square \"" + square + "\". Use a letter A-H and a number 1-8, like E2.");
        }

        // Rank 8 is row 0, so the rank is flipped; file A is already column 0
        return new Position(BOARD_SIZE - (rank - '0'), file - 'A');
    }

    /** Returns the square in notation (e.g. "E2"), or "(row,column)" if off the board. */
    @Override
    public String toString() {
        if (!isInBounds()) {
            return "(" + row + "," + column + ")";
        }
        return "" + (char) ('A' + column) + (BOARD_SIZE - row);
    }

    /** Two positions are equal when row and column match; needed for List.contains move checks. */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Position)) {
            return false;
        }
        Position that = (Position) other;
        return row == that.row && column == that.column;
    }

    /** Returns a hash code that matches equals. */
    @Override
    public int hashCode() {
        return 31 * row + column;
    }
}