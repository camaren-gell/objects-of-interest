package chess.pieces;

import chess.board.Board;
import chess.board.Position;
import java.util.ArrayList;
import java.util.List;

/** Base class for all chess pieces; stores color and position and defines how pieces report moves. */
public abstract class Piece {
    /** The side this piece belongs to. */
    private final Color color;

    /** Letter shown in the board display, such as "p" or "R". */
    private final String typeSymbol;

    /** The square this piece currently occupies. */
    private Position position;

    /** Creates a piece with its color, starting square, and display letter. */
    protected Piece(Color color, Position position, String typeSymbol) {
        this.color = color;
        this.position = position;
        this.typeSymbol = typeSymbol;
    }

    /** Returns the side this piece belongs to. */
    public Color getColor() {
        return color;
    }

    /** Returns the square this piece occupies. */
    public Position getPosition() {
        return position;
    }

    /** Returns the display symbol, such as "wp" or "bR". */
    public String getSymbol() {
        return (color == Color.WHITE ? "w" : "b") + typeSymbol;
    }

    /** Returns every square this piece can move to from its position (check is not considered yet). */
    public abstract List<Position> possibleMoves(Board board);

    /** Returns true if the target square is one of this piece's possible moves. */
    public boolean canMoveTo(Board board, Position target) {
        return possibleMoves(board).contains(target);
    }

    /** Updates this piece's own position; the Board is responsible for updating its grid. */
    public void move(Position newPosition) {
        this.position = newPosition;
    }

    /** Returns true if the square is on the board and is empty or holds an opponent's piece. */
    protected boolean canLandOn(Board board, Position target) {
        if (!target.isInBounds()) {
            return false;
        }
        Piece occupant = board.getPiece(target);
        return occupant == null || occupant.getColor() != color;
    }

    /** Returns moves along each {row, column} direction until the edge, a blocker, or a capture. */
    protected List<Position> slideMoves(Board board, int[][] directions) {
        List<Position> moves = new ArrayList<>();
        for (int[] d : directions) {
            Position next = new Position(position.getRow() + d[0], position.getColumn() + d[1]);
            while (canLandOn(board, next)) {
                moves.add(next);
                // Stop after capturing; the piece cannot jump over it
                if (board.getPiece(next) != null) {
                    break;
                }
                next = new Position(next.getRow() + d[0], next.getColumn() + d[1]);
            }
        }
        return moves;
    }

    /** Returns the one-step moves for each {row, column} offset (used by Knight and King). */
    protected List<Position> stepMoves(Board board, int[][] offsets) {
        List<Position> moves = new ArrayList<>();
        for (int[] o : offsets) {
            Position next = new Position(position.getRow() + o[0], position.getColumn() + o[1]);
            if (canLandOn(board, next)) {
                moves.add(next);
            }
        }
        return moves;
    }
}