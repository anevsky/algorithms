import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author Alex Nevsky
 *
 * Date: 03/08/2021
 */
public class WordNet {

  private final SAP sap;
  private final ST<Integer, String> idToSynset = new ST<>();
  private final ST<String, SET<Integer>> nounToIds = new ST<>();

  // constructor takes the name of the two input files
  public WordNet(String synsets, String hypernyms) {
    initSynsets(synsets);

    Digraph digraph = new Digraph(idToSynset.size());
    initGraph(hypernyms, digraph);

    DirectedCycle cycle = new DirectedCycle(digraph);
    if (cycle.hasCycle() || !rootedDAG(digraph)) {
      throw new IllegalArgumentException("Not a rooted DAG!");
    }

    sap = new SAP(digraph);
  }

  // returns all WordNet nouns
  public Iterable<String> nouns() {
    return nounToIds.keys();
  }

  // is the word a WordNet noun?
  public boolean isNoun(String word) {
    if (word == null) throw new IllegalArgumentException("arg is null");
    if ("".equals(word)) {
      return false;
    }
    return nounToIds.contains(word);
  }

  // distance between nounA and nounB
  public int distance(String nounA, String nounB) {
    if (!isNoun(nounA) || !isNoun(nounB)) {
      throw new IllegalArgumentException("Words are not in WordNet!");
    }
    return sap.length(nounToIds.get(nounA), nounToIds.get(nounB));
  }

  // a synset that is the common ancestor of nounA and nounB in a shortest ancestral path
  public String sap(String nounA, String nounB) {
    if (!isNoun(nounA) || !isNoun(nounB)) {
      throw new IllegalArgumentException("Words are not in WordNet!");
    }
    return idToSynset.get(sap.ancestor(nounToIds.get(nounA), nounToIds.get(nounB)));
  }

  private void initSynsets(String synsets) {
    In file = new In(synsets);
    while (file.hasNextLine()) {
      String[] line = file.readLine().split(",");
      int id = Integer.parseInt(line[0]);
      String n = line[1];
      idToSynset.put(id, n);
      String[] nouns = n.split(" ");
      for (String noun : nouns) {
        SET<Integer> ids = nounToIds.get(noun);
        if (null == ids) {
          ids = new SET<>();
        }
        ids.add(id);
        nounToIds.put(noun, ids);
      }
    }
  }

  private void initGraph(String hypernyms, Digraph digraph) {
    In file = new In(hypernyms);
    while (file.hasNextLine()) {
      String[] line = file.readLine().split(",");
      int synsetId = Integer.parseInt(line[0]);
      for (int i = 1; i < line.length; i++) {
        int id = Integer.parseInt(line[i]);
        digraph.addEdge(synsetId, id);
      }
    }
  }

  private boolean rootedDAG(Digraph G) {
    int roots = 0;
    for (int i = 0; i < G.V(); i++) {
      if (!G.adj(i).iterator().hasNext()) {
        roots++;
      }
    }
    return roots == 1;
  }

  // do unit testing of this class
  public static void main(String[] args) {
    WordNet wordNet = new WordNet(args[0], args[1]);
    while (!StdIn.isEmpty()) {
      String v = StdIn.readString();
      String w = StdIn.readString();
      if (!wordNet.isNoun(v) || !wordNet.isNoun(w)) {
        StdOut.println(v + " not in the word net");
        continue;
      }
      int distance = wordNet.distance(v, w);
      String ancestor = wordNet.sap(v, w);
      StdOut.printf("distance = %d, ancestor = %s\n", distance, ancestor);
    }
  }
}
