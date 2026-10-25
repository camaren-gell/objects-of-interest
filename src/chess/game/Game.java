package chess.game;

import chess.board.Board;
import java.util.Scanner; 
import chess.utils.InputValidator;
import chess.board.Position;
import chess.pieces.Piece;
import chess.pieces.Color;


public class Game{
	
	public void start(){
		
		
		Board board = new Board(); 
		Player whitePlayer = new Player(Color.WHITE); // creates new player that is White
		Player blackPlayer = new Player(Color.BLACK); // creates new player that is Black
		Player currentPlayer = whitePlayer;
		
		
		board.display();
		
		boolean gameRunning = true; // establishes that game is running
		
		Scanner scanner = new Scanner(System.in); // creates keyboard input

			while(gameRunning){ //loop that iterates until game is over

			System.out.println(currentPlayer.getColor() + "'s turn");
			System.out.print("Enter move: ");
				
			String moveInput = scanner.nextLine(); //reads player input
			
			if (moveInput.equalsIgnoreCase("QUIT")){
				break;
}

			if(InputValidator.isValidMoveFormat(moveInput) != true){
				System.out.println("Invalid move format!");	 // prints error
				continue; 				 	 // sends it to retry 
}				
			String[] squares = moveInput.split(" "); // splits the Input to read positions

			Position from = Position.fromString(squares[0]); //starting place
			Position to = Position.fromString(squares[1]); // destination place

			Piece selectedPiece = board.getPiece(from);
			Piece destinationPiece = board.getPiece(to);

			if (destinationPiece != null && destinationPiece.getColor() == currentPlayer.getColor()){
			System.out.println("You cannot capture your own piece!");
			continue;
}
				
			if (selectedPiece == null){ // if selected piece is not at the right spot
			System.out.println("No piece at that position.");
			continue; // lets player try again
}
			
			if (selectedPiece.getColor() != currentPlayer.getColor() ){
				System.out.println("You cannot move your opponent's piece!");
				continue;
}
			
			boolean moveSuccessful = board.movePiece(from, to); // calls and stores whether the move succeeded

			if (!moveSuccessful){
				System.out.println("Illegal move. Try again!");
				continue;
}

			System.out.println(moveInput); //prints the input if valid
			board.display();

			if (currentPlayer == whitePlayer){
				currentPlayer = blackPlayer;
}
				else {
					currentPlayer = whitePlayer;
}
			

				
		
}
			
	
		
		
	
	}

}



