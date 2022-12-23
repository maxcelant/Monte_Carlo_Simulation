/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    WeightedQuickUnionUF quickUnionUF;

    public int n;
    public int virtualTopSite;
    public int virtualBottomSite;
    public int[][] grid;
    public int[][] adjacencyGrid;
    private int openSites = 0;

    public Percolation(int n) {
        this.n = n;

        this.virtualTopSite = (n * n) + 1;
        this.virtualBottomSite = (n * n) + 2;
        this.grid = new int[n][n];
        this.adjacencyGrid = new int[n][n];

        // we are adding 3 because we need a virtual top and bottom site
        this.quickUnionUF = new WeightedQuickUnionUF((n * n) + 3);

        // Set all sites to full initiallty (1 = full, 0 = open)
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                this.grid[row][col] = 1;
            }
        }

        // Set the adjacency matrix [0][0] = 0, [0][1] = 1,...[n][n] = n^2
        int k = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                this.adjacencyGrid[row][col] = k;
                k++;
            }
        }

        // Attach top sites to virtual top site
        for (int col = 0; col < n; col++) {
            int p = this.adjacencyGrid[0][col];
            int q = this.virtualTopSite;
            quickUnionUF.union(p, q);
        }

        // Attach bottom sites to virtual bottom site
        for (int col = 0; col < n; col++) {
            int p = this.adjacencyGrid[n - 1][col];
            int q = this.virtualBottomSite;
            quickUnionUF.union(p, q);
        }
    }

    // Opens the site (row, col) if it is not already open
    public void open(int row, int col) {
        int r = row, c = col;

        // loop until we find a site not already open
        do {
            r = StdRandom.uniformInt(this.n);
            c = StdRandom.uniformInt(this.n);
        } while (this.isOpen(r, c));

        this.grid[r][c] = 0;

        // Check if bottom is open and not out of bounds
        if (r + 1 < n && this.isOpen(r + 1, c)) {
            int p = this.adjacencyGrid[r][c];
            int q = this.adjacencyGrid[r + 1][c];
            quickUnionUF.union(p, q);
        }

        // Check if top is open
        if (r - 1 >= 0 && this.isOpen(r - 1, c)) {
            int p = this.adjacencyGrid[r][c];
            int q = this.adjacencyGrid[r - 1][c];
            quickUnionUF.union(p, q);
        }

        // Check if right is open and in bounds
        if (c + 1 < n && this.isOpen(r, c + 1)) {
            int p = this.adjacencyGrid[r][c];
            int q = this.adjacencyGrid[r][c + 1];
            quickUnionUF.union(p, q);
        }

        // Check if left is open and in bounds
        if (c - 1 >= 0 && this.isOpen(r, c - 1)) {
            int p = this.adjacencyGrid[r][c];
            int q = this.adjacencyGrid[r][c - 1];
            quickUnionUF.union(p, q);
        }

        // Increase open sites by 1
        this.openSites++;
    }

    // Is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return this.grid[row][col] == 0;
    }

    // Is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return this.grid[row][col] == 1;
    }

    // Returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // Does the system percolate?
    public boolean percolates() {
        int p = this.virtualTopSite; // virtual top site
        int q = this.virtualBottomSite; // virtual bottom site
        return quickUnionUF.find(p) == quickUnionUF.find(q);
    }

    public void print() {
        for (int r = 0; r < this.n; r++) {
            for (int c = 0; c < this.n; c++) {
                StdOut.print(String.format("%4d", this.grid[r][c]) + " ");
            }
            StdOut.println();
        }
    }

}
