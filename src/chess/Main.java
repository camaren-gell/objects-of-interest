
 //The package declaration for chess.
 //An import for Game from chess.game.
 //A public class named Main.

package chess;
import chess.game.Game;

/**
 * Starts the chess game.
 */
public class Main{

    /**
     * Creates a new Main object.
     */
    public Main() {
    }

    /**
     * Runs the chess game.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args){

        Game game = new Game();// Game class, game object
        game.start();//starting object

    }

}
