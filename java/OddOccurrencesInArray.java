package com.alexnevsky.algorithms;

/**
 * Created by Alex Nevsky on 1/24/17.
 *
 * Arrays
 * Find value that occurs in odd number of elements.
 *
 * XOR of all elements gives us odd occurring element.
 * XOR of two elements is 0 if both elements are same.
 * XOR of a number x with 0 is x.
 *
 * Y ^ 0 = Y
 * X ^ X ^ Y = Y
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
