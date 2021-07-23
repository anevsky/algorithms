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

    // create local copy with null and repeat checks
    Point[] mPoints = new Point[points.length];
    for (int i = 0; i < points.length; i++) {
      if (points[i] == null) throw new IllegalArgumentException("one of the points is null");
      for (int j = 0; j < i; j++) {
        if (mPoints[j].compareTo(points[i]) == 0) throw new IllegalArgumentException("a repeated point");
      }
      mPoints[i] = points[i];
    }

    ls = new ArrayList<>();

    // To check whether the 4 points p, q, r, and s are collinear,
    // check whether the three slopes between p and q, between p and r,
    // and between p and s are all equal.
    for (int p = 0; p < mPoints.length; p++) {
      for (int q = p + 1; q < mPoints.length; q++) {
        double s1 = mPoints[p].slopeTo(mPoints[q]);
        for (int r = q + 1; r < mPoints.length; r++) {
          double s2 = mPoints[p].slopeTo(mPoints[r]);
          if (s1 != s2) continue;
          for (int s = r + 1; s < mPoints.length; s++) {
            double s3 = mPoints[p].slopeTo(mPoints[s]);
            if (s1 == s2 && s2 == s3) {
              Point[] pp = {mPoints[p], mPoints[q], mPoints[r], mPoints[s]};
              MergeX.sort(pp);
              ls.add(new LineSegment(pp[0], pp[3]));
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
