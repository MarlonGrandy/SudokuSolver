/*
Marlon Grandy
CS231 
2/23/2022
Sudoku Class...
Sudoku.java
*/

//import statements
import java.util.Random;
import java.util.Stack;

import java.awt.event.*;

public class Sudoku2 {// declares the sodoku class
    Board newBoard;
    LandscapeDisplay display;

    public Sudoku2() { // constructor initializing a new board
        newBoard = new Board();
        display = new LandscapeDisplay(newBoard, 30);
    }

    public Sudoku2(int N) { // constructor initializing a new board with number of populated starting values
                            // parameter
        Random ran = new Random();
        newBoard = new Board();
        display = new LandscapeDisplay(newBoard, 30);
        int i = 0;
        while (i <= N) {
            int randInt = ran.nextInt(9);
            int randCol = ran.nextInt(9);
            int randRow = ran.nextInt(9);
            // initializes a random board with random cell values
            while (newBoard.get(randRow, randCol).getValue() != 0) {
                randCol = ran.nextInt(9);
                randRow = ran.nextInt(9);
            }
            while (newBoard.validValue(randRow, randCol, randInt) == false) {
                randInt = ran.nextInt(9) + 1;

            }
            newBoard.set(randRow, randCol, randInt, true);

            i++;
        }
        System.out.println(newBoard);

    }

    public boolean solve(int delay) { // solves the board through stack implementation and backtracking

        Stack<Cell> stack = new Stack<Cell>();
        boolean back = true;

        while (stack.size() < 81 - newBoard.numLocked()) { // while loop to loop until all cells have been worked with

            Cell check = newBoard.findBest();
            if (check != null) { // if findBest is not null pushes cell onto stack
                stack.push(check);
                if (delay > 0) { //if there is a delay argument, then the program sleeps 
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException ex) {
                        System.out.println("Interrupted");
                    }
                    display.repaint();
                }
            } else { // if find best is null than the cell backtracks based on conditions
                back = true;

                while (back && !newBoard.validSolution() && stack.size() != 0) { // pops a cell and finds a new valid val
                                                                                
                    if (delay > 0) {
                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException ex) {
                            System.out.println("Interrupted");
                        }
                        display.repaint();
                    }
                    Cell backtrack = stack.pop(); //this is to pop a cell off the stack
                    newBoard.set(backtrack.getRow(), backtrack.getCol(), 0); //resets the board to all 0

                    for (int val = 1; val < 10; val++) { //loop to find new valid value

                        if (back && newBoard.validValue(backtrack.getRow(), backtrack.getCol(), val)
                                && !backtrack.ifVal(val)) {
                            // pushes cell onto stack with updated value and updates baord

                            newBoard.get(backtrack.getRow(), backtrack.getCol()).testedValsAdd(val); //thismis to add tested val to cell list
                            newBoard.set(backtrack.getRow(), backtrack.getCol(), val);

                            stack.push(newBoard.get(backtrack.getRow(), backtrack.getCol())); //pushes new value cell onto stack

                            back = false;

                        }

                    }
                    if (back) {
                        // clears the tested values in cell array
                        newBoard.get(backtrack.getRow(), backtrack.getCol()).testedVals.clear();

                    }

                }
            }
            if (stack.size() == 0) {
                //no solution checker
                break;
            }

        }

        if (newBoard.validSolution()) {

            return true;
        } else {
            System.out.println("There is no solution!");
            return false;
        }

    }

    public static void main(String[] args) { // main method to create and run sodoku solver
        Sudoku2 s = new Sudoku2();

        double startTime = System.nanoTime(); //recrods start time 
        System.out.println(s.newBoard);
        s.solve(10);
        double endTime = System.nanoTime(); //records endtime
        double totalTime = (endTime - startTime); //calculates total run time in Nanoseconds
        System.out.println(totalTime);

        s.display.bGet().addActionListener(new ActionListener() { //extension
            /* button to rest the board with 10 
            random locked cells*/ 
            public void actionPerformed(ActionEvent e) {
                s.newBoard.reset(); //calls reset method
                //this block of code is the same as Sudoku2 paramatarized constructor
                Random ran = new Random();
                int i = 0;
                while (i <= 10) { 
                    int randInt = ran.nextInt(9);
                    int randCol = ran.nextInt(9);
                    int randRow = ran.nextInt(9);
                    // loop to set random cells to valid values
                    while (s.newBoard.get(randRow, randCol).getValue() != 0) {
                        randCol = ran.nextInt(9);
                        randRow = ran.nextInt(9);
                    }
                    while (s.newBoard.validValue(randRow, randCol, randInt) == false) {
                        randInt = ran.nextInt(9) + 1;

                    }
                    s.newBoard.set(randRow, randCol, randInt, true);

                    i++;
                }
                s.display.repaint(); //reapinds the JFrame window

            }
        });
        
        s.display.cGet().addActionListener(new ActionListener() { // button to solve the reset board
            public void actionPerformed(ActionEvent e) {
                s.solve(1); //calls solve method on the now reset board

            }
        });

    }

}
