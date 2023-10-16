/**
 * Marlon Grandy
 * 2/21/2022
 * Board class that represents a Sudoku board. The class is capable of reading 
 * board data from a file, and provides various methods to interact with the 
 * board and verify its state.
 */
import java.io.*;
import java.awt.Graphics;

public class Board {

    // 2D array representing the Sudoku board
    private Cell[][] cellArray;
    public static final int size = 9; // Standard size for a Sudoku board

    /**
     * Constructor that initializes the Sudoku board with cells of value 0.
     */
    public Board() {
        cellArray = new Cell[Board.size][Board.size];
        for (int i = 0; i < cellArray.length; i++) {
            for (int j = 0; j < cellArray[i].length; j++) {
                cellArray[i][j] = new Cell(i, j, 0);
            }
        }
    }

    /**
     * Returns a string representation of the Sudoku board.
     */
    @Override
    public String toString() {
        StringBuilder grid = new StringBuilder();
        for (Cell[] cells : cellArray) {
            for (Cell cell : cells) {
                grid.append(cell.toString()).append(" ");
            }
            grid.append("\n");
        }
        return grid.toString();
    }

    /**
     * @return Number of columns of the board.
     */
    public int getCols() {
        return cellArray[0].length;
    }

    /**
     * @return Number of rows of the board.
     */
    public int getRows() {
        return cellArray.length;
    }

    /**
     * @return Cell object at the specified row and column.
     */
    public Cell get(int r, int c) {
        return cellArray[r][c];
    }

    /**
     * Checks if the cell at the given position is locked.
     */
    public boolean isLocked(int r, int c) {
        return cellArray[r][c].isLocked();
    }

    /**
     * @return Number of locked cells in the board.
     */
    public int numLocked() {
        int lockCounter = 0;
        for (Cell[] cells : cellArray) {
            for (Cell cell : cells) {
                if (cell.isLocked()) {
                    lockCounter++;
                }
            }
        }
        return lockCounter;
    }

    /**
     * @return Value of the cell at the specified position.
     */
    public int value(int r, int c) {
        return cellArray[r][c].getValue();
    }

    // Sets the value of the cell at the given position.
    public void set(int r, int c, int value) {
        cellArray[r][c].setValue(value);
    }

    // Sets the value and locked status of the cell at the given position.
    public void set(int r, int c, int value, boolean locked) {
        cellArray[r][c].setValue(value);
        cellArray[r][c].setLocked(locked);
    }

    /**
     * Reads the Sudoku board data from a file and returns the cell array.
     */
    public Cell[][] read(String filename) {
        try {
            FileReader filevar = new FileReader(filename);
            BufferedReader newbuffer = new BufferedReader(filevar);
            for (int i = 0; i < 9 && newbuffer.ready(); i++) {
                String[] splittedRow = newbuffer.readLine().split("[ ]+");
                for (int j = 0; j < 9; j++) {
                    cellArray[i][j].setValue(Integer.parseInt(splittedRow[j]));
                }
            }
            newbuffer.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Board.read():: unable to open file " + filename);
        } catch (IOException ex) {
            System.out.println("Board.read():: error reading file " + filename);
        }
        return cellArray;
    }

    /**
     * Checks if the given value is a valid entry at the specified position.
     */
    public boolean validValue(int row, int col, int value) {
        // Check column and row for the same value
        for (int i = 0; i < cellArray.length; i++) {
            if (value == cellArray[i][col].getValue() && row != i) {
                return false;
            }
            if (value == cellArray[row][i].getValue() && col != i) {
                return false;
            }
        }
        // Check the 3x3 grid for the same value
        int rowCount = row / 3;
        int colCount = col / 3;
        for (int j = rowCount * 3; j < 3 * rowCount + 3; j++) {
            for (int k = colCount * 3; k < 3 * colCount + 3; k++) {
                if (value == cellArray[j][k].getValue() && !(row == j && col == k)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the Sudoku board has a valid solution.
     */
    public boolean validSolution() {
        int validCounter = 0;
        for (int i = 0; i < cellArray.length; i++) {
            for (int j = 0; j < cellArray[i].length; j++) {
                if (validValue(i, j, value(i, j)) && value(i, j) <= 9 && value(i, j) >= 1) {
                    validCounter++;
                }
            }
        }
        return validCounter == 81;
    }

    /**
     * Finds the best cell to solve next.
     */
    public Cell findBest() {
        for (int i = 0; i < cellArray.length; i++) {
            for (int j = 0; j < cellArray.length; j++) {
                if (value(i, j) == 0) {
                    for (int k = 1; k < 10; k++) {
                        if (validValue(i, j, k)) {
                            set(i, j, k);
                            get(i, j).testedValsAdd(k);
                            return get(i, j);
                        }
                    }
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * Draws the Sudoku board.
     */
    public void draw(Graphics g, int scale) {
        for (int i = 0; i < cellArray.length; i++) {
            for (int j = 0; j < cellArray.length; j++) {
                cellArray[j][i].draw(g, i + 5, j + 5, 40);
            }
        }
    }

    /**
     * Resets the Sudoku board.
     */
    public void reset() {
        for (int i = 0; i < cellArray.length; i++) {
            for (int j = 0; j < cellArray.length; j++) {
                cellArray[i][j].setValue(0);
                cellArray[i][j].testedVals.clear();
                cellArray[i][j].setLocked(false);
            }
        }
    }

    /**
     * Main method for testing the Sudoku board functionalities.
     */
    public static void main(String[] args) {
        Board board = new Board();
        board.read("data/sample.txt");
        System.out.println(board.toString());
    }
}
