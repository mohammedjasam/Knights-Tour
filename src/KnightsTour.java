import java.util.*;
 
public class KnightsTour {
    private final static int size = 12; // defining the size 
    private final static int[][] Knightsmoves = {{1,-2},{2,-1},{2,1},{1,2},{-1,2},{-2,1},{-2,-1},{-1,-2}}; // used to store the possible KT moves
    private static int[][] matrix; // this array is used to store the moves of the knight on the board
    private static int totalmoves; // used to store the total number of moves, this helps in program termination
 
    public static void main(String[] args) {
        matrix = new int[size][size]; //array definition 
        totalmoves = (size - 4) * (size - 4); // storing the total number of moves, here it's 64 since the board has 8*8 cells
		
      
      	// used to check if the moves lie withing the board, if not the corresponding cell is filled with -1
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++)
                if (r < 2 || r > size - 3 || c < 2 || c > size - 3)                   
                    matrix[r][c] = -1;
 		
        // Starting Position
        int row =  5;
        int col =  4;
 		matrix[row][col] = 1;
 
        if (solve(row, col, 2)) // calling the function to find out the path
            printResult(); // displays the final path of the knight
        else System.out.println("No Results Found");
 
    }
 
    private static boolean solve(int r, int c, int count) {
        if (count > totalmoves) // implies that the Knights Tour is complete
            return true;
 
      
      	//Creates a list of neighbours from the current cell
        List<int[]> nbrs = neighbors(r, c); 
      	
      	// if there are no neighbours returns false
        if (nbrs.isEmpty() && count != totalmoves)
            return false;
      
 		// This block of code compares the neighbours and selects the one with the least number of accessible positions from it.
        Collections.sort(nbrs, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return a[2] - b[2];
            }
        });
 		
        for (int[] nb : nbrs) {
            r = nb[0];
            c = nb[1];
            matrix[r][c] = count;
            if (!l_accessible(count, r, c) && solve(r, c, count + 1))
                return true;
            matrix[r][c] = 0;
        }
 
        return false;
    }
 	// this block creates a list to keep track of the available neighbours from a particular cell
    private static List<int[]> neighbors(int r, int c) {
        List<int[]> nbrs = new ArrayList<>();
 
        for (int[] m : Knightsmoves) {
            int x = m[0];
            int y = m[1];
            if (matrix[r + y][c + x] == 0) {
                int num = countNeighbors(r + y, c + x);
                nbrs.add(new int[]{r + y, c + x, num});
            }
        }
        return nbrs;
    }
  
 	//calculates the number of neighbours from a particular cell
    private static int countNeighbors(int r, int c) {
        int num = 0;
        for (int[] m : Knightsmoves)
            if (matrix[r + m[1]][c + m[0]] == 0)
                num++;
        return num;
    }
 
    private static boolean l_accessible(int cnt, int r, int c) {
        if (cnt < totalmoves - 1) {
            List<int[]> nbrs = neighbors(r, c);
            for (int[] nb : nbrs)
                if (countNeighbors(nb[0], nb[1]) == 0)
                    return true;
        }
        return false;
    }
  
 	//This block of code prints the final 2d matrix which contains the path that the knight traversed.
    private static void printResult() {
        for (int[] row : matrix) {
            for (int i : row) {
                if (i == -1) continue;
                System.out.printf("%2d ", i);
            }
            System.out.println();
        }
    }
}