
package chess.game;

import chess.board.Board;
import java.util.Scanner;
import java.util.Locale;
import chess.utils.InputValidator;
import chess.board.Position;
import chess.pieces.Piece;
import chess.pieces.Color;

/**
 * Manages chess gameplay and player turns.
 */
public class Game {

    /**
     * Creates a new chess game.
     */
    public Game() {
    }

    /**
     * Starts the chess game and processes player moves.
     */
    public void start() {

        Board board = new Board(); // creates board
        Player whitePlayer = new Player(Color.WHITE); // creates new player that is White
        Player blackPlayer = new Player(Color.BLACK); // creates new player that is Black
        Player currentPlayer = whitePlayer; // white starts

        board.display(); // displays board

        boolean gameRunning = true; // establishes that game is running

        Scanner scanner = new Scanner(System.in); // creates keyboard input

        while (gameRunning) { //loop that iterates until game is over

            System.out.println(currentPlayer.getColor() + "'s turn");
            System.out.print("Enter move: ");

            String moveInput = scanner.nextLine().trim().toUpperCase(Locale.ROOT); // reads and normalizes input

            if (moveInput.equals("QUIT")) {
                break; // exits game
            }

            if (!InputValidator.isValidMoveFormat(moveInput)) {
                System.out.println("Invalid move format!"); // prints error
                continue; // sends it to retry
            }

            String[] squares = moveInput.split("\\s+"); // splits input into positions

            Position from = Position.fromString(squares[0]); // starting place
            Position to = Position.fromString(squares[1]); // destination place

            Piece selectedPiece = board.getPiece(from); // gets moving piece
            Piece destinationPiece = board.getPiece(to); // gets target piece

            if (destinationPiece != null &&
                destinationPiece.getColor() == currentPlayer.getColor()) {

                System.out.println("You cannot capture your own piece!");
                continue; // retries turn
            }

            if (selectedPiece == null) { // checks starting square
                System.out.println("No piece at that position.");
                continue; // lets player try again
            }

            if (selectedPiece.getColor() != currentPlayer.getColor()) {
                System.out.println("You cannot move your opponent's piece!");
                continue; // retries turn
            }

            boolean moveSuccessful = board.movePiece(from, to); // checks and executes move

            if (!moveSuccessful) {
                System.out.println("Illegal move. Try again!");
                continue; // retries turn
            }

            // capture message
            if (destinationPiece != null) {

                String movingPiece = selectedPiece.getSymbol(); // attacking piece
                String capturedPiece = destinationPiece.getSymbol(); // captured piece

                System.out.println(
                    currentPlayer.getColor() + " " + movingPiece +
                    " captured " + destinationPiece.getColor() + " " +
                    capturedPiece + " at " + squares[1] + "!"
                );
            }

            System.out.println(squares[0] + " " + squares[1]); // prints clean move
            board.display(); // updates board display

            // switches turns
            if (currentPlayer == whitePlayer) {
                currentPlayer = blackPlayer;
            }
            else {
                currentPlayer = whitePlayer;
            }
        }
    }
}
