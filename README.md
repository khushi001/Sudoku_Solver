# Sudoku Solver

Sudoku Solver is a Java application that allows users to play and solve Sudoku puzzles. The application provides a graphical user interface (GUI) built using Swing, where users can interact with the puzzle and solve it step by step.

## Features

The Sudoku Solver application offers the following features:

- GUI-based Sudoku puzzle board.
- Ability to input numbers and solve the puzzle.
- Option to reset the puzzle and start over.
- Display the solution for the puzzle.
- Check for incorrect moves and highlight them.

## Getting Started

To run the Sudoku Solver application, follow these steps:

1. Ensure that you have Java Development Kit (JDK) installed on your system.
2. Download or clone the project source code.
3. Compile the Java source files using the `javac` command.
```
javac SudokuSolver.java
```
4. Run the application using the `java` command.
```
java SudokuSolver
```
5. The Sudoku Solver GUI will open, and you can start playing Sudoku.

## How to Play

1. The Sudoku Solver GUI consists of a 9x9 grid representing the Sudoku puzzle.
2. The initial puzzle is pre-generated with some predefined values, which are displayed in blue color.
3. To solve the puzzle, click on a cell and input a number using the number buttons at the bottom.
4. If the number is a valid move, it will be displayed in the clicked cell.
5. If the number is invalid (conflicts with existing numbers in the same row, column, or 3x3 block), an error message will be shown.
6. Use the "Reset" button to clear the puzzle and start over.
7. Use the "Exit" button to close the application.
8. Use the "Solution" button to display the solution for the puzzle.
9. Use the "Check Moves" button to highlight incorrect moves.

## Limitations

- The Sudoku Solver application assumes a standard 9x9 Sudoku puzzle.
- The application currently only supports input of numbers from 1 to 9.
- The application does not provide a way to input custom puzzles.

## Contributions

Contributions to the Sudoku Solver project are welcome. If you find any issues or have suggestions for improvements, feel free to open an issue or submit a pull request on the project's repository.

## License

The Sudoku Solver project is released under the [MIT License](https://opensource.org/licenses/MIT). You can find the detailed license information in the [LICENSE](https://github.com/your-username/your-project/blob/master/LICENSE) file.
