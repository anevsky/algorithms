import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Collections;

/**
 * @author Alex Nevsky
 *
 * Date: 28/07/2021
 */
public class KdTree {

  private enum Separator { VERTICAL, HORIZONTAL }

  private Node root;
  private int size;

  // construct an empty tree
  public KdTree() {
  }

  // is the tree empty?
  public boolean isEmpty() {
    return root == null;
  }

  // number of nodes in the tree
  public int size() {
    return size;
  }

  // add the point to the tree (if it is not already in the tree)
  public void insert(Point2D p) {
    if (p == null) throw new IllegalArgumentException("arg is null");
    root = insert(root, p, Separator.VERTICAL);
    size++;
  }

  private Node insert(Node node, Point2D p, Separator separator) {
    if (node == null) {
      Node n = new Node(p);
      n.sep = separator;
      return n;
    }

    double px = p.x();
    double py = p.y();
    double nx = node.p.x();
    double ny = node.p.y();
    if (node.sep == Separator.VERTICAL) {
      if (px > nx) node.rt = insert(node.rt, p, node.nextSeparator());
      else if (px < nx) node.lb = insert(node.lb, p, node.nextSeparator());
      else if (py != ny) node.rt = insert(node.rt, p, node.nextSeparator());
      else --size;
    } else {
      if (py > ny) node.rt = insert(node.rt, p, node.nextSeparator());
      else if (py < ny) node.lb = insert(node.lb, p, node.nextSeparator());
      else if (px != nx) node.rt = insert(node.rt, p, node.nextSeparator());
      else --size;
    }

    return node;
  }

  // does the tree contain point p?
  public boolean contains(Point2D p) {
    if (p == null) throw new IllegalArgumentException("arg is null");
    return contains(root, p);
  }

  private boolean contains(Node node, Point2D p) {
    while (node != null) {
      if (node.sep == Separator.VERTICAL) {
        if (p.x() > node.p.x()) node = node.rt;
        else if (p.x() < node.p.x()) node = node.lb;
        else if (p.y() != node.p.y()) node = node.rt;
        else return true;
      } else {
        if (p.y() > node.p.y()) node = node.rt;
        else if (p.y() < node.p.y()) node = node.lb;
        else if (p.x() != node.p.x()) node = node.rt;
        else return true;
      }
    }
    return false;
  }

  // draw all points to standard draw
  public void draw() {
    draw(root, 0.0, 0.0, 1.0, 1.0);
  }

  private void draw(Node node, double xmin, double ymin, double xmax, double ymax) {
    if (node == null) return;
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(0.01);
    node.p.draw();
    if (node.sep == Separator.VERTICAL) {
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.setPenRadius();
      RectHV rect = new RectHV(node.p.x(), ymin, node.p.x(), ymax);
      rect.draw();
      draw(node.rt, node.p.x(), ymin, xmax, ymax);
      draw(node.lb, xmin, ymin, node.p.x(), ymax);
    } else {
      StdDraw.setPenColor(StdDraw.BLUE);
      StdDraw.setPenRadius();
      RectHV rect = new RectHV(xmin, node.p.y(), xmax, node.p.y());
      rect.draw();
      draw(node.rt, xmin, node.p.y(), xmax, ymax);
      draw(node.lb, xmin, ymin, xmax, node.p.y());
    }
    StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
    StdDraw.text(node.p.x(), node.p.y(), String.format("(%.1f, %.1f)", node.p.x(), node.p.y()));
  }

  // all points that are inside the rectangle (or on the boundary)
  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) throw new IllegalArgumentException("arg is null");
    if (root == null) return Collections.emptyList();
    Stack<Point2D> pointsInRect = new Stack<>();
    RectHV rootRect = new RectHV(0.0, 0.0, 1.0, 1.0);
    range(root, rootRect, rect, pointsInRect);
    return pointsInRect;
  }

  private void range(Node node, RectHV nRect, RectHV qRect, Stack<Point2D> pointsInRect) {
    if (node == null) return;
    if (!nRect.intersects(qRect)) return;

    if (qRect.contains(node.p)) pointsInRect.push(node.p);

    if (node.sep == Separator.VERTICAL) {
      double ymin = nRect.ymin();
      double ymax = nRect.ymax();
      double xmin = node.p.x();
      double xmax = nRect.xmax();
      range(node.rt, new RectHV(xmin, ymin, xmax, ymax), qRect, pointsInRect);
      xmin = nRect.xmin();
      xmax = node.p.x();
      range(node.lb, new RectHV(xmin, ymin, xmax, ymax), qRect, pointsInRect);
    } else {
      double xmin = nRect.xmin();
      double xmax = nRect.xmax();
      double ymin = node.p.y();
      double ymax = nRect.ymax();
      range(node.rt, new RectHV(xmin, ymin, xmax, ymax), qRect, pointsInRect);
      ymin = nRect.ymin();
      ymax = node.p.y();
      range(node.lb, new RectHV(xmin, ymin, xmax, ymax), qRect, pointsInRect);
    }
  }


  // a nearest neighbor in the tree to point p; null if the tree is empty
  public Point2D nearest(Point2D p) {
    if (p == null) throw new IllegalArgumentException("arg is null");
    if (isEmpty()) return null;

    Node n = new Node(root.p);
    n.lb = root.lb;
    n.rt = root.rt;
    n.sep = root.sep;
    RectHV rootRect = new RectHV(0.0, 0.0, 1.0, 1.0);
    nearest(root, rootRect, n, p);

    return n.p;
  }

  private void nearest(Node node, RectHV nRect, Node nearest, Point2D p) {
    if (node == null) return;

    if (p.distanceSquaredTo(node.p) < p.distanceSquaredTo(nearest.p)) {
      nearest.p = node.p;
    }

    double nx = node.p.x();
    double ny = node.p.y();
    double px = p.x();
    double py = p.y();
    double xmin, xmax, ymin, ymax;

    if (node.sep == Separator.VERTICAL) {
      ymin = nRect.ymin();
      ymax = nRect.ymax();

      xmin = nx;
      xmax = nRect.xmax();
      RectHV rtRect = new RectHV(xmin, ymin, xmax, ymax);

      xmin = nRect.xmin();
      xmax = nx;
      RectHV lbRect = new RectHV(xmin, ymin, xmax, ymax);

      if (px >= nx) {
        nearest(node.rt, rtRect, nearest, p);
        if (lbRect.distanceSquaredTo(p) < p.distanceSquaredTo(nearest.p)) {
          nearest(node.lb, lbRect, nearest, p);
        }
      } else {
        nearest(node.lb, lbRect, nearest, p);
        if (rtRect.distanceSquaredTo(p) < p.distanceSquaredTo(nearest.p)) {
          nearest(node.rt, rtRect, nearest, p);
        }
      }
    } else {
      xmin = nRect.xmin();
      xmax = nRect.xmax();

      ymin = ny;
      ymax = nRect.ymax();
      RectHV rtRect = new RectHV(xmin, ymin, xmax, ymax);

      ymin = nRect.ymin();
      ymax = ny;
      RectHV lbRect = new RectHV(xmin, ymin, xmax, ymax);

      if (py >= ny) {
        nearest(node.rt, rtRect, nearest, p);
        if (lbRect.distanceSquaredTo(p) < p.distanceSquaredTo(nearest.p)) {
          nearest(node.lb, lbRect, nearest, p);
        }
      } else {
        nearest(node.lb, lbRect, nearest, p);
        if (rtRect.distanceSquaredTo(p) < p.distanceSquaredTo(nearest.p)) {
          nearest(node.rt, rtRect, nearest, p);
        }
      }
    }
  }

  private static class Node {

    private Point2D p;      // the point
    private Node lb;        // the left/bottom subtree
    private Node rt;        // the right/top subtree
    private Separator sep; // vertical/horizontal

    public Node(Point2D p) {
      this.p = p;
    }

    public Separator nextSeparator() {
      return sep == Separator.VERTICAL ? Separator.HORIZONTAL : Separator.VERTICAL;
    }
  }

  // unit testing of the methods (optional)
  public static void main(String[] args) {
    String filename = args[0];
    In in = new In(filename);
    KdTree kdtree = new KdTree();
    while (!in.isEmpty()) {
      double x = in.readDouble();
      double y = in.readDouble();
      Point2D p = new Point2D(x, y);
      kdtree.insert(p);
    }
    kdtree.draw();
    System.out.println("size:" + kdtree.size());
    System.out.println("nearest:" + kdtree.nearest(new Point2D(0.81, 0.30)));
  }
}
