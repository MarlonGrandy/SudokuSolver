## Sudoku Solver: Board and Sudoku

### Overview
This project provides an implementation for a Sudoku solver. The `Board` class represents the Sudoku board with functionalities to read data from a file, interact with the board, and verify its state. The `Sudoku` class manages the Sudoku operations, offering features like initializing random board states and solving Sudoku puzzles.

### Table of Contents

1. [Board Class](#board-class)
2. [Sudoku Class](#sudoku2-class)
3. [Usage](#usage)
4. [Authors](#authors)

### Board Class

#### Features:
- Representation of a Sudoku board using a 2D array.
- Ability to read board data from a file.
- Methods to interact with individual cells in the board.
- Methods to validate the current board state and individual cell values.

#### Main Methods:
- `Board()`: Constructor to initialize an empty Sudoku board.
- `toString()`: Returns the string representation of the board.
- `get(int r, int c)`: Get the cell object at a specified position.
- `isLocked(int r, int c)`: Check if a cell is locked.
- `set(int r, int c, int value)`: Set the value of a cell.
- `read(String filename)`: Read the board data from a file.
- `validValue(int row, int col, int value)`: Check if a given value is valid for a specified cell.
- `validSolution()`: Check if the board represents a valid Sudoku solution.

### Sudoku Class

#### Features:
- Manage and visualize a Sudoku game using the `Board` class.
- Solve Sudoku puzzles using a stack implementation and backtracking.

#### Main Methods:
- `Sudoku()`: Constructor to initialize a new Sudoku board.
- `Sudoku(int N)`: Constructor to initialize a new Sudoku board with a specified number of populated starting values.
- `solve(int delay)`: Solve the board with optional delay for visualization purposes.

### Usage
To utilize the Sudoku solver, instantiate the `Sudoku` class and use its methods to manage and solve Sudoku puzzles. 

Example:
```java
Sudoku s = new Sudoku();
System.out.println(s.newBoard);
boolean solved = s.solve(500);
if (solved) {
    System.out.println("Sudoku solved successfully!");
} else {
    System.out.println("Sudoku couldn't be solved.");
}
```

### Authors
- **Marlon Grandy**: Responsible for the creation and maintenance of this repo.
---
