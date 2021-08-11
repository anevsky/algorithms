import edu.princeton.cs.algs4.Picture;

/**
 * @author Alex Nevsky
 *
 * https://coursera.cs.princeton.edu/algs4/assignments/seam/faq.php
 *
 * Date: 11/08/2021
 */
public class SeamCarver {

  private static final int BORDER_ENERGY = 1000;

  private static final int R = 16;
  private static final int G = 8;
  private static final int B = 0;

  private Picture picture;

  // create a seam carver object based on the given picture
  public SeamCarver(Picture picture) {
    if (picture == null) {
      throw new IllegalArgumentException("arg is null");
    }
    this.picture = new Picture(picture);
  }

  // current picture
  public Picture picture() {
    return new Picture(picture);
  }

  // width of current picture
  public int width() {
    return picture.width();
  }

  // height of current picture
  public int height() {
    return picture.height();
  }

  // energy of pixel at column x and row y
  public double energy(int x, int y) {
    if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1) {
      throw new IllegalArgumentException("wrong coordinate");
    }

    if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
      return BORDER_ENERGY;
    }

    int up = picture.getRGB(x, y - 1);
    int down = picture.getRGB(x, y + 1);
    int left = picture.getRGB(x - 1, y);
    int right = picture.getRGB(x + 1, y);

    double gradX = gradient(left, right);
    double gradY = gradient(up, down);

    return Math.sqrt(gradX + gradY);
  }

  private double gradient(int rgb1, int rgb2) {
    int r1 = (rgb1 >> R) & 0xFF;
    int g1 = (rgb1 >> G) & 0xFF;
    int b1 = (rgb1 >> B) & 0xFF;
    int r2 = (rgb2 >> R) & 0xFF;
    int g2 = (rgb2 >> G) & 0xFF;
    int b2 = (rgb2 >> B) & 0xFF;

    return Math.pow(r1 - r2, 2) + Math.pow(g1 - g2, 2) + Math.pow(b1 - b2, 2);
  }

  // sequence of indices for horizontal seam
  public int[] findHorizontalSeam() {
    transpose();
    int[] hSeam = findVerticalSeam();
    transpose();
    return hSeam;
  }

  // sequence of indices for vertical seam
  public int[] findVerticalSeam() {
    double[][] energy = new double[width()][height()];
    for (int y = 0; y < height(); ++y) {
      for (int x = 0; x < width(); ++x) {
        energy[x][y] = energy(x, y);
      }
    }

    int[][] edgeTo = new int[width()][height()];
    double[][] distTo = new double[width()][height()];
    for (int y = 0; y < height(); ++y) {
      for (int x = 0; x < width(); ++x) {
        if (y == 0) {
          distTo[x][y] = energy[x][y];
        } else {
          distTo[x][y] = Double.POSITIVE_INFINITY;
        }
      }
    }

    // relax edges from the pixel at picture[y][x]
    for (int y = 0; y < height() - 1; ++y) {
      for (int x = 0; x < width(); ++x) {
        if (distTo[x][y + 1] > distTo[x][y] + energy[x][y + 1]) {
          distTo[x][y + 1] = distTo[x][y] + energy[x][y + 1];
          edgeTo[x][y + 1] = x;
        }
        if (x - 1 > 0 && distTo[x - 1][y + 1] > distTo[x][y] + energy[x - 1][y + 1]) {
          distTo[x - 1][y + 1] = distTo[x][y] + energy[x - 1][y + 1];
          edgeTo[x - 1][y + 1] = x;
        }
        if (x + 1 < width() && distTo[x + 1][y + 1] > distTo[x][y] + energy[x + 1][y + 1]) {
          distTo[x + 1][y + 1] = distTo[x][y] + energy[x + 1][y + 1];
          edgeTo[x + 1][y + 1] = x;
        }
      }
    }

    // find column number with minimum energy in bottom
    int colNum = 0;
    double minEnergy = Double.POSITIVE_INFINITY;
    for (int x = 0; x < width(); ++x) {
      if (minEnergy > distTo[x][height() - 1]) {
        minEnergy = distTo[x][height() - 1];
        colNum = x;
      }
    }

    int[] vSeam = new int[height()];
    int minRow = height() - 1;
    while (minRow >= 0) {
      vSeam[minRow] = colNum;
      colNum = edgeTo[colNum][minRow--];
    }

    return vSeam;
  }

  // remove horizontal seam from current picture
  public void removeHorizontalSeam(int[] seam) {
    validateSeam(seam, width());
    if (height() <= 1) {
      throw new IllegalArgumentException("length of the picture is less than or equal to 1");
    }
    transpose();
    removeVerticalSeam(seam);
    transpose();
  }

  // remove vertical seam from current picture
  public void removeVerticalSeam(int[] seam) {
    validateSeam(seam, height());
    if (width() <= 1) {
      throw new IllegalArgumentException("length of the picture is less than or equal to 1");
    }

    Picture tmpPicture = new Picture(width() - 1, height());
    for (int y = 0; y < height(); ++y) {
      for (int x = 0; x < width() - 1; ++x) {
        validateColumnIndex(seam[y]);
        if (x < seam[y]) {
          tmpPicture.setRGB(x, y, picture.getRGB(x, y));
        } else {
          tmpPicture.setRGB(x, y, picture.getRGB(x + 1, y));
        }
      }
    }
    picture = tmpPicture;
  }

  private void transpose() {
    Picture tmpPic = new Picture(height(), width());
    for (int x = 0; x < width(); ++x) {
      for (int y = 0; y < height(); ++y) {
        tmpPic.setRGB(y, x, picture.getRGB(x, y));
      }
    }
    picture = tmpPic;
  }

  private void validateColumnIndex(int col) {
    if (col < 0 || col > width() - 1) {
      throw new IllegalArgumentException("column index is outside its prescribed range");
    }
  }

  private void validateSeam(int[] seam, int len) {
    if (seam == null) {
      throw new IllegalArgumentException("arg is null");
    }
    if (seam.length != len) {
      throw new IllegalArgumentException("the seam is outside its prescribed range");
    }
    for (int i = 0; i < seam.length - 1; ++i) {
      if (Math.abs(seam[i] - seam[i + 1]) > 1) {
        throw new IllegalArgumentException("two adjacent entries differ by more than 1");
      }
    }
  }

  //  unit testing (optional)
  public static void main(String[] args) {
    System.out.println("SeamCarver");
  }

}
