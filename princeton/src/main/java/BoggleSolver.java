import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alex Nevsky
 *
 * @link https://coursera.cs.princeton.edu/algs4/assignments/boggle/specification.php
 *
 * Date: 18/08/2021
 */
public class BoggleSolver {

  private static final int[][] ADJ_POSITION = {
      {-1, -1}, {-1, 0}, {-1, 1}, {0, -1},
      {0, 1}, {1, -1}, {1, 0}, {1, 1}
  };

  private final BoggleTrieST<String> dict;

  // Initializes the data structure using the given array of strings as the dictionary.
  // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
  public BoggleSolver(String[] dict) {
    this.dict =  new BoggleTrieST<>();
    for (int i = 0; i < dict.length; ++i) {
      this.dict.put(dict[i], String.valueOf(i));
    }
  }

  // Returns the set of all valid words in the given Boggle board, as an Iterable.
  public Iterable<String> getAllValidWords(BoggleBoard board) {
    if (board == null) throw new IllegalArgumentException("arg is null");
    Set<String> result = new HashSet<>();
    for (int i = 0; i < board.rows(); ++i) {
      for (int j = 0; j < board.cols(); ++j) {
        result.addAll(dfs(board, i, j));
      }
    }
    return result;
  }

  private Collection<String> dfs(BoggleBoard board, int i, int j) {
    final Set<String> paths = new HashSet<>();
    final int[][] marked = new int[board.rows()][board.cols()];
    dfs(board, marked, paths, i, j, new StringBuilder());
    return paths;
  }

  private void dfs(BoggleBoard board, int[][] marked, Collection<String> paths, int i, int j, StringBuilder sb) {
    if (i < 0 || i > board.rows() - 1 || j < 0 || j > board.cols() - 1) return;
    if (marked[i][j] == 1) return;

    marked[i][j] = 1;
    sb.append(board.getLetter(i, j));

    // when the current path corresponds to a string that is not a prefix of any word in the dict,
    // there is no need to expand the path further
    final String path = sb.toString().replace("Q", "QU");
    if (dict.keysWithPrefix(path).iterator().hasNext()) {
      // add valid path
      if (path.length() >= 3 && dict.contains(path)) paths.add(path);
      // explore adj characters
      for (int[] a: ADJ_POSITION) {
        dfs(board, marked, paths, i + a[0], j + a[1], sb);
      }
    }

    // not valid path, revert
    marked[i][j] = 0;
    sb.deleteCharAt(sb.length() - 1);
  }

  /**
   * word length points
   * 3–4 1
   * 5 2
   * 6 3
   * 7 5
   * 8+ 11
   *
   * @param word txt
   * @return points
   */
  // Returns the score of the given word if it is in the dictionary, zero otherwise.
  // (You can assume the word contains only the uppercase letters A through Z.)
  public int scoreOf(String word) {
    if (word == null) throw new IllegalArgumentException("arg is null");
    if (!this.dict.contains(word)) return 0;
    int length = word.length();
    if (length < 3) return 0;
    if (length <= 4) return 1;
    if (length == 5) return 2;
    if (length == 6) return 3;
    if (length == 7) return 5;
    return 11;
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    String[] dictionary = in.readAllStrings();
    BoggleSolver solver = new BoggleSolver(dictionary);
    BoggleBoard board = new BoggleBoard(args[1]);
    int score = 0;
    for (String word : solver.getAllValidWords(board)) {
      StdOut.println(word);
      score += solver.scoreOf(word);
    }
    StdOut.println("Score = " + score);
  }

}


