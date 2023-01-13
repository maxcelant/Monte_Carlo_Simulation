# Monte-Carlo Simulation

![image](https://user-images.githubusercontent.com/92411137/212406568-9e7ffdd4-6f3e-47d6-b806-04a01d7b585e.png)

This simulation is meant to find the percolation value of a 2-D Grid. Percolation is achieved when a fluid can 'flow' from the top row all the way to the bottom row. 
To find the average percolation value, we perform the thousands of iterations of the test and get the average `p` value. 
The `p` value of a single iteration is achieved by calculating the total open squares divided by the total squares in the grid `part / whole`.
Each iteration of a single test opens a hole in the grid, which the water can potentially flow to, until percolation is achieved.
