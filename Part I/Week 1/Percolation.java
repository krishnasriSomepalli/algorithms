import java.lang.Math;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int N;
	private int count = 0;
	private boolean[][] openSite;
	private WeightedQuickUnionUF unionFind;

	public Percolation(int n) {					// create n-by-n grid, with all sites blocked
		N = n;
		count = 0;
		unionFind = new WeightedQuickUnionUF(N*N+2);
		openSite = new boolean[N][N];
		for(int i=0; i<N; i++)
			for(int j=0; j<N; j++)
				openSite[i][j] = false;
	}
	private int getRow(int index) {
		return ((index-1)/N)+1;
	}
	private int getCol(int index) {
		return ((index-1)%N)+1;
	}
	private int getIndex(int row, int col) {
		if(row<1 || row>N || col<1 || col>N)
			throw new IllegalArgumentException("Invalid row or column number. Should lie in [1,n]. "+row+" "+col);
		return ((row-1)*N)+(col-1)+1;
	}
	private void union(int index1, int index2) {
		unionFind.union(index1, index2);
	}
	public void open(int row, int col) {		// open site (row, col) if it is not open already
		if(row<1 || row>N || col<1 || col>N)
			throw new IllegalArgumentException("Invalid row or column number. Should lie in [1,n]");
		if(isOpen(row, col) == true)
			return;
		openSite[row-1][col-1] = true;
		count++;
		if(row == 1)
			union(0, getIndex(row, col));
		if(row == N)
			union(getIndex(row, col), N*N+1);
		if(row-1 >= 1 && isOpen(row-1,col))
			union(getIndex(row, col), getIndex(row-1, col));
		if(col-1 >= 1 && isOpen(row,col-1))
			union(getIndex(row, col), getIndex(row, col-1));
		if(row+1<=N && isOpen(row+1,col))
			union(getIndex(row, col), getIndex(row+1, col));
		if(col+1<=N && isOpen(row,col+1))
			union(getIndex(row, col), getIndex(row, col+1));
	}
	public boolean isOpen(int row, int col) {	// is site (row, col) open?
		if(row<1 || row>N || col<1 || col>N)
			throw new IllegalArgumentException("Invalid row or column number. Should lie in [1,n]");
		return openSite[row-1][col-1];
	}
	public boolean isFull(int row, int col) { 	// is site (row, col) full?
		if(row<1 || row>N || col<1 || col>N)
			throw new IllegalArgumentException("Invalid row or column number. Should lie in [1,n]");
		return unionFind.connected(0, getIndex(row,col));
	}
	public int numberOfOpenSites() {			// number of open sites
		return count;
	}
	public boolean percolates() {				// does the system percolate?
		return unionFind.connected(0, N*N+1);
	}

	public static void main(String[] args) {	// test client (optional) 
	}  
}