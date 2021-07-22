import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author Alex Nevsky
 *
 * Date: 27/05/2021
 */
public class Permutation {

  // CMD+D to enter EOF
  public static void main(String[] args) {
    int k = Integer.parseInt(args[0]);
    RandomizedQueue<String> q = new RandomizedQueue<>();
    do {
      String input = StdIn.readString();
      if (q.size() < k) q.enqueue(input);
    } while ((!StdIn.isEmpty()));

    for (int i = 0; i < k; ++i) {
      StdOut.println(q.dequeue());
    }
  }
}
