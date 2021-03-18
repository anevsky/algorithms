import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * @author Alex Nevsky
 *
 * Write a program RandomWord.java that reads a sequence of words from standard input and
 * prints one of those words uniformly at random. Do not store the words in an array or list.
 *
 * Instead, use Knuth’s method: when reading the ith word, select it with probability 1/i to be
 * the champion, replacing the previous champion.
 *
 * After reading all of the words, print the surviving champion.
 *
 * Date: 24/02/2021
 */
public class RandomWord {

  public static void main(String[] args) {
    String champion = "";
    double i = 1;
    do {
      String input = StdIn.readString();
      if (StdRandom.bernoulli(1.0/i)) {
        champion = input;
      }
      ++i;
    } while ((!StdIn.isEmpty()));
    StdOut.println(champion);
  }

}
