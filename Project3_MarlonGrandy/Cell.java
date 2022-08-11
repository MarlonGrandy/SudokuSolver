
/*
Marlon Grandy
CS231 
2/21/2022
Cell class contains methods to manipulate and retrive information about a cell.
Cell.java
*/
import java.util.ArrayList;
import java.awt.Graphics;

class Cell {
    int rowIndex;
    int colIndex;
    private int value;
    private boolean locked;
    ArrayList<Integer> testedVals;

    public Cell() { // initializes all values to zero or false
        rowIndex = 0;
        colIndex = 0;
        value = 0;
        locked = false;
        testedVals = new ArrayList<Integer>();

    }

    public Cell(int row, int col, int value) {
        /*
         * initialize the row, column, and value fields to the given
         * parameter values. Set the locked field to false;
         */
        rowIndex = row;
        colIndex = col;
        this.value = value;
        testedVals = new ArrayList<Integer>();

    }

    public Cell(int row, int col, int value, boolean locked) {
        // initialize all of the Cell fields given the parameters.
        rowIndex = row;
        colIndex = col;
        this.value = value;
        this.locked = locked;
        testedVals = new ArrayList<Integer>();

    }

    public int getRow() { // returns the Cell's row index.
        return rowIndex;
    }

    public int getCol() { // returns the Cell's column index.
        return colIndex;
    }

    public int getValue() { // returns the Cell's value.
        return value;
    }

    public void setValue(int newval) { // sets the Cell's value.
        value = newval;
    }

    public boolean isLocked() { // returns the value of the locked field.
        return locked;
    }

    public void setLocked(boolean lock) { // sets the Cell's locked field to the new value.
        locked = lock;
    }

    public Cell clone() { // creates a copy cell of the existing cell
        Cell copyCell = new Cell(rowIndex, colIndex, value, locked);
        return copyCell;
    }

    public String toString() { // tostring describing the state of the cell
        return " " + getValue();
    }

    public void testedValsAdd(int val) { // keeps track of tested values in solve method
        testedVals.add(val);

    }

    public boolean ifVal(int val) { // returns true if value for a given cell has been tested as a solution
        if (testedVals.contains(val)) {
            return true;
        } else {
            return false;
        }

    }

    public void draw(Graphics g, int x0, int y0, int scale) { // draw method for a specific value using ascii values
        g.drawChars(new char[] { (char) ('0' + this.value), '0' }, 0, 1, x0 * scale, y0 * scale);
    }

    public static void main(String[] args) {
        Cell c = new Cell(5, 5, 0);
        System.out.println(c.getRow() + " get row");
        System.out.println(c.getCol() + " get col");
        System.out.println(c.getValue() + " get val");
        c.setValue(5);
        System.out.println(c.getValue() + " get set val");
        System.out.println(c.isLocked() + " is locked?");
        c.setLocked(true);
        System.out.println(c.isLocked() + " is locked set?");
        System.out.println(c.clone() + " clone cell");
        c.testedValsAdd(5);
        System.out.println(c.testedVals + " prints tested vals list"); // prijts tested vals list
        System.out.println(c.ifVal(3) + " if value in tested list");

    }
}
