import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * @author Alex Nevsky
 *
 * Date: 16/03/2021
 */
public class PercolationStats {

  private static final double CONFIDENCE_95 = 1.96;

  private final int n;
  private final int t;
  private final double[] thresholds;

  // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int t) {
    if (n <= 0 || t <= 0) throw new IllegalArgumentException("n <= 0 || trials <= 0");
    this.n = n;
    this.t = t;
    thresholds = new double[t];
    for (int i = 0; i < t; ++i) {
      thresholds[i] = getThreshold();
    }
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(thresholds);
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    if (t == 1) return Double.NaN;
    return StdStats.stddev(thresholds);
  }

  // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(t));
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(t));
  }

  // test client (see below)
  public static void main(String[] args) {
    if (args.length != 2) {
      throw new IllegalArgumentException();
    }

    int n = Integer.parseInt(args[0]);
    int t = Integer.parseInt(args[1]);

    PercolationStats ps = new PercolationStats(n, t);
    StdOut.println("mean" + "\t" + ps.mean());
    StdOut.println("stddev" + "\t" + ps.stddev());
    StdOut.println("95% confidence interval" + "\t"
        + ps.confidenceLo() + " " + ps.confidenceHi());
  }

  private double getThreshold() {
        int sitesCount = n * n;
    int[] sitesToOpen = new int[sitesCount];

    for (int i = 0; i < sitesCount; i++) {
      sitesToOpen[i] = i + 1;
    }
    StdRandom.shuffle(sitesToOpen);

    int k = 0;
    Percolation percolation = new Percolation(n);
    while (k < sitesCount) {
      int i = (sitesToOpen[k] - 1) / n;
      int j = (sitesToOpen[k] - 1) % n;
      percolation.open(i + 1, j + 1);
      k++;
      if (percolation.percolates()) {
        break;
      }
    }
    return (double) k / (double) sitesCount;
  }

}
