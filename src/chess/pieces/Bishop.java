package chess.pieces;

import chess.board.Board;
import chess.board.Position;
import java.util.List;

/** A bishop, which slides any distance along diagonals. */
public class Bishop extends Piece {
    /** Diagonal directions as {row, column} steps. */
    private static final int[][] DIRECTIONS = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

    /** Creates a bishop of the given color on the given square. */
    public Bishop(Color color, Position position) {
        super(color, position, "B");
    }

    /** Returns every square the bishop can slide to. */
    @Override
    public List<Position> possibleMoves(Board board) {
        return slideMoves(board, DIRECTIONS);
    }
}
