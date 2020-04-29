/*
 * Filename:    Life.java
 * Author:      Joshua Carney
 * Course:      CSCI 162 Section 1
 * Date:        01/27/2020
 * Assignment:  Assignment 1
 * Description: Create a life grid simulation
 */
import java.util.Scanner;
import java.util.Random;

public class Life {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int rows = input.nextInt();
        int columns = input.nextInt();
        Random r = new Random(input.nextLong());

        boolean[][] lifeGrid = createGrid(rows, columns, r);

        printGrid(lifeGrid);

    }

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

    public static void printGrid(boolean[][] lifeGrid){
        for(int i = 0; i < lifeGrid.length; ++i){
            for(int j = 0; j < lifeGrid[0].length; ++j){
                if(lifeGrid[i][j]){
                    System.out.print("#");
                } else {
                    System.out.print("-");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
