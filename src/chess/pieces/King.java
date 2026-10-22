package chess.pieces;

import chess.board.Board;
import chess.board.Position;
import java.util.List;

/** A king, which moves one square in any direction (check and castling are not handled yet). */
public class King extends Piece {
    /** The eight surrounding squares as {row, column} offsets. */
    private static final int[][] OFFSETS = {
        {-1, -1}, {-1, 0}, {-1, 1},
        {0, -1},           {0, 1},
        {1, -1},  {1, 0},  {1, 1}
    };

    /** Creates a king of the given color on the given square. */
    public King(Color color, Position position) {
        super(color, position, "K");
    }

    /** Returns every adjacent square the king can move to. */
    @Override
    public List<Position> possibleMoves(Board board) {
        return stepMoves(board, OFFSETS);
    }
}