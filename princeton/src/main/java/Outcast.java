import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author Alex Nevsky
 *
 * Date: 03/08/2021
 */
public class Outcast {

  private final WordNet wordNet;

  // constructor takes a WordNet object
  public Outcast(WordNet wordnet) {
    if (wordnet == null) {
      throw new IllegalArgumentException("arg is null");
    }
    this.wordNet = wordnet;
  }

  // given an array of WordNet nouns, return an outcast
  public String outcast(String[] nouns) {
    int id = -1;
    int max = -1;
    for (int i = 0; i < nouns.length; i++) {
      int distSum = 0;
      for (int j = 0; j < nouns.length; j++) {
        if (!nouns[i].equals(nouns[j])) distSum += wordNet.distance(nouns[i], nouns[j]);
      }
      if (distSum > max) {
        max = distSum;
        id = i;
      }
    }
    if (id == -1) {
      throw new IllegalArgumentException("outcast not found!");
    }
    return nouns[id];
  }

  // see test client below
  public static void main(String[] args) {
    WordNet wordnet = new WordNet(args[0], args[1]);
    Outcast outcast = new Outcast(wordnet);
    for (int t = 2; t < args.length; t++) {
      In in = new In(args[t]);
      String[] nouns = in.readAllStrings();
      StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
  }
}
