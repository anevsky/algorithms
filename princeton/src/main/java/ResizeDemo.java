/******************************************************************************
 *  Compilation:  javac ResizeDemo.java
 *  Execution:    java ResizeDemo input.png columnsToRemove rowsToRemove
 *  Dependencies: SeamCarver.java SCUtility.java
 *
 *
 *  Read image from file specified as command line argument. Use SeamCarver
 *  to remove number of rows and columns specified as command line arguments.
 *  Show the images and print time elapsed to screen.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class ResizeDemo {

  public static void main(String[] args) {
    if (args.length != 3) {
      StdOut.println("Usage:\njava ResizeDemo [image filename] [num cols to remove] [num rows to remove]");
      return;
    }

    Picture inputImg = new Picture(args[0]);
    int removeColumns = Integer.parseInt(args[1]);
    int removeRows = Integer.parseInt(args[2]);

    StdOut.printf("image is %d columns by %d rows\n", inputImg.width(), inputImg.height());
    SeamCarver sc = new SeamCarver(inputImg);

    Stopwatch sw = new Stopwatch();

    for (int i = 0; i < removeRows; i++) {
      Stopwatch swp = new Stopwatch();
      int[] horizontalSeam = sc.findHorizontalSeam();
      sc.removeHorizontalSeam(horizontalSeam);
      StdOut.printf("%d row from %d processed in %.2f secs\n", i + 1, removeRows, swp.elapsedTime());
    }

    for (int i = 0; i < removeColumns; i++) {
      Stopwatch swp = new Stopwatch();
      int[] verticalSeam = sc.findVerticalSeam();
      sc.removeVerticalSeam(verticalSeam);
      StdOut.printf("%d column from %d processed in %.2f secs\n", i + 1, removeColumns, swp.elapsedTime());
    }
    Picture outputImg = sc.picture();

    StdOut.printf("new image size is %d columns by %d rows\n", sc.width(), sc.height());

    StdOut.println("Resizing time: " + sw.elapsedTime() + " seconds.");
    inputImg.show();
    outputImg.show();
  }

}