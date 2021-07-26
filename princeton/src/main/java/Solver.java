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

  private final Board initial;
  private SearchNode solution;

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    this.initial = initial;
    find();
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

  private void find() {
    solution = search(new MinPQ<>(), initial, initial.manhattan());
    if (solution == null) {
      final Board twin = initial.twin();
      solution = search(new MinPQ<>(), twin, twin.manhattan());
    }
  }

  private SearchNode search(MinPQ<SearchNode> q, Board b, int m) {
    q.insert(new SearchNode(b, null, 0, m));
    SearchNode min = q.delMin();

    int maxMovesBarrier = initial.dimension() * initial.dimension() * initial.dimension();
    while (!min.board.isGoal() && q.size() <= maxMovesBarrier) {
      for (Board n : min.board.neighbors()) {
        if (min.prev == null) q.insert(new SearchNode(n, min, min.moves + 1, n.manhattan()));
        else if (!min.prev.board.equals(n)) q.insert(new SearchNode(n, min, min.moves + 1, n.manhattan()));
      }
      min = q.delMin();
    }

    return min.board.isGoal() ? min : null;
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
