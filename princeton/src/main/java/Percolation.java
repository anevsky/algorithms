import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author Alex Nevsky
 *
 * Date: 16/03/2021
 */
public class Percolation {

  // grid size
  private final int n;

  // open sites
  private final boolean[] open;
  private int openN;

  // connected sites (union–find data structure)
  private final WeightedQuickUnionUF uf;

  ////////////////////////////////////////
  // public
  ////////////////////////////////////////

  // creates n-by-n grid, with all sites initially blocked
  public Percolation(int n) {
    if (n <= 0) throw new IllegalArgumentException("n <= 0");
    this.n = n;
    uf = new WeightedQuickUnionUF(n * n + 1);
    open = new boolean[n * n + 1];
  }

  // opens the site (row, col) if it is not open already
  public void open(int row, int col) {
    validate(row, col);

    int p = xyTo1D(row, col);
    if (!open[p]) open[p] = true;
    else return;

    openN++;

    if (col > 1 && isOpen(row, col - 1)) uf.union(p, xyTo1D(row, col - 1)); // left
    if (col < n && isOpen(row, col + 1)) uf.union(p, xyTo1D(row, col + 1)); // right
    if (row < n && isOpen(row + 1, col)) uf.union(p, xyTo1D(row + 1, col)); // top
    if (row > 1 && isOpen(row - 1, col)) uf.union(p, xyTo1D(row - 1, col)); // bottom

    if (row == 1) uf.union(p, 0); // top
    if (row == n) uf.union(p, n * n); // bottom
  }

  // is the site (row, col) open?
  public boolean isOpen(int row, int col) {
    validate(row, col);
    return open[xyTo1D(row, col)];
  }

  // is the site (row, col) full?
  // a full site is an open site that can be connected to an open site in the top row
  // via a chain of neighboring (left, right, up, down) open sites
  public boolean isFull(int row, int col) {
    validate(row, col);
    if (!isOpen(row, col)) return false;
    int p = xyTo1D(row, col);
    return uf.find(p) == uf.find(0); // current and top
  }

  // returns the number of open sites
  public int numberOfOpenSites() {
    return openN;
  }

  // does the system percolate?
  public boolean percolates() {
    return uf.find(0) == uf.find(n * n);
  }

  // test client (optional)
  public static void main(String[] args) {
    int n = 4;
    int sitesCount = n * n;
    int[] sitesToOpen = new int[sitesCount];

    for (int i = 0; i < sitesCount; ++i) {
      sitesToOpen[i] = i + 1;
    }
    StdRandom.shuffle(sitesToOpen);

    Percolation percolation = new Percolation(n);
    int k = 0;
    while (k < sitesCount) {
      int i = (sitesToOpen[k] - 1) / n;
      int j = (sitesToOpen[k] - 1) % n;
      percolation.open(i + 1, j + 1);
      k++;
      if (percolation.percolates()) {
        System.out.println("System percolates at " + k + " opened site");
        break;
      }
    }
  }

//  public static void main(String[] args) {
//    int n = 3;
//    Percolation percolation = new Percolation(n);
//    percolation.open(1, 3);
//    percolation.open(2, 3);
//    percolation.open(3, 3);
//    percolation.open(3, 1);
//    System.out.println("isFull(3, 1) " + percolation.isFull(3,1)); // false
//    percolation.open(2, 1);
//    percolation.open(1, 1);
//  }

  ////////////////////////////////////////
  // private
  ////////////////////////////////////////

  // map from a 2-dimensional (row, column) pair to a 1-dimensional union find object index
  private int xyTo1D(int row, int col) {
    int i = (row - 1) * n + col;
    if (i < 0 || i > n * n) throw new IllegalArgumentException(
        String.format("index i={%d} for params {row=%d, col=%d} is outside its prescribed range", i, row, col));
    return i;
  }

  // validate that row, col are in valid range
  private void validate(int row, int col) {
    if (row < 1 || row > n || col < 1 || col > n) {
      throw new IllegalArgumentException(
          String.format("{row=%d, col=%d} is outside its prescribed range {%d, %d}", row, col, 1, n));
    }
  }
}
