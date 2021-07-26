import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

/**
 * @author Alex Nevsky
 *
 * Date: 25/07/2021
 */
public class Board {

  private final int[][] tiles;
  private final int n;

  // create a board from an n-by-n array of tiles,
  // where tiles[row][col] = tile at (row, col)
  public Board(int[][] tiles) {
    if (tiles == null) throw new IllegalArgumentException("tiles is null");
    this.n = tiles.length;
    if (!(2 <= n && n < 128)) throw new IllegalArgumentException(String.format("n = %s, should be 2 <= n < 128", n));

    this.tiles = new int[tiles.length][tiles[0].length];
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        this.tiles[i][j] = tiles[i][j];
      }
    }
  }

  // string representation of this board
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(tiles.length);
    for (int i = 0; i < tiles.length; ++i) {
      sb.append("\n");
      for (int j = 0; j < tiles[i].length; ++j) {
        sb.append(String.format("%d ", tiles[i][j]));
      }
    }
    return sb.toString();
  }

  // board dimension n
  public int dimension() {
    return n;
  }

  // number of tiles out of place
  public int hamming() {
    int h = -1;
    for (int i = 0; i < tiles.length; ++i) {
      for (int j = 0; j < tiles[i].length; ++j) {
        if (this.tiles[i][j] != i * n + j + 1) ++h;
      }
    }
    return h;
  }

  // sum of Manhattan distances between tiles and goal
  public int manhattan() {
    int m = 0;
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        int s = tiles[i][j];
        int targetNum = i * n + j + 1;
        if (s != targetNum) {
          int curRow;
          int curCol;
          boolean found = false;
          for (int r = 0; r < n && !found; ++r) {
            for (int c = 0; c < n; ++c) {
              if (tiles[r][c] == targetNum) {
                curRow = r;
                curCol = c;
                m += Math.max(i, curRow) - Math.min(i, curRow);
                m += Math.max(j, curCol) - Math.min(j, curCol);
                found = true;
                break;
              }
            }
          }
        }
      }
    }
    return m;
  }

  // is this board the goal board?
  public boolean isGoal() {
    return hamming() == 0;
  }

  // does this board equal y?
  public boolean equals(Object y) {
    if (y == this) return true;
    if (y == null) return false;
    if (y.getClass() != this.getClass()) return false;
    Board that = (Board) y;
    return n == that.n && Arrays.deepEquals(tiles, that.tiles);
  }

  // all neighboring boards
  public Iterable<Board> neighbors() {
    Queue<Board> q = new Queue<>();
    if (n < 2) return q;
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        if (tiles[i][j] == 0) {
          if (i > 0) { // with up
            q.enqueue(swap(i - 1, j, i, j));
          }
          if (i != n - 1 && n >= i + 1) { // with down
            q.enqueue(swap(i + 1, j, i, j));
          }
          if (j > 0) { // with left
            q.enqueue(swap(i, j - 1, i, j));
          }
          if (j != n - 1 && n >= j + 1) { // with right
            q.enqueue(swap(i, j + 1, i, j));
          }
          break;
        }
      }
    }
    return q;
  }

  // a board that is obtained by exchanging any pair of tiles
  public Board twin() {
    if (n < 1) return null;

    int r1 = -1;
    int c1 = -1;
    int r2 = -1;
    int c2 = -1;
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        if (r1 == -1 && tiles[i][j] != 0) {
          r1 = i;
          c1 = j;
          continue;
        }
        if (r1 != -1 && tiles[i][j] != 0) {
          r2 = i;
          c2 = j;
        }
        if (r1 != -1 && r2 != -1) {
          return swap(r1, c1, r2, c2);
        }
      }
    }

    return null;
  }

  private Board swap(int r1, int c1, int r2, int c2) {
    int[][] copy = new int[n][n];
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        copy[i][j] = tiles[i][j];
      }
    }
    int t = copy[r1][c1];
    copy[r1][c1] = copy[r2][c2];
    copy[r2][c2] = t;

    return new Board(copy);
  }

  // unit testing (not graded)
  public static void main(String[] args) {
    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] tiles = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        tiles[i][j] = in.readInt();

    Board board = new Board(tiles);
    StdOut.println(board);
    StdOut.println("hamming: " + board.hamming());
    StdOut.println("manhattan: " + board.manhattan());
    StdOut.println("isGoal: " + board.isGoal());
    StdOut.println("twin: " + board.twin());
    StdOut.println("neighbors: " + board.neighbors());
  }

}
