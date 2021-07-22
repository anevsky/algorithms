import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex Nevsky
 *
 * Date: 22/07/2021
 */
public class BruteCollinearPoints {

  private final List<LineSegment> ls;

  // finds all line segments containing 4 points
  public BruteCollinearPoints(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException("argument to constructor is null");
    }
    if (points.length < 4) {
      throw new IllegalArgumentException("less then 4 points provided");
    }
    for (Point p : points) {
      if (p == null) {
        throw new IllegalArgumentException("one of the points is null");
      }
    }

    MergeX.sort(points);

    for (int i = 0; i < points.length - 1; i++) {
      if ((points[i]).compareTo(points[i + 1]) == 0) {
        throw new IllegalArgumentException("a repeated point: " + points[i]);
      }
    }

    ls = new ArrayList<>();

    // To check whether the 4 points p, q, r, and s are collinear,
    // check whether the three slopes between p and q, between p and r,
    // and between p and s are all equal.
    List<Point> starters = new ArrayList<>();
    for (int p = 0; p < points.length; p++) {
      for (int q = 1; q < points.length; q++) {
        for (int r = 2; r < points.length; r++) {
          for (int s = 3; s < points.length; s++) {
            double s1 = points[p].slopeTo(points[q]);
            double s2 = points[p].slopeTo(points[r]);
            double s3 = points[p].slopeTo(points[s]);
            if (s1 == s2 && s2 == s3
                && points[p].compareTo(points[q]) != 0
                && points[q].compareTo(points[r]) != 0
                && points[r].compareTo(points[s]) != 0
                && s - p == 4
                && !starters.contains(points[p])
            ) {
              starters.add(points[p]);
              ls.add(new LineSegment(points[p], points[s]));
            }
          }
        }
      }
    }
  }

  // the number of line segments
  public int numberOfSegments() {
    return ls.size();
  }

  // the line segments
  public LineSegment[] segments() {
    return ls.toArray(new LineSegment[]{});
  }

  public static void main(String[] args) {
    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
      p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}
