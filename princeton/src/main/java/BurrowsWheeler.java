import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/**
 * Burrows–Wheeler data compression algorithm.
 *
 * This revolutionary algorithm outcompresses gzip and PKZIP, is relatively easy to implement,
 * and is not protected by any patents. It forms the basis of the Unix compression utility bzip2.
 *
 * The Burrows–Wheeler data compression algorithm consists of three algorithmic components:
 *
 * 1) Burrows–Wheeler transform. Given a typical English text file, transform it into
 * a text file in which sequences of the same character occur near each other many times.
 *
 * 2) Move-to-front encoding. Given a text file in which sequences of the same character occur near
 * each other many times, convert it into a text file in which certain characters appear much more frequently than others.
 *
 * 3) Huffman compression. Given a text file in which certain characters appear much more frequently
 * than others, compress it by encoding frequently occurring characters with short codewords and
 * infrequently occurring characters with long codewords.
 *
 * @link https://coursera.cs.princeton.edu/algs4/assignments/burrows/specification.php
 *
 * @author Alex Nevsky
 *
 * Date: 23/08/2021
 */
public class BurrowsWheeler {

  private static final int R = 256;

  // apply Burrows-Wheeler transform,
  // reading from standard input and writing to standard output
  public static void transform() {
    final String s = BinaryStdIn.readString();

    final CircularSuffixArray csa = new CircularSuffixArray(s);

    int first = 0;
    final int n = s.length();
    while (first < n && csa.index(first) != 0) ++first;

    BinaryStdOut.write(first);

    for (int i = 0; i < n; ++i) {
      int lastIdx = (csa.index(i) - 1 + n) % n;
      BinaryStdOut.write(s.charAt(lastIdx));
    }

    BinaryStdOut.close();
  }

  // apply Burrows-Wheeler inverse transform,
  // reading from standard input and writing to standard output
  public static void inverseTransform() {
    final int first = BinaryStdIn.readInt();
    final String t = BinaryStdIn.readString();
    final int n = t.length();

    int[] next = new int[n];
    int[] count = new int[R + 1];

    for (int i = 0; i < n; ++i)
      count[t.charAt(i) + 1]++;
    for (int i = 1; i < R + 1; ++i)
      count[i] += count[i - 1];
    for (int i = 0; i < n; ++i)
      next[count[t.charAt(i)]++] = i;

    int c = 0;
    int i = next[first];
    while (c < n) {
      BinaryStdOut.write(t.charAt(i));
      i = next[i];
      ++c;
    }

    BinaryStdOut.close();
  }

  // if args[0] is "-", apply Burrows-Wheeler transform
  // if args[0] is "+", apply Burrows-Wheeler inverse transform
  public static void main(String[] args) {
    if (args.length != 1) throw new IllegalArgumentException("Expected + or -");
    else if (args[0].equals("-")) transform();
    else if (args[0].equals("+")) inverseTransform();
    else throw new IllegalArgumentException("Expected args + or -");
  }
}
