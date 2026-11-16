import chess.board.Board;
import chess.board.Position;
import chess.pieces.Color;
import chess.pieces.Piece;
import chess.utils.GameStorage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/** Checks that saving and loading restores the exact game state. */
public class SaveLoadTest {
    private static int failures = 0;

    private static void check(String name, boolean ok) {
        System.out.println((ok ? "PASS " : "FAIL ") + name);
        if (!ok) {
            failures++;
        }
    }

    public static void main(String[] args) throws IOException {
        File file = File.createTempFile("chess", ".chess");
        file.deleteOnExit();

        // Play a few moves, including a capture, then save with Black to move
        Board original = new Board();
        original.movePiece(Position.fromString("E2"), Position.fromString("E4"));
        original.movePiece(Position.fromString("D7"), Position.fromString("D5"));
        original.movePiece(Position.fromString("E4"), Position.fromString("D5"));
        GameStorage.save(original, Color.BLACK, file);

        Board loaded = new Board();
        Color turn = GameStorage.load(file, loaded);
        check("turn restored", turn == Color.BLACK);

        boolean same = true;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Position pos = new Position(row, col);
                Piece a = original.getPiece(pos);
                Piece b = loaded.getPiece(pos);
                if (a == null || b == null) {
                    same &= a == b;
                } else {
                    same &= a.getSymbol().equals(b.getSymbol()) && b.getPosition().equals(pos);
                }
            }
        }
        check("every square and piece position matches", same);
        check("captured pawn stays gone", loaded.getPiece(Position.fromString("D7")) == null);
        check("loaded pieces can still move", loaded.movePiece(Position.fromString("D8"), Position.fromString("D5")));

        // A bad file must be rejected and must not change the board
        File bad = File.createTempFile("bad", ".chess");
        bad.deleteOnExit();
        Files.writeString(bad.toPath(), "hello");
        Board untouched = new Board();
        boolean rejected = false;
        try {
            GameStorage.load(bad, untouched);
        } catch (IOException e) {
            rejected = true;
        }
        check("bad file rejected", rejected);
        check("board untouched after bad file", untouched.getPiece(Position.fromString("E2")) != null);

        System.out.println(failures == 0 ? "All tests passed" : failures + " test(s) failed");
    }
}
