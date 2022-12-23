/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    public int n;
    public int[] trialThreshold; // to hold the thresholds for all the trials

    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.trialThreshold = new int[trials];

        for (int t = 0; t < trials; t++) {
            Percolation percolationTest = new Percolation(n);
            // do this until the system percolates
            while (!percolationTest.percolates()) {
                int r = StdRandom.uniformInt(n);
                int c = StdRandom.uniformInt(n);
                percolationTest.open(r, c);
            }
            this.trialThreshold[t] = percolationTest.numberOfOpenSites();
        }
    }

    // Sample mean of percolation threshold
    public double mean() {
        double mean = StdStats.mean(this.trialThreshold);
        return mean / (this.n * this.n);
    }

    // Sample standard deviation of percolation threshold
    public double stddev() {
        double stddev = StdStats.stddev(this.trialThreshold);
        return stddev / (this.n * this.n);
    }

    // Low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - 1.96 * this.stddev();
    }

    // High endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + 1.96 * this.stddev();
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trials);
        StdOut.printf("mean                    = %-25.8f\n", percolationStats.mean());
        StdOut.printf("stddev                  = %-25.10f\n", percolationStats.stddev());
        StdOut.printf("95%% confidence interval = [%-5.10f, %-5.10f]\n",
                      percolationStats.confidenceLo(), percolationStats.confidenceHi());
    }
}
