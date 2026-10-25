# Objects of Interest — Java Chess Game

## Course
Object-Oriented Programming

## Team Members
- Camaren Gell (`qha16`)
- Kataylna Hinojosa (`kzy18`)
- Francel(i) Gonzalez (`tdq14`)

## Project Overview
This project is a two-player chess game written in Java. It runs in the terminal and uses object-oriented programming to represent chess pieces, the board, and game flow.

## Requirements
- Java Development Kit (JDK) installed.
- A terminal with access to `javac` and `java`.

Check your Java installation:

```bash
java -version
javac -version
```

## Compile the Project
From the repository root, run:

```bash
mkdir -p out
javac -d out $(find src -name "*.java")
```

## Run the Game
Run:

```bash
java -cp out chess.Main
```

## Entering Moves
Moves use a source square followed by a destination square, separated by a space.

Example:

```text
E2 E4
```

To leave the game, enter:

```text
quit
```

## Project Structure

```text
src/chess/
├── Main.java
├── board/
│   ├── Board.java
│   ├── BoardTest.java
│   └── Position.java
├── game/
│   ├── Game.java
│   └── Player.java
├── pieces/
│   ├── Piece.java
│   ├── Pawn.java
│   ├── Rook.java
│   ├── Knight.java
│   ├── Bishop.java
│   ├── Queen.java
│   ├── King.java
│   └── Color.java
└── utils/
    └── InputValidator.java
```

## Features
- Console display of the chessboard.
- Object-oriented classes for chess pieces.
- Two-player turn flow.
- Move input parsing and basic format validation.
- Basic checks for empty source squares and attempts to capture one's own pieces.

## Limitations
This is a Phase One project. Do not assume that every official chess rule is implemented. Check, checkmate, castling, en passant, pawn promotion, and full legal-move enforcement may not be supported.

## Testing
Compile the project using the commands above. Run the game and try a correctly formatted move, malformed input, an empty source square, and `quit`.

## Documentation
Javadoc documentation can be generated from the source files using:

```bash
javadoc -d docs/api -sourcepath src -subpackages chess
```
