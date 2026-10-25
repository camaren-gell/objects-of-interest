\# Objects of Interest - Java Chess Game



Welcome to the \*\*Objects of Interest\*\* team repository!



\## Course Information



\*\*Course:\*\* Object-Oriented Programming  

\*\*Project:\*\* Chess Game — Phase 1  

\*\*Language:\*\* Java  

\*\*Development Environment:\*\* JDK 21, Git, GitHub



\## Team Members



| Team Member | Course Identifier | GitHub Username |

|---|---|---|

| Camaren Gell | qha16 | camaren-gell |

| Katalyna Hinojosa | kzy18 | bellaiscool93 |

| Franceli Gonzalez | tdq14 | francelivgonzalez |



\## Project Overview



This project implements a console-based chess game using object-oriented programming principles in Java.



The program initializes a standard chessboard, displays chess pieces, accepts user moves, validates movement, and alternates between White and Black players.



The project was developed collaboratively using Git branches, pull requests, code reviews, and documentation.



\## Features Implemented



\### Chessboard

\- Initializes an 8×8 chessboard.

\- Places all 32 pieces in their standard starting positions.

\- Displays the board using ASCII characters.

\- Updates the board after successful moves.



\### Chess Pieces

The project includes six chess piece types:



\- Pawn

\- Rook

\- Knight

\- Bishop

\- Queen

\- King



Each piece has its own movement logic using shared functionality from the `Piece` class.



\### Game Controller

\- White moves first.

\- Players alternate turns after successful moves.

\- Invalid moves do not change turns.

\- Players cannot move an opponent's piece.

\- Players cannot capture their own pieces.

\- Illegal moves are rejected.

\- Successful captures display an announcement.



\### Input Validation

Moves use the following format:



`E2 E4`



The program supports:



\- Uppercase and lowercase coordinates.

\- Extra whitespace between coordinates.

\- Validation of chessboard coordinates A–H and 1–8.

\- Error messages for incorrectly formatted input.

\- The `quit` command to exit the game.



Successful moves are displayed in a consistent uppercase format.



\## Requirements



\- Java Development Kit (JDK) 21

\- Command Prompt, terminal, or Java-compatible IDE

\- Git for cloning and version control



The project was compiled and tested using Java 21.



\## Installation and Setup



\### 1. Clone the Repository



Open a terminal and run:



```bash

git clone https://github.com/camaren-gell/objects-of-interest.git

cd objects-of-interest

```



\### 2. Switch to the Development Branch



```bash

git switch dev

git pull origin dev

```



\### 3. Compile the Project



From the repository's root directory:



```bash

javac -d out -sourcepath src src/chess/Main.java

```



This compiles the Java source files and places the compiled classes in the `out` directory.



For Windows Command Prompt:



```cmd

javac -d out -sourcepath src src\\chess\\Main.java

```



\### 4. Run the Game



```bash

java -cp out chess.Main

```



The console will display the initialized chessboard and prompt White to enter the first move.



\## How to Play



Players enter moves using the starting square followed by the destination square.



For example:



```text

WHITE's turn

Enter move: E2 E4

E2 E4



BLACK's turn

Enter move: E7 E5

E7 E5

```



\### Example Game Sequence



```text

E2 E4

D7 D5

E4 D5

```



On the third move, the White pawn captures the Black pawn at D5.



Expected capture announcement:



```text

WHITE wp captured BLACK bp at D5!

```



The board updates and play continues with Black.



\### Invalid Move Example



```text

WHITE's turn

Enter move: E2 E5

Illegal move. Try again!



WHITE's turn

Enter move: E2 E4

```



The invalid move is rejected, and White retains the turn until a legal move is entered.



\### Exiting the Game



Enter:



```text

quit

```



The command is case-insensitive.



\## Project Structure



```text

objects-of-interest/

├── README.md

├── docs/

│   ├── ai-usage/

│   │   └── <github-username>.md

│   └── api/                  # Generated Javadocs, when included

└── src/

&#x20;   └── chess/

&#x20;       ├── Main.java

&#x20;       ├── board/

&#x20;       │   ├── Board.java

&#x20;       │   ├── BoardTest.java

&#x20;       │   └── Position.java

&#x20;       ├── game/

&#x20;       │   ├── Game.java

&#x20;       │   └── Player.java

&#x20;       ├── pieces/

&#x20;       │   ├── Piece.java

&#x20;       │   ├── Color.java

&#x20;       │   ├── Pawn.java

&#x20;       │   ├── Rook.java

&#x20;       │   ├── Knight.java

&#x20;       │   ├── Bishop.java

&#x20;       │   ├── Queen.java

&#x20;       │   └── King.java

&#x20;       └── utils/

&#x20;           └── InputValidator.java

```



\## Object-Oriented Design



The project separates responsibilities across classes.



| Class | Responsibility |

|---|---|

| `Main` | Starts the application |

| `Game` | Handles the gameplay loop and turns |

| `Player` | Stores the player's color |

| `Board` | Initializes, displays, and updates the board |

| `Position` | Represents chessboard coordinates |

| `Piece` | Provides shared chess piece behavior |

| Individual piece classes | Implement piece-specific movement |

| `InputValidator` | Validates move input |



The design uses inheritance, abstraction, and encapsulation to organize the game's functionality.



\## Testing



The team performed manual console testing to verify core functionality.



\### Tested Scenarios



| Test | Observed Result |

|---|---|

| Starting board initialization | Passed |

| Standard pawn movement | Passed |

| Knight movement | Passed |

| Valid pawn capture | Passed |

| Capture announcement | Passed |

| Invalid pawn movement | Passed |

| Invalid move retains turn | Passed |

| Moving from an empty square | Passed |

| Moving an opponent's piece | Passed |

| Attempting to capture a friendly piece | Passed |

| Lowercase move input | Passed |

| Extra spaces between coordinates | Passed |

| Case-insensitive quit command | Passed |



Additional testing of complete chess-game rules and less common movement scenarios may be needed.



\## Known Limitations



Phase 1 focuses on chessboard functionality, movement validation, user input, and object-oriented design.



The following advanced chess features are not currently documented as supported:



\- Castling

\- En passant

\- Pawn promotion

\- Check and checkmate detection

\- Stalemate and other complete game-ending conditions



These features should not be assumed to work without additional implementation and testing.



\## Javadoc Documentation



Javadoc is used to document the project classes and methods.



To generate documentation for the entire project:



```bash

javadoc -Xdoclint:all -d docs/api -sourcepath src -subpackages chess

```



The generated documentation can be opened using:



```text

docs/api/index.html

```



Javadoc generation has been tested successfully. Some classes still produce documentation warnings relating to missing comments and parameter or return descriptions.



These warnings do not prevent documentation from being generated.



\## Git and Collaboration



The team used a collaborative Git workflow:



1\. Develop features on individual branches.

2\. Commit changes with descriptive commit messages.

3\. Push branches to GitHub.

4\. Open pull requests targeting `dev`.

5\. Review teammates' changes.

6\. Resolve merge conflicts when necessary.

7\. Merge reviewed changes into `dev`.



Individual AI usage logs are maintained under `docs/ai-usage/`.



\## AI Usage



AI tools were used as learning and development resources for Java concepts, troubleshooting, code review, testing, documentation, and Git workflows.



Each team member is responsible for documenting their own AI usage accurately in their individual Markdown file.



\## Project Demonstration



\*\*Phase 1 Demonstration Video:\*\* To be added before final submission.



The demonstration should show the initialized board, valid moves, invalid input handling, captures, turn switching, and program exit.



\## Future Improvements



Potential future improvements include:



\- Implementing advanced chess rules.

\- Adding check and checkmate detection.

\- Improving game-ending conditions.

\- Adding automated unit tests.

\- Improving the command-line interface.

\- Supporting chess move history.



\---



\*\*Texas State University - Object-Oriented Programming - Chess Project Phase 1\*\*

