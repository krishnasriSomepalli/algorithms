import java.lang.Math;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private int count = 0;
    private boolean[][] openSite;
    private final WeightedQuickUnionUF unionFind;

    public Percolation(int n) {                    // create n-by-n grid, with all sites blocked
        if(n<=0)
            throw new IllegalArgumentException("Illegal value for 'n'");
        this.n = n;
        count = 0;
        unionFind = new WeightedQuickUnionUF(n*n+2);
        openSite = new boolean[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                openSite[i][j] = false;
    }
    private int getIndex(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException("Invalid row or column number. Should lie in [1,n]. "+row+" "+col);
        return ((row-1)*n)+(col-1)+1;
    }
    private void union(int index1, int index2) {
        unionFind.union(index1, index2);
    }
    public void open(int row, int col) {        // open site (row, col) if it is not open already
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException("Invalid row or column number. Should lie in [1,n]");
        if (isOpen(row, col) == true)
            return;
        openSite[row-1][col-1] = true;
        count++;
        if (row == 1)
            union(0, getIndex(row, col));
        if (row == n)
            union(getIndex(row, col), n*n+1);
        if (row-1  >= 1 && isOpen(row-1,col))
            union(getIndex(row, col), getIndex(row-1, col));
        if (col-1  >= 1 && isOpen(row,col-1))
            union(getIndex(row, col), getIndex(row, col-1));
        if (row+1 <= n && isOpen(row+1,col))
            union(getIndex(row, col), getIndex(row+1, col));
        if (col+1 <= n && isOpen(row,col+1))
            union(getIndex(row, col), getIndex(row, col+1));
    }
    public boolean isOpen(int row, int col) {    // is site (row, col) open?
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException("Invalid row or column number. Should lie in [1,n]");
        return openSite[row-1][col-1];
    }
    public boolean isFull(int row, int col) {     // is site (row, col) full?
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException("Invalid row or column number. Should lie in [1,n]");
        return unionFind.connected(0, getIndex(row,col));
    }
    public int numberOfOpenSites() {            // number of open sites
        return count;
    }
    public boolean percolates() {                // does the system percolate?
        return unionFind.connected(0, n*n+1);
    }

    public static void main(String[] args) {    // test client (optional) 
    }  
}