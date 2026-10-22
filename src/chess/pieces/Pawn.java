package chess.pieces;

import chess.board.Board;
import chess.board.Position;
import java.util.ArrayList;
import java.util.List;

/** A pawn, which moves forward, captures diagonally, and can advance two squares from its starting row. */
public class Pawn extends Piece {
    /** Creates a pawn of the given color on the given square. */
    public Pawn(Color color, Position position) {
        super(color, position, "p");
    }

    /** Returns forward moves and diagonal captures (promotion and en passant are not handled yet). */
    @Override
    public List<Position> possibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int row = getPosition().getRow();
        int column = getPosition().getColumn();

        // White moves toward row 0 (rank 8) and black toward row 7 (rank 1)
        int direction = getColor() == Color.WHITE ? -1 : 1;
        int startRow = getColor() == Color.WHITE ? 6 : 1;

        // One step forward, only onto an empty square
        Position oneStep = new Position(row + direction, column);
        if (oneStep.isInBounds() && board.getPiece(oneStep) == null) {
            moves.add(oneStep);

            // Two steps from the starting row, only if both squares are empty
            Position twoSteps = new Position(row + 2 * direction, column);
            if (row == startRow && board.getPiece(twoSteps) == null) {
                moves.add(twoSteps);
            }
        }

        // Diagonal captures, only onto an opponent's piece
        for (int side = -1; side <= 1; side += 2) {
            Position target = new Position(row + direction, column + side);
            if (target.isInBounds()) {
                Piece occupant = board.getPiece(target);
                if (occupant != null && occupant.getColor() != getColor()) {
                    moves.add(target);
                }
            }
        }
        return moves;
    }
}
