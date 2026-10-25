 //package declaration for chess.game
package chess.game;

// order to import is package,subpackage, class
import chess.pieces.Color;

//public class named player
/** Creates a class that represents one chess player. */
public class Player {
    //A private attribute named color with the type Color- uses enum for type safety
    /**Stores the player's color.*/
    private Color color;

    /**Constructor purpose: what information it needs to know and assign.
     * @param color the color assigned to the player
     */
    public Player(Color color){

        this.color = color; //uses the current object and applies the attribute

    }

        /**Getter: returning a private datatype encapsulated.
         * @return the player's color
         */
        public Color getColor(){
            return color;
	}
}