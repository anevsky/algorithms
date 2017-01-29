package com.alexnevsky.algorithms;

/**
 * Created by Alex Nevsky on 1/28/17.
 *
 * Find the decimal zip of two non-negative integers A and B. The zip is an integer C
 */
public class DecimalZipOfIntegers {

    public static int solution(int A, int B) {
        if (A < 0 || B < 0) {
            return -1;
        }

        if (A > 100000000 || B > 100000000) {
            return -1;
        }

        String zip = "";
        char[] aa = String.valueOf(A).toCharArray();
        char[] bb = String.valueOf(B).toCharArray();

        for (int i = 0; i < aa.length || i < bb.length; ++i) {
            if (i < aa.length) {
                zip += aa[i];
            }
            if (i < bb.length) {
                zip += bb[i];
            }
        }

        if (Long.parseLong(zip) > 100000000) {
            return -1;
        }

        return Integer.parseInt(zip);
    }
}
