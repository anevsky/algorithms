package com.alexnevsky.algorithms;

import java.util.Arrays;

/**
 * Created by Alex Nevsky on 1/28/17.
 *
 * Find in alphanumeric input the longest substring with at least one upper letter without digits.
 */
public class LongestSubstring {

    /**
     *
     * Sliding Window Algorithm
     *
     * Incorrect realization and wrong result sometimes.
     *
     * @see http://stackoverflow.com/a/39388752/721525
     */
    @Deprecated
    public static int solution1(String S) {
        int result = -1;

        char[] chars = S.toCharArray();
        int length = chars.length;

        if (length == 1) {
           return Character.isUpperCase(chars[0]) ? 1 : -1;
        }

        int up = 0;
        int front = 0;
        int back = 0;

        int index = 0;
        while (front < length && index < length) {
            char c = chars[index];
            if (!Character.isDigit(c)) {
                if (front == -1) {
                    front = index;
                    back = front - 1;
                }
                front += 1;
                if (Character.isUpperCase(c)) {
                    up += 1;
                    if (up >= 1) {
                        result = Math.max(result, front - back);
                    }
                }
            } else {
                up = 0;
                front = -1;
                back = 0;
            }
            ++index;
        }

        return result;
    }

    public static int solution2(String S) {
        int result = -1;

        String[] splits = S.split("[0-9]");
        for (String s : splits) {
            for (char c : s.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    result = Math.max(result, s.length());
                    break;
                }
            }
        }

        return result;
    }

    public static int solution3(String S) {
        int result = -1;

        final int length = Arrays.stream(S.split("[0-9]+"))
                .filter(s -> s.matches("(.+)?[A-Z](.+)?"))
                .sorted((s1, s2) -> s2.length() - s1.length())
                .findFirst()
                .orElse("")
                .length();

        if (length != 0) {
            result = length;
        }

        return result;
    }
}
