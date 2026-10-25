
package chess.utils;

import java.util.Locale;

/**
 * Validates chess move input.
 */
public class InputValidator {

    /**
     * Prevents creating InputValidator objects.
     */
    private InputValidator() { }

    /**
     * Checks if a move is in the required chess format.
     *
     * @param moveInput the move entered by the player
     * @return true if the move format is valid, false otherwise
     */
    public static boolean isValidMoveFormat(String moveInput) {

        return moveInput != null &&
               moveInput.trim()
                        .toUpperCase(Locale.ROOT)
                        .matches("[A-H][1-8]\\s+[A-H][1-8]");
    }
}
