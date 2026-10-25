package chess.utils;

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

        if (moveInput.length() != 5) { // validates length
            return false;
        }

        if (moveInput.charAt(2) != ' ') { // validates space
            return false;
        }

        if (moveInput.charAt(0) < 'A' || moveInput.charAt(0) > 'H') { // validates first column
            return false;
        }

        if (moveInput.charAt(3) < 'A' || moveInput.charAt(3) > 'H') { // validates second column
            return false;
        }

        if (moveInput.charAt(1) < '1' || moveInput.charAt(1) > '8') { // validates first row
            return false;
        }

        if (moveInput.charAt(4) < '1' || moveInput.charAt(4) > '8') { // validates second row
            return false;
        }

        return true;
    }
}