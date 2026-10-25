\# Chess Game - Integration Notes



\## File Structure



We'll use this structure to keep our files organized and make it easier to combine everyone's work. Each section has its own files so we can work separately without too much overlap.



```text

src/

└── chess/

&#x20;   │

&#x20;   │   SECTION 3: Game Loop

&#x20;   ├── Main.java

&#x20;   ├── game/

&#x20;   │   ├── Game.java

&#x20;   │   └── Player.java

&#x20;   │

&#x20;   │   SECTION 2: Chessboard

&#x20;   ├── board/

&#x20;   │   ├── Board.java

&#x20;   │   └── Position.java

&#x20;   │

&#x20;   │   SECTION 1: Chess Pieces

&#x20;   ├── pieces/

&#x20;   │   ├── Piece.java

&#x20;   │   ├── Pawn.java

&#x20;   │   ├── Rook.java

&#x20;   │   ├── Knight.java

&#x20;   │   ├── Bishop.java

&#x20;   │   ├── Queen.java

&#x20;   │   ├── King.java

&#x20;   │   └── Color.java

&#x20;   │

&#x20;   │   SECTION 3: Input Validation

&#x20;   └── utils/

&#x20;       └── InputValidator.java

```



\## Responsibilities



\### Section 1: Chess Pieces

This section handles the Piece class and the six different chess pieces. Each piece will have its own movement rules. It also includes the Color enum so we're using the same color values throughout the game.



\### Section 2: Chessboard

This section handles the 8x8 board, placing the pieces in their starting positions, and displaying the board in the console. It will also handle updating the board when pieces move.



\### Section 3: Game Loop and Input

This section handles starting the game, player turns, and accepting moves like `E2 E4`. It will check the input, communicate with Board to attempt moves, and switch turns when a move is successful.



\## How Everything Connects



When someone enters a move, Game will read the input and check whose turn it is. Board will handle updating the piece's position, while the Piece classes will determine whether that type of move is allowed.



To make sure everything works together, we're planning to use these methods:



\*\*Section 1: Piece.java\*\*

\- `getColor()` returns the piece's color.

\- `possibleMoves(Board board)` returns the possible moves for that piece.



\*\*Section 2: Board.java\*\*

\- `display()` prints the board.

\- `getPiece(Position position)` returns the piece at a specific square.

\- `movePiece(Position from, Position to)` attempts a move and returns true or false.



\*\*Section 2: Position.java\*\*

\- `getRow()` and `getColumn()` return the row and column.



We'll use A8 as `(0,0)` and H1 as `(7,7)`.



\*\*Section 3: Game.java\*\*

\- `start()` starts the game.

\- `play()` handles the game loop.

\- `switchTurn()` changes the current player.



These are the methods we're planning to use. If anything needs to change, we can communicate through GitHub so everyone's code stays compatible.



\## GitHub



We'll each work on our own feature branch and commit our progress as we go. Once our sections are ready, we'll open pull requests into `dev` to review and combine our code. We'll use `main` for the final version.



