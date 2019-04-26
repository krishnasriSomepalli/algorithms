import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int trials;
    private final double[] threshold;
    private static final double CONSTANT = 1.96;
    private Double meanValue = null;
    private Double stddevValue = null;

    public PercolationStats(int n, int trials) {   // perform trials independent experiments on an n-by-n grid
        if(n<=0)
            throw new IllegalArgumentException("Illegal value for 'n'");
        if(trials<=0)
            throw new IllegalArgumentException("Illegal value for 'n'");
        this.trials = trials;
        threshold = new double[trials];
        for (int i = 0; i<trials; i++) {
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
        meanValue = StdStats.mean(threshold);
        return meanValue;
    }
    public double stddev() {                       // sample standard deviation of percolation threshold
        stddevValue = Double.NaN;
        if (trials == 1)
            return Double.NaN;
        stddevValue = StdStats.stddev(threshold);
        return stddevValue;
    }
    public double confidenceLo() {                  // low  endpoint of 95% confidence interval
        if(meanValue == null)
            meanValue = mean();
        if(stddevValue == null)
            stddevValue = stddev();
        return meanValue-(CONSTANT*stddevValue/Math.sqrt(trials));
    }
    public double confidenceHi() {                 // high endpoint of 95% confidence interval
        if(meanValue == null)
            meanValue = mean();
        if(stddevValue == null)
            stddevValue = stddev();
        return meanValue+(CONSTANT*stddevValue/Math.sqrt(trials));
    }

    public static void main(String[] args) {       // test client (described below)
        if (args.length != 2) {
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