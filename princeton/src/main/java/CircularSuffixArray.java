/**
 * @author Alex Nevsky
 *
 * Date: 23/08/2021
 */
public class CircularSuffixArray {

  private static final int CUTOFF =  15;   // cutoff to insertion sort
  private final int n;
  private final int[] index;

  // circular suffix array of s
  public CircularSuffixArray(String s) {
    if (s == null) throw new IllegalArgumentException("arg is null");
    n = s.length();
    index = new int[n];

    for (int i = 0; i < s.length(); ++i)
      index[i] = i;

    if (!isUnary(s))
      sort(s, 0, n - 1, 0);
  }

  // length of s
  public int length() {
    return n;
  }

  // returns index of ith sorted suffix
  public int index(int i) {
    if (i < 0 || i > n - 1) throw new IllegalArgumentException("i is outside its prescribed range 0 <= i <= n - 1");
    return index[i];
  }

  // 3-way string quicksort lo..hi starting at dth character based on
  // http://algs4.cs.princeton.edu/51radix/Quick3string.java.html
  private void sort(String s, int lo, int hi, int d) {
    // cutoff to insertion sort for small subarrays
    if (hi - lo <= CUTOFF) {
      insertion(s, lo, hi, d);
      return;
    }

    int lt = lo, gt = hi;
    int v = charAt(s, index[lo], d);
    int i = lo + 1;
    while (i <= gt) {
      int t = charAt(s, index[i], d);
      if (t < v) exch(lt++, i++);
      else if (t > v) exch(i, gt--);
      else i++;
    }

    // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi].
    sort(s, lo, lt - 1, d);
    if (v >= 0) sort(s, lt, gt, d + 1);
    sort(s, gt + 1, hi, d);
  }

  // sort from a[lo] to a[hi], starting at the dth character
  private void insertion(String v, int lo, int hi, int d) {
    for (int i = lo; i <= hi; i++)
      for (int j = i; j > lo && less(v, j, j - 1, d); j--)
        exch(j, j - 1);
  }

  // is suffix i less than suffix j, starting at d
  private boolean less(String v, int i, int j, int d) {
    int indI = index[i];
    int indJ = index[j];
    while (d < n) {
      int chI = charAt(v, indI, d);
      int chJ = charAt(v, indJ, d);
      if (chI < chJ) return true;
      else if (chI > chJ) return false;
      ++d;
    }
    return false;
  }

  private void exch(int i, int j) {
    int tmp = index[i];
    index[i] = index[j];
    index[j] = tmp;
  }

  private char charAt(String s, int index, int offset) {
    return s.charAt((index + offset) % n);
  }

  private static boolean isUnary(String s) {
    for (int i = 1; i < s.length(); i++)
      if (s.charAt(i) != s.charAt(i-1)) return false;
    return true;
  }

  // unit testing (required)
  public static void main(String[] args) {
    final String s = "ABRACADABRA!";
    final CircularSuffixArray csa = new CircularSuffixArray(s);
    for (int i = 0; i < s.length(); i++) {
      System.out.println(s.charAt(csa.index(i)));
    }
  }
}
