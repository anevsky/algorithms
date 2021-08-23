import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/**
 * @author Alex Nevsky
 *
 * Date: 23/08/2021
 */
public class MoveToFront {

  private static final int R = 256;
  private static final int R_BITS = 8;

  // apply move-to-front encoding, reading from standard input and writing to standard output
  public static void encode() {
    char[] chars = initSequence();
    while (!BinaryStdIn.isEmpty()) {
      char count = 0;
      char tIn;
      char tOut = chars[0];
      char c = BinaryStdIn.readChar();
      while (c != chars[count]) {
        tIn = chars[count];
        chars[count] = tOut;
        tOut = tIn;
        ++count;
      }
      chars[count] = tOut;
      BinaryStdOut.write(count);
      chars[0] = c;
    }
    BinaryStdOut.close();
  }

  // apply move-to-front decoding, reading from standard input and writing to standard output
  public static void decode() {
    char[] chars = initSequence();
    while (!BinaryStdIn.isEmpty()) {
      char count = BinaryStdIn.readChar();
      BinaryStdOut.write(chars[count], R_BITS);
      char index = chars[count];
      while (count > 0) chars[count] = chars[--count];
      chars[0] = index;
    }
    BinaryStdOut.close();
  }

  private static char[] initSequence() {
    char[] chars = new char[R];
    for (char i = 0; i < R; ++i) {
      chars[i] = i;
    }
    return chars;
  }

  // if args[0] is "-", apply move-to-front encoding
  // if args[0] is "+", apply move-to-front decoding
  public static void main(String[] args) {
    if (args.length != 1) throw new IllegalArgumentException("Expected + or -");
    else if (args[0].equals("-")) encode();
    else if (args[0].equals("+")) decode();
    else throw new IllegalArgumentException("Expected args + or -");
  }

}
