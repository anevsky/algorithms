import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Alex Nevsky
 *
 * Date: 22/07/2021
 */
public class FastCollinearPoints {

  private final List<LineSegment> ls;

  // finds all line segments containing 4 points
  public FastCollinearPoints(Point[] points) {
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

    List<Point> extremes = new ArrayList<>();
    for (int p = 0; p < points.length; p++) {
      Point origin = points[p];
      Point[] ax = Arrays.copyOf(points, points.length);
      MergeX.sort(ax, origin.slopeOrder());
      for (int q = 0; q < ax.length - 2; q++) {
        if (ax[q].slopeTo(origin) == ax[q + 1].slopeTo(origin)
            && ax[q + 1].slopeTo(origin) == ax[q + 2].slopeTo(origin)
            && !extremes.contains(ax[q + 2]
        )) {
          ls.add(new LineSegment(origin, ax[q + 2]));
          extremes.add(ax[q + 2]);
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
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}
