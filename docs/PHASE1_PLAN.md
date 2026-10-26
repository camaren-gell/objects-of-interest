# Chess Game - Phase 1 Plan

## Objective

For this project, we're creating a two-player chess game in Java that runs through the console. The goal is to apply what we've learned about object-oriented programming while making sure all of our classes work together.

## Team Responsibilities

We decided to split the project into three main parts so everyone can work on their own section without too much overlap.

- **Chess Pieces:** Create the abstract `Piece` class and the six subclasses (Pawn, Rook, Knight, Bishop, Queen, and King). This includes setting up the basic movement rules for each piece.
- **Board:** Create the 8x8 chessboard, place the pieces in their correct starting positions, and display the board in the console.
- **Game Loop:** Create the `Game`, `Player`, and `Main` classes. This includes handling player input, switching turns, and checking whether moves are valid.

## Milestone 1: Project Setup

- Set up the GitHub repository and create separate branches for each team member.
- Organize our Java files into packages.
- Make sure we're using the same methods and class references so everything can connect later.

**Manual Test:** Make sure the project compiles and that our folders and files are organized correctly.

## Milestone 2: Chess Pieces and Board

- Create the different chess piece classes and their movement rules.
- Set up the board with all pieces in their starting positions.
- Make sure the board displays correctly in the console.

**Manual Test:** Run the program and check that all 32 pieces are in the correct positions.

## Milestone 3: Game Loop and User Input

- Allow players to enter moves using coordinates like `E2 E4`.
- Check that the input is formatted correctly.
- Make sure players can only move their own pieces and cannot capture their own pieces.
- Switch turns after a successful move and allow players to try again if their move is invalid.

**Manual Test:** Try valid and invalid moves to make sure the program responds correctly, updates the board, and switches turns when appropriate.

## Milestone 4: Integration and Documentation

- Combine everyone's code and make sure the classes work together.
- Add Javadoc comments and generate the project documentation.
- Test the game to catch any issues before submitting.
- Review each other's code and merge our pull requests into the `dev` branch.

**Manual Test:** Run the game with both players, test different moves and incorrect inputs, and make sure the program continues running without errors.

## Development Workflow

We'll each work on our own GitHub feature branch and commit our progress as we complete different parts of the project. We'll also keep track of any AI assistance we use, as required by the rubric.

Once our sections are ready, we'll create pull requests into the `dev` branch, review each other's work, and fix any issues before combining everything into the final project.


## AI-Assisted Planning

ChatGPT was used to help organize the development roadmap, identify implementation milestones, and discuss how the different sections of the project would integrate. The team reviewed the plan and made decisions about task assignments, development priorities, and testing.
