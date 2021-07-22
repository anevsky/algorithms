package com.alexnevsky.algs;

import java.util.Arrays;

/**
 * @author Alex Nevsky
 *
 * Two Sum
 * @link https://leetcode.com/problems/two-sum/
 *
 * Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 * You can return the answer in any order.
 *
 * Date: 18/03/2021
 */
public class TwoSum {

  public static void main(String[] args) {
    System.out.printf("array i: %s", Arrays.toString(TwoSum.twoSum(
        new int[]{1, 3, 4, 7, 2, 5},
        5
    )));
  }

  public static int[] twoSum(int[] nums, int target) {
    int n = nums.length;
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        if (nums[i] + nums[j] == target) return new int[]{i, j};
      }
    }

    return new int[]{};
  }
}
