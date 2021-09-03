package com.alexnevsky.algs;

/**
 * @author Alex Nevsky
 *
 * Date: 27/08/2021
 */
public class MaxArraySum {

  public static void main(String[] args) {
    int[] arr = new int[]{3, 7, 4, 6, 5};
    System.out.printf("maxSubsetSum: %d%n", maxNotAdjSubsetSum(arr)); // 13
  }

  // Dynamic Programming
  public static int maxNotAdjSubsetSum(int[] arr) {
    if (arr.length == 0) return 0;
    arr[0] = Math.max(0, arr[0]);
    if (arr.length == 1) return arr[0];
    arr[1] = Math.max(arr[0], arr[1]);
    for (int i = 2; i < arr.length; ++i)
      arr[i] = Math.max(arr[i - 1], arr[i] + arr[i - 2]);
    return arr[arr.length - 1];
  }
}
