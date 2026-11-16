package chess.utils;

import chess.board.Board;
import chess.board.Position;
import chess.pieces.Bishop;
import chess.pieces.Color;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;

/** Saves and loads a game as a plain text file. */
public final class GameStorage {
    /** First line of every save file, used to reject other files. */
    private static final String HEADER = "CHESS_SAVE_V1";

    private GameStorage() {
    }

    /** Writes the current turn and the board (one line per rank, A8 first) to the file. */
    public static void save(Board board, Color turn, File file) throws IOException {
        try (PrintWriter out = new PrintWriter(file)) {
            out.println(HEADER);
            out.println(turn);
            for (int row = 0; row < 8; row++) {
                StringBuilder line = new StringBuilder();
                for (int col = 0; col < 8; col++) {
                    Piece piece = board.getPiece(new Position(row, col));
                    line.append(piece == null ? "--" : piece.getSymbol());
                    if (col < 7) {
                        line.append(' ');
                    }
                }
                out.println(line);
            }
        }
    }

    /**
     * Reads a save file into the given board and returns whose turn it was.
     * The board is only changed if the whole file is valid.
     */
    public static Color load(File file, Board target) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());
        if (lines.size() != 10 || !lines.get(0).trim().equals(HEADER)) {
            throw new IOException("This is not a valid chess save file.");
        }

        Color turn;
        try {
            turn = Color.valueOf(lines.get(1).trim());
        } catch (IllegalArgumentException e) {
            throw new IOException("The save file has an invalid turn.", e);
        }

        // Read everything into a temporary grid first so a bad file cannot damage the board
        Piece[][] parsed = new Piece[8][8];
        int whiteKings = 0;
        int blackKings = 0;
        for (int row = 0; row < 8; row++) {
            String[] cells = lines.get(row + 2).trim().split("\\s+");
            if (cells.length != 8) {
                throw new IOException("Rank " + (8 - row) + " of the save file is invalid.");
            }
            for (int col = 0; col < 8; col++) {
                if (!cells[col].equals("--")) {
                    parsed[row][col] = createPiece(cells[col], new Position(row, col));
                    if (parsed[row][col] instanceof King) {
                        if (parsed[row][col].getColor() == Color.WHITE) {
                            whiteKings++;
                        } else {
                            blackKings++;
                        }
                    }
                }
            }
        }
        if (whiteKings != 1 || blackKings != 1) {
            throw new IOException("The save file must have exactly one king per side.");
        }

        target.clear();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (parsed[row][col] != null) {
                    target.setPiece(new Position(row, col), parsed[row][col]);
                }
            }
        }
        return turn;
    }

    /** Builds a piece from a symbol such as "wR" or "bp". */
    private static Piece createPiece(String symbol, Position pos) throws IOException {
        if (symbol.length() != 2) {
            throw new IOException("Unknown piece \"" + symbol + "\".");
        }
        Color color;
        if (symbol.charAt(0) == 'w') {
            color = Color.WHITE;
        } else if (symbol.charAt(0) == 'b') {
            color = Color.BLACK;
        } else {
            throw new IOException("Unknown piece \"" + symbol + "\".");
        }
        switch (symbol.charAt(1)) {
            case 'p': return new Pawn(color, pos);
            case 'R': return new Rook(color, pos);
            case 'N': return new Knight(color, pos);
            case 'B': return new Bishop(color, pos);
            case 'Q': return new Queen(color, pos);
            case 'K': return new King(color, pos);
            default: throw new IOException("Unknown piece \"" + symbol + "\".");
        }
    }
}
