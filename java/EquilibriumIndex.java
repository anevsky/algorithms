package com.alexnevsky.algorithms;

/**
 * Created by Alex Nevsky on 1/28/17.
 *
 * Equi
 * Find an index in an array such that its prefix sum equals its suffix sum.
 */
public class EquilibriumIndex {

    public static int solution(int[] A) {
        int result = -1;

        int length = A.length;
        if (length == 0) {
            return result;
        }

        long sumRight = 0;
        for (int aA : A) {
            sumRight += aA;
        }

        long sumLeft = 0;
        for (int i = 0; i < length; ++i) {
            long tempRight = sumRight - A[i];
            if (sumLeft == tempRight) {
                result = i;
                break;
            } else {
                sumLeft += A[i];
                sumRight = tempRight;
            }
        }

        return result;
    }
}
