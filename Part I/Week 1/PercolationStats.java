import java.lang.Math;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private int T;
	private double[] threshold;

	public PercolationStats(int n, int trials) {   // perform trials independent experiments on an n-by-n grid
		T = trials;
		threshold = new double[T];
		for(int i=0; i<T; i++) {
			int row, col;
			Percolation thisPerc = new Percolation(n);
			do {
				do {
					row = StdRandom.uniform(1,n+1);
					col = StdRandom.uniform(1,n+1);
				} while(thisPerc.isOpen(row,col));
				thisPerc.open(row,col);
			} while(!thisPerc.percolates());
			threshold[i] = thisPerc.numberOfOpenSites()/(n*n*1.0);
		}
	}
	public double mean() {                         // sample mean of percolation threshold
		return StdStats.mean(threshold);
	}
	public double stddev() {                       // sample standard deviation of percolation threshold
		if(T == 1)
			return Double.NaN;
		return StdStats.stddev(threshold);
	}
	public double confidenceLo() {                  // low  endpoint of 95% confidence interval
		double x = mean();
		double s = stddev();
		return x-(1.96*s/Math.sqrt(T));
	}
	public double confidenceHi() {                 // high endpoint of 95% confidence interval
		double x = mean();
		double s = stddev();
		return x+(1.96*s/Math.sqrt(T));
	}

	public static void main(String[] args) {       // test client (described below)
		if(args.length != 2) {
			System.out.println("Usage: <N, for an NxN grid> <number of trials>");
			return;
		}

		int N = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);
		PercolationStats percStats = new PercolationStats(N, trials);
		System.out.println(String.format("%-23s = " , "mean")+percStats.mean());
		System.out.println(String.format("%-23s = " , "stddev")+percStats.stddev());
		System.out.println(String.format("%-23s = [" , "95% confidence interval")+percStats.confidenceLo()+", "+percStats.confidenceHi()+"]");
	}
}