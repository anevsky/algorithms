import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alex Nevsky
 *
 * Date: 03/08/2021
 */
public class SAP {

  private final Digraph digraph;
  private final LinearProbingHashST<String, SAPResult> cache;

  // constructor takes a digraph (not necessarily a DAG)
  public SAP(Digraph G) {
    if (G == null) {
      throw new IllegalArgumentException("arg is null");
    }
    digraph = new Digraph(G);
    cache = new LinearProbingHashST<>();
  }

  // length of shortest ancestral path between v and w; -1 if no such path
  public int length(int v, int w) {
    return length(Collections.singletonList(v), Collections.singletonList(w));
  }

  // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
  public int ancestor(int v, int w) {
    return ancestor(Collections.singletonList(v), Collections.singletonList(w));
  }

  // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
  public int length(Iterable<Integer> v, Iterable<Integer> w) {
    isValid(v);
    isValid(w);
    if (!v.iterator().hasNext() || !w.iterator().hasNext()) return -1;
    final String key = String.format("%s_%s", v, w);
    return cache.contains(key) ?
        cache.get(key).minDistance :
        search(new BreadthFirstDirectedPaths(digraph, v), (new BreadthFirstDirectedPaths(digraph, w)), key).minDistance;
  }

  // a common ancestor that participates in shortest ancestral path; -1 if no such path
  public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    isValid(v);
    isValid(w);
    if (!v.iterator().hasNext() || !w.iterator().hasNext()) return -1;
    final String key = String.format("%s_%s", v, w);
    return cache.contains(key) ?
        cache.get(key).shortestAncestor :
        search(new BreadthFirstDirectedPaths(digraph, v), (new BreadthFirstDirectedPaths(digraph, w)), key).shortestAncestor;
  }

  private SAPResult search(BreadthFirstDirectedPaths bfs1, BreadthFirstDirectedPaths bfs2, String key) {
    List<Integer> ancestors = new ArrayList<>();
    for (int i = 0; i < digraph.V(); i++) {
      if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
        ancestors.add(i);
      }
    }

    int shortestAncestor = -1;
    int minDistance = Integer.MAX_VALUE;
    for (int ancestor : ancestors) {
      int dist = bfs1.distTo(ancestor) + bfs2.distTo(ancestor);
      if (dist < minDistance) {
        minDistance = dist;
        shortestAncestor = ancestor;
      }
    }

    if (minDistance == Integer.MAX_VALUE) minDistance = -1;

    final SAPResult result = new SAPResult(shortestAncestor, minDistance);
    cache.put(key, result);

    return result;
  }

  private void isValid(Iterable<Integer> i) {
    if (i == null) {
      throw new IllegalArgumentException("arg is null");
    }
    for (Integer v : i) {
      if (v == null || v < 0 || v >= digraph.V()) {
        throw new IllegalArgumentException(String.format("v %d is not between 0 and %d", v, (digraph.V() - 1)));
      }
    }
  }

  private class SAPResult {

    private final int shortestAncestor;
    private final int minDistance;

    public SAPResult(int shortestAncestor, int minDistance) {
      this.shortestAncestor = shortestAncestor;
      this.minDistance = minDistance;
    }

    public int getShortestAncestor() {
      return shortestAncestor;
    }

    public int getMinDistance() {
      return minDistance;
    }
  }

  // do unit testing of this class
  public static void main(String[] args) {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);
    while (!StdIn.isEmpty()) {
      int v = StdIn.readInt();
      int w = StdIn.readInt();
      int length = sap.length(v, w);
      int ancestor = sap.ancestor(v, w);
      StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
  }
}
