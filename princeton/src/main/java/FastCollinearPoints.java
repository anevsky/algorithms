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

    for (int p = 0; p < points.length; p++) {
      Point pp = points[p];
      Point[] ax = Arrays.copyOfRange(points, p + 1, points.length);
      MergeX.sort(ax, pp.slopeOrder());
      for (int q = 0; q < ax.length - 3; q = q + 3) {
        if (ax[q].slopeTo(pp) == ax[q + 1].slopeTo(pp) && ax[q + 1].slopeTo(pp) == ax[q + 2].slopeTo(pp)) {
          ls.add(new LineSegment(points[p], points[q + 2]));
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
