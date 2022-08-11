
/*
Marlon Grandy
CS231 
2/21/2022
Board2 class reads txt file data from a command line and creates a Soduku board from the data.
Class has key methods to check if board is solved, read in txt file, and check if a value on the board
is valid.
Board2.java
*/
import java.io.*;
import java.awt.Graphics;

public class Board {
   private Cell[][] cellArray;
   public static final int size = 9;

   public Board() {
      /*
       * constructor for Board2 class that initializes a new array and fills it with
       * cells of value 0.
       */
      cellArray = new Cell[Board.size][Board.size];
      for (int i = 0; i < cellArray.length; i++) {
         for (int j = 0; j < cellArray[i].length; j++) {

            cellArray[i][j] = new Cell(i, j, 0);
         }
      }

   }

   public String toString() {
      String grid = "";
      for (int i = 0; i < cellArray.length; i++) {// for loop iterating rows
         for (Cell cell : cellArray[i]) {// for loop iterating cols.

            grid = grid + cell.toString() + " ";

         }
         grid += "\n";
      }
      return grid;

   }

   public int getCols() {// returns the number of columns
      return cellArray[0].length;
   }

   public int getRows() { // returns the number of rows
      return cellArray.length;
   }

   public Cell get(int r, int c) {// returns the Cell at location r, c.
      return cellArray[r][c];
   }

   public boolean isLocked(int r, int c) {// returns whether the Cell at r, c, is locked.
      if (cellArray[r][c].isLocked()) {
         return true;
      } else {
         return false;
      }
   }

   public int numLocked() {// returns the number of locked Cells on the board.
      int lockCounter = 0;
      for (int i = 0; i < cellArray.length; i++) {// for loop iterating rows
         for (Cell cell : cellArray[i]) {
            if (cell.isLocked()) {
               lockCounter++;
            } else {
               continue;
            }
         }
      }
      return lockCounter;

   }

   public int value(int r, int c) {// returns the value at Cell r, c.
      return cellArray[r][c].getValue();
   }

   public void set(int r, int c, int value) {// sets the value of the Cell at r, c.
      cellArray[r][c].setValue(value);
   }

   public void set(int r, int c, int value, boolean locked) {// sets the value and locked fields of the Cell at r, c.
      cellArray[r][c].setValue(value);
      cellArray[r][c].setLocked(locked);
   }

   public Cell[][] read(String filename) { // method reads data from a file and prints out data along with line length
      try {
         FileReader filevar = new FileReader(filename);
         BufferedReader newbuffer = new BufferedReader(filevar);

         for (int i = 0; i < 9 && newbuffer.ready(); i++) {
            String[] splittedRow = newbuffer.readLine().split("[ ]+"); // split using the space character
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

   public boolean validValue(int row, int col, int value) {// tests if the given value is a valid value at the given row
                                                           // and column

      for (int i = 0; i < cellArray.length; i++) {

         if (value == cellArray[i][col].getValue() && row != i) {
            return false;

         }

         if (value == cellArray[row][i].getValue() && col != i) {
            return false;
         }

      }
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

   public boolean validSolution() { // tests to see if the board is solved
      int counter = 0;
      for (int i = 0; i < cellArray.length; i++) {
         for (int j = 0; j < cellArray[i].length; j++) {
            if (validValue(i, j, value(i, j)) && value(i, j) <= 9 && value(i, j) >= 1) {
               counter++;

            }
         }

      }
      if (counter == 81) {
         return true;
      } else {
         return false;
      }

   }

   public Cell findBest() { // finds the best cell to check on the board

      for (int i = 0; i < cellArray.length; i++) {
         for (int j = 0; j < cellArray.length; j++) {
            // loops through cell array and the value is zero, finds a valid value calling
            // method
            if (value(i, j) == 0) {
               for (int k = 1; k < 10; k++) {
                  if (validValue(i, j, k)) { // if there is a valid value found, sets the board value and returns the
                                             // cell location

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

   public void draw(Graphics g, int scale) { // draw method calling cell draw method using loop

      for (int i = 0; i < cellArray.length; i++) {
         for (int j = 0; j < cellArray.length; j++) {
            cellArray[j][i].draw(g, i + 5, j + 5, 40);
         }
      }
   }

   public void reset() { // resets the board and tested vals lists (extension)
      for (int i = 0; i < cellArray.length; i++) {
         for (int j = 0; j < cellArray.length; j++) {
            cellArray[i][j].setValue(0);
            cellArray[i][j].testedVals.clear();
            cellArray[i][j].setLocked(false);

         }
      }

   }

   public static void main(String[] args) { // board method testing
      Board newBoard = new Board();

      System.out.println(newBoard);
      System.out.println(newBoard.getCols() + " getCols");
      System.out.println(newBoard.getRows() + " getRows");
      System.out.println(newBoard.get(3, 3));
      System.out.println(newBoard.isLocked(3, 3) + " is locked?");
      System.out.println(newBoard.numLocked() + " num locked");
      System.out.println(newBoard.value(3, 3) + " valAt");
      newBoard.set(0, 0, 1, true);
      System.out.println(newBoard.value(3, 3) + " Set method");
      // newBoard.read(args[0]); //test method for read method
      System.out.println(newBoard); // prints displayed new board
      System.out.println(newBoard.validValue(0, 5, 1) + " Valid val?");
      System.out.println(newBoard.validSolution() + " validSolution");

   }

}
