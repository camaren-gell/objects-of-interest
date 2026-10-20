package chess.pieces;

import chess.board.Board;
import chess.board.Position;
import java.util.List;

/** A knight, which jumps in an L shape and is not blocked by pieces in between. */
public class Knight extends Piece {
    /** The eight L-shaped jumps as {row, column} offsets. */
    private static final int[][] OFFSETS = {
        {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
        {1, -2}, {1, 2}, {2, -1}, {2, 1}
    };

    /** Creates a knight of the given color on the given square. */
    public Knight(Color color, Position position) {
        super(color, position, "N");
    }

    /** Returns every square the knight can jump to. */
    @Override
    public List<Position> possibleMoves(Board board) {
        return stepMoves(board, OFFSETS);
    }
}