package com.alexnevsky.algorithms;

/**
 * Created by Alex Nevsky on 1/19/17.
 *
 * Iterations
 * Find longest sequence of zeros in binary representation of an integer.
 */
public class BinaryGap {

    public static int solution(int N) {
        int max = 0;

        String binaryString = Integer.toBinaryString(N);
        int localMax = 0;
        for (int i = 0; i < binaryString.length(); ++i) {
            if (binaryString.codePointAt(i) == 48) {
                ++localMax;
            } else {
                if (max < localMax) {
                    max = localMax;
                }
                localMax = 0;
            }
        }

        return max;
    }
}
