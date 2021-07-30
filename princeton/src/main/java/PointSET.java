import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex Nevsky
 *
 * Date: 28/07/2021
 */
public class PointSET {

  private final SET<Point2D> set;

  // construct an empty set of points
  public PointSET() {
    set = new SET<>();
  }

  // is the set empty?
  public boolean isEmpty() {
    return set.isEmpty();
  }

  // number of points in the set
  public int size() {
    return set.size();
  }

  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p) {
    if (p == null) throw new IllegalArgumentException("arg is null");
    set.add(p);
  }

  // does the set contain point p?
  public boolean contains(Point2D p) {
    if (p == null) throw new IllegalArgumentException("arg is null");
    return set.contains(p);
  }

  // draw all points to standard draw
  public void draw() {
    for (Point2D p : set) {
      p.draw();
    }
  }

  // all points that are inside the rectangle (or on the boundary)
  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) throw new IllegalArgumentException("arg is null");
    List<Point2D> range = new ArrayList<>();
    for (Point2D p : set) {
      if (rect.contains(p)) range.add(p);
    }
    return range;
  }

  // a nearest neighbor in the set to point p; null if the set is empty
  public Point2D nearest(Point2D p) {
    if (p == null) throw new IllegalArgumentException("arg is null");
    if (isEmpty()) return null;
    Point2D nearest = null;
    double min = Double.POSITIVE_INFINITY;
    for (Point2D pp : set) {
      double d = p.distanceSquaredTo(pp);
      if (d < min) {
        min = d;
        nearest = pp;
      }
    }
    return nearest;
  }

  // unit testing of the methods (optional)
  public static void main(String[] args) {
    String filename = args[0];
    In in = new In(filename);
    PointSET brute = new PointSET();
    while (!in.isEmpty()) {
      double x = in.readDouble();
      double y = in.readDouble();
      Point2D p = new Point2D(x, y);
      brute.insert(p);
    }
    brute.draw();
    System.out.println("nearest: " + brute.nearest(new Point2D(0.81, 0.30)));
  }
}
