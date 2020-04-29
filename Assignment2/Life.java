/*
 * Filename:    Life.java
 * Author:      Joshua Carney
 * Course:      CSCI 162 Section 1
 * Date:        02/06/2020
 * Assignment:  Assignment 2
 * Description: Create a life grid simulation
 */
import java.util.Scanner;
import java.util.Random;

public class Life {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int rows = input.nextInt();
        int columns = input.nextInt();
        Random seed = new Random(input.nextLong());
        
        int birthLow = input.nextInt();
        int birthHigh = input.nextInt();
        int liveLow = input.nextInt();
        int liveHigh = input.nextInt();
        input.close();

        boolean[][] lifeGrid = createGrid(rows, columns, seed);
        printGrid(lifeGrid);
        
        for(int i = 1; i <= 5; ++i) {
        	iterateGrid(lifeGrid, birthLow, birthHigh, liveLow, liveHigh);
        	printGrid(lifeGrid);
        }
    }

    // Initializes lifeGrid and populates it with random values according to the seed
    public static boolean[][] createGrid(int rows, int columns, Random r){
        boolean[][] lifeGrid = new boolean[rows][columns];

        //Start at 1 to skip the 0th row and column of the matrix
        for(int i = 1; i < rows - 1; ++i){
            for(int j = 1; j < columns - 1; ++j){
                lifeGrid[i][j] = r.nextBoolean();
            }
        }

        return lifeGrid;
    }

    // prints the current life grid, "#" represents alive cells, "-" represents dead cells
    public static void printGrid(boolean[][] lifeGrid){
        for(int row = 0; row < lifeGrid.length; ++row){
            for(int column = 0; column < lifeGrid[0].length; ++column){
                if(lifeGrid[row][column]){
                    System.out.print("#");
                } else {
                    System.out.print("-");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    // Creates a copy of the current grid to compare it, and then modify the current lifeGrid.
    public static void iterateGrid(boolean[][] lifeGrid, int birthLow, int birthHigh, int liveLow, int liveHigh) {
    	
    	boolean[][] clonedLifeGrid = lifeGrid.clone();
    	for (int row = 0; row < lifeGrid.length; ++row) {
    	  clonedLifeGrid[row] = lifeGrid[row].clone();
    	}
    	
    	for(int row = 1; row < clonedLifeGrid.length - 1; ++row) {
    		for(int column = 1; column < clonedLifeGrid[0].length - 1; ++column) {
    			int aliveNeighbors = countAliveNeighbors(clonedLifeGrid, row, column);
    			//birth 
    			if(!clonedLifeGrid[row][column]) {
    				if(aliveNeighbors >= birthLow && aliveNeighbors <= birthHigh) {
    					lifeGrid[row][column] = true;
    				}
    			}
    			//death
    			else {
    				if(aliveNeighbors < liveLow || aliveNeighbors > liveHigh) {
    					lifeGrid[row][column] = false;
    				}
    			}
    		}
    	}
    }
    
    // To be used by iterateGrid; counts alive neighbors of a given cell
    public static int countAliveNeighbors(boolean[][] lifeGrid, int passedRow, int passedColumn) {
    	int alive = 0;
    	for(int row = -1; row <= 1; ++row) {
    		for(int column = -1; column <= 1; ++column) {
    			if(lifeGrid[passedRow + row][passedColumn + column]) {
    				++alive;
    			}
    		}
    	}
    	
    	return alive;
    }
}
