import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Alex Nevsky
 *
 * Date: 22/07/2021
 */

public class Point implements Comparable<Point> {

  private final int x;     // x-coordinate of this point
  private final int y;     // y-coordinate of this point

  /**
   * Initializes a new point.
   *
   * @param  x the <em>x</em>-coordinate of the point
   * @param  y the <em>y</em>-coordinate of the point
   */
  public Point(int x, int y) {
    /* DO NOT MODIFY */
    this.x = x;
    this.y = y;
  }

  /**
   * Draws this point to standard draw.
   */
  public void draw() {
    /* DO NOT MODIFY */
    StdDraw.point(x, y);
  }

  /**
   * Draws the line segment between this point and the specified point
   * to standard draw.
   *
   * @param that the other point
   */
  public void drawTo(Point that) {
    /* DO NOT MODIFY */
    StdDraw.line(this.x, this.y, that.x, that.y);
  }

  /**
   * Returns the slope between this point and the specified point.
   * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
   * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
   * +0.0 if the line segment connecting the two points is horizontal;
   * Double.POSITIVE_INFINITY if the line segment is vertical;
   * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
   *
   * @param  that the other point
   * @return the slope between this point and the specified point
   */
  public double slopeTo(Point that) {
    /* YOUR CODE HERE */
    if (x == that.x && y == that.y) return Double.NEGATIVE_INFINITY;
    if (that.x - x == 0.0) return Double.POSITIVE_INFINITY;
    if (that.y - y == 0.0) return 0.0;
    return (double) (that.y - y) / (that.x - x);
  }

  /**
   * Compares two points by y-coordinate, breaking ties by x-coordinate.
   * Formally, the invoking point (x0, y0) is less than the argument point
   * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
   *
   * @param  that the other point
   * @return the value <tt>0</tt> if this point is equal to the argument
   *         point (x0 = x1 and y0 = y1);
   *         a negative integer if this point is less than the argument
   *         point; and a positive integer if this point is greater than the
   *         argument point
   */
  public int compareTo(Point that) {
    /* YOUR CODE HERE */
    if (x == that.x && y == that.y) return 0;
    if ((y < that.y) || (y == that.y && x < that.x)) return -1;
    return +1;
  }

  /**
   * Compares two points by the slope they make with this point.
   * The slope is defined as in the slopeTo() method.
   *
   * @return the Comparator that defines this ordering on points
   */
  public Comparator<Point> slopeOrder() {
    /* YOUR CODE HERE */
    return (p1, p2) -> {
      if (slopeTo(p1) > slopeTo(p2)) return +1;
      if (slopeTo(p1) < slopeTo(p2)) return -1;
      return 0;
    };
  }

  /**
   * Returns a string representation of this point.
   * This method is provide for debugging;
   * your program should not rely on the format of the string representation.
   *
   * @return a string representation of this point
   */
  public String toString() {
    /* DO NOT MODIFY */
    return "(" + x + ", " + y + ")";
  }

  /**
   * Unit tests the Point data type.
   */
  public static void main(String[] args) {
    /* YOUR CODE HERE */
    int x0 = 1;
    int y0 = 1;
    int n = 10;

    StdDraw.setCanvasSize(800, 800);
    StdDraw.setXscale(0, 100);
    StdDraw.setYscale(0, 100);
    StdDraw.setPenRadius(0.005);
    StdDraw.enableDoubleBuffering();

    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = StdRandom.uniform(100);
      int y = StdRandom.uniform(100);
      points[i] = new Point(x, y);
      points[i].draw();
    }

    // draw p = (x0, x1) in red
    Point p = new Point(x0, y0);
    StdDraw.setPenColor(StdDraw.RED);
    StdDraw.setPenRadius(0.02);
    p.draw();

    // draw line segments from p to each point, one at a time, in polar order
    StdDraw.setPenRadius();
    StdDraw.setPenColor(StdDraw.BLUE);
    Arrays.sort(points, p.slopeOrder());
    for (int i = 0; i < n; i++) {
      p.drawTo(points[i]);
      StdDraw.show();
      StdDraw.pause(100);
    }
  }
}
