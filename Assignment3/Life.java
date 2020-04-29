import java.util.Random;

public class Life {
	
	private int birthLow;
	private int birthHigh;
	private int liveLow;
	private int liveHigh;
	private boolean[][] lifeGrid;
	
	public Life(long seed, int rows, int columns, int birthLow, int birthHigh, int liveLow, int liveHigh) {
		if(rows < 1 || columns < 1) throw new IllegalArgumentException("Row and column must be >= 1, not " + rows + ", " + columns);
		if(birthLow < 1 || birthLow > 9) throw new IllegalArgumentException(birthLow + " is an invalid birthLow value, must be between 1 and 9 inclusive");
		if(birthHigh < 1 || birthHigh > 9) throw new IllegalArgumentException(birthHigh + " is an invalid birthHigh value, must be between 1 and 9 inclusive");
		if(liveLow < 1 || liveLow > 9) throw new IllegalArgumentException(liveLow + " is an invalid liveLow value, must be between 1 and 9 inclusive");
		if(liveHigh < 1 || liveHigh > 9) throw new IllegalArgumentException(liveHigh + " is an invalid liveHigh value, must be between 1 and 9 inclusive");
		
		lifeGrid = new boolean[rows][columns];
		Random seededRandom = new Random(seed);

        //Start at 1 to skip the 0th row and column of the matrix because the first and last row and column are incompatible with life
        for(int r = 1; r < rows - 1; ++r){
            for(int c = 1; c < columns - 1; ++c){
                lifeGrid[r][c] = seededRandom.nextBoolean();
            }
        }

        this.birthLow = birthLow;
        this.birthHigh = birthHigh;
        this.liveLow = liveLow;
        this.liveHigh = liveHigh;
	}
	
	// Advances the lifeGrid object by one time unit
	public void update() {
		boolean[][] clonedLifeGrid = world();
		
		for(int row = 1; row < lifeGrid.length - 1; ++row) {
    		for(int column = 1; column < lifeGrid[0].length - 1; ++column) {
    			int aliveNeighbors = countAliveNeighbors(clonedLifeGrid, row, column);
    			//birth 
    			if(!lifeGrid[row][column]) {
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
	
	// Used by the update() method, counts how many neighbors are alive in a given cell
	private int countAliveNeighbors(boolean[][] lifeGrid, int passedRow, int passedColumn) {
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
	
	// Returns a COPY of the current life grid.
	public boolean[][] world(){
		boolean[][] clonedLifeGrid = lifeGrid.clone();
    	for (int row = 0; row < lifeGrid.length; ++row) {
    	  clonedLifeGrid[row] = lifeGrid[row].clone();
    	}
    	
    	return clonedLifeGrid;
	}
}
