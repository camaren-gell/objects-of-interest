package chess.pieces;

import chess.board.Board;
import chess.board.Position;
import java.util.List;

/** A rook, which slides any distance along ranks and files. */
public class Rook extends Piece {
    /** Straight directions as {row, column} steps. */
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    /** Creates a rook of the given color on the given square. */
    public Rook(Color color, Position position) {
        super(color, position, "R");
    }

    /** Returns every square the rook can slide to. */
    @Override
    public List<Position> possibleMoves(Board board) {
        return slideMoves(board, DIRECTIONS);
    }
}
