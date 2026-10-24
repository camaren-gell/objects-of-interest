package chess.pieces;

import chess.board.Board;
import chess.board.Position;
import java.util.List;

/** A queen, which slides any distance along ranks, files, and diagonals. */
public class Queen extends Piece {
    /** Straight and diagonal directions as {row, column} steps. */
    private static final int[][] DIRECTIONS = {
        {-1, 0}, {1, 0}, {0, -1}, {0, 1},
        {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
    };

    /** Creates a queen of the given color on the given square. */
    public Queen(Color color, Position position) {
        super(color, position, "Q");
    }

    /** Returns every square the queen can slide to. */
    @Override
    public List<Position> possibleMoves(Board board) {
        return slideMoves(board, DIRECTIONS);
    }
}
