package com.alexnevsky.algorithms;

/**
 * Created by Alex Nevsky on 1/24/17.
 *
 * Arrays
 * Find value that occurs in odd number of elements.
 */
public class OddOccurrencesInArray {

    public static int solution(int[] A) {
        if (A.length == 1) {
            return A[0];
        }

        int result = 0;
        for (int aA : A) {
            result ^= aA;
        }

        return result;
    }
}
