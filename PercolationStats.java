import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double grid[];



    private double mean;
    private double stddev;
    private double std;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if(n<=0||trials<=0){
            throw new java.lang.IllegalArgumentException("Row or col is outside its prescribed range");
        }
        else {
            int tot = n * n;
            grid = new double[trials];
            Percolation perc;
            for (int i = 0; i < trials; i++) {
                perc = new Percolation(n);
                int y;
                int x;
                int num = 0;
                while (!perc.percolates()) {
                    y = StdRandom.uniformInt(n);
                    x = StdRandom.uniformInt(n);
                    if (!perc.isOpen(y + 1, x + 1)) {
                        perc.open(y + 1, x + 1);
                    }
                }
                num = perc.numberOfOpenSites();
                double ratio = (double) num / tot;
                grid[i] = ratio;

            }
            mean = StdStats.mean(grid);
            stddev = StdStats.stddev(grid);
            std = (stddev * 1.96)/Math.sqrt(trials);
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean-std;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean+std;

    }

    // test client (see below)
    public static void main(String[] args){
        String str = args[0];
        String str1 = args[1];
        PercolationStats PStats = new PercolationStats(Integer.parseInt(str),Integer.parseInt(str1));
        System.out.printf("Mean                    =%f\n",PStats.mean());
        System.out.printf("Stddev                  =%f\n",PStats.stddev());

            System.out.println("95% confidence interval =["+ PStats.confidenceLo()+", "+ PStats.confidenceHi()+"]");


    }
}
