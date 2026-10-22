package chess.pieces;

/**
 * Represents the two sides in a chess game.
 * Shared by pieces, players, and the board so every class uses the same values.
 */
public enum Color {
    /** The white side, which moves first. */
    WHITE,
    /** The black side. */
    BLACK
}