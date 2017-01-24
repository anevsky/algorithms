package com.alexnevsky.algorithms;

/**
 * Created by Alex Nevsky on 1/24/17.
 *
 * Arrays
 * Rotate an array to the right by a given number of steps.
 */
public class CyclicRotation {

    public static int[] solution(int[] A, int K) {
        int length = A.length;

        if (length == 0 || length == 1) {
            return A;
        }

        if (K > length) {
            while (K > length) {
                K -= length;
            }
        }

        int[] result = new int[A.length];
        for (int i = length - K, j = 0; i < length; ++i, ++j) {
            result[j] = A[i];
        }
        for (int i = 0; i < length - K; ++i) {
            result[K + i] = A[i];
        }

        return result;
    }
}
