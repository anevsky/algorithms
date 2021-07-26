import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author Alex Nevsky
 *
 * Date: 25/07/2021
 */
public class Solver {

  private SearchNode solution;

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    search(initial);
  }

  // is the initial board solvable? (see below)
  public boolean isSolvable() {
    return solution != null;
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    return solution == null ? -1 : solution.moves;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    if (solution != null) {
      Stack<Board> result = new Stack<>();
      SearchNode node = solution;
      result.push(node.board);
      while (node.prev != null) {
        result.push(node.prev.board);
        node = node.prev;
      }
      return result;
    } else {
      return null;
    }
  }

  private void search(Board b) {
    SearchNode initial = new SearchNode(b, null, 0, b.manhattan());
    SearchNode twin = new SearchNode(b.twin(), null, 0, b.twin().manhattan());

    MinPQ<SearchNode> q1 = new MinPQ<>();
    MinPQ<SearchNode> q2 = new MinPQ<>();

    q1.insert(initial);
    q2.insert(twin);

    int maxMovesBarrier = initial.board.dimension() * initial.board.dimension() * initial.board.dimension();

    while (!initial.board.isGoal() && !twin.board.isGoal() && q1.size() < maxMovesBarrier) {
      initial = q1.delMin();
      twin = q2.delMin();

      for (Board n : initial.board.neighbors()) {
        if (initial.prev == null || !initial.prev.board.equals(n)) q1.insert(new SearchNode(n, initial, initial.moves + 1, n.manhattan()));
      }

      for (Board n : twin.board.neighbors()) {
        if (twin.prev == null || !twin.prev.board.equals(n)) q2.insert(new SearchNode(n, twin, twin.moves + 1, n.manhattan()));
      }
    }

    solution = initial.board.isGoal() ? initial : null;
  }

  private class SearchNode implements Comparable<SearchNode> {

    private final Board board;
    private final SearchNode prev;
    private final int moves;
    private final int manhattan;

    public SearchNode(Board board, SearchNode prev, int moves, int manhattan) {
      this.board = board;
      this.prev = prev;
      this.moves = moves;
      this.manhattan = manhattan;
    }

    @Override
    public int compareTo(SearchNode that) {
      return this.manhattan - that.manhattan;
    }
  }

  // test client (see below)
  public static void main(String[] args) {
    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] tiles = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        tiles[i][j] = in.readInt();
    Board initial = new Board(tiles);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }

}
